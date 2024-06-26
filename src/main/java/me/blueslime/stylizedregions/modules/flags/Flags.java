package me.blueslime.stylizedregions.modules.flags;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.commands.sender.Sender;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextReplacer;
import me.blueslime.bukkitmeteor.utils.list.ReturnableArrayList;
import me.blueslime.stylizedregions.modules.flags.flag.Flag;
import me.blueslime.stylizedregions.modules.flags.list.block.breaks.BlockBreakFlag;
import me.blueslime.stylizedregions.modules.flags.list.block.place.BlockPlaceFlag;
import me.blueslime.stylizedregions.modules.flags.list.extras.farewell.ChatFarewellFlag;
import me.blueslime.stylizedregions.modules.flags.list.extras.greeting.ChatGreetingFlag;
import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Flags implements Module {
    private final List<Flag> externalActions = new ReturnableArrayList<Flag>();
    private final List<Flag> action = new ReturnableArrayList<Flag>();
    private final BukkitMeteorPlugin plugin;

    public Flags(BukkitMeteorPlugin plugin) {
        this.plugin = plugin;
        registerInternalFlag(
            new BlockPlaceFlag(),
            new BlockBreakFlag(),
            new ChatFarewellFlag(),
            new ChatGreetingFlag()
        );
        Implements.register(this);
    }

    /**
     * Register actions to the plugin
     * These actions will not be refreshed in a reload event.
     * @param actions to register
     */
    public void registerInternalFlag(Flag... actions) {
        action.addAll(Arrays.asList(actions));
    }

    /**
     * Register actions to the plugin
     * @param actions to register
     */
    public void registerFlag(Flag... actions) {
        externalActions.addAll(Arrays.asList(actions));
    }

    public List<Flag> getFlags() {
        return action;
    }

    public List<Flag> getExternalFlags() {
        return externalActions;
    }

    public void execute(Region region, Player player, String parameter) {
        execute(region, player, parameter, false);
    }

    public void execute(Region region, Player player, String parameter, boolean forced) {
        List<Flag> entireList = new ReturnableArrayList<Flag>();

        entireList.addAll(externalActions);
        entireList.addAll(action);

        fetch(entireList, player, region, parameter, forced);
    }

    private void fetch(List<Flag> list, Player player, Region region, String param, boolean forced) {
        if (player == null) {
            return;
        }
        for (Flag flag : list) {
            if (flag.isThisFlag(param) && flag.canExecute(player, region)) {
                if (flag.performValid(param)) {
                    flag.perform(plugin, param, region, player);
                    if (forced) {
                        return;
                    }
                    Sender sender = Sender.build(player);
                    sender.send(
                        player,
                        Implements.fetch(FileConfiguration.class, "messages.yml"),
                        "region.flag-modify",
                        TextReplacer.builder()
                            .replace("<flag_example>", flag.getExampleUsage())
                            .replace("<param>", param)
                            .replace("%param%", param)
                            .replace("%flag_example%", flag.getExampleUsage())
                            .replace("<flag_id>", flag.getIdentifier())
                            .replace("%flag_id%", flag.getIdentifier())
                            .replace("%value%", flag.replace(param))
                            .replace("%flag%", flag.getIdentifier())
                    );
                    return;
                }
                if (forced) {
                    plugin.getLogs().error("Wrong value for flag, your flag set: '" + param + "', example usage: '" + flag.getExampleUsage() + "'");
                    return;
                }
                Sender sender = Sender.build(player);
                sender.send(
                    player,
                    Implements.fetch(FileConfiguration.class, "messages.yml"),
                    "region.wrong-flag-usage",
                    TextReplacer.builder()
                        .replace("<flag_example>", flag.getExampleUsage())
                        .replace("<param>", param)
                        .replace("%param%", param)
                        .replace("%flag_example%", flag.getExampleUsage())
                        .replace("<flag_id>", flag.getIdentifier())
                        .replace("%flag_id%", flag.getIdentifier())
                        .replace("%value%", flag.replace(param))
                        .replace("%flag%", flag.getIdentifier())
                );
                return;
            }
        }
    }
}
