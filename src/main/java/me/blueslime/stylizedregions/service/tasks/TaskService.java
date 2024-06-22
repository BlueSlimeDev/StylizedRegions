package me.blueslime.stylizedregions.service.tasks;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.service.tasks.event.AsyncRegionTickEvent;
import org.bukkit.scheduler.BukkitTask;

public class TaskService implements Module {
    private BukkitTask task = null;

    @Override
    public void initialize() {
        StylizedRegions plugin = Implements.fetch(StylizedRegions.class);
        this.task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(
            plugin,
            () -> plugin.getServer().getPluginManager().callEvent(new AsyncRegionTickEvent(plugin)),
            180L,
            20L
        );
    }

    @Override
    public void shutdown() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }
}
