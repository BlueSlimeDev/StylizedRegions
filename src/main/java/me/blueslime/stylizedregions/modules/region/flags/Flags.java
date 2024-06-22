package me.blueslime.stylizedregions.modules.region.flags;

import me.blueslime.bukkitmeteor.implementation.Implements;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public enum Flags {
    CREEPER_EXPLOSION,
    QUIT_MESSAGE,
    JOIN_MESSAGE,
    BLOCK_PLACE,
    BLOCK_BREAK,
    QUIT_TITLE,
    JOIN_TITLE,
    INTERACT,
    PVP;

    public Object getDefault(Object defaultValue) {
        return Implements.fetch(FileConfiguration.class, "settings.yml").get("default-region-flags." + getPathName(), defaultValue);
    }

    public String getPathName() {
        return toString().toLowerCase(Locale.ENGLISH).replace("_", "-");
    }

    public String toPath() {
        return "flags." + getPathName();
    }
}
