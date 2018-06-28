package com.wumple.foodfunk.capabilities.rot;

import javax.annotation.Nullable;

import com.wumple.foodfunk.Reference;

import choonster.capability.SimpleCapabilityProvider;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class RotProvider extends SimpleCapabilityProvider<IRot> 
{ 
	// The {@link Capability} instance
	@CapabilityInject(IRot.class) 
	public static final Capability<IRot> CAPABILITY = null;
	public static final EnumFacing DEFAULT_FACING = null;
	
	// IDs of the capability
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "rot");
	
	public RotProvider(Capability<IRot> capability, @Nullable EnumFacing facing) {
		super(capability, facing, capability.getDefaultInstance());
	}

	public RotProvider(Capability<IRot> capability, @Nullable EnumFacing facing, IRot instance) {
		super(capability, facing, instance);
	}
}