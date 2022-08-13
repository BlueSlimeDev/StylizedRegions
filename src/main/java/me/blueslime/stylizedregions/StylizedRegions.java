package me.blueslime.stylizedregions;

import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.stylizedregions.exceptions.NotFoundLanguageException;
import me.blueslime.stylizedregions.loader.PluginLoader;
import me.blueslime.stylizedregions.loader.PluginLoaderDelay;
import me.blueslime.stylizedregions.region.RegionLoader;
import me.blueslime.stylizedregions.region.user.UserManager;
import me.blueslime.stylizedregions.runnable.PlayerRegionRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class StylizedRegions extends JavaPlugin implements SlimePlugin<JavaPlugin> {

    private SlimePluginInformation information;

    private PluginLoader loader;

    private SlimeLogs logs;

    public void onEnable() {
        this.logs = SlimeLogger.createLogs(
                getServerType(),
                this
        );

        this.logs.getProperties().getPrefixes().changeMainText("StylizedRegions");

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

    public RegionLoader getRegionLoader() {
        return getLoader().getRegionLoader();
    }

    public UserManager getUsers() {
        return getLoader().getUserManager();
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

    public PlayerRegionRunnable getRunnable() {
        return getLoader().getRunnable();
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
