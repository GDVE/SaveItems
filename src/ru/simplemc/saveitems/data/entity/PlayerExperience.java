package ru.simplemc.saveitems.data.entity;

import org.bukkit.entity.Player;

public class PlayerExperience extends AbstractPlayerData {

    private final int experience;

    public PlayerExperience(Player player) {

        super(player);
        this.experience = player.getLevel();

        if (experience > 0) {
            player.sendMessage("§6Ваш опыт сохранен.");
            player.setLevel(0);
        }
    }

    @Override
    public void restore(Player player) {
        player.setLevel(experience);
        player.sendMessage("§6Ваш опыт восстановлен.");
    }
}
