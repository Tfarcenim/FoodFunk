package com.wumple.foodfunk.chest.icebox;

import com.wumple.foodfunk.ModObjectHolder;
import com.wumple.foodfunk.util.SingleChestBlockEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class IceboxTileEntity extends SingleChestBlockEntity {

	public IceboxTileEntity() {
		super(ModObjectHolder.IceboxBlock_Tile,ModObjectHolder.icebox_open,ModObjectHolder.icebox_close);
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.foodfunk.icebox");
	}

}
