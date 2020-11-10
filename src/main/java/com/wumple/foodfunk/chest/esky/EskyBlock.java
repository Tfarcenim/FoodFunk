package com.wumple.foodfunk.chest.esky;

import com.wumple.foodfunk.ModObjectHolder;
import com.wumple.foodfunk.util.SingleChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class EskyBlock extends SingleChestBlock<EskyTileEntity> {
    public static final String ID = "foodfunk:esky";

    public EskyBlock() {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD), () -> ModObjectHolder.EskyBlock_Tile);
        setRegistryName(ID);
    }
}
