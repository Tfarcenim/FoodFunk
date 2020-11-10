package com.wumple.util.config;

import com.wumple.util.base.misc.Util;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class DualMatchingConfig<T, U>
{
    public final MatchingConfig<T> config1;
    public final MatchingConfig<U> config2;

    public DualMatchingConfig(T falseValue1In, U falseValue2In)
    {
        config1 = new MatchingConfig<T>(new HashMap<>(), falseValue1In);
        config2 = new MatchingConfig<U>(new HashMap<>(), falseValue2In);
    }

    public SimpleMatchingConfig<T> get1()
    {
    	return config1;
    }
    
    public SimpleMatchingConfig<T> get2()
    {
    	return config1;
    }
    
    public void clear()
    {
    	config1.clear();
    	config2.clear();
    }

    // ----------------------------------------------------------------------
    // Add default properties to config

    // --- add by String

    public boolean addDefaultProperty(String name, T amount1In, U amount2In)
    {
        return
                config1.addDefaultProperty(name, amount1In) &&
                config2.addDefaultProperty(name, amount2In);
    }

    // --- add by Item

    public boolean addDefaultProperty(Item item, String backup, T amount1In, U amount2In)
    {
        return
                config1.addDefaultProperty(item, backup, amount1In) &&
                config2.addDefaultProperty(item, backup, amount2In);
    }

    // ----------------------------------------------------------------------
    // Get value for different types

    public Pair<T, U> getProperty(String name)
    {
        return Pair.of(config1.getProperty(name), config2.getProperty(name));
    }
}