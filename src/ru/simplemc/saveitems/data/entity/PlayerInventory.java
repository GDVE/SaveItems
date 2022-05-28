package ru.simplemc.saveitems.data.entity;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory extends AbstractPlayerData {

    private final ItemStack[] items;
    private final ItemStack[] armor;

    public PlayerInventory(Player player) {

        super(player);

        this.items = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();

        if (items != null && items.length > 0) {
            player.sendMessage("§6Ваш инвентарь сохранен.");
        }

        if (armor != null && armor.length > 0) {
            player.sendMessage("§6Ваша броня сохранена.");
        }

        player.getInventory().clear();
    }

    @Override
    public void restore(Player player) {

        if (items != null && items.length > 0) {
            player.getInventory().setContents(items);
            player.sendMessage("§6Ваш инвентарь восстановлен.");
        }

        if (armor != null && armor.length > 0) {
            player.getInventory().setArmorContents(armor);
            player.sendMessage("§6Ваша броня восстановлена.");
        }
    }
}
