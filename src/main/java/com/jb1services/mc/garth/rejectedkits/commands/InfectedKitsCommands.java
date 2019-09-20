package com.jb1services.mc.garth.rejectedkits.commands;

import com.chaoscrasher.commands.ChaosCommandExecutor;
import com.chaoscrasher.commands.arglen.ArgLenFour;
import com.chaoscrasher.commands.arglen.ArgLenTwo;
import com.jb1services.mc.garth.rejectedkits.main.InfectedKitsPlugin;
import com.jb1services.mc.garth.rejectedkits.structure.Kit;

import net.md_5.bungee.api.ChatColor;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;

public class InfectedKitsCommands extends ChaosCommandExecutor {

	private InfectedKitsPlugin plugin;
	
	public InfectedKitsCommands(InfectedKitsPlugin plugin)
	{
		this.plugin = plugin;
		ArgLenTwo<Boolean, String> cmd1 = new ArgLenTwo<>(Boolean.class, String.class, true, false, a0 -> a0.equalsIgnoreCase("select")).defineEffectAB(alen -> 
		{
			Optional<Kit> kito = plugin.getKit(alen.getDataBNonOptional());
			if (kito.isPresent())
			{
				Kit kit = kito.get();
				if (canPlayerAfford(player, kit))
				{
					buyPlayerKit(player, kit);
					sendGreen("You bought kit '" + CWT + kit.getIngameName() + CGN + "'!");
				}
				else
					sendRed("Sorry, but you don't have enough coin to buy that kit. Required: " + CWT + kit.getPrice());
			}
			else
				sendRed("No such kit found '"+CWT+alen.getDataBNonOptional()+CRD+"'!");
		}).applyTo(this);
	}

	private boolean canPlayerAfford(Player player, Kit k)
	{
		throw new IllegalStateException("Not yet implemented!");
	}
	
	public boolean buyPlayerKit(Player p, Kit k)
	{
		throw new IllegalStateException("Not yet implemented!");
	}
	
	@Override
	public String getCommandShorthand()
	{
		return "ikits";
	}
}
