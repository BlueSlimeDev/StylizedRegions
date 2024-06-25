package me.blueslime.stylizedregions.modules.region.block;

import me.blueslime.stylizedregions.modules.region.builder.RegionBuilder;
import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

@SuppressWarnings("unused")
public class RegionBlock {
    private final FileConfiguration configuration;
    private final Material block;
    private final int behind;
    private final int ahead;
    private final int right;

    private final String id;
    private final int left;
    private final int maxY;
    private final int minY;

    public RegionBlock(FileConfiguration configuration, String id, Material block, int behind, int ahead, int right, int left, int maxY, int minY) {
        this.configuration = configuration;
        this.behind = behind;
        this.block  = block;
        this.ahead  = ahead;
        this.right  = right;
        this.left   = left;
        this.maxY   = maxY;
        this.minY   = minY;
        this.id     = id;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public int getAhead() {
        return ahead;
    }

    public int getBehind() {
        return behind;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public Material getBlock() {
        return block;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    public String getId() {
        return id;
    }

    public Cuboid createCuboid(Location location, int expand) {
        return RegionBuilder.build(
            location, ahead, behind, right, left, maxY, minY, expand
        );
    }
}
