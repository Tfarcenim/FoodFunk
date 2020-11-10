package com.wumple.util.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigUtil
{
	// ------------------------------------------------------------------------
	// copy values from a Config object into a Map for actual usage
	
	public static <V> void handlePairConfig(Config config, Map<String, V> map)
	{
		config.entrySet().forEach((e) -> {
			map.put(e.getKey(), e.getValue());
		});
	}

	// ------------------------------------------------------------------------
	// Search a map containing overridable config-like values

	// ------------------------------------------------------------------------

	/// shortcut to build a config value for a key/value set
	public static ForgeConfigSpec.ConfigValue<Config> buildSet(ForgeConfigSpec.Builder builder, String name, String comment)
	{
		// Example from https://github.com/gigaherz/Survivalist/blob/1.14/src/main/java/gigaherz/survivalist/ConfigManager.java#L131

		return builder
				.comment(comment)
				.define(Collections.singletonList(name),
						() -> Config.of(TomlFormat.instance()),
						x -> true,
						Config.class);
	}

	/// make r process config set c, clear the mirrored map, and read c into the mirrored map
	public static void handleConfigSet(Config c, Consumer<Config> r, Map<String,?> map)
	{
		r.accept(c);
		map.clear();
		handlePairConfig(c, map);
	}
}
