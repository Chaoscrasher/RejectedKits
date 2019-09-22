package com.jb1services.mc.garth.rejectedkits.structure;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.World;

public class AgroSpider extends EntitySpider 
{
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public AgroSpider(World world) 
    {
        super(world);
        setSize(1.4F, 0.9F);
       
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
        //this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false)); // Creeper melee attack
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true)); // Creeper attack nearest target selector
    }
    
    public static Spider spawn(Location loc)
    {
    	World mcWorld = (World) ((CraftWorld) loc.getWorld()).getHandle();
    	final AgroSpider customEntity = new AgroSpider(mcWorld);
    
    	customEntity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    	((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
    	mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
    	return (Spider) customEntity.getBukkitEntity();
    }
}