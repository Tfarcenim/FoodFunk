package com.wumple.foodfunk;

import com.wumple.foodfunk.util.ModTileEntityRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.client.renderer.Atlases.CHEST_ATLAS;

public class ClientProxy {

    public static ResourceLocation ESKY = new ResourceLocation(Reference.MOD_ID, "entity/esky");
    public static Material ESKY_MATERIAL = new Material(CHEST_ATLAS, ESKY);

    public static ResourceLocation FREEZER = new ResourceLocation(Reference.MOD_ID, "entity/freezer");
    public static Material FREEZER_MATERIAL = new Material(CHEST_ATLAS, FREEZER);

    public static ResourceLocation ICEBOX = new ResourceLocation(Reference.MOD_ID, "entity/icebox");
    public static Material ICEBOX_MATERIAL = new Material(CHEST_ATLAS, ICEBOX);

    public static ResourceLocation LARDER = new ResourceLocation(Reference.MOD_ID, "entity/larder");
    public static Material LARDER_MATERIAL = new Material(CHEST_ATLAS, LARDER);

    public static void init(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModObjectHolder.LarderBlock_Tile, dispatcher -> new ModTileEntityRenderer(dispatcher,LARDER_MATERIAL));
        ClientRegistry.bindTileEntityRenderer(ModObjectHolder.IceboxBlock_Tile, dispatcher -> new ModTileEntityRenderer(dispatcher,ICEBOX_MATERIAL));
        ClientRegistry.bindTileEntityRenderer(ModObjectHolder.EskyBlock_Tile, dispatcher -> new ModTileEntityRenderer(dispatcher,ESKY_MATERIAL));
        ClientRegistry.bindTileEntityRenderer(ModObjectHolder.FreezerBlock_Tile, dispatcher -> new ModTileEntityRenderer(dispatcher,FREEZER_MATERIAL));

        //ScreenManager.registerFactory(ModObjectHolder.LarderBlock_Container, LarderScreen::new);
        //ScreenManager.registerFactory(ModObjectHolder.IceboxBlock_Container, IceboxScreen::new);
    }

    public static void stitch(TextureStitchEvent.Pre e) {
        if (e.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            e.addSprite(ICEBOX);
            e.addSprite(ESKY);
            e.addSprite(FREEZER);
            e.addSprite(LARDER);
        }
    }
}