package me.blueslime.stylizedregions.modules.flags.list.entity.hanging.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Optional;

public class HangingBreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getEntity().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Entity removerEntity = event.getRemover();
        Hanging hanging = event.getEntity();
        Region region = regionOptional.get();

        if (removerEntity instanceof Projectile) {
            Projectile projectile = (Projectile) removerEntity;
            ProjectileSource remover = projectile.getShooter();
            removerEntity = (remover instanceof LivingEntity ? (LivingEntity) remover : null);
        }

        if (!(removerEntity instanceof Player)) {
            boolean entityPaintingDestroy = region.getFlag(
                "entity-painting-destroy",
                false
            );
            boolean entityItemFrameDestroy = region.getFlag(
                "entity-item-frame-destroy",
                false
            );

            if (hanging instanceof Painting) {
                if (entityPaintingDestroy) {
                    return;
                }
                event.setCancelled(true);
                return;
            }
            if (hanging instanceof ItemFrame) {
                if (entityItemFrameDestroy) {
                    return;
                }
                event.setCancelled(true);
            }
        }
    }
}
