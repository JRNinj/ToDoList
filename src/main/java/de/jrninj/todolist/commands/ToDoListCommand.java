package de.jrninj.todolist.commands;

import de.jrninj.todolist.Todolist;
import de.jrninj.todolist.utils.ToDoListAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoListCommand implements CommandExecutor, TabCompleter {

    public static Inventory inv = Bukkit.createInventory(null, 9*3, "§c§lTO§3§lDO §2§lKategorie");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("todolist.command.todo")) {
                if(args.length == 0) {

                    //Paper
                    ItemStack paper = new ItemStack(Material.PAPER);
                    ItemMeta paperm = paper.getItemMeta();
                    paperm.setDisplayName("§6TO-DO Kategorieübersicht");
                    List<String> lore = new ArrayList<>();
                    lore.add("§fWähle eine Kategorie für die To-Do Liste!");
                    paperm.setLore(lore);
                    paper.setItemMeta(paperm);

                    //Builder
                    ItemStack grass = new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta grassm = grass.getItemMeta();
                    grassm.setDisplayName("§3Builder");
                    lore.clear();
                    lore.add("§fWähle Builder TO-DO Liste!");
                    grassm.setLore(lore);
                    grass.setItemMeta(grassm);

                    //Support
                    ItemStack dia = new ItemStack(Material.DIAMOND_BLOCK);
                    ItemMeta diam = dia.getItemMeta();
                    diam.setDisplayName("§3Support");
                    lore.clear();
                    lore.add("§fWähle Support TO-DO Liste!");
                    diam.setLore(lore);
                    dia.setItemMeta(diam);

                    //Developer
                    ItemStack cb = new ItemStack(Material.COMMAND_BLOCK);
                    ItemMeta cbm = cb.getItemMeta();
                    cbm.setDisplayName("§3Developer");
                    lore.clear();
                    lore.add("§fWähle Developer TO-DO Liste!");
                    cbm.setLore(lore);
                    cb.setItemMeta(cbm);

                    //Designer
                    ItemStack flower = new ItemStack(Material.POPPY);
                    ItemMeta flowerm = flower.getItemMeta();
                    flowerm.setDisplayName("§3Designer");
                    lore.clear();
                    lore.add("§fWähle Designer TO-DO Liste!");
                    flowerm.setLore(lore);
                    flower.setItemMeta(flowerm);

                    //Allgemein
                    ItemStack wool = new ItemStack(Material.WHITE_WOOL);
                    ItemMeta woolm = wool.getItemMeta();
                    woolm.setDisplayName("§3Allgemein");
                    lore.clear();
                    lore.add("§fWähle Allgemein TO-DO Liste!");
                    woolm.setLore(lore);
                    wool.setItemMeta(woolm);

                    //Personal
                    ItemStack gold = new ItemStack(Material.GOLD_BLOCK);
                    ItemMeta goldm = gold.getItemMeta();
                    goldm.setDisplayName("§3Persönlich");
                    lore.clear();
                    lore.add("§fWähle Persönliche TO-DO Liste!");
                    goldm.setLore(lore);
                    gold.setItemMeta(goldm);

                    //Close
                    ItemStack close = new ItemStack(Material.BARRIER);
                    ItemMeta closem = close.getItemMeta();
                    closem.setDisplayName("§cSchlieẞen");
                    lore.clear();
                    lore.add("§fSchlieẞe das Menü!");
                    closem.setLore(lore);
                    close.setItemMeta(closem);

                    inv.setItem(4, paper);
                    inv.setItem(10, grass);
                    inv.setItem(12, dia);
                    inv.setItem(14, cb);
                    inv.setItem(16, flower);
                    inv.setItem(21, wool);
                    inv.setItem(23, gold);
                    inv.setItem(18, close);
                    player.openInventory(inv);

                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("show")) {
                        if(player.hasPermission("todolist.command.todo.show")) {

                            if(Bukkit.getPlayer(args[1]) == null && Bukkit.getOfflinePlayer(args[1]) == null) {
                                player.sendMessage(Todolist.PREFIX + "§4Der Spieler §6" + args[1] + " §4existiert nicht!");
                                return false;
                            }

                            ToDoListAPI.show(args[1], player.getName());

                        } else
                            player.sendMessage(Todolist.PREFIX + "§4Dafür hast du keine Rechte!");
                    } else
                        player.sendMessage(Todolist.PREFIX + "§4Bitte benutze nur: §6/todo <show> <Spieler>");
                } else
                    player.sendMessage(Todolist.PREFIX + "§4Bitte benutze nur: §6/todo");
            } else
                player.sendMessage(Todolist.PREFIX + "§4Dafür hast du keine Rechte!");
        } else
            sender.sendMessage(Todolist.PREFIX + "§4Deisen Befehl kann nur ein Spieler verwenden!");

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        ArrayList<String> tc =  new ArrayList<>();
        if(args.length == 1) {
            tc.add("show");
            return tc.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        } else if(args.length == 2) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                tc.add(all.getName());
            }
            return tc.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        }

        return null;
    }
}
