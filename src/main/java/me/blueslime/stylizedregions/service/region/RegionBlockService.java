package me.blueslime.stylizedregions.service.region;

import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.modules.region.block.RegionBlock;

import java.util.HashMap;
import java.util.Map;

public class RegionBlockService implements Module {
    private final Map<String, RegionBlock> regionBlockMap = new HashMap<>();

    @Override
    public void initialize() {
        //TODO: Load Default Region Blocks
    }

    public RegionBlock fetchRegionBlock(String identifier) {
        return regionBlockMap.get(identifier);
    }
}
