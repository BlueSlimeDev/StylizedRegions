package me.blueslime.slimeplugin.spigot;

import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.slimeplugin.spigot.exceptions.NotFoundLanguageException;
import me.blueslime.slimeplugin.spigot.loader.PluginLoader;
import me.blueslime.slimeplugin.spigot.loader.PluginLoaderDelay;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements SlimePlugin<JavaPlugin> {

    private SlimePluginInformation information;

    private PluginLoader loader;

    private SlimeLogs logs;

    public void onEnable() {
        this.logs = SlimeLogger.createLogs(
                getServerType(),
                this
        );

        this.information = new SlimePluginInformation(
                getServerType(),
                this
        );

        this.loader = new PluginLoader(this);

        new PluginLoaderDelay(this).runTaskLater(
                this,
                1L
        );
    }

    public ConfigurationHandler getMessages() {
        ConfigurationHandler configuration = getLoader().getMessages();

        if (configuration == null) {
            exception();
        }

        return configuration;
    }

    private void exception() {
        new NotFoundLanguageException("The current language in the settings file doesn't exists, probably you will see errors in console").printStackTrace();
    }

    @Override
    public SlimePluginInformation getPluginInformation() {
        return information;
    }

    @Override
    public PluginLoader getLoader() {
        return loader;
    }

    @Override
    public SlimeLogs getLogs() {
        return logs;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public void reload() {
        loader.reload();
    }
}
