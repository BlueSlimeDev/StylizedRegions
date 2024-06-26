package me.blueslime.stylizedregions.modules.flags.list.block.leaves.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

import java.util.Optional;

public class BlockLeavesDecayListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(LeavesDecayEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canGrow = region.getFlag(
            "leaves-decay",
            true
        );

        if (canGrow) {
            return;
        }
        event.setCancelled(true);
    }
}
