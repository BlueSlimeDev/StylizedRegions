package me.blueslime.stylizedregions.modules.flags.list.block.place.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(BlockPlaceEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canPlace = region.getFlag(
            "block-place",
            false
        );

        if (!canPlace) {
            canPlace = region.isTrusted(event.getPlayer()) || region.isOwner(event.getPlayer());
        }

        if (canPlace) {
            return;
        }

        event.setCancelled(true);
    }

}
