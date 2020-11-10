package com.wumple.foodfunk.chest.freezer;

import com.wumple.foodfunk.ModObjectHolder;
import com.wumple.foodfunk.util.SingleChestBlockEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FreezerTileEntity extends SingleChestBlockEntity {
	public FreezerTileEntity()
	{
		super(ModObjectHolder.FreezerBlock_Tile,ModObjectHolder.freezer_open,ModObjectHolder.freezer_close);
	}
	
	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.foodfunk.freezer");
	}

}
