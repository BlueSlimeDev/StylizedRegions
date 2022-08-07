package me.blueslime.stylizedregions.loader;

import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.SlimeFile;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginLoaderDelay extends BukkitRunnable {

    private final StylizedRegions main;

    public PluginLoaderDelay(StylizedRegions main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getLoader().setFiles(SlimeFile.class);

        main.getLoader().init();
    }
}
