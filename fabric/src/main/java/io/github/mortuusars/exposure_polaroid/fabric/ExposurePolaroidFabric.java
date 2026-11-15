package io.github.mortuusars.exposure_polaroid.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import io.github.mortuusars.exposure.Exposure;
import io.github.mortuusars.exposure_polaroid.Config;
import io.github.mortuusars.exposure_polaroid.ExposurePolaroid;
import io.github.mortuusars.exposure_polaroid.network.fabric.FabricC2SPackets;
import io.github.mortuusars.exposure_polaroid.network.fabric.FabricS2CPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.config.ModConfig;

public class ExposurePolaroidFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExposurePolaroid.init();

        ConfigRegistry.INSTANCE.register(ExposurePolaroid.ID, ModConfig.Type.SERVER, Config.Server.SPEC);
        ConfigRegistry.INSTANCE.register(ExposurePolaroid.ID, ModConfig.Type.CLIENT, Config.Client.SPEC);

        ResourceKey<CreativeModeTab> exposureTabKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Exposure.resource("exposure"));

        CreativeModeTabEvents.modifyOutputEvent(exposureTabKey).register(output -> {
            output.accept(ExposurePolaroid.Items.INSTANT_CAMERA.get());
            output.accept(ExposurePolaroid.Items.INSTANT_BLACK_AND_WHITE_SLIDE.get());
            output.accept(ExposurePolaroid.Items.INSTANT_COLOR_SLIDE.get());
            output.accept(ExposurePolaroid.Items.HIGH_SENSITIVITY_INSTANT_BLACK_AND_WHITE_SLIDE.get());
            output.accept(ExposurePolaroid.Items.HIGH_SENSITIVITY_INSTANT_COLOR_SLIDE.get());
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
        });

        ExposurePolaroid.Stats.register();

        FabricC2SPackets.register();
        FabricS2CPackets.register();
    }
}
