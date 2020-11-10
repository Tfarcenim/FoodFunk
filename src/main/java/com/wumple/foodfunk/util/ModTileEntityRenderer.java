package com.wumple.foodfunk.util;

import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;

public class ModTileEntityRenderer extends ChestTileEntityRenderer<ChestTileEntity> {

	private final Material material;

	public ModTileEntityRenderer(TileEntityRendererDispatcher p_i226008_1_, Material material) {
		super(p_i226008_1_);
		this.material = material;
	}

	protected Material getMaterial(ChestTileEntity tileEntity, ChestType chestType) {
		return material;
	}
}
