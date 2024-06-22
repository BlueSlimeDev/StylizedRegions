package me.blueslime.stylizedregions.service.commands;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.commands.PluginCommand;

public class CommandService implements Module {
    public CommandService() {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        new PluginCommand(plugin).register(
            plugin
        );
    }
}
