package me.blueslime.stylizedregions.commands;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.commands.sender.Sender;
import dev.mruniverse.slimelib.commands.sender.player.SlimePlayer;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.region.Region;
import me.blueslime.stylizedregions.region.user.RegionUser;
import me.blueslime.stylizedregions.utils.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(
        description = "Main Command of the plugin",
        shortDescription = "Plugin Command",
        usage = "/region (arguments)"
)
public class PluginCommand implements SlimeCommand {

    private final StylizedRegions plugin;

    public PluginCommand(StylizedRegions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommand() {
        return "region";
    }

    @Override
    public void execute(Sender sender, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendColoredMessage("&aCreated by JustJustin with &lLove");
            return;
        }

        String arg = args[0].toLowerCase();

        if (sender.isConsoleSender()) {
            //TODO: Console Sender Commands
            return;
        }

        Player player = ((SlimePlayer)sender).get();

        RegionUser user = plugin.getUsers().fetchRegionUser(
            player
        );

        ConfigurationHandler messages = plugin.getMessages();

        switch (arg) {
            case "list":
                if (user.getRegionSize() == 0) {
                    sender.sendColoredMessage(
                            messages.getString("region.list-empty", "&cYou don't have any regions created yet")
                    );
                    return;
                }
                sender.sendColoredMessage(
                        messages.getString("region.list-header", "&aRegion(s) [%size]:").replace(
                                "%size",
                                user.getRegionSize() + ""
                        )
                );

                String format = messages.getString("region.list-value", " &8- &7%id% - %location%");
                for (String regions : user.getRegions()) {
                    Region region = plugin.getRegionLoader().fetchRegion(regions);

                    if (region == null) {
                        continue;
                    }

                    Location loc = region.getRegion().getCenter();

                    String location = LocationSerializer.toString(loc);

                    sender.sendColoredMessage(
                            format.replace("%id%", region.getId())
                                    .replace("%location%", location)
                    );
                }
                return;
            case "info":
                if (user.getCurrentRegion() != null && !user.getCurrentRegion().equals("")) {
                    Region region = plugin.getRegionLoader().fetchRegion(user.getCurrentRegion());
                    //TODO: Info Page
                }
        }
        //TODO: ToDo
    }
}
