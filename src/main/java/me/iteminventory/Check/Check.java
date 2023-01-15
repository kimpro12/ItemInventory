package me.iteminventory.Check;

import me.iteminventory.Task.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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
                for (Player p : Bukkit.getOnlinePlayers()) {
                    check1(p);
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public void check1(Player p) {
        Set<String> all = plugin.getConfig().getConfigurationSection("itemlist").getKeys(false);
        for (String idk : all) {
            try {
                Material m = Material.matchMaterial(idk);
                ItemStack i = new ItemStack(m);
                ItemMeta meta = i.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                lore.add(plugin.getConfig().getString("itemlist." + idk + ".lore"));
                meta.setLore(lore);
                meta.setDisplayName(plugin.getConfig().getString("itemlist." + idk + ".displayname"));
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
                i.setItemMeta(meta);
                lore.clear();
                if (hasItem(p, i)) {
                    List<String> stringList = plugin.getConfig().getStringList("command");
                    for (String idk2 : stringList) {
                        if (idk2.contains("%player")) {
                            String idk3 = idk2.replace("%player", p.getDisplayName());
                            if (idk2.contains("%op")) {
                                String idk4 = idk3.replace("%op", getOperators().getDisplayName());
                                if (idk2.contains("%item")) {
                                    String idk5 = idk4.replace("%item", i.getType().name());
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk5);
                                    continue;
                                }
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk4);
                                continue;
                            }
                            if (idk2.contains("item")) {
                                String idk5 = idk3.replace("%item", i.getType().name());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk5);
                                continue;
                            }
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk3);
                            continue;
                        }
                        if (idk2.contains("%op") && !(idk2.contains("%player"))) {
                            String idk4 = idk2.replace("%op", getOperators().getDisplayName());
                            if (idk2.contains("item")) {
                                String idk5 = idk4.replace("%item", i.getType().name());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk5);
                                continue;
                            }
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk4);
                            continue;
                        }
                        if (idk2.contains("%item") && !(idk2.contains("%player")) && !(idk2.contains("%op"))) {
                            String idk5 = idk2.replace("%item", i.getType().name());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), idk5);
                        }
                    }
                } else {

                }
            } catch (Exception e) {

            }
        }
    }

    public boolean hasItem(Player p, ItemStack item) {
        for (int i = plugin.getConfig().getInt("slot.start"); i <= plugin.getConfig().getInt("slot.end"); i++) {
            ItemStack i1 = p.getInventory().getItem(i);
            //if (i1.getType() == null || i1.getType() != item.getType()) continue;
            int slot = p.getInventory().first(i1);
            if (slot == i) {
                if (isEqual(i1, item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEqual(ItemStack i1, ItemStack i2) {
        if (i1.getType().equals(i2.getType())) {
            if (i2.getItemMeta().hasLore()) {
                if (!(i1.getItemMeta().hasLore())) {
                    return false;
                }
                if (i1.getItemMeta().getLore().equals(i2.getItemMeta().getLore())) {
                    if (i2.getItemMeta().hasDisplayName()) {
                        if (!(i1.getItemMeta().hasDisplayName())) {
                            return false;
                        }
                        if (i1.getItemMeta().getDisplayName().equals(i2.getItemMeta().getDisplayName())) {
                            return true;
                        }
                    }
                    if (i1.getItemMeta().hasDisplayName()) {
                        if (!(i2.getItemMeta().hasDisplayName())) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            if (i1.getItemMeta().hasLore()) {
                if (!(i2.getItemMeta().hasLore())) {
                    return false;
                }
            }
            if (i1.getItemMeta().hasDisplayName()) {
                if (!(i2.getItemMeta().hasDisplayName())) {
                    return false;
                }
            }
            if (i2.getItemMeta().hasDisplayName()) {
                if (!(i1.getItemMeta().hasDisplayName())) {
                    return false;
                }
                if (i1.getItemMeta().getDisplayName().equals(i2.getItemMeta().getDisplayName())) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    public Player getOperators() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
                return p;
            }
        }
        return null;
    }
}
