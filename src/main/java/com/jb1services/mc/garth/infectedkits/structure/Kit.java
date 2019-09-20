package com.jb1services.mc.garth.infectedkits.structure;

import java.util.Arrays;
import java.util.HashMap;
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
	private String id;
	private String ingameName;
	private double price;
	private List<ItemStack> items;

	public Kit(Map<String, Object> map)
	{
		id = (String) map.get("id");
		ingameName = (String) map.get("ingameName");
		price = (double) map.get("price");
		items = (List<ItemStack>) map.get("items");
	}
	
	public Kit(String id, String ingameName, double price, ItemStack... items)
	{
		this(id, ingameName, price, Arrays.asList(items));
	}
	
	public Kit(String id, String ingameName, double price, List<ItemStack> items)
	{
		super();
		this.id = id;
		this.ingameName = ingameName;
		this.price = price;
		this.items = items;
	}

	public String getId()
	{
		return id;
	}

	public String getIngameName()
	{
		return ingameName;
	}

	public double getPrice()
	{
		return price;
	}

	public List<ItemStack> getItems()
	{
		return items;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("ingameName", ingameName);
		map.put("price", price);
		map.put("items", items);
		return map;
	}
}
