package me.blueslime.stylizedregions.modules.region.builder;

import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.World;

public class RegionBuilder {

    public static Cuboid build(Location pos1, Location pos2) {
        return new Cuboid(
            pos1,
            pos2
        );
    }

    public static Cuboid build(Location location, int ahead, int behind, int right, int left, int maxY, int minY) {
        Location pos1 = calculatePosition(location, true, ahead, right, maxY);
        Location pos2 = calculatePosition(location, false, behind, left, minY);
        return new Cuboid(
            pos1,
            pos2
        );
    }

    @SuppressWarnings("unused")
    public static Cuboid build(World world, double x, double y, double z, int ahead, int behind, int right, int left, int maxY, int minY) {
        return build(
            new Location(
                world,
                x,
                y,
                z
            ),
            ahead,
            behind,
            right,
            left,
            maxY,
            minY
        );
    }

    private static Location calculatePosition(Location cloneableLocation, boolean ahead, int multiplier, int secondMultiplier, int locationY) {
        Location location = cloneableLocation.clone();

        double defX = location.getX();
        double defZ = location.getZ();

        if (ahead) {
            location.setX(defX + multiplier);
            location.setZ(defZ + secondMultiplier);
        } else {
            location.setX(defX - multiplier);
            location.setZ(defZ - secondMultiplier);
        }

        location.setY(locationY);

        return location;
    }



}
