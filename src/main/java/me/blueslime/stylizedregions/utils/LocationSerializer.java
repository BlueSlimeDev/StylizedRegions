package me.blueslime.stylizedregions.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.text.DecimalFormat;

public class LocationSerializer {

    private static final DecimalFormat format = new DecimalFormat("0.00");

    public static Location fromString(String location) {
        if (location == null) {
            return null;
        }

        String[] text = location.replace(" ", "").split(",");

        if (text.length != 4) {
            return null;
        }

        double x = Double.parseDouble(text[1]);
        double y = Double.parseDouble(text[2]);
        double z = Double.parseDouble(text[3]);

        return new Location(
                Bukkit.getWorld(text[0]),
                x,
                y,
                z
        );
    }

    public static String toString(Location location) {
        return location.getWorld() + ", " +
                format.format(location.getX()) + ", " +
                format.format(location.getY()) + ", " +
                format.format(location.getZ());
    }

}
