package com.wumple.util.adapter;

import com.wumple.util.adapter.IThing;
import com.wumple.util.adapter.ThingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemStackThingBase extends ThingBase<ItemStack>
{
    public ItemStackThingBase(ItemStack ownerIn)
    {
        super(ownerIn);
    }
    
    @Override
    public World getWorld()
    {
        return null; // owner.getEntityWorld();
    }
    
    @Override
    public BlockPos getPos()
    {
        return null; // owner.getPosition();
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean sameAs(IThing entity)
    {
        if (entity instanceof com.wumple.util.adapter.ItemStackThingBase)
        {
            return get() == ((com.wumple.util.adapter.ItemStackThingBase) entity).get();
        }
        return false;
    }
    
    @Override
    public int getCount()
    {
        return isValid() ? get().getCount() : 0;
    }
    
    @Override
    public ICapabilityProvider capProvider()
    { return get(); }
    
    @Override
    public void forceUpdate()
    { 
    }
}