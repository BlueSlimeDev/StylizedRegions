package me.blueslime.stylizedregions.modules.flags.list.extras.greeting.listener;

import me.blueslime.bukkitmeteor.libs.utilitiesapi.text.TextUtilities;
import me.blueslime.stylizedregions.api.events.RegionJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatGreetingListener implements Listener {

    @EventHandler
    public void on(RegionJoinEvent event) {
        String greetingMessage = event.getRegion().getFlag(
            "greeting",
            "&aNow you are in a protected region of " + event.getRegion().getOwner()
        );

        if (greetingMessage.equalsIgnoreCase("none") || greetingMessage.isEmpty()) {
            return;
        }

        event.getPlayer().sendMessage(
            TextUtilities.colorize(
                greetingMessage.replace("\\n", "\n")
            )
        );
    }

}
