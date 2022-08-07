package me.blueslime.stylizedregions.region.user;

import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.storage.JsonController;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private final ConcurrentHashMap<UUID, RegionUser> userMap = new ConcurrentHashMap<>();

    private final JsonController<String, Map<String, String>> region;
    private final StylizedRegions plugin;

    public UserManager(StylizedRegions plugin) {
        this.plugin = plugin;
        this.region = new JsonController<>(
                plugin.getLogs(),
                plugin.getDataFolder(),
                "players",
                String.class,
                String.class
        );
    }

    public RegionUser fetchRegionUser(Player player) {
        RegionUser user = userMap.computeIfAbsent(player.getUniqueId(), k -> new RegionUser(player));

        if (!user.isLoaded()) {
            Map<String, String> data = region.computeIfAbsent(
                    player.getUniqueId().toString(),
                    dataMap(player)
            );

            String regions = data.get("regions").replace(" ", "");

            if (regions.equals("")) {
                user.setStatus(true);
            } else {

                String[] split = regions.split(",");

                List<String> regionList = new ArrayList<>(Arrays.asList(split));

                user.setStatus(true);

                user.setRegions(regionList);
            }
        }

        return user;
    }

    public Collection<RegionUser> getUsers() {
        return userMap.values();
    }

    public void removeUser(Player player) {
        updateRegions(player);

        userMap.remove(player.getUniqueId());
    }

    public void updateRegions(Player player) {
        RegionUser user = userMap.computeIfAbsent(player.getUniqueId(), k -> new RegionUser(player));

        Map<String, String> data = region.computeIfAbsent(
                player.getUniqueId().toString(),
                dataMap(player)
        );

        StringBuilder list = new StringBuilder();

        for (String region : user.getRegions()) {
            list.append(region).append(",");
        }

        data.put("regions", list.toString());

        region.update(
                player.getUniqueId().toString(),
                data
        );
    }

    private Map<String, String> dataMap(Player player) {
        Map<String, String> map = new HashMap<>();

        map.put("username", player.getName());
        map.put("privateInt", "0");
        map.put("regions", "");

        return map;
    }

}
