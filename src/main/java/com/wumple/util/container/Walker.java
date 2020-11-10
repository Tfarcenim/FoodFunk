package com.wumple.util.container;

import com.wumple.util.base.function.TriConsumer;
import com.wumple.util.capability.CapabilityUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Walker
{

    public static void walkContainer(ICapabilityProvider provider, TriConsumer<Integer, IItemHandler, ItemStack> block)
    {
        LazyOptional<IItemHandler> capability = CapabilityUtils.fetchCapability(provider, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        capability.ifPresent((cap)-> walkContainer(cap, block));
    }

    public static void walkContainer(IItemHandler inventory, TriConsumer<Integer, IItemHandler, ItemStack> block)
    {
        int slots = (inventory == null) ? 0 : inventory.getSlots();
        if (slots <= 0)
        {
            return;
        }

        for (int index = 0; index < slots; index++)
        {
            ItemStack slotItem = inventory.getStackInSlot(index);

            if (!slotItem.isEmpty())
            {
                walkContainer(slotItem, block);
                block.accept(index, inventory, slotItem);
            }
        }
    }

    public static void walkContainer(Container inventory, TriConsumer<Integer, Container, ItemStack> block)
    {
        int count = (inventory == null) || (inventory.inventorySlots == null) ? 0 : inventory.inventorySlots.size();

        for (int i = 0; i < count; i++)
        {
            Slot slot = inventory.getSlot(i);
            ItemStack slotItem = slot.getStack();

            if (!slotItem.isEmpty())
            {
                block.accept(i, inventory, slotItem);
            }
        }
    }

    public static void walkContainer(IInventory inventory, TriConsumer<Integer, IInventory, ItemStack> block)
    {
        int slots = (inventory == null) ? 0 : inventory.getSizeInventory();

        for (int index = 0; index < slots; index++)
        {
            ItemStack slotItem = inventory.getStackInSlot(index);

            if (!slotItem.isEmpty())
            {
                block.accept(index, inventory, slotItem);
            }
        }
    }
}
