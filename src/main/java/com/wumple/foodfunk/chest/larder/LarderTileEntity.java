package com.wumple.foodfunk.chest.larder;

import com.wumple.foodfunk.ModObjectHolder;

import com.wumple.foodfunk.util.SingleChestBlockEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class LarderTileEntity extends SingleChestBlockEntity
{
	public LarderTileEntity()
	{
		super(ModObjectHolder.LarderBlock_Tile,ModObjectHolder.larder_open,ModObjectHolder.larder_close);
	}
	
	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.foodfunk.larder");
	}

}
