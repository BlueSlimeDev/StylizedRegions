package me.blueslime.stylizedregions.modules.flags.list.block.physics.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import java.util.Optional;

public class BlockPhysicsListener implements Listener {
    @EventHandler
    public void on(BlockPhysicsEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canPhysics = region.getFlag(
            "block-physics",
            true
        );

        if (canPhysics) {
            return;
        }
        event.setCancelled(true);
    }
}
