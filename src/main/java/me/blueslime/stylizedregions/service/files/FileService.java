package me.blueslime.stylizedregions.service.files;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.bukkitmeteor.implementation.registered.Register;
import me.blueslime.bukkitmeteor.utils.FileUtil;
import me.blueslime.stylizedregions.StylizedRegions;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.InputStream;

public class FileService implements Module {
    private FileConfiguration messages;

    public FileService() {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        loadLanguage(plugin, "de", "en", "es", "fa", "fr", "jp", "pl");
        loadRegionBlocks(plugin, "first", "second");
        messages = fetchMessagesConfiguration(plugin);
        register(this);
    }

    private void loadLanguage(StylizedRegions plugin, String... codes) {
        File folder = new File(plugin.getDataFolder(), "languages");
        boolean generate = !folder.exists();
        if (!generate) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
            generate = files == null || files.length == 0;
        }
        if (generate) {
            for (String code : codes) {
                File file = new File(folder, code);
                FileUtil.saveResource(
                    file,
                    getResource("/lang/" + code + ".yml")
                );
            }
        }
    }

    private void loadRegionBlocks(StylizedRegions plugin, String... names) {
        File folder = new File(plugin.getDataFolder(), "blocks");
        boolean generate = !folder.exists();
        if (!generate) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
            generate = files == null || files.length == 0;
        }
        if (generate) {
            for (String code : names) {
                File file = new File(folder, code);
                FileUtil.saveResource(
                    file,
                    getResource("/blocks/" + code + ".yml")
                );
            }
        }
    }

    public InputStream getResource(String location) {
        if (location == null) {
            return null;
        }
        InputStream src = FileUtil.build(location);
        return src == null ?
                this.getResource(location) :
                src;
    }

    @Override
    public void reload() {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        loadLanguage(plugin, "de", "en", "es", "fa", "fr", "jp", "pl");
        messages = fetchMessagesConfiguration(plugin);
        Implements.setEntry(FileConfiguration.class, "messages.yml", messages);
    }

    @Register(identifier = "messages.yml")
    public FileConfiguration provideMessages() {
        return messages;
    }

    private FileConfiguration fetchMessagesConfiguration(StylizedRegions plugin) {
        FileConfiguration settings = Implements.fetch(FileConfiguration.class, "settings.yml");
        String code = settings.getString("settings.default-lang", "en");
        File folder = new File(plugin.getDataFolder(), "languages");
        return plugin.load(
            new File(folder, code.replace(".yml", "") + ".yml"),
            "locale-template.yml"
        );
    }
}
