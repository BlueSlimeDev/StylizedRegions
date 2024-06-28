package me.blueslime.stylizedregions.modules.flags.flag.types;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.Player;

public abstract class BooleanFlag extends Flag {
    public BooleanFlag(String prefix, String... extraPrefixes) {
        super(prefix, extraPrefixes);
    }

    /**
     * Execute action
     *
     * @param plugin       of the event
     * @param parameter    text to check
     * @param region       region of the executable
     * @param flagExecutor this is optional but if you want to get the player that executed the command you can do it here.
     */
    @Override
    public void execute(BukkitMeteorPlugin plugin, String parameter, Region region, Player flagExecutor) {
        region.setFlag(getIdentifier(), Boolean.parseBoolean(parameter));
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

    /**
     * Creates an example of usage for your users
     *
     * @return example
     */
    @Override
    public String getExampleUsage() {
        return getIdentifier() + " true";
    }
}
