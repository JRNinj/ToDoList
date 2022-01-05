package de.jrninj.todolist.utils;

import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.todolist.Todolist;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAPI {

    public static void add(String playername, String text) {

        if(PlayerData.getUUID(playername) == null) {
            return;
        }

        FileConfiguration config = Todolist.INSTANCE.getConfig();
        List<String> s = new ArrayList<>();
        if(config.getList("ToDoLists." + PlayerData.getUUID(playername)) != null) {
            s = (List<String>) config.getList("ToDoLists." + PlayerData.getUUID(playername));
            s.add(text);
        } else {
            s.add(text);
        }

        config.set("ToDoLists." + PlayerData.getUUID(playername), s);
        Todolist.INSTANCE.saveConfig();
    }

    public static void show(String targetname, String playername) {

        if(PlayerData.getUUID(targetname) == null) {
            return;
        }
        if(Bukkit.getPlayer(playername) == null) {
            return;
        }

        Player sender = (Player) Bukkit.getPlayer(playername);

        Inventory inv = Bukkit.createInventory(null, 6*9, "§0§lTO-DO Liste | §6" + PlayerData.getUUID(targetname));

        //Close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName("§cSchlieẞen");
        List<String> lore = new ArrayList<>();
        lore.clear();
        lore.add("§fSchlieẞe das Menü!");
        closem.setLore(lore);
        close.setItemMeta(closem);

        inv.setItem(45, close);

        FileConfiguration config = Todolist.INSTANCE.getConfig();
        List<String> s = new ArrayList<>();
        s = config.getStringList("ToDoLists." + PlayerData.getUUID(targetname));

        for(String task : s) {

            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperm = paper.getItemMeta();
            List<String> x = new ArrayList<>();
            x.add(" ");
            paperm.setLore(x);
            paperm.setDisplayName(task);
            paper.setItemMeta(paperm);

            inv.addItem(paper);

        }

        sender.openInventory(inv);

    }

}
