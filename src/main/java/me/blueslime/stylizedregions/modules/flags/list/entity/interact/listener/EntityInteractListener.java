package me.blueslime.stylizedregions.modules.flags.list.entity.interact.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

import java.util.Locale;
import java.util.Optional;

public class EntityInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean farmland = region.getFlag(
            "crop-tramp",
            true
        );

        boolean turtle = region.getFlag(
            "turtle-trampling",
            true
        );

        boolean sniffer = region.getFlag(
            "sniffer-trampling",
            true
        );

        String material = event.getBlock().getType().toString().toLowerCase(Locale.ENGLISH);

        if (material.equals("farmland") && !farmland) {
            event.setCancelled(true);
            return;
        }

        if (material.equals("turtle_egg") && !turtle) {
            event.setCancelled(true);
            return;
        }

        if (material.equals("sniffer_egg") && !sniffer) {
            event.setCancelled(true);
        }
    }
}
