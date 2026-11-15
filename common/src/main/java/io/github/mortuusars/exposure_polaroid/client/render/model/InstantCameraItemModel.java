package io.github.mortuusars.exposure_polaroid.client.render.model;

import io.github.mortuusars.exposure.client.util.Minecrft;
import io.github.mortuusars.exposure.world.camera.ExposureType;
import io.github.mortuusars.exposure.world.entity.CameraHolder;
import io.github.mortuusars.exposure.world.entity.EntityRepresentationHolder;
import io.github.mortuusars.exposure.world.item.SensitiveFilmItem;
import io.github.mortuusars.exposure.world.item.camera.CameraItem;
import io.github.mortuusars.exposure_polaroid.world.item.camera.InstantCameraAttachment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 26.1 replacement for the old BakedModel-based instant-camera model + ItemRendererMixin.
 * Selects the correct sub-model (base/active/bw/color/selfie/print/gui) depending on the
 * camera state (active / selfie-stick / selfie), the loaded slide type (b&w / color) and the
 * printing cooldown, mirroring the old {@code overrides} predicate combination.
 *
 * <p>The sub-models are baked once by the Fabric {@code modifyItemModelBeforeBake} hook from the
 * {@code minecraft:composite} declared in {@code assets/exposure_polaroid/items/instant_camera.json};
 * their order is fixed and must match the {@code PART_*} indices below.
 */
public class InstantCameraItemModel implements ItemModel {

    // Order MUST match the models array in assets/exposure_polaroid/items/instant_camera.json
    public static final int PART_BASE = 0;
    public static final int PART_ACTIVE = 1;
    public static final int PART_BW = 2;
    public static final int PART_BW_PRINT1 = 3;
    public static final int PART_BW_PRINT2 = 4;
    public static final int PART_BW_PRINT3 = 5;
    public static final int PART_COLOR = 6;
    public static final int PART_ACTIVE_BW = 7;
    public static final int PART_ACTIVE_BW_PRINT1 = 8;
    public static final int PART_ACTIVE_BW_PRINT2 = 9;
    public static final int PART_ACTIVE_BW_PRINT3 = 10;
    public static final int PART_ACTIVE_COLOR = 11;
    public static final int PART_COLOR_PRINT1 = 12;
    public static final int PART_COLOR_PRINT2 = 13;
    public static final int PART_COLOR_PRINT3 = 14;
    public static final int PART_ACTIVE_COLOR_PRINT1 = 15;
    public static final int PART_ACTIVE_COLOR_PRINT2 = 16;
    public static final int PART_ACTIVE_COLOR_PRINT3 = 17;
    public static final int PART_SELFIE_STICK = 18;
    public static final int PART_SELFIE = 19;
    public static final int PART_SELFIE_BW = 20;
    public static final int PART_SELFIE_COLOR = 21;
    public static final int PART_GUI = 22;
    public static final int PART_GUI_BW = 23;
    public static final int PART_GUI_COLOR = 24;
    public static final int PART_COUNT = 25;

    private final List<ItemModel> parts;

    public InstantCameraItemModel(List<ItemModel> parts) {
        if (parts.size() != PART_COUNT) {
            throw new IllegalArgumentException("Expected " + PART_COUNT + " baked instant-camera sub-models, got " + parts.size());
        }
        this.parts = parts;
    }

    private ItemModel part(int index) {
        return parts.get(index);
    }

    @Override
    public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext,
                       @Nullable ClientLevel level, @Nullable ItemOwner itemOwner, int seed) {
        if (!(stack.getItem() instanceof CameraItem camera)) {
            part(PART_BASE).update(state, stack, resolver, displayContext, level, itemOwner, seed);
            return;
        }

        boolean inGui = displayContext == ItemDisplayContext.GUI;

        if (inGui) {
            int slideType = getSlideType(stack);
            int part = slideType == 1 ? PART_GUI_COLOR : (slideType == 2 ? PART_GUI_BW : PART_GUI);
            part(part).update(state, stack, resolver, displayContext, level, itemOwner, seed);
            return;
        }

        int part = selectPart(stack, camera, itemOwner);
        part(part).update(state, stack, resolver, displayContext, level, itemOwner, seed);
    }

    /**
     * Replicates the old {@code overrides} predicate matching (camera_state / slide_type / printing),
     * keeping the effective combinations. The active+slide_type combination overrides from upstream
     * were shadowed by the plain active override, so they are intentionally not used here.
     */
    private int selectPart(ItemStack stack, CameraItem camera, @Nullable ItemOwner itemOwner) {
        float cameraState = getCameraState(stack, camera, itemOwner);
        int slideType = getSlideType(stack);
        float printing = getPrinting(stack, camera, itemOwner);

        if (cameraState == 0.1f) return PART_ACTIVE;
        if (cameraState == 0.2f) return PART_SELFIE_STICK;
        if (cameraState == 0.3f) {
            if (slideType == 2) return PART_SELFIE_BW;
            if (slideType == 1) return PART_SELFIE_COLOR;
            return PART_SELFIE;
        }

        if (slideType == 2) {
            if (printing >= 0.7f) return PART_BW_PRINT3;
            if (printing >= 0.4f) return PART_BW_PRINT2;
            if (printing >= 0.1f) return PART_BW_PRINT1;
            return PART_BW;
        }

        if (slideType == 1) {
            if (printing >= 0.7f) return PART_COLOR_PRINT3;
            if (printing >= 0.4f) return PART_COLOR_PRINT2;
            if (printing >= 0.1f) return PART_COLOR_PRINT1;
            return PART_COLOR;
        }

        return PART_BASE;
    }

    private float getCameraState(ItemStack stack, CameraItem camera, @Nullable ItemOwner itemOwner) {
        if (!camera.isActive(stack)) return 0f;

        if (camera.isInSelfieMode(stack)) {
            boolean ownSelfieView = itemOwner instanceof LivingEntity entity
                    && entity.equals(Minecrft.get().getCameraEntity());
            return ownSelfieView ? 0.2f : 0.3f;
        }

        return 0.1f;
    }

    /** 0 = no slide, 1 = color, 2 = black & white. */
    private int getSlideType(ItemStack stack) {
        if (InstantCameraAttachment.INSTANT_SLIDE.get(stack).getItem() instanceof SensitiveFilmItem film) {
            return film.getType() == ExposureType.COLOR ? 1 : 2;
        }
        return 0;
    }

    private float getPrinting(ItemStack stack, CameraItem camera, @Nullable ItemOwner itemOwner) {
        Entity holderEntity = itemOwner instanceof Entity entity
                ? entity
                : ((EntityRepresentationHolder) (Object) stack).exposure$getEntityRepresentation();
        if (holderEntity instanceof CameraHolder holder) {
            return camera.isOnCooldown(holder, stack) ? 1f - camera.getCooldownPercent(holder, stack) : 0f;
        }
        return 0f;
    }
}
