package me.blueslime.stylizedregions.modules.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.utils.PluginConsumer;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.flags.Flags;
import me.blueslime.stylizedregions.modules.region.block.RegionBlock;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.service.region.RegionBlockService;
import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Region {
    private final FileConfiguration configuration;
    private final File regionFile;
    private final String name;
    private final UUID uuid;
    private final String id;
    private Cuboid region;

    public Region(FileConfiguration configuration, File regionFile, String id, String uuid, String name, Cuboid cuboid) {
        this.configuration = configuration;
        this.regionFile = regionFile;
        this.region = cuboid;
        this.uuid   = UUID.fromString(uuid);
        this.name   = name;
        this.id = id;
    }

    /**
     * Replace the region
     * @param cuboid Cuboid
     */
    public void replaceRegion(Cuboid cuboid) {
        this.region = cuboid;
    }

    public boolean isOwner(Player player) {
        return uuid == player.getUniqueId();
    }

    /**
     * Get the UniqueId of the owner of the region
     * @return UUID
     */
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Get the owner's name of the region
     * @return Username
     */
    public String getOwner() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void addTrusted(Player player) {
        //TODO: Add this one
    }

    public boolean isTrusted(Player player) {
        return configuration.getStringList("trusted-users").contains(player.getUniqueId().toString());
    }

    public boolean isTrusted(RegionUser player) {
        return configuration.getStringList("trusted-users").contains(player.getUniqueId().toString());
    }

    public boolean hasAdminPermissions(RegionUser player) {
        return configuration.getStringList("admin-users").contains(player.getUniqueId().toString());
    }

    public boolean hasAdminPermissions(Player player) {
        return configuration.getStringList("admin-users").contains(player.getUniqueId().toString());
    }

    /**
     * Get the region Cuboid
     * @return Cuboid
     */
    public Cuboid getRegion() {
        return region;
    }

    public File getFile() {
        return regionFile;
    }

    /**
     * gets the region block of this region
     * @return region protection block
     */
    public RegionBlock getRegionBlock() {
        return Implements.fetch(RegionBlockService.class).fetchRegionBlock(
            configuration.getString("region-block", "")
        );
    }

    /**
     * Set the flag data in the configuration file of the flag
     * @param flagId identifier
     * @param value flag value
     */
    public void setFlag(String flagId, Object value) {
        configuration.set("flags." + flagId, value);
        Implements.fetch(StylizedRegions.class).save(configuration, regionFile, "region-template.yml");
    }

    /**
     * This is user for the user request to add a flag to the current region, if you are a developer
     * Please use the setFlag, this is normally used with the setFlag command.
     * @param parameter data
     */
    public void addFlag(Player player, String parameter) {
        Implements.fetch(Flags.class).execute(
            this,
            player,
            parameter,
            false
        );
    }

    /**
     * Save region in console
     */
    public void save() {
        Implements.fetch(StylizedRegions.class).save(configuration, regionFile, "region-template.yml");
    }

    /**
     * Gets a flag from this region
     * @param flagId identifier of this flag
     * @param defaultValue flag value
     * @return result
     */
    @SuppressWarnings("unchecked")
    public <T> T getFlag(String flagId, T defaultValue) {
        Object result = configuration.get("flags." + flagId);
        if (result != null) {
            return (T) result;
        }
        return (T) getRegionBlock().getConfiguration().get("flags." + flagId, defaultValue);
    }

    /**
     * Gets a flag from this region
     * @param flagId identifier of this flag
     * @param defaultValue flag value
     * @return result
     */
    @SuppressWarnings("unchecked")
    public <T> T getFlag(String flagId, T defaultValue, PluginConsumer.PluginExecutableConsumer<T> consumer) {
        T result = consumer.accept();
        if (result != null) {
            return result;
        }
        return (T) getRegionBlock().getConfiguration().get("flags." + flagId, defaultValue);
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Checks if something is in the region
     * @param entity to check
     * @return result
     */
    public boolean isIn(Entity entity) {
        return region.isIn(entity.getLocation());
    }

    /**
     * Checks if something is in the region
     * @param location to check
     * @return result
     */
    public boolean isIn(Location location) {
        return region.isIn(location);
    }

    public Player getBukkitOwner() {
        return Implements.fetch(StylizedRegions.class).getServer().getPlayer(uuid);
    }
}
