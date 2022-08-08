package me.blueslime.stylizedregions.region.user;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionUser {
    private List<String> regions = new ArrayList<>();

    private String currentRegion = "";

    private boolean status = false;

    private final Player player;

    public RegionUser(Player player) {
        this.player = player;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public void setCurrentRegion(String region) {
        this.currentRegion = region;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public List<String> getRegions() {
        return regions;
    }

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public String getCurrentRegion() {
        return currentRegion;
    }

    public boolean isLoaded() {
        return status;
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
