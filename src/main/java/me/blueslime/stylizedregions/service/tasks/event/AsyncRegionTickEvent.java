package me.blueslime.stylizedregions.service.tasks.event;

import com.sun.istack.internal.NotNull;
import me.blueslime.stylizedregions.StylizedRegions;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncRegionTickEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final StylizedRegions plugin;

    public AsyncRegionTickEvent(@NotNull final StylizedRegions plugin) {
        this.plugin = plugin;
    }

    public StylizedRegions getPlugin() {
        return plugin;
    }

    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }
}

