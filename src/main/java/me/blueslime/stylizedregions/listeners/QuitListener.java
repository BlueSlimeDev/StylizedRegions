package me.blueslime.stylizedregions.listeners;

import me.blueslime.stylizedregions.StylizedRegions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final StylizedRegions plugin;

    public QuitListener(StylizedRegions plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerQuitEvent event) {
        plugin.getUsers().removeUser(event.getPlayer());
    }
}
