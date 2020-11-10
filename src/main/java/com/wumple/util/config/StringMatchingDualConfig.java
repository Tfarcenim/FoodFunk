package com.wumple.util.config;

import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class StringMatchingDualConfig<U> extends DualMatchingConfig<String, U>
{
	public StringMatchingDualConfig(String falseValue1In, U falseValue2In)
	{
		super(falseValue1In, falseValue2In);
	}

	public boolean addDefaultProperty(String name, @Nullable Item amount1In, U amount2In)
    {
        return addDefaultProperty(name, amount1In.getRegistryName().toString(), amount2In);
    }
    
    public boolean addDefaultProperty(Item item, String backup, @Nullable Item amount1In, U amount2In)
    {
          return addDefaultProperty(item, backup, amount1In.getRegistryName().toString(), amount2In);
    }
}
