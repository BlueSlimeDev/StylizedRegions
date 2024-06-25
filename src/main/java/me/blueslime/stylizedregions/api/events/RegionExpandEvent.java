package me.blueslime.stylizedregions.api.events;

import me.blueslime.stylizedregions.api.RegionEvent;
import me.blueslime.stylizedregions.modules.region.Region;

public class RegionExpandEvent extends RegionEvent {
    public RegionExpandEvent(Region region) {
        super(region);
    }
}
