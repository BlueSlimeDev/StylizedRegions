package me.blueslime.stylizedregions.modules.flags.list.block.explode.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.Optional;

public class BlockExplodeListener implements Listener {
    @EventHandler
    public void on(BlockExplodeEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canExplode = region.getFlag(
            "block-explode",
            true
        );

        if (canExplode) {
            return;
        }
        event.setCancelled(true);
    }
}
