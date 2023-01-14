package me.iteminventory.Check;

import me.iteminventory.Task.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Check {
    private final JavaPlugin plugin;

    public Check(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void check() {
        new Task() {
            @Override
            public void run() {
                check1();
            }
        }.runTaskTimer(plugin, 20, 20);
    }
    public void check1() {
        Set<String> all = plugin.getConfig().getConfigurationSection("itemlist").getKeys(false);
        for (String idk : all) {
            try {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Material m = Material.matchMaterial(idk);
                    ItemStack i = new ItemStack(m);
                    ItemMeta meta = i.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(plugin.getConfig().getString("itemlist." + idk + ".lore"));
                    meta.setLore(lore);
                    meta.setDisplayName(plugin.getConfig().getString("itemlist." + idk + ".displayname"));
                    meta.setLocalizedName(plugin.getConfig().getString("itemlist." + idk + ".nametag"));
                    String lore1 = plugin.getConfig().getString("itemlist." + idk + ".lore");
                    if (lore1.equalsIgnoreCase("none")) {
                        meta.setLore(null);
                        lore.remove(lore1);
                    }
                    String displayename1 = plugin.getConfig().getString("itemlist." + idk + ".displayname");
                    if (displayename1.equalsIgnoreCase("none")) {
                        meta.setDisplayName(null);
                        lore.remove(displayename1);
                    }
                    String nametag = plugin.getConfig().getString("itemlist." + idk + ".nametag");
                    if (nametag.equalsIgnoreCase("none")) {
                        meta.setLocalizedName(null);
                        lore.remove(nametag);
                    }
                    i.setItemMeta(meta);
                    lore.clear();
                    if (hasItem(p, i)) {
                        for (String idk2 : plugin.getConfig().getStringList("command")) {
                            if (idk2.contains("%player")) {
                                String idk3 = idk2.replace("%player", p.getDisplayName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk3);
                            } else {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk2);
                            }
                        }
                    } else {

                    }
                }
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("[ItemInventory] wtf is item " + idk + " is set in config");
            }
        }
    }
    public boolean hasItem(Player p, ItemStack item) {
        for (int i = plugin.getConfig().getInt("slot.start"); i <= plugin.getConfig().getInt("slot.end"); i++) {
            //Check if item in each slot of inventory equal to item

            if (p.getInventory().getStorageContents()[i].getType().equals(item.getType())) {
                if (item.getItemMeta().hasLore() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLocalizedName()) {
                    if (p.getInventory().getStorageContents()[i].getItemMeta().getLore().equals(item.getItemMeta().getLore()) && p.getInventory().getStorageContents()[i].getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()) && p.getInventory().getStorageContents()[i].getItemMeta().getLocalizedName().equals(item.getItemMeta().getLocalizedName())) {
                        return true;
                    }
                }
                if (item.getItemMeta().hasLore()) {
                    if (p.getInventory().getStorageContents()[i].getItemMeta().getLore().equals(item.getItemMeta().getLore())) {
                        if (!(item.getItemMeta().hasDisplayName()) && item.getItemMeta().hasLocalizedName()) {
                            if (p.getInventory().getStorageContents()[i].getItemMeta().getLocalizedName().equals(item.getItemMeta().getLocalizedName())) {
                                return true;
                            }
                        }
                        if (!(item.getItemMeta().hasLocalizedName()) && item.getItemMeta().hasDisplayName()) {
                            if (p.getInventory().getStorageContents()[i].getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                                return true;
                            }
                        }
                        return true;
                    }
                }
                if (item.getItemMeta().hasDisplayName()) {
                    if (p.getInventory().getStorageContents()[i].getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                        if (item.getItemMeta().hasLocalizedName()) {
                            if (p.getInventory().getStorageContents()[i].getItemMeta().getLocalizedName().equals(item.getItemMeta().getLocalizedName())) {
                                return true;
                            }
                        }
                        return true;
                    }
                }
                if (item.getItemMeta().hasLocalizedName()) {
                    if (p.getInventory().getStorageContents()[i].getItemMeta().getLocalizedName().equals(item.getItemMeta().getLocalizedName())) {
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
