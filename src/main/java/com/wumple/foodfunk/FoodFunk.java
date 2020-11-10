package com.wumple.foodfunk;

import com.wumple.foodfunk.capability.preserving.Preserving;
import com.wumple.foodfunk.capability.rot.Rot;
import com.wumple.foodfunk.chest.esky.EskyBlock;
import com.wumple.foodfunk.chest.esky.EskyTileEntity;
import com.wumple.foodfunk.chest.freezer.FreezerBlock;
import com.wumple.foodfunk.chest.freezer.FreezerTileEntity;
import com.wumple.foodfunk.chest.icebox.IceboxBlock;
import com.wumple.foodfunk.chest.icebox.IceboxTileEntity;
import com.wumple.foodfunk.chest.larder.LarderBlock;
import com.wumple.foodfunk.chest.larder.LarderTileEntity;
import com.wumple.foodfunk.configuration.ModConfiguration;
import com.wumple.foodfunk.crafting.recipe.RotMergeRecipe;
import com.wumple.foodfunk.rotten.BiodegradableItem;
import com.wumple.foodfunk.rotten.RottedItem;
import com.wumple.foodfunk.rotten.RottenFoodItem;
import com.wumple.foodfunk.rotten.SpoiledMilkItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class FoodFunk {
    public static final SimpleChannel network = ModNetwork.getNetworkChannel();

    public static ModSetup setup = new ModSetup();

    public Logger getLogger() {
        return LogManager.getLogger(Reference.MOD_ID);
    }

    public FoodFunk() {
        ModConfiguration.register(ModLoadingContext.get());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(ClientProxy::stitch);
        }

        modEventBus.addGenericListener(Block.class,RegistryEvents::onBlocksRegistry);
        modEventBus.addGenericListener(Item.class,RegistryEvents::onItemsRegistry);
        modEventBus.addGenericListener(TileEntityType.class,RegistryEvents::onTileEntityRegistry);
        modEventBus.addGenericListener(SoundEvent.class,RegistryEvents::onSoundEventRegistry);
        modEventBus.addGenericListener(IRecipeSerializer.class,RegistryEvents::registerRecipeSerializers);

    }

    public void setup(final FMLCommonSetupEvent event) {
        Rot.register();
        Preserving.register();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientProxy.init(event);
    }


    //@SubscribeEvent
    public void onFingerprintViolation(final FMLFingerprintViolationEvent event) {
        getLogger().warn("Invalid fingerprint detected! The file " + event.getSource().getName()
                + " may have been tampered with. This version will NOT be supported by the author!");
        getLogger().warn("Expected " + event.getExpectedFingerprint() + " found " + event.getFingerprints().toString());
    }

    public static class RegistryEvents {
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new LarderBlock());
            event.getRegistry().register(new IceboxBlock());
            event.getRegistry().register(new EskyBlock());
            event.getRegistry().register(new FreezerBlock());
        }

        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);

            event.getRegistry().register(new RottenFoodItem().setRegistryName("rotten_food"));
            event.getRegistry().register(new SpoiledMilkItem().setRegistryName("spoiled_milk"));
            event.getRegistry().register(new RottedItem().setRegistryName("rotted_item"));
            event.getRegistry().register(new BiodegradableItem().setRegistryName("biodegradable_item"));

            event.getRegistry().register(new BlockItem(ModObjectHolder.LarderBlock, new Item.Properties().group(setup.itemGroup)).setRegistryName("larder"));
            event.getRegistry().register(new BlockItem(ModObjectHolder.IceboxBlock, new Item.Properties().group(setup.itemGroup)).setRegistryName("icebox"));
            event.getRegistry().register(new BlockItem(ModObjectHolder.EskyBlock, new Item.Properties().group(setup.itemGroup)).setRegistryName("esky"));
            event.getRegistry().register(new BlockItem(ModObjectHolder.FreezerBlock, new Item.Properties().group(setup.itemGroup)).setRegistryName("freezer"));
        }


        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(LarderTileEntity::new, ModObjectHolder.LarderBlock)
                    .build(null).setRegistryName("larder"));
            event.getRegistry().register(TileEntityType.Builder.create(IceboxTileEntity::new, ModObjectHolder.IceboxBlock)
                    .build(null).setRegistryName("icebox"));
            event.getRegistry().register(TileEntityType.Builder.create(EskyTileEntity::new, ModObjectHolder.EskyBlock)
                    .build(null).setRegistryName("esky"));
            event.getRegistry().register(TileEntityType.Builder.create(FreezerTileEntity::new, ModObjectHolder.FreezerBlock)
                    .build(null).setRegistryName("freezer"));

        }

        protected static SoundEvent registrationHelper(IForgeRegistry<SoundEvent> registry, String id) {
            SoundEvent soundEvent = new SoundEvent(new ResourceLocation(id)).setRegistryName(id);
            registry.register(soundEvent);
            return soundEvent;
        }

        public static void onSoundEventRegistry(final RegistryEvent.Register<SoundEvent> event) {
            ModObjectHolder.larder_open = registrationHelper(event.getRegistry(), "foodfunk:larder_open");
            ModObjectHolder.larder_close = registrationHelper(event.getRegistry(), "foodfunk:larder_close");
            ModObjectHolder.icebox_open = registrationHelper(event.getRegistry(), "foodfunk:icebox_open");
            ModObjectHolder.icebox_close = registrationHelper(event.getRegistry(), "foodfunk:icebox_close");
            ModObjectHolder.esky_open = registrationHelper(event.getRegistry(), "foodfunk:esky_open");
            ModObjectHolder.esky_close = registrationHelper(event.getRegistry(), "foodfunk:esky_close");
            ModObjectHolder.freezer_open = registrationHelper(event.getRegistry(), "foodfunk:freezer_open");
            ModObjectHolder.freezer_close = registrationHelper(event.getRegistry(), "foodfunk:freezer_close");
        }

        // --------------------------------------------------------------------
        // recipes

        public static RotMergeRecipe.Serializer<RotMergeRecipe> CRAFTING_SPECIAL_ROTMERGE;

        public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            CRAFTING_SPECIAL_ROTMERGE = new RotMergeRecipe.Serializer<RotMergeRecipe>(RotMergeRecipe::new);
            CRAFTING_SPECIAL_ROTMERGE.setRegistryName("rot_merge_crafting");
            event.getRegistry().registerAll(CRAFTING_SPECIAL_ROTMERGE);
        }

    }
}
