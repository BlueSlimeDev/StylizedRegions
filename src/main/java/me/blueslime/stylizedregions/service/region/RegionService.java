package me.blueslime.stylizedregions.service.region;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.utils.consumer.PluginConsumer;
import me.blueslime.bukkitmeteor.logs.MeteorLogger;
import me.blueslime.bukkitmeteor.utils.WorldLocation;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.modules.region.block.RegionBlock;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import me.blueslime.stylizedregions.modules.region.builder.RegionBuilder;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RegionService implements Module {
    private final Map<String, List<Region>> worldRegionList = new ConcurrentHashMap<>();
    private final Map<String, Region> regionMap = new ConcurrentHashMap<>();

    @Override
    public void initialize() {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        File folder = new File(plugin.getDataFolder(), "regions");

        if (folder.exists() || folder.mkdirs()) {
            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    loadRegionsWorld(file);
                }
            }
        }
    }

    private void loadRegionsWorld(File folder) {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) {
            return;
        }
        for (File file : files) {
            String id = file.getName()
                    .toLowerCase(Locale.ENGLISH)
                    .replace(".yml", "");

            FileConfiguration configuration = PluginConsumer.ofUnchecked(
                () -> YamlConfiguration.loadConfiguration(file),
                e -> {},
                new YamlConfiguration()
            );

            String ownerId = configuration.getString("owner-uuid", "");
            String ownerName = configuration.getString("owner-name", "");

            WorldLocation center = WorldLocation.fromConfiguration(
                configuration,
                "block-position"
            );

            String regionBlockIdentifier = configuration.getString("region-block", "");

            if (regionBlockIdentifier.isEmpty()) {
                Implements.fetch(MeteorLogger.class).debug("Ignoring " + file.getName() + " because region block is empty.");
                return;
            }

            RegionBlock block = Implements.fetch(RegionBlockService.class).fetchRegionBlock(regionBlockIdentifier);

            if (block == null) {
                if (file.delete()) {
                    Implements.fetch(MeteorLogger.class).error(
                        "Region at: " + center.getWorld() + ", x: " + center.getX() + ", y: " + center.getY() + ", z: " + center.getZ() + " has been removed because the region block was removed from your block list."
                    );
                }
                return;
            }

            Cuboid cuboid = block.createCuboid(
                center.toLocation(),
                configuration.getInt("expand-area", 0)
            );

            if (cuboid == null) {
                // World is null so the world is unloaded.
                return;
            }

            Region region = new Region(
                configuration,
                file,
                id,
                ownerId,
                ownerName,
                cuboid
            );

            List<Region> regionList = worldRegionList.computeIfAbsent(
                center.getWorld(), s -> new CopyOnWriteArrayList<>()
            );

            regionList.add(
                region
            );

            regionMap.put(
                region.getId(),
                region
            );
        }
    }

    public boolean create(RegionBlock regionBlock, RegionUser user, Location location) {
        int max = Implements.fetch(FileConfiguration.class, "settings.yml").getInt("settings.max-regions-per-user");

        if (location == null || location.getWorld() == null) {
            return false;
        }

        if (user.getRegionList().size() == max) {
            return false;
        }

        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        File folder = new File(plugin.getDataFolder(), "regions");

        String id = user.getUsername() + "-" + ThreadLocalRandom.current().nextInt();
        File worldFolder = new File(folder, location.getWorld().getName());
        if (!worldFolder.exists() && worldFolder.mkdirs()) {
            plugin.getLogs().info("A new world folder for regions has been created.");
        }
        File regionFile = new File(worldFolder, id + ".yml");
        PluginConsumer.process(
            () -> {
                if (regionFile.createNewFile()) {
                    plugin.getLogs().info("Created region file: " + regionFile.getName());
                }
            },
            e -> {}
        );
        FileConfiguration configuration = PluginConsumer.ofUnchecked(
            () -> YamlConfiguration.loadConfiguration(regionFile),
            e -> {},
            new YamlConfiguration()
        );
        configuration.set("owner-uuid", user.getUniqueId().toString());
        configuration.set("owner-name", user.getUsername());
        WorldLocation worldLocation = WorldLocation.at(location);
        worldLocation.print(
            configuration,
            "block-position",
            false
        );
        configuration.set("region-block", regionBlock.getId());
        configuration.set("expand-area", 0);
        plugin.save(configuration, regionFile, "region-template.yml");

        Cuboid cuboid = RegionBuilder.build(
            location,
            regionBlock.getAhead(),
            regionBlock.getBehind(),
            regionBlock.getRight(),
            regionBlock.getLeft(),
            regionBlock.getMaxY(),
            regionBlock.getMinY(),
            0
        );

        if (cuboid == null || location.getWorld() == null) {
            //World was unloaded.
            return false;
        }

        for (Region ownedRegion : getRegionList(location.getWorld().getName())) {
            Cuboid regionCuboid = ownedRegion.getRegion();
            if (regionCuboid.isIn(location) || regionCuboid.isIn(cuboid.getPoint1()) || regionCuboid.isIn(cuboid.getPoint2())) {
                return false;
            }
        }

        Region region = new Region(
            configuration,
            regionFile,
            id,
            user.getUniqueId().toString(),
            user.getUsername(),
            cuboid
        );

        List<Region> regionList = worldRegionList.computeIfAbsent(
            location.getWorld().getName(), s -> new CopyOnWriteArrayList<>()
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

    public List<Region> getRegionList(String world) {
        return worldRegionList.computeIfAbsent(world, s -> new CopyOnWriteArrayList<>());
    }
}
