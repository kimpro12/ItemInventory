package me.iteminventory.Command;

import me.iteminventory.Check.Check;
import me.iteminventory.Color.color;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class command implements CommandExecutor {
    private final JavaPlugin plugin;
    public command(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("iteminventory").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("iteminventory.reload") || p.hasPermission("iteminventory")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        plugin.reloadConfig();
                        p.sendMessage("[ItemInventory] plugin reload successfully");
                    }
                }
            }
            if (p.hasPermission("iteminventory.check") || p.hasPermission("iteminventory")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("check")) {
                        new Check(plugin).check1();
                    }
                }
            }
            if (p.hasPermission("iteminventory")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(ChatColor.AQUA + "[ItemInventory] -------------------------Plugin made by FIP----------------------------");
                        p.sendMessage(ChatColor.AQUA + "[ItemInventory] /iteminventory check to check all player if has a custom item in config");
                        p.sendMessage(ChatColor.AQUA + "[ItemInventory] /iteminventory reload to reload config");
                    }
                }
            }
            else {
                p.sendMessage(color.transalate(plugin.getConfig().getString("message")));
            }
        }
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.AQUA + "[ItemInventory] -------------------------Plugin made by FIP----------------------------");
                    sender.sendMessage(ChatColor.AQUA + "[ItemInventory] /iteminventory check to check all player if has a custom item in config");
                    sender.sendMessage(ChatColor.AQUA + "[ItemInventory] /iteminventory reload to reload config");
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage("[ItemInventory] plugin reload successfully");
                }
                if (args[0].equalsIgnoreCase("check")) {
                    new Check(plugin).check1();
                }
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.AQUA + "[ItemInventory] ----------Plugin made by FIP-----------");
                sender.sendMessage(ChatColor.AQUA + "[ItemInventory] /iteminventory help to see all commands");
            }
        }
        return true;
    }
}
