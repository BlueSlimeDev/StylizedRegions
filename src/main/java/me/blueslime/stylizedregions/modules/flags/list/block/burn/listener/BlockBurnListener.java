package me.blueslime.stylizedregions.modules.flags.list.block.burn.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import java.util.Optional;

public class BlockBurnListener implements Listener {

    @EventHandler
    public void on(BlockBurnEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canBurn = region.getFlag(
            "block-burn",
            false
        );

        if (canBurn) {
            return;
        }
        event.setCancelled(true);
    }

}