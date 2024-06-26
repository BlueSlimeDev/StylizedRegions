package me.blueslime.stylizedregions.modules.flags.list.block.spread.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

import java.util.Optional;

public class BlockSpreadListener implements Listener {
    @EventHandler
    public void on(BlockSpreadEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canPhysics = region.getFlag(
            "block-spread",
            true
        );

        if (canPhysics) {
            return;
        }
        event.setCancelled(true);
    }
}
