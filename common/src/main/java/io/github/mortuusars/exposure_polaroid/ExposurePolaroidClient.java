package io.github.mortuusars.exposure_polaroid;

import io.github.mortuusars.exposure.client.animation.CameraModelPoses;
import io.github.mortuusars.exposure.client.camera.viewfinder.ViewfinderRegistry;
import io.github.mortuusars.exposure.client.capture.template.CameraCaptureTemplate;
import io.github.mortuusars.exposure.client.capture.template.CaptureTemplates;
import io.github.mortuusars.exposure_polaroid.client.animation.InstantCameraPoses;
import io.github.mortuusars.exposure_polaroid.client.camera.viewfinder.InstantCameraViewfinder;
import io.github.mortuusars.exposure_polaroid.world.item.InstantCameraItem;

public class ExposurePolaroidClient {
    public static void init() {
        CameraModelPoses.register(ExposurePolaroid.Items.INSTANT_CAMERA.get(), new InstantCameraPoses());

        ViewfinderRegistry.register(ExposurePolaroid.Items.INSTANT_CAMERA.get(), InstantCameraViewfinder::new);

        CaptureTemplates.register(InstantCameraItem.CAPTURE_TYPE, new CameraCaptureTemplate());
    }
}
