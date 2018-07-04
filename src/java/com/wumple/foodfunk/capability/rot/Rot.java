package com.wumple.foodfunk.capability.rot;

import java.util.List;
import java.util.Random;

import com.wumple.foodfunk.Reference;
import com.wumple.foodfunk.capability.ContainerListenerRot;
import com.wumple.foodfunk.capability.preserving.IPreserving;
import com.wumple.foodfunk.capability.preserving.PreservingHelper;
import com.wumple.foodfunk.configuration.ConfigContainer;
import com.wumple.foodfunk.configuration.ConfigHandler;
import com.wumple.misc.ContainerUseTracker;
import com.wumple.misc.ContainerUtil;
import com.wumple.misc.CraftingUtil;

import choonster.capability.CapabilityContainerListenerManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.oredict.OreDictionary;

public class Rot implements IRot
{
    // The {@link Capability} instance
    @CapabilityInject(IRot.class)
    public static final Capability<IRot> CAPABILITY = null;
    public static final EnumFacing DEFAULT_FACING = null;

    // IDs of the capability
    public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "rot");
    
    // MAYBE re-init on WorldEvent.Load instead of waiting for first world tick to do so via handler
    protected static long lastWorldTimestamp = 0;
    protected static Random random = new Random();

    protected RotInfo info = new RotInfo();
    ItemStack owner = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IRot.class, new RotStorage(), () -> new Rot());

        CapabilityContainerListenerManager.registerListenerFactory(ContainerListenerRot::new);
    }

    public Rot()
    {

    }

    public Rot(Rot other)
    {
        info = other.info;
    }

    @Override
    public long getDate()
    {
        return info.date;
    }

    @Override
    public long getTime()
    {
        return info.time;
    }

    @Override
    public void setDate(long dateIn)
    {
        info.date = dateIn;
    }

    @Override
    public void setTime(long timeIn)
    {
        info.time = timeIn;
    }

    @Override
    public void setRot(long dateIn, long timeIn)
    {
        info.date = dateIn;
        info.time = timeIn;
    }

    @Override
    public void reschedule(long timeIn)
    {
        info.date += timeIn;
    }

    public RotInfo setInfo(RotInfo infoIn)
    {
        info = infoIn;
        return info;
    }

    public RotInfo getInfo()
    {
        return info;
    }

    /*
     * Set the owner of this capability, and init based on that owner
     */
    public void setOwner(ItemStack ownerIn)
    {
        if (ownerIn != owner)
        {
            owner = ownerIn;
            setDefaults(owner);
            info.date = lastWorldTimestamp;
        }
    }

    public ItemStack getOwner()
    {
        return owner;
    }

    // ----------------------------------------------------------------------
    // Functionality

    public static void setLastWorldTimestamp(long timestamp)
    {
        lastWorldTimestamp = timestamp;
    }

    public static long getLastWorldTimestamp()
    {
        return lastWorldTimestamp;
    }

    /*
     * Evaluate this rot, which belongs to stack
     */
    public ItemStack evaluateRot(World world, ItemStack stack)
    {
        // TODO integrate with Serene Seasons temperature system
        // If stack in a cold/frozen location, change rot appropriately (as if esky or
        // freezer)
        // Might allow building a walk-in freezer like in RimWorld

        long rotTime = getTime();

        long UBD = getDate();
        long worldTime = world.getTotalWorldTime();

        // initialization was missed somehow - so fix it
        if (UBD == 0)
        {
            // previous calculation:
            // UBD = (worldTime/ConfigHandler.TICKS_PER_DAY) * ConfigHandler.TICKS_PER_DAY;
            // UBD = UBD <= 0L? 1L : UBD
            UBD = worldTime;
            setRot(UBD, rotTime);
        }

        long rotTimeStamp = UBD + rotTime;

        if (worldTime >= rotTimeStamp)
        {
            ConfigHandler.Rotting.RotProperty rotProps = ConfigHandler.Rotting.getRotProperty(stack);
            // forget owner to eliminate dependency
            owner = null;
            return forceRot(stack, rotProps);
        }

        // this shouldn't be needed - but if rotProps.rotTime changes from config
        // change, this will update it
        setRot(UBD, rotTime);

        return stack;
    }
    
    /*
     * Build tooltip info based on this rot
     */
    public void doTooltip(ItemStack stack, EntityPlayer entity, boolean advanced, List<String> tips)
    {
        if (ConfigContainer.enabled && (stack != null) && !stack.isEmpty() && (entity != null))
        {
            if (info != null)
            {
                // preserving container state aka fake temperature - ambient, chilled, cold, frozen
                if (info.isSet())
                {
                    if (entity.openContainer != null)
                    {
                        TileEntity tecontainer = ContainerUseTracker.getUsedOpenContainer(entity, stack);
                        IPreserving cap = PreservingHelper.getPreserving(tecontainer);
                        if (cap != null)
                        {
                            int ratio = cap.getRatio();
                            String key = getTemperatureTooltipKey(ratio);                            
                            tips.add(new TextComponentTranslation(key, ratio).getUnformattedText());
                        }
                    }
                }

                // Rot state
                boolean beingCrafted = CraftingUtil.isItemBeingCraftedBy(stack, entity);
                String key = getRotStateTooltipKey(beingCrafted);

                if (key != null)
                {
                    tips.add(new TextComponentTranslation(key, info.getPercent() + "%", info.getDaysLeft(),
                            info.getDaysTotal()).getUnformattedText());
                }                

                //  advanced tooltip debug info
                if (advanced && ConfigContainer.zdebugging.debug)
                {
                    tips.add(new TextComponentTranslation("misc.foodfunk.tooltip.advanced.datetime", info.getDate(),
                            info.getTime()).getUnformattedText());
                    tips.add(new TextComponentTranslation("misc.foodfunk.tooltip.advanced.expire", info.getCurTime(),
                            info.getExpirationTimestamp()).getUnformattedText());
                }
            }
        }
    }

    /*
     * Set rot on crafted items dependent on the ingredients
     */
    public void handleCraftedRot(World world, IInventory craftMatrix, ItemStack crafting)
    {
        // TODO remove if can get data from this instead
        ConfigHandler.Rotting.RotProperty rotProps = ConfigHandler.Rotting.getRotProperty(crafting);
        long worldTime = world.getTotalWorldTime();
        long rotTime = rotProps.getRotTime();
        // end TODO

        long lowestDate = worldTime;

        int slots = craftMatrix.getSizeInventory();
        for (int i = 0; i < slots; i++)
        {
            ItemStack stack = craftMatrix.getStackInSlot(i);

            if (stack == null || stack.isEmpty() || stack.getItem() == null)
            {
                continue;
            }

            IRot cap = RotCapHelper.getRot(stack);

            if ((cap != null) && (cap.getDate() < lowestDate))
            {
                lowestDate = cap.getDate();
            }
        }

        setRot(lowestDate, rotTime);
    }

    // ----------------------------------------------------------------------
    // Internal

    protected static ItemStack forceRot(ItemStack stack, ConfigHandler.Rotting.RotProperty rot)
    {
        if (rot.rotID.isEmpty())
        {
            return ItemStack.EMPTY;
        }

        Item item = Item.REGISTRY.getObject(new ResourceLocation(rot.rotID));

        if (item == null)
        {
            NonNullList<ItemStack> ores = OreDictionary.getOres(rot.rotID);
            if (!ores.isEmpty())
            {
                ItemStack choice = ores.get(random.nextInt(ores.size()));
                return choice.copy();
            }
        }

        if (item == null)
        {
            return ItemStack.EMPTY;
        }

        return (rot.rotMeta == null) ? new ItemStack(item, stack.getCount())
                : new ItemStack(item, stack.getCount(), rot.rotMeta.intValue());
    }

    protected void setDefaults(ItemStack stack)
    {
        ConfigHandler.Rotting.RotProperty rotProps = ConfigHandler.Rotting.getRotProperty(stack);

        if ((rotProps != null) && rotProps.doesRot())
        {
            setTime(rotProps.getRotTime());
        }
    }

    protected static String getTemperatureTooltipKey(final int ratio)
    {
        String key = null;
        
        if (ratio <= 0)
        {
            key = "misc.foodfunk.tooltip.state.cold0";
        }
        else if ((ratio > 0) && (ratio < 50))
        {
            key = "misc.foodfunk.tooltip.state.cold1";
        }
        else if ((ratio >= 50) && (ratio < 100))
        {
            key = "misc.foodfunk.tooltip.state.cold2";
        }
        else if (ratio >= 100)
        {
            key = "misc.foodfunk.tooltip.state.cold3";
        }
        
        return key;
    }
    
    protected String getRotStateTooltipKey(boolean beingCrafted)
    {
        String key = null;
        
        if (info.isSet() && !beingCrafted)
        {
            if (info.getPercent() >= 100)
            {
                key = "misc.foodfunk.tooltip.decaying";
            }
            else
            {
                key = "misc.foodfunk.tooltip.rot";
            }
        }
        else if (info.isNoRot())
        {
            key = "misc.foodfunk.tooltip.preserved";
        }
        else if (info.time > 0)
        {
            key = "misc.foodfunk.tooltip.fresh";
        }

        return key;
    }
    
    // ----------------------------------------------------------------------
    // Possible deprecated

    public static void removeDeprecatedRotData(ItemStack stack)
    {
        NBTTagCompound tags = stack.getTagCompound();

        if (tags != null)
        {
            if (tags.hasKey("EM_ROT_DATE"))
            {
                tags.removeTag("EM_ROT_DATE");
            }
            if (tags.hasKey("EM_ROT_TIME"))
            {
                tags.removeTag("EM_ROT_TIME");
            }

            // remove empty NBT tag compound from rotten items so they can be merged - saw this bug once
            if (tags.hasNoTags())
            {
                stack.setTagCompound(null);
            }
        }
    }

    // ----------------------------------------------------------------------
    // Possible future

    /*
     * public static boolean isInTheCold(ItemStack stack) { // TODO walk up container tree, if any is cold chest then true. If none, get world pos of topmost container. // TODO
     * Check isOnItemFrame // TODO if temperature mod, get temp from it // TODO ToughAsNails:TemperatureHelper.getTargetAtPosUnclamped() ?
     * https://github.com/Glitchfiend/ToughAsNails // TODO if no temperature mod, check biome for temp?
     * 
     * return false; }
     */
}
