package me.blueslime.stylizedregions.modules.listeners.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.service.region.RegionService;
import me.blueslime.stylizedregions.service.tasks.event.AsyncRegionTickEvent;
import me.blueslime.stylizedregions.service.users.UserService;

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
        for (Region region : service.getRegionList()) {
            boolean isIn = region.getRegion().isIn(user.getLocation());

            if (!isIn && user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion("");
                region.send(false, user.getPlayer());
                continue;
            }

            if (isIn && !user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion(region.getId());
                region.send(true, user.getPlayer());
            }
        }
    }
}

