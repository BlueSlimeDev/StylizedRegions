package me.blueslime.stylizedregions;

import me.blueslime.bukkitmeteor.BukkitMeteorPlugin;
import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.stylizedregions.modules.flags.Flags;
import me.blueslime.stylizedregions.service.commands.CommandService;
import me.blueslime.stylizedregions.service.files.FileService;
import me.blueslime.stylizedregions.service.getter.MeteorGetter;
import me.blueslime.stylizedregions.service.getter.MeteorLowerGetter;
import me.blueslime.stylizedregions.service.region.RegionBlockService;
import me.blueslime.stylizedregions.service.region.RegionService;
import me.blueslime.stylizedregions.service.tasks.TaskService;
import me.blueslime.stylizedregions.service.users.UserService;

public class StylizedRegions extends BukkitMeteorPlugin {

    public void onEnable() {
        initialize(this);
    }

    @Override
    public void registerModules() {
        new MeteorGetter(this);
        new MeteorLowerGetter();

        // We use Implements.fetch because in the MeteorGetter we are already registering a new instance for that module
        // So we want to add these modules to the getModule of the plugin too

        registerModule(
            new FileService(),
            Implements.fetch(Flags.class),
            Implements.fetch(UserService.class),
            Implements.fetch(RegionService.class),
            Implements.fetch(RegionBlockService.class),
            new TaskService(),
            new CommandService()
        );
    }

    @Override
    public void build() {

    }
}
