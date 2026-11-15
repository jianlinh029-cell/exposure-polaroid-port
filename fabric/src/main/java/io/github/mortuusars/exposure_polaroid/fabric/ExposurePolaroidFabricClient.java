package io.github.mortuusars.exposure_polaroid.fabric;

import io.github.mortuusars.exposure_polaroid.ExposurePolaroid;
import io.github.mortuusars.exposure_polaroid.ExposurePolaroidClient;
import io.github.mortuusars.exposure_polaroid.client.color.GlassTintSource;
import io.github.mortuusars.exposure_polaroid.client.render.model.InstantCameraItemModel;
import io.github.mortuusars.exposure_polaroid.network.fabric.FabricS2CPacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.color.item.ItemTintSources;

public class ExposurePolaroidFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ExposurePolaroidClient.init();

        // 26.1: replaces the removed ColorProviderRegistry.ITEM — register a custom ItemTintSource that
        // dynamically tints the instant camera's lens glass (tintindex 1) based on shutter/filter state.
        ItemTintSources.ID_MAPPER.put(ExposurePolaroid.resource("glass_tint"), GlassTintSource.MAP_CODEC);

        // 26.1: assets/exposure_polaroid/items/instant_camera.json declares a minecraft:composite whose
        // children, in a fixed order, are every instant-camera sub-model. Before baking we wrap that
        // composite: each child is baked by the vanilla context, then the baked list is handed to
        // InstantCameraItemModel to select from at render time (replacing the old overrides + ItemRendererMixin).
        ModelLoadingPlugin.register(pluginContext ->
                pluginContext.modifyItemModelBeforeBake().register((unbaked, context) -> {
                    if (context.itemId().equals(ExposurePolaroid.resource("instant_camera"))
                            && unbaked instanceof net.minecraft.client.renderer.item.CompositeModel.Unbaked composite) {
                        java.util.List<net.minecraft.client.renderer.item.ItemModel.Unbaked> children = composite.models();
                        return new net.minecraft.client.renderer.item.ItemModel.Unbaked() {
                            @Override
                            public void resolveDependencies(net.minecraft.client.resources.model.ResolvableModel.Resolver resolver) {
                                composite.resolveDependencies(resolver);
                            }

                            @Override
                            public net.minecraft.client.renderer.item.ItemModel bake(
                                    net.minecraft.client.renderer.item.ItemModel.BakingContext bakingContext, org.joml.Matrix4fc rootTransform) {
                                java.util.List<net.minecraft.client.renderer.item.ItemModel> baked =
                                        new java.util.ArrayList<>(children.size());
                                for (net.minecraft.client.renderer.item.ItemModel.Unbaked child : children) {
                                    baked.add(child.bake(bakingContext, rootTransform));
                                }
                                return new InstantCameraItemModel(baked);
                            }

                            @Override
                            public com.mojang.serialization.MapCodec<? extends net.minecraft.client.renderer.item.ItemModel.Unbaked> type() {
                                return net.minecraft.client.renderer.item.CompositeModel.Unbaked.MAP_CODEC;
                            }
                        };
                    }
                    return unbaked;
                }));

        FabricS2CPacketHandler.register();
    }
}
