package me.blueslime.stylizedregions.modules.flags.list.player.teleport.listener;

import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextUtilities;
import me.blueslime.stylizedregions.modules.region.Region;
import me.blueslime.stylizedregions.utils.entities.EntitiesTools;
import me.blueslime.stylizedregions.utils.region.RegionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Locale;
import java.util.Optional;

public class PlayerTeleportListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (EntitiesTools.isNPC(player)) {
            return;
        }

        Optional<Region> regionOptional = RegionUtil.getRegionAt(event.getTo());

        if (!regionOptional.isPresent()) {
            return;
        }

        Region region = regionOptional.get();

        boolean joinWithEnderPearl = region.getFlag(
            "join-via-enderpearl",
            false
        );

        boolean joinWithTeleport = region.getFlag(
            "join-via-teleport",
            false
        );

        String cause = event.getCause().toString().toLowerCase(Locale.ENGLISH);

        if (cause.equals("ender_pearl")) {
            if (!joinWithTeleport || !joinWithEnderPearl) {
                String entryDenyMessage = !joinWithEnderPearl ? region.getFlag(
                    "deny-enderpearl-message",
                    "&cCan't join to the region of " + region.getOwner() + " with ender pearl"
                ) : region.getFlag(
                    "deny-teleport-message",
                    "&cCan't join to the region of " + region.getOwner() + " with teleport"
                );

                if (entryDenyMessage.equalsIgnoreCase("none") || entryDenyMessage.isEmpty()) {
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(
                    TextUtilities.colorize(
                        entryDenyMessage.replace("\\n", "\n")
                    )
                );
            }
            return;
        }

        boolean joinWithChorusFruit = region.getFlag(
            "join-via-chorus-fruit",
            false
        );

        if (cause.equals("chorus_fruit")) {
            if (!joinWithChorusFruit || !joinWithTeleport) {
                String entryDenyMessage = !joinWithChorusFruit ? region.getFlag(
                    "deny-chorus-fruit-message",
                    "&cCan't join to the region of " + region.getOwner() + " with chorus fruit"
                ) : region.getFlag(
                    "deny-teleport-message",
                    "&cCan't join to the region of " + region.getOwner() + " with teleport"
                );

                if (entryDenyMessage.equalsIgnoreCase("none") || entryDenyMessage.isEmpty()) {
                    return;
                }
                event.setCancelled(true);
                player.sendMessage(
                    TextUtilities.colorize(
                        entryDenyMessage.replace("\\n", "\n")
                    )
                );
                return;
            }
        }
    }
}
