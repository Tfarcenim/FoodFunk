package com.wumple.util.adapter;

import com.wumple.util.config.NameKeys;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ItemStackThing extends ItemStackThingBase implements IThing
{
    public ItemStackThing(ItemStack ownerIn)
    {
        super(ownerIn);
    }
    
    public ArrayList<String> getNameKeys()
    {
        return NameKeys.getNameKeys(get());
    }
}