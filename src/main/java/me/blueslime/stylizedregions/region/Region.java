package me.blueslime.stylizedregions.region;

import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.file.configuration.TextDecoration;
import me.blueslime.stylizedregions.SlimeFile;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.region.utils.Cuboid;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Region {
    private final ConfigurationHandler configuration;
    private final StylizedRegions plugin;
    private final String name;
    private final UUID uuid;
    private final String id;
    private Cuboid region;

    public Region(StylizedRegions plugin, ConfigurationHandler configuration, String id, String uuid, String name, Cuboid cuboid) {
        this.configuration = configuration;
        this.plugin = plugin;
        this.region = cuboid;
        this.uuid   = UUID.fromString(uuid);
        this.name   = name;
        this.id = id;
    }

    /**
     * Replace the region
     * @param cuboid Cuboid
     */
    public void replaceRegion(Cuboid cuboid) {
        this.region = cuboid;
    }

    /**
     * Get the UniqueId of the owner of the region
     * @return UUID
     */
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Get the owner's name of the region
     * @return Username
     */
    public String getOwner() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void send(boolean isJoining, Player player) {
        Flags flag;
        Flags secondFlag;
        if (isJoining) {
            flag = Flags.JOIN_MESSAGE;
            secondFlag = Flags.JOIN_TITLE;
        } else {
            flag = Flags.QUIT_MESSAGE;
            secondFlag = Flags.QUIT_TITLE;
        }

        player.sendMessage(
            configuration.getString(
                    TextDecoration.LEGACY,
                    "flags." + flag.getPath(),
                    (String)flag.getDefault(
                        plugin.getConfigurationHandler(SlimeFile.SETTINGS)
                    )
            )
        );

        String[] split = configuration.getString(
                TextDecoration.LEGACY,
                "flags." + secondFlag.getPath(),
                (String)flag.getDefault(
                        plugin.getConfigurationHandler(SlimeFile.SETTINGS)
                )
        ).split("%nl%");

        if (split.length == 1) {
            //TODO: To-Do This is only when it only have a title
            //TODO: MessageUtil.sendTitle(player, split[0]);
        } else {
            //TODO: To-Do This is only when it have title and subtitle
            //TODO: MessageUtil.sendTitle(player, split[0], split[1]);
        }
    }

    /**
     * Get the region Cuboid
     * @return Cuboid
     */
    public Cuboid getRegion() {
        return region;
    }

    public enum Flags {
        CREEPER_EXPLOSION("creeper-explosion"),
        QUIT_MESSAGE("quit-message"),
        JOIN_MESSAGE("join-message"),
        BLOCK_PLACE("block-place"),
        BLOCK_BREAK("block-break"),
        QUIT_TITLE("quit-title"),
        JOIN_TITLE("join-title"),
        INTERACT("interact"),
        PVP("pvp");

        private final String path;

        Flags(String path) {
            this.path = path;
        }

        public Object getDefault(ConfigurationHandler configuration) {
            return configuration.get("default-region-flags." + path);
        }

        public String getPath() {
            return path;
        }
    }
}
