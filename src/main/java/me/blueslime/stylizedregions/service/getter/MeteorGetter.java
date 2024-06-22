package me.blueslime.stylizedregions.service.getter;

import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.bukkitmeteor.implementation.registered.Register;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.service.region.RegionService;
import me.blueslime.stylizedregions.service.users.UserService;

import java.io.File;

public class MeteorGetter implements Module {
    private final StylizedRegions plugin;

    public MeteorGetter(StylizedRegions plugin) {
        this.plugin = plugin;
        register(this);
    }

    @Register
    public StylizedRegions providePlugin() {
        return plugin;
    }

    @Register(identifier = "folder")
    public File provideDataFolder() {
        return plugin.getDataFolder();
    }

    @Register
    public UserService provideUserService() {
        return new UserService(plugin);
    }

    @Register
    public RegionService provideRegionService() {
        return new RegionService(plugin);
    }
}

