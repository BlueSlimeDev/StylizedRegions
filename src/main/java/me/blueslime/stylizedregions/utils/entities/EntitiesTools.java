package me.blueslime.stylizedregions.utils.entities;

import org.bukkit.entity.*;

public class EntitiesTools {

    public static boolean isHostile(Entity entity) {
        return entity instanceof Monster
                || entity instanceof Slime
                || entity instanceof Flying
                || entity instanceof EnderDragon
                || entity instanceof Shulker;
    }

    public static boolean isNonHostile(Entity entity) {
        return !isHostile(entity) && entity instanceof Creature;
    }

    public static boolean isAmbient(Entity entity) {
        return entity instanceof Ambient;
    }

    public static boolean isNPC(Entity entity) {
        return entity instanceof NPC || entity.hasMetadata("NPC");
    }

    public static boolean isCreature(Entity entity) {
        return entity instanceof LivingEntity && !(entity instanceof Player);
    }
}
