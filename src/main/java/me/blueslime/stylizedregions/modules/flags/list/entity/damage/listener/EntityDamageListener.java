package me.blueslime.stylizedregions.modules.flags.list.entity.damage.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.entities.EntitiesTools;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Optional;

public class EntityDamageListener implements Listener {
    private void handleByBlock(EntityDamageByBlockEvent event, Region region) {
        Entity victim = event.getEntity();
        EntityDamageEvent.DamageCause type = event.getCause();

        if (victim instanceof Wolf && ((Wolf) victim).isTamed()) {
            boolean wolfDumbness = region.getFlag(
                "wolf-dumbness",
                true
            );

            if (!wolfDumbness) {
                return;
            }

            if (type != EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);
            }
            return;
        }

        boolean blockExplosion = region.getFlag(
            "other-explosion",
            true
        );

        if (
            type == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION &&
            !blockExplosion
        ) {
            event.setCancelled(true);
        }
    }

    private void handleByEntity(EntityDamageByEntityEvent event, Region region) {
        if (event.getDamager() instanceof Projectile) {
            handleByProjectile(event, region);
            return;
        }

        Entity attacker = event.getDamager();
        Entity victim = event.getEntity();

        if (victim instanceof ItemFrame) {
            boolean itemFrameDestroy = region.getFlag(
                "item-frame-destroy",
                false
            );
            // Bukkit for a strange reason, throws this event when a player attempts to remove an item from a frame
            // This is weird, but I'm also checking this too xD
            if (!(attacker instanceof Player)) {
                if (itemFrameDestroy) {
                    return;
                }
                event.setCancelled(true);
                return;
            }
        }

        if (attacker instanceof EnderCrystal) {
            boolean blockExplosion = region.getFlag(
                "other-explosion",
                true
            );
            if (blockExplosion) {
                event.setCancelled(true);
                return;
            }
        }

        if (victim instanceof Player && !EntitiesTools.isNPC(victim)) {
            if (attacker instanceof LivingEntity && !(attacker instanceof Player)) {
                boolean mobDamage = region.getFlag(
                    "mob-damage",
                    false
                );
                if (mobDamage || attacker instanceof Tameable) {
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

    private void handleByProjectile(EntityDamageByEntityEvent event, Region region) {
        Entity victim = event.getEntity();
        Entity attacker;
        ProjectileSource source = ((Projectile) event.getDamager()).getShooter();

        if (source instanceof LivingEntity) {
            attacker = (LivingEntity) source;
        } else {
            return;
        }

        if (victim instanceof Player && !EntitiesTools.isNPC(victim)) {
            if (!(attacker instanceof Player)) {
                boolean mobDamage = region.getFlag(
                    "mob-damage",
                    false
                );
                if (!mobDamage) {
                    event.setCancelled(true);
                    return;
                }
                if (event.getDamager() instanceof Fireball) {
                    boolean ghastFireball = region.getFlag(
                        "ghast-fireball",
                        false
                    );
                    if (ghastFireball) {
                        return;
                    }
                    event.setCancelled(true);
                    return;
                }
            }
            return;
        }
        if (victim instanceof ItemFrame) {
            boolean itemFrameDestroy = region.getFlag(
                "item-frame-destroy",
                false
            );
            if (!(attacker instanceof Player)) {
                if (itemFrameDestroy) {
                    return;
                }
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(EntityDamageEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getEntity().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        if (event instanceof EntityDamageByEntityEvent) {
            this.handleByEntity((EntityDamageByEntityEvent) event, region);
            return;
        } else if (event instanceof EntityDamageByBlockEvent) {
            this.handleByBlock((EntityDamageByBlockEvent) event, region);
            return;
        }

        Entity victim = event.getEntity();
        EntityDamageEvent.DamageCause type = event.getCause();

        if (victim instanceof Wolf && ((Wolf) victim).isTamed()) {
            boolean wolfDumbness = region.getFlag(
                "wolf-dumbness",
                true
            );
            if (!wolfDumbness) {
                return;
            }
            event.setCancelled(true);
            return;
        }
        if (victim instanceof Player && !EntitiesTools.isNPC(victim)) {
            Player player = (Player) victim;

            if (type == EntityDamageEvent.DamageCause.WITHER) {
                boolean mobDamage = region.getFlag(
                    "mob-damage",
                    false
                );
                if (mobDamage) {
                    return;
                }
                event.setCancelled(true);
                return;
            }

            if (type == EntityDamageEvent.DamageCause.DROWNING) {
                boolean waterBreathing = region.getFlag(
                "water-breathing",
                    false
                );
                if (waterBreathing) {
                    player.setRemainingAir(player.getMaximumAir());
                    event.setCancelled(true);
                }
            }
        }
    }

}
