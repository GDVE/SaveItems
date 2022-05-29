package ru.simplemc.saveitems.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import ru.simplemc.saveitems.SaveItems;
import ru.simplemc.saveitems.manager.PlayersSavingManager;

public class PlayerListener implements Listener {

    private final SaveItems plugin;
    private final PlayersSavingManager manager = new PlayersSavingManager();

    public PlayerListener(SaveItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (player.getWorld().getName().equalsIgnoreCase("vethea")) {
            return;
        }

        if (manager.canSavePlayerInventory(player)) {
            if (!manager.isPlayerAlreadyInventorySaved(player)) {
                manager.savePlayerInventory(player);
                event.setKeepInventory(true);
            }

            event.getDrops().clear();
            Bukkit.getScheduler().runTaskLater(plugin, () -> manager.tryToRespawnPlayer(player), 60L);
        }

        if (manager.canSavePlayerExperience(player)) {
            if (!manager.isPlayerExperienceAlreadySaved(player)) {
                manager.savePlayerExperience(player);
            }

            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> manager.restorePlayer(event.getPlayer()), 10L);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if ((player.hasPermission("SaveItems.items") && player.getHealth() < 3.0D)
                || manager.isPlayerAlreadyInventorySaved(player)) {

            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("SaveItems.level") || player.hasPermission("SaveItems.items")) {
            manager.tryToRespawnPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("SaveItems.level") || player.hasPermission("SaveItems.items")) {
            manager.tryToRespawnPlayer(player);
        }
    }

}
