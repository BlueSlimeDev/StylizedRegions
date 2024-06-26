package me.blueslime.stylizedregions.modules.flags.list.block.form.listener;

import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Optional;

public class BlockFormListener implements Listener {
    @EventHandler
    public void on(BlockFormEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getBlock().getLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean canForm = region.getFlag(
            "block-form",
            true
        );

        if (canForm) {
            return;
        }
        event.setCancelled(true);
    }
}
