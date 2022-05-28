package ru.simplemc.saveitems;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.simplemc.saveitems.listener.PlayerListener;

public class SaveItems extends JavaPlugin {

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }
}
