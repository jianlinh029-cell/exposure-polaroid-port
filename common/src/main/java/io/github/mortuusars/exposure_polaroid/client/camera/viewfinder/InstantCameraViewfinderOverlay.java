package io.github.mortuusars.exposure_polaroid.client.camera.viewfinder;

import io.github.mortuusars.exposure.client.camera.viewfinder.Viewfinder;
import io.github.mortuusars.exposure.client.camera.viewfinder.ViewfinderOverlay;
import io.github.mortuusars.exposure.client.util.GuiUtil;
import io.github.mortuusars.exposure.world.camera.Camera;
import io.github.mortuusars.exposure_polaroid.ExposurePolaroid;
import io.github.mortuusars.exposure_polaroid.world.item.InstantCameraItem;
import io.github.mortuusars.exposure_polaroid.world.item.camera.InstantCameraAttachment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class InstantCameraViewfinderOverlay extends ViewfinderOverlay {
    public static final Identifier VIEWFINDER_TEXTURE = ExposurePolaroid.resource("textures/gui/viewfinder/viewfinder.png");
    public static final Identifier NO_SLIDES_ICON_TEXTURE = ExposurePolaroid.resource("textures/gui/viewfinder/no_slides.png");
    public static final Identifier REMAINING_SLIDES_ICON_TEXTURE = ExposurePolaroid.resource("textures/gui/viewfinder/remaining_slides.png");

    public InstantCameraViewfinderOverlay(Camera camera, Viewfinder viewfinder) {
        super(camera, viewfinder);
    }

    @Override
    protected void drawViewfinderTexture(GuiGraphicsExtractor guiGraphics) {
        GuiUtil.blitFull(guiGraphics, VIEWFINDER_TEXTURE, opening);
    }

    @Override
    protected void renderStatusIcons(GuiGraphicsExtractor guiGraphics, ItemStack cameraStack) {
        ItemStack slideStack = InstantCameraAttachment.INSTANT_SLIDE.get(cameraStack).getForReading();
        if (slideStack.isEmpty()) {
            renderNoSlidesIcon(guiGraphics);
            return;
        }

        renderRemainingSlidesIcon(guiGraphics, cameraStack);
    }

    protected void renderNoSlidesIcon(GuiGraphicsExtractor guiGraphics) {
        int x = (int) ((opening.x + (opening.width / 2) - 12));
        int y = (int) (opening.y + opening.height - 18);
        GuiUtil.blit(guiGraphics, NO_SLIDES_ICON_TEXTURE, x, y, 23, 18, 0, 0, 23, 18);
    }

    protected void renderRemainingSlidesIcon(GuiGraphicsExtractor guiGraphics, ItemStack cameraStack) {
        if (!(cameraStack.getItem() instanceof InstantCameraItem instantCamera)) return;

        int maxSlides = instantCamera.getMaxSlideCount();
        int remainingSlides = instantCamera.getRemainingSlides(cameraStack);

        if (maxSlides > 5 && remainingSlides <= 3) {
            float x = (int) (opening.x + (opening.width / 2) - 17);
            float y = (int) (opening.y + opening.height - 15);
            int vOffset = (remainingSlides - 1) * 15;
            GuiUtil.blit(guiGraphics, REMAINING_SLIDES_ICON_TEXTURE, x, y, 33, 15, 0, vOffset, 33, 45);
        }
    }
}
