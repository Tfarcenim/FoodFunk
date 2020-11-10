package com.wumple.foodfunk.chest.esky;

import com.wumple.foodfunk.ModObjectHolder;

import com.wumple.foodfunk.util.SingleChestBlockEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EskyTileEntity extends SingleChestBlockEntity
{
	public EskyTileEntity()
	{
		super(ModObjectHolder.EskyBlock_Tile,ModObjectHolder.esky_open,ModObjectHolder.esky_close);
	}
	
	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.foodfunk.esky");
	}

}
