package me.blueslime.stylizedregions.modules.listeners.player;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.service.users.UserService;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerQuitEvent event) {
        Implements.fetch(UserService.class).removeUser(event.getPlayer());
    }
}
