package ru.simplemc.saveitems.data;

import org.bukkit.entity.Player;
import ru.simplemc.saveitems.data.entity.AbstractPlayerData;

import java.util.HashMap;

public class PlayersContentsMap<V extends AbstractPlayerData> extends HashMap<String, V> {

    public boolean containsPlayer(Player player) {
        return containsKey(player.getName());
    }

    public void put(Player player, V value) {
        super.put(player.getName(), value);
    }

    public void restore(Player player) {
        if (containsPlayer(player)) {
            get(player.getName()).restore(player);
            remove(player.getName());
        }
    }
}
