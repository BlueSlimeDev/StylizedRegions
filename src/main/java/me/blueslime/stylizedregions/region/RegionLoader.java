package me.blueslime.stylizedregions.region;

import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.stylizedregions.SlimeFile;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.region.user.RegionUser;
import me.blueslime.stylizedregions.region.utils.Cuboid;
import me.blueslime.stylizedregions.region.utils.RegionBuilder;
import me.blueslime.stylizedregions.storage.JsonController;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegionLoader {

    private final JsonController<String, Map<String, String>> region;

    private final Map<String, Region> regionMap = new ConcurrentHashMap<>();

    private final List<Region> regionList = new ArrayList<>();

    private final StylizedRegions plugin;

    public RegionLoader(StylizedRegions plugin) {

        this.region = new JsonController<>(
                plugin.getLogs(),
                plugin.getDataFolder(),
                "regions",
                String.class,
                String.class
        );

        this.plugin = plugin;
    }

    public boolean create(RegionBlock regionBlock, RegionUser user, Location location) {
        int max = plugin.getConfigurationHandler(SlimeFile.SETTINGS).getInt("settings.max-regions-per-user");

        if (location == null) {
            return false;
        }

        if (user.getRegionSize() == max && max != -1) {
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

        //: ToDo

        Region region = new Region(
                plugin,
                ConfigurationHandler.loadControl(
                        null,
                        null
                ),
                "ToDo",
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

    public Region fetchRegion(String id) {
        return regionMap.get(id);
    }

    public Map<String, Map<String, String>> getMap() {
        return region.toMap();
    }

    public List<Region> getRegionList() {
        return new ArrayList<>(regionList);
    }
}
