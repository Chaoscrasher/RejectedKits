package com.jb1services.mc.garth.rejectedkits.main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor.EntityType;
import com.chaoscrasher.global.ChaosBukkit;
import com.jb1services.mc.garth.rejectedkits.commands.InfectedKitsCommands;
import com.jb1services.mc.garth.rejectedkits.events.RejectedKitsEvents;
import com.jb1services.mc.garth.rejectedkits.structure.AgroSpider;
import com.jb1services.mc.garth.rejectedkits.structure.Kit;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntitySpider;

public class InfectedKitsPlugin extends JavaPlugin {

	public static InfectedKitsPlugin instance;

	private List<Kit> kits;
	
	@Override
	public void onEnable()
	{
		instance = this;
		System.out.println("InfectedKits loaded!");
		new InfectedKitsCommands(this);
		new RejectedKitsEvents(this);
		this.saveDefaultConfig();
		instantiateKits();
	}

	@Override
	public void onDisable()
	{

	}
	
	public ItemStack loadWitchBlazeRod()
	{
		String base = "special-items.witch-blaze-rod.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		is = ChaosBukkit.applyName(is, base+"name");
		is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		return is;
	}
	
	public ItemStack loadWitchPotionConditioning()
	{
		String base = "special-items.witch-potion-conditioning.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		is = ChaosBukkit.applyName(is, base+"name");
		is = ChaosBukkit.applyEnchantmentGlow(is);
		return is;
	}
	
	public ItemStack loadWitchPotionPower()
	{
		String base = "special-items.witch-power-potion.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		is = ChaosBukkit.applyName(is, base+"name");
		is = ChaosBukkit.applyEnchantmentGlow(is);
		is.setAmount(5);
		return is;
	}
	
	public ItemStack loadSpiderSword()
	{
		String base = "special-items.spider-sword.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public ItemStack loadEndermansPearl()
	{
		String base = "special-items.ender-pearl.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public ItemStack loadVampiresSword()
	{
		String base = "special-items.vampire-sword.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public ItemStack loadSlimesSword()
	{
		String base = "special-items.slime-sword.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public ItemStack loadSlimeBoots()
	{
		String base = "special-items.slime-boots.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		is.addEnchantment(Enchantment.PROTECTION_FALL, 4);
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public ItemStack loadSpiderEye()
	{
		String base = "special-items.spider-eye.";
		ItemStack is = new ItemStack(Material.valueOf(getConfig().getString(base+"material")));
		return ChaosBukkit.applyName(is, base+"name");
	}
	
	public Spider spawnCustomSpider(Location location)
	{
		registerCustomSpider();
		Spider spider = AgroSpider.spawn(location);
		deRegisterCustomSpider();
		return spider;
	}
	
	private void registerCustomSpider()
	{
		registerEntity("Agro Spider", 52, EntitySpider.class, AgroSpider.class);
	}
	
	private void deRegisterCustomSpider()
	{
		registerEntity("Spider", 52, AgroSpider.class, EntitySpider.class);
	}
	
	public void loadKits()
	{
		ConfigurationSection csec = getConfig().getConfigurationSection("kits");
		for (String key : csec.getKeys(false))
		{
			kits.add((Kit) csec.get(key));
		}
	}
	
	public void saveKits()
	{
		for (Kit kit : kits)
		{
			getConfig().set("kits." + kit.getId(), kit);
		}
		saveConfig();
	}
	
	public void instantiateKits()
	{
		kits.add(new Kit("skeleton", "Skeleton Kit", 100,
				new ItemStack(Material.LEATHER_HELMET),
				new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS),
				new ItemStack(Material.LEATHER_BOOTS),
				new ItemStack(Material.ARROW))); 
		
		kits.add(new Kit("witch", "Witch Kit", 75,
				new ItemStack(Material.GOLD_HELMET),
				new ItemStack(Material.GOLD_CHESTPLATE),
				new ItemStack(Material.GOLD_LEGGINGS),
				new ItemStack(Material.GOLD_BOOTS),
				loadWitchBlazeRod(),
				loadWitchPotionConditioning(),
				loadWitchPotionPower())); 
		
		kits.add(new Kit("vampire", "Vampire Kit", 60,
				new ItemStack(Material.DIAMOND_HELMET),
				loadVampiresSword())); 
		
		kits.add(new Kit("enderman", "Enderman", 50,
				new ItemStack(Material.LEATHER_HELMET),
				new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS),
				new ItemStack(Material.LEATHER_BOOTS),
				new ItemStack(Material.IRON_SWORD),
				loadEndermansPearl()));
		
		kits.add(new Kit("slime", "slime-kit", 45,
				new ItemStack(Material.DIAMOND_HELMET),
				new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.CHAINMAIL_LEGGINGS),
				loadSlimeBoots()));
		
		kits.add(new Kit("spider", "spider-kit", 30,
				new ItemStack(Material.LEATHER_HELMET),
				new ItemStack(Material.IRON_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS),
				new ItemStack(Material.DIAMOND_BOOTS),
				loadSpiderSword()));
	}
	
	public void onEndGame(Player p)
	{
		p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
		p.removePotionEffect(PotionEffectType.JUMP);
	}
	
	public Optional<Kit> getKit(String kitId)
	{
		Object o = getConfig().get("kits."+kitId);
		if (o != null)
			return Optional.of((Kit) o);
		return Optional.empty();
	}
	
	public void sheduleApplyPotionEffects()
	{
		Bukkit.getScheduler().runTaskTimer(this, () ->
		{
			ItemStack is = loadSlimesSword();
			for (Player plyr : Bukkit.getOnlinePlayers())
			{
				if (plyr.getItemInHand().equals(is))
				{
					PotionEffect pob = new PotionEffect(PotionEffectType.JUMP, 20*3600, 1);
					boolean did = false;
					for (PotionEffect pe : plyr.getActivePotionEffects())
					{
						if (pe.getType().equals(PotionEffectType.JUMP))
							did = true;
					}
					if (!did)
						plyr.addPotionEffect(pob);
				}
			}
		}, 20*2, 20*2);
	}
	
	public static void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass)
	{
		try
		{
			List<Map<?, ?>> dataMap = new ArrayList<>();
			for (Field f : EntityType.class.getDeclaredFields())
			{
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName()))
				{
					f.setAccessible(true);
					dataMap.add((Map<?,?>) f.get(null)); 
				}
			}
			
			if (dataMap.get(2).containsKey(id))
			{
				dataMap.get(0).remove(name);
				dataMap.get(2).remove(id);
			}
			
			Method method = EntityType.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, customClass, name, id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
