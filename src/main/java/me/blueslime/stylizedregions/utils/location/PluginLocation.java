package me.blueslime.stylizedregions.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PluginLocation {
    private String world = "null";
    private float pitch;
    private float yaw;

    private double x;
    private double y;
    private double z;

    /**
     * Creates a location without creating a Location class but storing the current location
     */
    public PluginLocation(String world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    /**
     * Creates a location without creating a Location class but storing the current location
     */
    public PluginLocation(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    /**
     * Creates a location without creating a Location class but storing the current location
     */
    public PluginLocation(int x, int y, int z) {
        this(x, y, z, 0, 0);
    }

    /**
     * Creates a location without creating a Location class but storing the current location
     */
    public PluginLocation(double x, double y, double z, float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a location without creating a Location class but storing the current location
     */
    public PluginLocation(String world, double x, double y, double z, float pitch, float yaw) {
        this.world = world;
        this.pitch = pitch;
        this.yaw = yaw;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(world);
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setWorld(World world) {
        if (world != null) {
            this.world = world.getName();
        }
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public int getBlockX() {
        return (int)x;
    }

    public int getBlockY() {
        return (int)y;
    }

    public int getBlockZ() {
        return (int)z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void addX(double x) {
        this.x += x;
    }

    public void addY(double y) {
        this.y += y;
    }

    public void addZ(double z) {
        this.z += z;
    }

    public void removeX(double x) {
        this.x -= x;
    }

    public void removeY(double y) {
        this.y -= y;
    }

    public void removeZ(double z) {
        this.z -= z;
    }

    public Location toLocationReferenced(Location referencedLocation) {
        return new Location(
                referencedLocation.getWorld(),
                x,
                y,
                z,
                yaw,
                pitch
        );
    }

    public Location toLocation(World world) {
        return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
        );
    }

    public static PluginLocation fromLocation(Location location) {
        PluginLocation pluginLocation = new PluginLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw()
        );

        if (location.getWorld() != null) {
            pluginLocation.setWorld(
                    location.getWorld().getName()
            );
        }

        return pluginLocation;
    }

    public static PluginLocation fromPlayer(Player player) {
        return fromLocation(player.getLocation());
    }

    public static PluginLocation fromSerializedString(String location) {
        return LocationSerializer.getCustomCoordinates(
                location
        );
    }

    public String toSerializedString() {
        return LocationSerializer.toString(this);
    }

    public String toSerializedString(boolean includeExtras) {
        return LocationSerializer.toString(this, includeExtras);
    }

    /**
     * Compare if a Location is similar than the PluginLocation
     * @param include check yaw and pitch
     * @return true if x y and z are location's x y z
     */
    public boolean isSimilar(Location location, boolean include) {
        return includeExtras(
                include,
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    /**
     * Compare if a Location is similar than the PluginLocation
     * @param include check yaw and pitch
     * @return true if x y and z are location's x y z
     */
    public boolean isSimilar(PluginLocation location, boolean include) {
        return includeExtras(
                include,
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    private boolean includeExtras(boolean include, int blockX, int blockY, int blockZ, float yaw, float pitch) {
        if (!include) {
            return (int) x == blockX
                    && (int) y == blockY
                    && (int) z == blockZ;
        }
        boolean isIncluded = (yaw == yaw
                && pitch == pitch
        );

        return (int) x == blockX
                && (int) y == blockY
                && (int) z == blockZ
                && isIncluded;
    }

    public Location toLocation(String world) {
        if (world == null) {
            return toLocation((World)null);
        }
        return toLocation(
                Bukkit.getWorld(world)
        );
    }

    public Location toLocation() {
        return toLocation(world);
    }

    public Block toBlock() {
        return toBlock(
                Bukkit.getWorld(world)
        );
    }

    public Block toBlock(World world) {
        if (world == null) {
            return null;
        }
        return world.getBlockAt(
                (int) x,
                (int) y,
                (int) z
        );
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public PluginLocation clone() {
        PluginLocation clone = new PluginLocation(
                x,
                y,
                z,
                pitch,
                yaw
        );

        if (world != null) {
            clone.setWorld(world);
        }

        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            PluginLocation that = (PluginLocation)o;
            if (this.x == that.x && this.y == that.y && this.z == that.z) {
                return this.world != null ? this.world.equals(that.world) : that.world == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = this.world != null ? this.world.hashCode() : 0;
        result = 31 * result + (int)this.x;
        result = 31 * result + (int)this.y;
        result = 31 * result + (int)this.z;
        return result;
    }
}