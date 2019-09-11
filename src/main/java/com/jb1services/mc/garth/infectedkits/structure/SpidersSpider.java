package com.jb1services.mc.garth.infectedkits.structure;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.World;

public class SpidersSpider extends EntitySpider
{
    public SpidersSpider(World world)
	{
    	super(world);
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 16, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 16));
	}

	int task;
 
    public void setTarget(Player player)
    {
        this.setTarget(player);
    }
 
    
    /*
    @Override
    protected EntityHuman findTarget()
    {
        try
        {
            double d0 = 16.0D;
            return this.world.findNearbyPlayer(this, d0);
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
    */
}