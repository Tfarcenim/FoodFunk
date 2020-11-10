package com.wumple.util.adapter;

import com.wumple.util.config.NameKeys;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileEntityThing extends TileEntityThingBase implements IThing
{
    public TileEntityThing(TileEntity ownerIn)
    {
        super(ownerIn);
    }
    
    public ArrayList<String> getNameKeys()
    {
        return NameKeys.getNameKeys(get());
    }
}
