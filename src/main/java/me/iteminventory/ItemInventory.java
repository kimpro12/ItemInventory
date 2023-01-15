package me.iteminventory;

import me.iteminventory.Check.Check;
import me.iteminventory.Command.command;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemInventory extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("[ItemInventory] Plugin enabled");
        if (getConfig().getBoolean("auto-check")) {
            new Check(this).check();
        }
        new command(this);
        int pluginId = 17409; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[ItemInventory] Plugin disabled");
    }
}
