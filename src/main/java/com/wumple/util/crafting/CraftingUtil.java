package com.wumple.util.crafting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class CraftingUtil
{

    public static boolean isItemBeingCraftedBy(ItemStack stack, Entity entity)
    {
        boolean beingCrafted = false;

        PlayerEntity player = (PlayerEntity) (entity);
        if (player != null)
        {
            if (player.openContainer != null)
            {
                Slot slot = player.openContainer.getSlot(0);
                if (slot instanceof CraftingResultSlot && slot.getStack() == stack)
                {
                    beingCrafted = true;
                }
            }
        }

        return beingCrafted;
    }
    
    // adapted from http://www.minecraftforge.net/forum/topic/22927-player-based-crafting-recipes/
    public static Field eventHandlerField = ObfuscationReflectionHelper.findField(CraftingInventory.class, "field_70465_c"); // "eventHandler"
    public static Field PlayerContainerPlayerField = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_82862_h"); // "player"
    public static Field slotCraftingPlayerField = ObfuscationReflectionHelper.findField(CraftingResultSlot.class, "field_75238_b"); // "player"
	
    public static PlayerEntity findPlayer(CraftingInventory inv)
    {
        try
        {
            Container container = (Container) eventHandlerField.get(inv);
            if (container instanceof PlayerContainer)
            {
                return (PlayerEntity) PlayerContainerPlayerField.get(container);
            }
            else if (container instanceof WorkbenchContainer)
            {
                return (PlayerEntity) slotCraftingPlayerField.get(container.getSlot(0));
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            //throw e;
            return null;
        }
    }
    
    public static World findWorld(CraftingInventory inv)
    {
        PlayerEntity player = findPlayer(inv);
        return (player != null) ? player.getEntityWorld() : null;
    }
}
