package com.wumple.foodfunk.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public abstract class SingleChestBlockEntity extends ChestTileEntity {

    private final SoundEvent open;
    private final SoundEvent close;

    public SingleChestBlockEntity(TileEntityType<?> type,SoundEvent open, SoundEvent close) {
        super(type);
        this.open = open;
        this.close = close;
    }


    @Override
    public void playSound(SoundEvent soundIn) {
        if (SoundEvents.BLOCK_CHEST_OPEN == soundIn) {
            playSound0(open);
        } else if (SoundEvents.BLOCK_CHEST_CLOSE == soundIn) {
            playSound0(close);
        }
    }

    public void playSound0(SoundEvent soundIn) {
        double d0 = this.pos.getX() + 0.5D;
        double d1 = this.pos.getY() + 0.5D;
        double d2 = this.pos.getZ() + 0.5D;
        this.world.playSound(null, d0, d1, d2, soundIn, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
        this.world.notifyNeighborsOfStateChange(this.pos, block);
    }
}
