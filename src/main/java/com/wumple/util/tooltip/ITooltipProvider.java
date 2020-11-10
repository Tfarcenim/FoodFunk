package com.wumple.util.tooltip;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface ITooltipProvider
{
    void doTooltip(ItemStack stack, PlayerEntity entity, boolean advanced, List<ITextComponent> tips);
    default void doTooltipAddon(ItemStack stack, PlayerEntity entity, boolean advanced, List<ITextComponent> tips)
        { doTooltip(stack, entity, advanced, tips); }
}
