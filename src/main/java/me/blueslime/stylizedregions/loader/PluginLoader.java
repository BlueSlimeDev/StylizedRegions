package me.blueslime.stylizedregions.loader;

import dev.mruniverse.slimelib.SlimeStorage;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.file.configuration.ConfigurationProvider;
import dev.mruniverse.slimelib.file.configuration.provider.BukkitConfigurationProvider;
import dev.mruniverse.slimelib.file.input.InputManager;
import dev.mruniverse.slimelib.loader.BaseSlimeLoader;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.SlimeFile;
import me.blueslime.stylizedregions.commands.PluginCommand;
import me.blueslime.stylizedregions.region.RegionLoader;
import me.blueslime.stylizedregions.region.user.UserManager;
import me.blueslime.stylizedregions.utils.FileUtilities;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginLoader extends BaseSlimeLoader<JavaPlugin> {

    private ConfigurationHandler messages = null;

    private final RegionLoader regionLoader;

    private final UserManager userManager;

    private final File langDirectory;

    public PluginLoader(StylizedRegions plugin) {
        super(plugin);

        super.storage(
                new SlimeStorage(
                        plugin.getServerType(),
                        plugin.getLogs(),
                        InputManager.createInputManager(
                                plugin.getServerType(),
                                plugin.getPlugin()
                        )
                )
        );

        langDirectory = new File(
                plugin.getDataFolder(),
                "lang"
        );

        boolean loadDefaults = false;

        if (!langDirectory.exists()) {
            plugin.getLogs().info("Language directory has been verified (" + langDirectory.mkdirs() + ")");
            loadDefaults = true;
        }

        if (loadDefaults) {
            loadDefaults();
        }

        regionLoader = new RegionLoader(plugin);

        userManager = new UserManager(plugin);

        getCommands().register(new PluginCommand());
    }

    public void init() {
        if (this.getFiles() != null) {
            this.getFiles().init();

            String lang = getFiles().getConfigurationHandler(SlimeFile.SETTINGS).getString("settings.default-lang", "en");

            File messages = new File(
                    langDirectory,
                    lang + ".yml"
            );

            if (messages.exists()) {
                ConfigurationProvider provider = new BukkitConfigurationProvider();

                this.messages = provider.create(
                        getPlugin().getLogs(),
                        messages
                );

                getPlugin().getLogs().info("Scoreboards and Messages are loaded from Lang files successfully.");
            } else {
                getPlugin().getLogs().error("Can't load scoreboards and messages correctly, debug will be showed after this message:");
                getPlugin().getLogs().debug("Language file of messages: " + messages.getAbsolutePath());
                getPlugin().getLogs().debug("Language name file of messages: " + messages.getName());
            }
        }
    }

    private void loadDefaults() {
        loadLanguageFile("de");
        loadLanguageFile("en");
        loadLanguageFile("es");
        loadLanguageFile("fa");
        loadLanguageFile("fr");
        loadLanguageFile("jp");
        loadLanguageFile("pl");
    }

    private void loadLanguageFile(String name) {
        FileUtilities.load(
                getPlugin().getLogs(),
                langDirectory,
                name + ".yml",
                "/lang/" + name + ".yml"
        );
    }

    public ConfigurationHandler getMessages() {
        return messages;
    }

    public RegionLoader getRegionLoader() {
        return regionLoader;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void shutdown() {
        this.getCommands().unregister();
    }

    public void reload() {
        this.getFiles().reloadFiles();
    }

}
