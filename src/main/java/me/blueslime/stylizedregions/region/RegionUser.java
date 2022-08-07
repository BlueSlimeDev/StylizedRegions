package me.blueslime.stylizedregions.region;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionUser {
    private List<String> regions = new ArrayList<>();

    private final String currentRegion = "";

    private final Player player;

    public RegionUser(Player player) {
        this.player = player;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public String getCurrentRegion() {
        return currentRegion;
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public String getUsername() {
        return player.getName();
    }

    public int getRegionSize() {
        return regions.size();
    }

}
