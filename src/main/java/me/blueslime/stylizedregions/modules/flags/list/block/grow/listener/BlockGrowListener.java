package me.blueslime.stylizedregions.modules.flags.list.block.grow.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

import java.util.Optional;

public class BlockGrowListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(BlockGrowEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canGrow = region.getFlag(
            "block-grow",
            true
        );

        if (canGrow) {
            return;
        }
        event.setCancelled(true);
    }
}
