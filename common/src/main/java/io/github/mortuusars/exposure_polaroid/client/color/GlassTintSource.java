package io.github.mortuusars.exposure_polaroid.client.color;

import com.mojang.serialization.MapCodec;
import io.github.mortuusars.exposure.world.item.camera.CameraItem;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * 26.1 replacement for the removed ColorProviderRegistry.ITEM: tints the instant camera's lens
 * glass (tintindex 1) dynamically based on shutter/filter state, reusing the original
 * CameraItem#getGlassTintColor logic.
 */
public record GlassTintSource() implements ItemTintSource {
    public static final MapCodec<GlassTintSource> MAP_CODEC = MapCodec.unit(new GlassTintSource());

    @Override
    public int calculate(ItemStack stack, ClientLevel level, LivingEntity entity) {
        return CameraItem.getGlassTintColor(stack, 1);
    }

    @Override
    public MapCodec<GlassTintSource> type() {
        return MAP_CODEC;
    }
}
