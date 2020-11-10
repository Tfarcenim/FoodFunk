package com.wumple.util.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class LeafUtil
{
    public static boolean isLeaves(Block block, BlockState blockstate, IWorld world, BlockPos pos)
    {
    	return BlockTags.LEAVES.contains(block) || (block instanceof LeavesBlock);
    }

    public static boolean isLeaves(IWorld world, BlockPos pos)
    {
        BlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
            
        return isLeaves(block, blockstate, world, pos);
    }

}
