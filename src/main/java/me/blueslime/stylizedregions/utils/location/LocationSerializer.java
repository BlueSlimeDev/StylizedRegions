package me.blueslime.stylizedregions.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.text.DecimalFormat;

@SuppressWarnings("unused")
public class LocationSerializer {

    private static final DecimalFormat format = new DecimalFormat("0.00");

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    public static String toDecimal(double value) {
        return decimalFormat.format(value);
    }

    private LocationSerializer() {}

    public static Location getCoordinates(ConfigurationSection conf) {
        if (conf == null) {
            return new Location(
                    null,
                    0,
                    70,
                    0,
                    0,
                    0
            );
        }
        return new Location(
                null,
                conf.getDouble("x"),
                conf.getDouble("y"),
                conf.getDouble("z"),
                (float) conf.getDouble("yaw"),
                (float) conf.getDouble("pitch")
        );
    }

    public static PluginLocation getCustomCoordinates(ConfigurationSection conf) {
        return new PluginLocation(
                null,
                conf.getDouble("x"),
                conf.getDouble("y"),
                conf.getDouble("z"),
                (float) conf.getDouble("yaw"),
                (float) conf.getDouble("pitch")
        );
    }

    public static PluginLocation getCustomCoordinates(String location) {
        if (location == null) {
            return null;
        }

        String[] text = location.replace(" ", "").split(",");

        if (text.length < 4) {
            return null;
        }

        String world = text[0];

        double x = Double.parseDouble(text[1]);
        double y = Double.parseDouble(text[2]);
        double z = Double.parseDouble(text[3]);

        if (text.length != 6) {
            return new PluginLocation(
                    world,
                    x,
                    y,
                    z
            );
        }

        double pitch = Double.parseDouble(text[5]);
        double yaw = Double.parseDouble(text[4]);

        return new PluginLocation(
                world,
                x,
                y,
                z,
                (float)pitch,
                (float)yaw
        );
    }

    public static void setCoordinates(ConfigurationSection section, Location location) {
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
    }

    public static Location getWorldLocation(ConfigurationSection conf) {
        if (conf == null || !conf.contains("world")) {
            return null;
        }

        final World world = Bukkit.getWorld(conf.getString("world", "world"));

        if (world == null) {
            return null;
        }

        return new Location(
                world,
                conf.getDouble("x"),
                conf.getDouble("y"),
                conf.getDouble("z"),
                (float) conf.getDouble("yaw"),
                (float) conf.getDouble("pitch")
        );
    }

    public static String toString(Location location, boolean includeExtras) {
        if (location.getWorld() == null) {
            if (includeExtras) {
                return "world, " +
                        format.format(location.getX()) + ", " +
                        format.format(location.getY()) + ", " +
                        format.format(location.getZ()) + ", " +
                        location.getYaw() + ", " +
                        location.getPitch();
            } else {
                return "world, " +
                        format.format(location.getX()) + ", " +
                        format.format(location.getY()) + ", " +
                        format.format(location.getZ());
            }
        }
        if (includeExtras) {
            return location.getWorld().getName() + ", " +
                    format.format(location.getX()) + ", " +
                    format.format(location.getY()) + ", " +
                    format.format(location.getZ()) + ", " +
                    location.getYaw() + ", " +
                    location.getPitch();
        }
        return location.getWorld().getName() + ", " +
                format.format(location.getX()) + ", " +
                format.format(location.getY()) + ", " +
                format.format(location.getZ());
    }

    public static String toString(PluginLocation location, boolean includeExtras) {
        if (location.getWorld() == null) {
            if (includeExtras) {
                return "world, " +
                        format.format(location.getX()) + ", " +
                        format.format(location.getY()) + ", " +
                        format.format(location.getZ()) + ", " +
                        location.getYaw() + ", " +
                        location.getPitch();
            } else {
                return "world, " +
                        format.format(location.getX()) + ", " +
                        format.format(location.getY()) + ", " +
                        format.format(location.getZ());
            }
        }
        if (includeExtras) {
            return location.getWorld() + ", " +
                    format.format(location.getX()) + ", " +
                    format.format(location.getY()) + ", " +
                    format.format(location.getZ()) + ", " +
                    location.getYaw() + ", " +
                    location.getPitch();
        }
        return location.getWorld() + ", " +
                format.format(location.getX()) + ", " +
                format.format(location.getY()) + ", " +
                format.format(location.getZ());
    }

    /**
     * Create a string text from location
     * @param location to make string
     * @return location including extras (yaw and pitch)
     */
    public static String toString(PluginLocation location) {
        return toString(location, true);
    }

    public static String toString(Location location) {
        return toString(location, true);
    }

    public static void setWorldLocation(ConfigurationSection section, Location location) {
        if (location.getWorld() != null) {
            section.set("world", location.getWorld().getName());
        }
        setCoordinates(section, location);
    }
}
