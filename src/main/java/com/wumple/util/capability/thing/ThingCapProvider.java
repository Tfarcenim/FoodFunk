package com.wumple.util.capability.thing;

import com.wumple.util.adapter.IThing;
import com.wumple.util.capability.listener.SerializableCapabilityProvider;
import com.wumple.util.capability.thing.IThingCap;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class ThingCapProvider<I extends IThing, W extends IThingCap<I>> extends SerializableCapabilityProvider<W>
{
	protected I owner = null;

	public ThingCapProvider(Capability<W> capability, @Nullable Direction facing, I ownerIn)
	{
		super(capability, facing, capability.getDefaultInstance());
		owner = ownerIn;
	}

	public ThingCapProvider(Capability<W> capability, @Nullable Direction facing, W instance, I ownerIn)
	{
		super(capability, facing, instance);
		owner = ownerIn;
		instance.checkInit(owner);
	}

	@Override
	public W getInstance()
	{
		instance.checkInit(owner);
		return instance;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capabilityIn, Direction facing)
	{
		// super.getCapability() takes care of (capabilityIn == capability)
		LazyOptional<W> lo = super.getCapability(capability, facing);
		lo.ifPresent(xcap -> xcap.checkInit(owner));

		return lo.cast();
	}
}