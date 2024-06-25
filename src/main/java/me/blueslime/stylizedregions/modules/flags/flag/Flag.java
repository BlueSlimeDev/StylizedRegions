package me.blueslime.stylizedregions.modules.flags.flag;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.region.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class Flag {
    private final List<Listener> listeners = new ArrayList<>();
    private final List<String> prefixes = new ArrayList<>();

    public Flag(String prefix, String... extraPrefixes) {
        this.prefixes.addAll(Arrays.asList(extraPrefixes));
        this.prefixes.add(prefix);
    }

    /**
     * Execute action
     *
     * @param plugin    of the event
     * @param region    region of the executable
     * @param flagExecutor    this is optional but if you want to get the player that executed the command you can do it here.
     * @param parameter text to check
     */
    public abstract void execute(BukkitMeteorPlugin plugin, String parameter, Region region, Player flagExecutor);

    public void perform(BukkitMeteorPlugin plugin, String parameter, Region region, Player flagExecutor) {
        execute(plugin, replace(parameter), region, flagExecutor);
    }

    /**
     * Modify it to allow the execution of your flag
     * @param parameter to check
     * @return true if the parameter is correct false if is not.
     */
    public abstract boolean isValidValue(String parameter);

    public boolean performValid(String parameter) {
        return isValidValue(replace(parameter));
    }

    public void registerListeners(Listener... listeners) {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        PluginManager manager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) {
            manager.registerEvents(listener, plugin);
            this.listeners.add(listener);
        }
    }

    public void shutdown() {
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }

        listeners.clear();
    }

    public String replace(String parameter) {
        for (String prefix : prefixes) {
            parameter = parameter.replace(" " + prefix + " ", "").replace(" " + prefix, "").replace(prefix + " ", "").replace(prefix, "");
        }
        return parameter;
    }

    public abstract String getIdentifier();

    public boolean isThisFlag(String parameter) {
        if (parameter == null) {
            return false;
        }
        String param = parameter.toLowerCase(Locale.ENGLISH);
        for (String prefix : prefixes) {
            if (param.startsWith(" " + prefix.toLowerCase(Locale.ENGLISH)) || param.startsWith(prefix.toLowerCase(Locale.ENGLISH))) {
                return true;
            }
        }
        return false;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public boolean canExecute(Player player, Region region) {
        return region.isOwner(player) || region.hasAdminPermissions(player);
    }

    /**
     * Creates an example of usage for your users
     * @return example
     */
    public abstract String getExampleUsage();
}
