package me.blueslime.stylizedregions.modules.flags.list.player.teleport;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.Player;

public class JoinUsingEnderPearlFlag extends Flag {
    public JoinUsingEnderPearlFlag() {
        super("join-via-enderpearl:", "join-via-enderpearl", "<join-via-enderpearl>", "enderpearl");
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
        region.setFlag("join-via-enderpearl", Boolean.parseBoolean(parameter));
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
        return "join-via-enderpearl";
    }

    /**
     * Creates an example of usage for your users
     *
     * @return example
     */
    @Override
    public String getExampleUsage() {
        return "join-via-enderpearl true";
    }
}

