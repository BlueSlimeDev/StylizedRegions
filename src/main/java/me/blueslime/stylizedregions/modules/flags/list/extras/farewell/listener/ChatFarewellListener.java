package me.blueslime.stylizedregions.modules.flags.list.extras.farewell.listener;

import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextUtilities;
import me.blueslime.stylizedregions.api.events.RegionQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatFarewellListener implements Listener {

    @EventHandler
    public void on(RegionQuitEvent event) {
        String farewellMessage = event.getRegion().getFlag(
            "farewell",
            "&cLeaving protected region of " + event.getRegion().getOwner()
        );

        if (farewellMessage.equalsIgnoreCase("none") || farewellMessage.isEmpty()) {
            return;
        }

        event.getPlayer().sendMessage(
            TextUtilities.colorize(
                farewellMessage.replace("\\n", "\n")
            )
        );
    }

}
