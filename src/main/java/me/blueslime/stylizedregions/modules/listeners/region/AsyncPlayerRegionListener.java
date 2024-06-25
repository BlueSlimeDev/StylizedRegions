package me.blueslime.stylizedregions.modules.listeners.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.api.events.RegionJoinEvent;
import me.blueslime.stylizedregions.api.events.RegionQuitEvent;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.service.region.RegionService;
import me.blueslime.stylizedregions.service.tasks.event.AsyncRegionTickEvent;
import me.blueslime.stylizedregions.service.users.UserService;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncPlayerRegionListener implements Listener {

    @EventHandler
    public void on(AsyncRegionTickEvent event) {
        UserService service = Implements.fetch(UserService.class);
        for (RegionUser user : service.getUsers()) {
            fetchRegion(user);
        }
    }

    private void fetchRegion(RegionUser user) {
        RegionService service = Implements.fetch(RegionService.class);
        Location location = user.getLocation();
        if (location == null || location.getWorld() == null) {
            return;
        }
        for (Region region : service.getRegionList(location.getWorld().getName())) {
            boolean isIn = region.getRegion().isIn(location);

            if (!isIn && user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion("");
                callEventSync(
                    new RegionQuitEvent(region, user.getPlayer())
                );
                continue;
            }

            if (isIn && !user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion(region.getId());
                callEventSync(
                    new RegionJoinEvent(region, user.getPlayer())
                );
            }
        }
    }

    public void callEventSync(Event event) {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        plugin.getServer().getScheduler().runTask(plugin, () -> plugin.getServer().getPluginManager().callEvent(event));
    }
}

