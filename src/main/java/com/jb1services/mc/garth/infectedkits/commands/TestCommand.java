package com.jb1services.mc.garth.infectedkits.commands;

import com.chaoscrasher.commands.ChaosCommandExecutor;
import com.chaoscrasher.commands.arglen.ArgLenFour;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.World;

public class TestCommand extends ChaosCommandExecutor {

	public TestCommand()
	{
		ArgLenFour<World, Integer, Integer, Integer> cmd1 = new ArgLenFour<>(World.class, Integer.class, Integer.class, Integer.class, false, true).defineEffectABCD(alen -> 
		{
			Block block = (new Location(alen.getDataA().get(), alen.getDataB().get(), alen.getDataC().get(), alen.getDataD().get())).getBlock();
			sender.sendMessage("That block is of type " + block.getType());
		}).applyTo(this);
	}

	@Override
	public String getCommandShorthand()
	{
		return "tcom";
	}
}
