package ru.simplemc.saveitems.manager;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.simplemc.saveitems.data.PlayersContentsMap;
import ru.simplemc.saveitems.data.entity.PlayerExperience;
import ru.simplemc.saveitems.data.entity.PlayerInventory;

public class PlayersSavingManager {

    private final PlayersContentsMap<PlayerInventory> playersInventories = new PlayersContentsMap<>();
    private final PlayersContentsMap<PlayerExperience> playersExperiences = new PlayersContentsMap<>();

    public boolean isPlayerInventorySaved(Player player) {
        return playersInventories.containsPlayer(player);
    }

    public boolean canSavePlayerInventory(Player player) {

        ApplicableRegionSet regions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());

        if (regions.size() > 0) {
            for (ProtectedRegion region : regions) {
                if (region.getId().equalsIgnoreCase("pvp_drops")) {
                    player.sendMessage("§cВаш инвентарь не сохранен. (Вы погибли на PvP-арене с выпадением вещей)");
                    return false;
                }
            }
        }

        return player.hasPermission("SaveItems.items") && !isPlayerInventorySaved(player);
    }

    public boolean canSavePlayerExperience(Player player) {
        return player.hasPermission("SaveItems.experience") && !playersExperiences.containsPlayer(player);
    }

    public void savePlayerInventory(Player player) {
        playersInventories.put(player, new PlayerInventory(player));
    }

    public void savePlayerExperience(Player player) {
        playersExperiences.put(player, new PlayerExperience(player));
    }

    public void restorePlayer(Player player) {
        playersInventories.restore(player);
        playersExperiences.restore(player);
    }

    public void tryToRespawnPlayer(Player player) {
        if (player.isDead()) {
            ((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
        }
    }
}
