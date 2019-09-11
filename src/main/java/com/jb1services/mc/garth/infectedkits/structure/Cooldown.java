package com.jb1services.mc.garth.infectedkits.structure;

public class Cooldown
{
	String id;
	Long lastTrigger;
	Long duration;
	
	public Cooldown(String id, Long duration)
	{
		super();
		this.id = id;
		this.duration = duration;
	}
	
	public boolean tryTrigger()
	{
		if (lastTrigger == null || System.currentTimeMillis() - lastTrigger > duration)
		{
			lastTrigger = System.currentTimeMillis();
			return true;
		}
		else
			return false;
	}
	
	public double getDurationSeconds()
	{
		return duration / 1000;
	}
	
	public double getLeftSeconds()
	{
		long left = duration - (System.currentTimeMillis() - lastTrigger);
		if (left <= 0)
			return 0.0;
		else
			return left / 1000;
	}
}
