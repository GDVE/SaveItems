package ru.simplemc.saveitems.data.entity;

import org.bukkit.entity.Player;

public abstract class AbstractPlayerData {

    public AbstractPlayerData(Player player) {
    }

    public abstract void restore(Player player);
}
