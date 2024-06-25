package me.blueslime.stylizedregions.api.events;

import me.blueslime.stylizedregions.api.RegionEvent;
import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.event.Cancellable;

public class RegionPreExpandEvent extends RegionEvent implements Cancellable {
    private boolean cancel = false;
    public RegionPreExpandEvent(Region region) {
        super(region);
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
