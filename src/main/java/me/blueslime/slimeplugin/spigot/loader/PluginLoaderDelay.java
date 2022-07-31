package me.blueslime.slimeplugin.spigot.loader;

import me.blueslime.slimeplugin.spigot.Main;
import me.blueslime.slimeplugin.spigot.SlimeFile;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginLoaderDelay extends BukkitRunnable {

    private final Main main;

    public PluginLoaderDelay(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getLoader().setFiles(SlimeFile.class);

        main.getLoader().init();
    }
}
