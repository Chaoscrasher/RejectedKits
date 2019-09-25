package com.jb1services.mc.garth.rejectedkits.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.chaoscrasher.events.ChaosEventListener;
import com.chaoscrasher.utils.StaticHelpers;
import com.jb1services.mc.garth.rejectedkits.main.InfectedKitsPlugin;
import com.jb1services.mc.garth.rejectedkits.structure.AgroSpider;
import com.jb1services.mc.garth.rejectedkits.structure.Cooldown;


public class RejectedKitsEvents extends ChaosEventListener implements StaticHelpers {

	private Map<Player, Map<String, Cooldown>> cooldowns = new HashMap<>();
	
	public RejectedKitsEvents(InfectedKitsPlugin plugin)
	{
		super(plugin);
	}
	
	public InfectedKitsPlugin getPlugin()
	{
		return (InfectedKitsPlugin) plugin;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e)
	{
		if (e.getDamager() instanceof Player)
		{
			Player plyr = (Player) e;
			if (isIngame(plyr))
			{
				if (plyr.getInventory().getItemInHand().equals(getPlugin().loadVampiresSword()))
				{
					vampireSwordOnHit(plyr);
				}
				else if (plyr.getInventory().getItemInHand().equals(getPlugin().loadSpiderSword()))
				{
					spiderSwordOnHit(plyr, e.getEntity());
				}
			}
		}
	}
	
	public void onInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if (isIngame(p))
			{
				if (p.getItemInHand().equals(getPlugin().loadEndermansPearl()))
				{
					Cooldown ecd = getEndermanCooldown(p);
					if (ecd.tryTrigger())
					{
						sheduleTeleport(p);
						p.sendMessage(CYW+"Triggered!");
					}
					else
					{
						p.sendMessage(CRD+"Cooldown...");
					}
					e.setCancelled(true);
				}
				else if (p.getItemInHand().equals(getPlugin().loadSpiderEye()))
				{
					p.setItemInHand(null);
					spiderEyeEffect(p);
					e.setCancelled(true);
				}
				else if (p.getItemInHand().equals(getPlugin().loadWitchPotionPower()))
				{
					p.setItemInHand(null);
					PotionEffect effa = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 1);
					PotionEffect effb = new PotionEffect(PotionEffectType.REGENERATION, 20*10, 1);
					PotionEffect effc = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*3600, 1);
					p.addPotionEffect(effa);
					p.addPotionEffect(effb);
					p.addPotionEffect(effc);
				}
				else if (p.getItemInHand().equals(getPlugin().loadWitchPotionConditioning()))
				{
					p.setItemInHand(null);
					PotionEffect effa = new PotionEffect(PotionEffectType.SPEED, 20*25, 1);
					PotionEffect effb = new PotionEffect(PotionEffectType.NIGHT_VISION, 20*25, 1);
					p.addPotionEffect(effa);
					p.addPotionEffect(effb);
				}
				else if (p.getItemInHand().equals(getPlugin().loadSpiderEye()))
				{
					CraftWorld world = (CraftWorld) p.getWorld();
					getPlugin().spawnCustomSpider(p.getLocation());
					p.setItemInHand(null);
				}
			}
		}
	}
	
	private Cooldown makeEndermanCooldown()
	{
		return new Cooldown("ender-pearl", 15000L);
	}
	
	public Cooldown getEndermanCooldown(Player plyr)
	{
		if (cooldowns.containsKey(plyr))
		{
			Map<String, Cooldown> map = cooldowns.get(plyr);
			if (map.containsKey("ender-pearl"))
			{
				return map.get("ender-pearl");
			}
			else
			{
				Cooldown cd = makeEndermanCooldown();
				map.put("ender-pearl", cd);
				return cd;
			}
		}
		else
		{
			Map<String, Cooldown> cds = new HashMap<>();
			Cooldown cd = makeEndermanCooldown();
			cds.put("ender-pearl", cd);
			cooldowns.put(plyr, cds);
			return cd;
		}
	}
	
	@EventHandler
	public void onPlayerKilled(PlayerDeathEvent e)
	{
		Player killed = e.getEntity();
		Player killer = killed.getKiller();
		if (killer != null)
		{
			if (isIngame(killer))
			{
				if (killer.getInventory().getItemInHand().equals(getPlugin().loadSlimesSword()))
				{
					slimeSwordOnKill(killer, killed);
				}
			}
		}
	}
	
	public void onPlayerPickUpItem(PlayerPickupItemEvent e)
	{
		Player p = e.getPlayer();
		if (isIngame(p))
		{
			if (e.getItem().getItemStack().equals(getPlugin().loadSlimesSword()))
			{
				PotionEffect pob = new PotionEffect(PotionEffectType.JUMP, 20*3600, 1);
				p.addPotionEffect(pob);
			}
		}
	}
	
	//EXT
	private boolean isIngame(Player p)
	{
		throw new IllegalStateException("Not yet implemented!");
	}
	
	//EXT
	private boolean isEnemy(Entity e)
	{
		return e instanceof HumanEntity;
	}
	
	private void spiderSwordOnHit(Player plyr, Entity eHit)
	{
		if (isEnemy(eHit))
		{
			HumanEntity he = (HumanEntity) eHit;
			Potion potion = new Potion(PotionType.POISON, 2);
			he.addPotionEffects(potion.getEffects());
		}
	}
	
	private void slimeSwordOnKill(Player slime, Player killed)
	{
		slime.setHealth(slime.getHealth()+3.0);
		PotionEffect pe = new PotionEffect(PotionEffectType.REGENERATION, 20*3, 1);
		for (PotionEffect pes : slime.getActivePotionEffects())
		{
			if (pes.getType().equals(PotionEffectType.JUMP) && pes.getAmplifier() < 4)
			{
				PotionEffect jpe = new PotionEffect(PotionEffectType.JUMP, 20*3600, pes.getAmplifier()+1);
				slime.addPotionEffect(jpe);
			}
		}
		slime.addPotionEffect(pe);
	}
	
	private void vampireSwordOnHit(Player plyr)
	{
		plyr.setHealth(plyr.getHealth()+0.5);
	}
	
	private void spiderEyeEffect(Player plyr)
	{
		plyr.getLocation().getWorld().spawnEntity(plyr.getLocation(), EntityType.SPIDER);
	}
	
	//EXT
	private Player chooseRandomPlayer()
	{
		return null;
	}
	
	public void sheduleTeleport(Player enderman)
	{
		Bukkit.getScheduler().runTaskLater(plugin, () -> enderman.teleport(chooseRandomPlayer()), 20*12);
	}
}
