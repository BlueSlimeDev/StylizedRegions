package me.blueslime.stylizedregions.region;

import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.storage.JsonController;
import org.bukkit.Location;

import java.util.Map;

public class RegionLoader {

    private final JsonController<String, String> region;

    private final StylizedRegions plugin;

    public RegionLoader(StylizedRegions plugin) {

        this.region = new JsonController<>(
                plugin,
                "regions",
                String.class,
                String.class
        );

        this.plugin = plugin;
    }

    public void create(Location location) {
        //TODO: todo
    }

    public Map<String, String> getMap() {
        return region.toMap();
    }

}
