package me.blueslime.slimeplugin.spigot.loader;

import dev.mruniverse.slimelib.SlimeStorage;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.file.configuration.ConfigurationProvider;
import dev.mruniverse.slimelib.file.configuration.provider.BukkitConfigurationProvider;
import dev.mruniverse.slimelib.file.input.InputManager;
import dev.mruniverse.slimelib.loader.BaseSlimeLoader;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.slimeplugin.spigot.Main;
import me.blueslime.slimeplugin.spigot.SlimeFile;
import me.blueslime.slimeplugin.spigot.utils.FileUtilities;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginLoader extends BaseSlimeLoader<JavaPlugin> {

    private ConfigurationHandler messages = null;

    private final File langDirectory;

    public PluginLoader(Main plugin) {
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
        SlimeLogs logs = getPlugin().getLogs();

        FileUtilities.load(
                logs,
                langDirectory,
                "en.yml",
                "/lang/messages/en.yml"
        );

        FileUtilities.load(
                logs,
                langDirectory,
                "es.yml",
                "/lang/messages/es.yml"
        );
    }

    public ConfigurationHandler getMessages() {
        return messages;
    }

    public void shutdown() {
        this.getCommands().unregister();
    }

    public void reload() {
        this.getFiles().reloadFiles();
    }

}
