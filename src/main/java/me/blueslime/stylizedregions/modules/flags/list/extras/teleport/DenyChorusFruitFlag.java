package me.blueslime.stylizedregions.modules.flags.list.extras.teleport;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.entity.Player;

public class DenyChorusFruitFlag extends Flag {
    public DenyChorusFruitFlag() {
        super("deny-chorus-fruit-message", "deny-chorus-fruit-message:", "<deny-chorus-fruit-message>");
    }

    /**
     * Execute action
     *
     * @param plugin       of the event
     * @param parameter    text
     * @param region       region of the executable
     * @param flagExecutor this is optional but if you want to get the player that executed the command you can do it here.
     */
    @Override
    public void execute(BukkitMeteorPlugin plugin, String parameter, Region region, Player flagExecutor) {
        region.setFlag(getIdentifier(), parameter);
    }

    /**
     * Modify it to allow the execution of your flag
     *
     * @param parameter to check
     * @return true if the parameter is correct false if is not.
     */
    @Override
    public boolean isValidValue(String parameter) {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "deny-chorus-fruit-message";
    }

    /**
     * Creates an example of usage for your users
     *
     * @return example
     */
    @Override
    public String getExampleUsage() {
        return "deny-chorus-fruit-message: &cCAN'T DO THIS HERE.";
    }
}