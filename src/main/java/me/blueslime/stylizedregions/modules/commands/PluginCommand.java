package me.blueslime.stylizedregions.modules.commands;

import me.blueslime.bukkitmeteor.implementation.Implements;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.commands.SimpleCommand;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.commands.sender.Sender;
import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextReplacer;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.service.region.RegionService;
import me.blueslime.stylizedregions.service.users.UserService;
import me.blueslime.stylizedregions.utils.location.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PluginCommand extends SimpleCommand<StylizedRegions> {

    public PluginCommand(StylizedRegions plugin) {
        super(plugin, "regions");
    }

    @Override
    public void execute(Sender sender, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.send("&aCreated by JustJustin with &lLove");
            return;
        }

        String arg = args[0].toLowerCase();

        if (sender.isConsole()) {
            return;
        }

        Player player = sender.toPlayer();

        RegionUser user = Implements.fetch(UserService.class).fetchRegionUser(
            player
        );

        FileConfiguration messages = Implements.fetch(FileConfiguration.class, "messages.yml");

        switch (arg) {
            case "list":
                if (user.getRegionList().isEmpty()) {
                    sender.send(
                        player,
                        messages,
                        "region.list-empty"
                        , "&cYou don't have any regions created yet"
                    );
                    return;
                }
                sender.send(
                    player,
                    messages,
                    "region.list-header",
                    "&aRegion(s) [%size]:",
                    TextReplacer.builder()
                        .replace("%size", String.valueOf(user.getRegionList()))
                );

                String format = messages.getString("region.list-value", " &8- &7%id% - %location%");

                RegionService regionService = Implements.fetch(RegionService.class);

                for (String regions : user.getRegionList()) {
                    Region region = regionService.fetchRegion(regions);

                    if (region == null) {
                        continue;
                    }

                    Location loc = region.getRegion().getCenter();

                    String location = LocationSerializer.toString(loc);

                    sender.send(
                        format.replace("%id%", region.getId())
                            .replace("%location%", location)
                    );
                }
                return;
            case "info":
                if (user.getCurrentRegion() != null && !user.getCurrentRegion().isEmpty()) {
                    Region region = Implements.fetch(RegionService.class).fetchRegion(user.getCurrentRegion());
                    sender.send(
                        "&eRegion owner: " + region.getOwner(),
                        "&eRegion id: " + region.getId()
                    );
                }
                return;
            case "reload":
                Implements.fetch(StylizedRegions.class).reload();
                sender.send("&aPlugin has been reloaded.");
        }
    }
}
