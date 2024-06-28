package me.blueslime.stylizedregions.modules.flags.list.player.respawn.listener;

import me.blueslime.bukkitmeteor.utils.WorldLocation;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.entities.EntitiesTools;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Optional;

public class PlayerRespawnListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getRespawnLocation());

        if (!regionOptional.isPresent()) {
            return;
        }

        Player player = event.getPlayer();

        if (EntitiesTools.isNPC(player)) {
            return;
        }

        Region region = regionOptional.get();

        WorldLocation worldLocation = region.getFlag(
            "spawn-location",
            null,
            () -> {
                boolean contains = region.getConfiguration().contains("flags.spawn-location");
                if (!contains) {
                    return null;
                }
                return WorldLocation.fromConfiguration(region.getConfiguration(), "flags.spawn-location");
            }
        );

        Location location;

        if (worldLocation == null) {
            location = event.getRespawnLocation();
        } else {
            location = worldLocation.toLocation();
        }

        // WorldLocation toLocation can return null if that location is in an unloaded world
        if (location == null) {
            location = event.getRespawnLocation();
        }

        event.setRespawnLocation(location);
    }

}
