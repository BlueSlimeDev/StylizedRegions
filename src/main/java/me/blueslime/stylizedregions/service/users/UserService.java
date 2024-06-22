package me.blueslime.stylizedregions.service.users;

import me.blueslime.bukkitmeteor.implementation.module.Module;
import me.blueslime.stylizedregions.StylizedRegions;
import me.blueslime.stylizedregions.modules.region.user.RegionUser;
import me.blueslime.stylizedregions.modules.storage.JsonData;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserService implements Module {
    private final ConcurrentHashMap<UUID, RegionUser> userMap = new ConcurrentHashMap<>();
    private final JsonData<String, Map<String, String>> region;

    public UserService(StylizedRegions plugin) {
        this.region = new JsonData<>(
            plugin.getDataFolder(),
            "players",
            String.class,
            String.class
        );
        register(this);
    }

    public RegionUser fetchRegionUser(Player player) {
        RegionUser user = userMap.computeIfAbsent(player.getUniqueId(), k -> new RegionUser(player));

        if (!user.isLoaded()) {
            Map<String, String> data = region.computeIfAbsent(
                    player.getUniqueId().toString(),
                    dataMap(player)
            );

            String regions = data.get("regions").replace(" ", "");

            if (regions.isEmpty()) {
                user.setLoadedStatus(true);
            } else {

                String[] split = regions.split(",");

                List<String> regionList = new ArrayList<>(Arrays.asList(split));

                user.setLoadedStatus(true);

                user.setRegionList(regionList);
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

        for (String region : user.getRegionList()) {
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
