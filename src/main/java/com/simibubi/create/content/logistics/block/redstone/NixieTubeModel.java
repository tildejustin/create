package com.simibubi.create.content.logistics.block.redstone;

import io.github.fabricators_of_create.porting_lib.model.CompositeModel;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class NixieTubeModel extends ForwardingBakedModel {
	private static final RenderMaterial SOLID_MATERIAL = RendererAccess.INSTANCE.getRenderer().materialFinder().blendMode(0, BlendMode.SOLID).find();
	private static final RenderMaterial TRANSLUCENT_MATERIAL = RendererAccess.INSTANCE.getRenderer().materialFinder().blendMode(0, BlendMode.TRANSLUCENT).find();

	public CompositeModel.Baked baked;

	public NixieTubeModel(BakedModel baked) {
		this.wrapped = baked;
		this.baked = (CompositeModel.Baked) baked;
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
		context.pushTransform(quad -> {
			quad.material(SOLID_MATERIAL);
			return true;
		});
		((FabricBakedModel)baked.getPart("connectors")).emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
		context.pushTransform(quad -> {
			quad.material(TRANSLUCENT_MATERIAL);
			return true;
		});
		((FabricBakedModel)baked.getPart("tubes")).emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
		context.pushTransform(quad -> {
			quad.material(SOLID_MATERIAL);
			return true;
		});
		((FabricBakedModel)baked.getPart("connectors")).emitItemQuads(stack, randomSupplier, context);
		context.popTransform();
		context.pushTransform(quad -> {
			quad.material(TRANSLUCENT_MATERIAL);
			return true;
		});
		((FabricBakedModel)baked.getPart("tubes")).emitItemQuads(stack, randomSupplier, context);
		context.popTransform();
	}
}