package me.blueslime.stylizedregions.region;

import org.bukkit.Material;

@SuppressWarnings("unused")
public class RegionBlock {
    private final Material block;
    private final int behind;
    private final int ahead;
    private final int right;

    private final String id;
    private final int left;
    private final int maxY;
    private final int minY;

    public RegionBlock(String id, Material block, int behind, int ahead, int right, int left, int maxY, int minY) {
        this.behind = behind;
        this.block  = block;
        this.ahead  = ahead;
        this.right  = right;
        this.left   = left;
        this.maxY   = maxY;
        this.minY   = minY;
        this.id     = id;
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
}
