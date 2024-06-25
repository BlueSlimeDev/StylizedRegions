package me.blueslime.stylizedregions.utils.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.service.region.RegionService;
import org.bukkit.Location;

import java.util.Optional;

public class RegionUtil {
    public static boolean isBoolean(String parameter) {
        return parameter.equalsIgnoreCase("true") || parameter.equalsIgnoreCase("false");
    }
    public static Optional<Region> getRegionAt(Location location) {
        if (location == null || location.getWorld() == null) {
            return Optional.empty();
        }
        return Implements.fetch(
            RegionService.class
        ).getRegionList(location.getWorld().getName())
            .stream()
            .filter(region -> region.isIn(location))
            .findAny();
    }
}
