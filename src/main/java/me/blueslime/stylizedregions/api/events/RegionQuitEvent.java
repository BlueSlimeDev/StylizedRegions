package me.blueslime.stylizedregions.api.events;

import me.blueslime.stylizedregions.api.RegionEvent;
import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.entity.Player;

public class RegionQuitEvent extends RegionEvent {
    private final Player player;
    public RegionQuitEvent(Region region, Player player) {
        super(region);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
