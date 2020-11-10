package com.wumple.foodfunk.chest.larder;

import com.wumple.foodfunk.ModObjectHolder;
import com.wumple.foodfunk.util.SingleChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class LarderBlock extends SingleChestBlock<LarderTileEntity>
{
	public static final String ID = "foodfunk:larder";

	public LarderBlock()
	{
		super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD), () -> ModObjectHolder.LarderBlock_Tile);
		
		setRegistryName(ID);
	}

	/*
	public LarderBlock(Block.Properties properties)
	{
		super(properties);
		
		setRegistryName(ID);
	}
	*/
}
