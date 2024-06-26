package me.blueslime.stylizedregions.modules.flags.list.block.ignite.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

import java.util.Optional;

public class BlockIgniteListener implements Listener {

    @EventHandler
    public void on(BlockIgniteEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canIgnite = region.getFlag(
            "block-ignite",
            false
        );

        if (!canIgnite && event.getPlayer() != null) {
            canIgnite = region.isTrusted(event.getPlayer()) || region.isOwner(event.getPlayer());
        }

        if (canIgnite) {
            return;
        }

        event.setCancelled(true);
    }

}