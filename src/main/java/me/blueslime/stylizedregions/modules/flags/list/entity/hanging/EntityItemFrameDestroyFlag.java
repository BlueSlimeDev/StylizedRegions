package me.blueslime.stylizedregions.modules.flags.list.entity.hanging;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.flags.list.entity.hanging.listener.HangingBreakListener;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.Player;

public class EntityItemFrameDestroyFlag extends Flag {
    public EntityItemFrameDestroyFlag() {
        super("entity-item-frame-destroy", "entity-item-frame", "<entity-item-frame-destroy>", "entity-frame-destroy", "entity-item-frame-destroy:");
        registerListeners(new HangingBreakListener());
    }

    /**
     * Execute action
     *
     * @param plugin    of the event
     * @param parameter text
     * @param region    region of the executable
     */
    @Override
    public void execute(BukkitMeteorPlugin plugin, String parameter, Region region, Player player) {
        region.setFlag("entity-item-frame-destroy", Boolean.parseBoolean(parameter));
    }

    /**
     * Modify it to allow the execution of your flag
     *
     * @param parameter to check
     * @return true if the parameter is correct false if is not.
     */
    @Override
    public boolean isValidValue(String parameter) {
        return RegionUtil.isBoolean(parameter);
    }

    @Override
    public String getIdentifier() {
        return "entity-item-frame-destroy";
    }

    /**
     * Creates an example of usage for your users
     *
     * @return example
     */
    @Override
    public String getExampleUsage() {
        return "entity-item-frame-destroy true";
    }
}
