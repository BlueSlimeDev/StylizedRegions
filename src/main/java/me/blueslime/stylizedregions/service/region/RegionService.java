package me.blueslime.stylizedregions.service.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.modules.region.block.RegionBlock;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import me.blueslime.stylizedregions.modules.region.builder.RegionBuilder;
import me.blueslime.stylizedregions.modules.storage.JsonData;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class RegionService implements Module {
    private final JsonData<String, Map<String, String>> region;
    private final Map<String, Region> regionMap = new ConcurrentHashMap<>();
    private final List<Region> regionList = new ArrayList<>();

    public RegionService(StylizedRegions plugin) {
        this.region = new JsonData<>(
                plugin.getDataFolder(),
                "regions",
                String.class,
                String.class
        );
    }

    public boolean create(RegionBlock regionBlock, RegionUser user, Location location) {
        int max = Implements.fetch(FileConfiguration.class, "settings.yml").getInt("settings.max-regions-per-user");

        if (location == null) {
            return false;
        }

        if (user.getRegionList().size() == max) {
            return false;
        }

        Cuboid cuboid = RegionBuilder.build(
            location,
            regionBlock.getAhead(),
            regionBlock.getBehind(),
            regionBlock.getRight(),
            regionBlock.getLeft(),
            regionBlock.getMaxY(),
            regionBlock.getMinY()
        );

        for (Region ownedRegion : regionList) {
            Cuboid regionCuboid = ownedRegion.getRegion();
            if (regionCuboid.isIn(location) || regionCuboid.isIn(cuboid.getPoint1()) || regionCuboid.isIn(cuboid.getPoint2())) {
                return false;
            }
        }

        Region region = new Region(
                new YamlConfiguration(),
                user.getUsername() + "-" + ThreadLocalRandom.current().nextInt(),
                user.getUniqueId().toString(),
                user.getUsername(),
                cuboid
        );

        regionList.add(
            region
        );
        regionMap.put(
            region.getId(),
            region
        );
        return true;
    }

    public Map<String, Map<String, String>> getMap() {
        return region.toMap();
    }

    public Region fetchRegion(String id) {
        return regionMap.get(id);
    }

    public List<Region> getRegionList() {
        return new ArrayList<>(regionList);
    }
}
