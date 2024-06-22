package me.blueslime.stylizedregions.service.listeners;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.listeners.region.AsyncPlayerRegionListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ListenerService implements Module {
    public ListenerService() {
        registerAll(
            new AsyncPlayerRegionListener()
        );
    }

    private void registerAll(Listener... listeners) {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        PluginManager manager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) {
            manager.registerEvents(listener, plugin);
        }
    }
}
