package me.blueslime.stylizedregions.api;

import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class RegionEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Region region;

    public RegionEvent(Region region) {
        this.region = region;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public Region getRegion() {
        return region;
    }

    public Player getRegionOwner() {
        return region.getBukkitOwner();
    }
}

