package com.wumple.util;

import com.wumple.util.ModConfiguration;
import com.wumple.util.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Mod(Reference.MOD_ID)
public class WumpleUtil
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public WumpleUtil()
    {
        ModConfiguration.setupConfig(ModLoadingContext.get());
        
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
	}

	public void setup(final FMLCommonSetupEvent event)
	{
    }

    /*@SubscribeEvent
    public void onFingerprintViolation(final FMLFingerprintViolationEvent event)
    {
    	LOGGER.warn("Invalid fingerprint detected! The file " + event.getSource().getName()
    			+ " may have been tampered with. This version will NOT be supported by the author!");
    	LOGGER.warn("Expected " + event.getExpectedFingerprint() + " found " + event.getFingerprints().toString());
    }*/
}
