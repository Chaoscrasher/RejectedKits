package com.jb1services.mc.garth.infectedkits.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.chaoscrasher.global.ChaosBukkit;

public class Kit implements ConfigurationSerializable
{
	
			
	private String name;
	private List<ItemStack> items;

	public Kit(String name, ItemStack... items)
	{
		this(name, Arrays.asList(items));
	}
	
	public Kit(String name, List<ItemStack> items)
	{
		super();
		this.items = items;
	}

	@Override
	public Map<String, Object> serialize()
	{
		
	}
}
