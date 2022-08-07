package me.blueslime.stylizedregions.commands;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.commands.sender.Sender;
import dev.mruniverse.slimelib.commands.sender.player.SlimePlayer;
import me.blueslime.stylizedregions.region.utils.RegionBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(
        description = "Main Command of the plugin",
        shortDescription = "Plugin Command",
        usage = "/region (arguments)"
)
public class PluginCommand implements SlimeCommand {
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
        if (sender.hasPermission("test")) {
            sender.sendColoredMessage("&aLoading..");

            SlimePlayer slimePlayer = (SlimePlayer)sender;
            Player player = slimePlayer.get();

            sender.sendColoredMessage("&aCurrent Location: " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ());

            Location pos1 = RegionBuilder.calculatePosition(
                    player.getLocation(),
                    true, 10, 10, 10
            );
            sender.sendColoredMessage("&aPos1: " +
                    pos1.getX() + ", " + pos1.getY() + ", " + pos1.getZ()
            );

            Location pos2 = RegionBuilder.calculatePosition(
                    player.getLocation(),
                    false, 10, 10, 10
            );
            sender.sendColoredMessage("&aPos2: " +
                    pos2.getX() + ", " + pos2.getY() + ", " + pos2.getZ()
            );

        }
    }
}
