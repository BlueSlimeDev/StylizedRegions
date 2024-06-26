package me.blueslime.stylizedregions.modules.flags.list.block.breaks.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(BlockBreakEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canBreak = region.getFlag(
            "block-break",
            false
        );

        if (!canBreak) {
            canBreak = region.isTrusted(event.getPlayer()) || region.isOwner(event.getPlayer());
        }

        if (canBreak) {
            return;
        }

        event.setCancelled(true);
    }

}