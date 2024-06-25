package me.blueslime.stylizedregions.service.getter;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.bukkitmeteor.implementation.registered.Register;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.flags.Flags;

public class MeteorLowerGetter implements Module {
    public MeteorLowerGetter() {
        register(this);
    }

    @Register
    public Flags provideFlagsService() {
        return new Flags(Implements.fetch(StylizedRegions.class));
    }
}
