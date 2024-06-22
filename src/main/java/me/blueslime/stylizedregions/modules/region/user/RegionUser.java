package me.blueslime.stylizedregions.modules.region.user;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.StylizedRegions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionUser {
    private List<String> regionList = new ArrayList<>();
    private boolean loadedStatus = false;
    private String currentRegion = "";

    private final String username;
    private final UUID uuid;

    public RegionUser(Player player) {
        this(player.getUniqueId(), player.getName());
    }

    public RegionUser(UUID uuid, String username) {
        this.username = username;
        this.uuid = uuid;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public void setLoadedStatus(boolean status) {
        this.loadedStatus = status;
    }

    public void setCurrentRegion(String region) {
        this.currentRegion = region;
    }

    public boolean hasPermission(String permission) {
        Player player = getPlayer();
        if (player != null) {
            return player.hasPermission(permission);
        }
        return false;
    }

    public List<String> getRegionList() {
        return regionList;
    }

    public Location getLocation() {
        Player player = getPlayer();
        if (player != null) {
            return player.getLocation();
        }
        return null;
    }

    public String getCurrentRegion() {
        return currentRegion;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public boolean isLoaded() {
        return loadedStatus;
    }


    public Player getPlayer() {
        return Implements.fetch(StylizedRegions.class).getServer().getPlayer(uuid);
    }

}
