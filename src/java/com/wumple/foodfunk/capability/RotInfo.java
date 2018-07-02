package com.wumple.foodfunk.capability;

import com.wumple.foodfunk.capabilities.rot.IRot;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Wrapper class used to encapsulate information about an IRot.
 */
public final class RotInfo
{
	/*
	 *  The timestamp at which the stack is considered at 0% rot - creation time at first, but advanced by preserving containers
	 */
	public long date;
	/*
	 * The amount of time the item takes to rot.   The time at which becomes rotten is date + time
	 */
    public long time;
    
    public RotInfo()
    {
    }

    public RotInfo(long date, long time)
    {
        this.date = date;
        this.time = time;
    }

    public RotInfo(IRot tank)
    {
        this.date = tank.getDate();
        this.time = tank.getTime();
    }
    
    public RotInfo(RotInfo other)
    {
    	this.date = other.date;
    	this.time = other.time;
    }
    
    public RotInfo(NBTTagCompound tags)
    {
    	this.readFromNBT(tags);
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound tags)
    {
		tags.setLong("rotStartTimestamp", this.date);
		tags.setLong("rotLengthTime", this.time);
		
		return tags;
    }
    
    public NBTTagCompound readFromNBT(NBTTagCompound tags)
    {
		this.date = tags.getLong("rotStartTimestamp");
		this.time = tags.getLong("rotLengthTime");
		
		return tags;
    }
}