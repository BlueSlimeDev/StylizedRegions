package me.blueslime.stylizedregions.modules.flags.list.block.fade;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.flags.list.block.fade.listener.BlockFadeListener;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.Player;

public class BlockFadeFlag extends Flag {
    public BlockFadeFlag() {
        super("block-fade", "block-fade:", "<block-fade>");
        registerListeners(new BlockFadeListener());
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
        region.setFlag("block-fade", Boolean.parseBoolean(parameter));
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
        return "block-fade";
    }

    /**
     * Creates an example of usage for your users
     *
     * @return example
     */
    @Override
    public String getExampleUsage() {
        return "block-fade true";
    }
}