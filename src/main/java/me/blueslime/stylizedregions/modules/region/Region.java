package me.blueslime.stylizedregions.modules.region;

import me.blueslime.bukkitmeteor.libs.messagehandler.types.titles.TitlesHandler;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextUtilities;
import me.blueslime.stylizedregions.modules.region.flags.Flags;
import me.blueslime.stylizedregions.utils.cuboid.Cuboid;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Region {
    private final FileConfiguration configuration;
    private final String name;
    private final UUID uuid;
    private final String id;
    private Cuboid region;

    public Region(FileConfiguration configuration, String id, String uuid, String name, Cuboid cuboid) {
        this.configuration = configuration;
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
            TextUtilities.colorize(
                configuration.getString(
                    flag.toPath(),
                    flag.getDefault(
                        flag.toPath()
                    ).toString()
                )
            )
        );

        String title = configuration.getString(
                secondFlag.toPath(),
                secondFlag.getDefault(
                        secondFlag.toPath()
                ).toString()
        );

        TitlesHandler.sendTitle(
            player,
            TextUtilities.colorize(title)
        );
    }

    /**
     * Get the region Cuboid
     * @return Cuboid
     */
    public Cuboid getRegion() {
        return region;
    }

}
