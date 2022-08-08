package me.blueslime.stylizedregions.runnable;

import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.stylizedregions.SlimeFile;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.region.Region;
import me.blueslime.stylizedregions.region.user.RegionUser;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRegionRunnable extends BukkitRunnable {

    private final StylizedRegions plugin;

    public PlayerRegionRunnable(StylizedRegions plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        try {
            cancel();
        } catch (Exception ignored) {}

        ConfigurationHandler settings = plugin.getConfigurationHandler(SlimeFile.SETTINGS);
        boolean async = settings.getBoolean("settings.runnable.async", true);
        long delay = settings.getLong("settings.runnable.delay", 20L);

        if (async) {
            runTaskTimerAsynchronously(
                    plugin,
                    0L,
                    delay
            );
        } else {
            runTaskTimer(
                    plugin,
                    0L,
                    delay
            );
        }
    }

    public void update() {
        load();
    }

    @Override
    public void run() {
        for (RegionUser user : plugin.getUsers().getUsers()) {
            fetchRegion(user);
        }
    }

    private void fetchRegion(RegionUser user) {
        for (Region region : plugin.getRegionLoader().getRegionList()) {
            boolean isIn = region.getRegion().isIn(user.getLocation());

            if (!isIn && user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion("");
                region.send(false, user.getPlayer());
            }

            if (isIn && !user.getCurrentRegion().equals(region.getId())) {
                user.setCurrentRegion(region.getId());
                region.send(true, user.getPlayer());
                continue;
            }
        }
    }
}
