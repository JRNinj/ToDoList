package de.jrninj.todolist.listener;

import de.jrninj.todolist.Todolist;
import de.jrninj.todolist.commands.ToDoListCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InvListener implements Listener {

    private static HashMap<UUID, Boolean> chatlistener = new HashMap<>();
    private static HashMap<UUID, Integer> collection = new HashMap<>();
    private static Inventory inv1 = Bukkit.createInventory(null, 3*9, "§0§lWähle eine Aktion aus!");
    private int taskID;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;

        if(e.getView().getTitle().equals("§c§lTO§3§lDO §2§lKategorie")) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Builder")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 1);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Support")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 2);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Developer")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 3);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Designer")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 4);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Allgemein")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 5);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§3Persönlich")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                actionselecter(player, 6);
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSchlieẞen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.closeInventory();
            }
        } else if(e.getView().getTitle().equals("§0§lWähle eine Aktion aus!")) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Ansehen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                bookopener(player);

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2Hinzufügen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                adder(player);

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cEntfernen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 5);

                delete(player);

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8Zurück")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.openInventory(ToDoListCommand.inv);

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSchlieẞen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.closeInventory();
            }
        } else if(e.getView().getTitle().equals("§0§lWähle ein Eintrag aus!")) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSchlieẞen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.closeInventory();

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8Zurück")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.openInventory(InvListener.inv1);

            } else {

                String text = e.getCurrentItem().getItemMeta().getDisplayName();
                FileConfiguration config = Todolist.INSTANCE.getConfig();
                List<String> s = new ArrayList<>();

                String list = "§4ERROR";
                if(collection.get(player.getUniqueId()).equals(1)) {
                    list = "Builder";
                } else if(collection.get(player.getUniqueId()).equals(2)) {
                    list = "Support";
                } else if(collection.get(player.getUniqueId()).equals(3)) {
                    list = "Developer";
                } else if(collection.get(player.getUniqueId()).equals(4)) {
                    list = "Designer";
                } else if(collection.get(player.getUniqueId()).equals(5)) {
                    list = "Allgemein";
                } else if(collection.get(player.getUniqueId()).equals(6)) {
                    list = "Persönlich";

                    if(config.getStringList("ToDoLists." + player.getUniqueId()).contains(text)) {

                        s = config.getStringList("ToDoLists." + player.getUniqueId());

                        s.remove(text);
                        config.set("ToDoLists." + player.getUniqueId(), s);
                        Todolist.INSTANCE.saveConfig();

                        player.sendMessage(Todolist.PREFIX + "§2Du hast: §f" + text + " §2- von der To-Do Liste §6" + list + " §2entfernt!");
                        delete(player);

                    } else {
                        player.closeInventory();
                        player.sendMessage(Todolist.PREFIX + "§4Es ist ein Fehler aufgetreten!");
                    }

                    return;

                }

                if(config.getStringList("ToDoLists." + list).contains(text)) {

                    s = config.getStringList("ToDoLists." + list);

                    s.remove(text);
                    config.set("ToDoLists." + list, s);
                    Todolist.INSTANCE.saveConfig();

                    player.sendMessage(Todolist.PREFIX + "§2Du hast: §f" + text + " §2- von der To-Do Liste §6" + list + " §2entfernt!");
                    delete(player);

                } else {
                    player.closeInventory();
                    player.sendMessage(Todolist.PREFIX + "§4Es ist ein Fehler aufgetreten!");
                }

            }
        } else if(e.getView().getTitle().startsWith("§0§lTO-DO Liste | §6")) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§cSchlieẞen")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.closeInventory();

            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8Zurück")) {
                player.playSound(e.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
                player.openInventory(InvListener.inv1);

            }
        }
    }

    public static void actionselecter(Player player, Integer selection) {

        collection.put(player.getUniqueId(), selection);

        //Anzeigen
        ItemStack show = new ItemStack(Material.SPYGLASS);
        ItemMeta showm = show.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§fSchau dir die To-DO Liste an!");
        showm.setDisplayName("§6Ansehen");
        showm.setLore(lore);
        show.setItemMeta(showm);

        //Hinzufügen
        ItemStack add = new ItemStack(Material.GREEN_WOOL);
        ItemMeta addm = add.getItemMeta();
        lore.clear();
        lore.add("§fFüge eine Aufgabe zur TO-DO Liste hinzu!");
        addm.setDisplayName("§2Hinzufügen");
        addm.setLore(lore);
        add.setItemMeta(addm);

        //Löschen
        ItemStack del = new ItemStack(Material.RED_WOOL);
        ItemMeta delm = del.getItemMeta();
        lore.clear();
        lore.add("§fLösche eine Aufgabe aus der TO-DO Liste!");
        delm.setDisplayName("§cEntfernen");
        delm.setLore(lore);
        del.setItemMeta(delm);

        //Close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName("§cSchlieẞen");
        lore.clear();
        lore.add("§fSchlieẞe das Menü!");
        closem.setLore(lore);
        close.setItemMeta(closem);

        //Back
        ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backm = back.getItemMeta();
        backm.setDisplayName("§8Zurück");
        lore.clear();
        lore.add("§fNavigiere ins vorherige Menü!");
        backm.setLore(lore);
        back.setItemMeta(backm);

        inv1.setItem(10, show);
        inv1.setItem(13, add);
        inv1.setItem(16, del);
        inv1.setItem(18, close);
        inv1.setItem(19, back);

        player.openInventory(inv1);

    }

    public static void bookopener(Player player) {

        String list = "§4ERROR";
        if(collection.get(player.getUniqueId()).equals(1)) {
            list = "Builder";
        } else if(collection.get(player.getUniqueId()).equals(2)) {
            list = "Support";
        } else if(collection.get(player.getUniqueId()).equals(3)) {
            list = "Developer";
        } else if(collection.get(player.getUniqueId()).equals(4)) {
            list = "Designer";
        } else if(collection.get(player.getUniqueId()).equals(5)) {
            list = "Allgemein";
        } else if(collection.get(player.getUniqueId()).equals(6)) {
            list = "Persönlich";

            Inventory inv = Bukkit.createInventory(null, 6*9, "§0§lTO-DO Liste | §6" + list);

            //Close
            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closem = close.getItemMeta();
            closem.setDisplayName("§cSchlieẞen");
            List<String> lore = new ArrayList<>();
            lore.clear();
            lore.add("§fSchlieẞe das Menü!");
            closem.setLore(lore);
            close.setItemMeta(closem);

            //Back
            ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta backm = back.getItemMeta();
            backm.setDisplayName("§8Zurück");
            lore.clear();
            lore.add("§fNavigiere ins vorherige Menü!");
            backm.setLore(lore);
            back.setItemMeta(backm);

            inv.setItem(45, close);
            inv.setItem(46, back);

            FileConfiguration config = Todolist.INSTANCE.getConfig();
            List<String> s = new ArrayList<>();
            s = config.getStringList("ToDoLists." + player.getUniqueId());

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

            player.openInventory(inv);

            return;
        }

        Inventory inv = Bukkit.createInventory(null, 6*9, "§0§lTO-DO Liste | §6" + list);

        //Close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName("§cSchlieẞen");
        List<String> lore = new ArrayList<>();
        lore.clear();
        lore.add("§fSchlieẞe das Menü!");
        closem.setLore(lore);
        close.setItemMeta(closem);

        //Back
        ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backm = back.getItemMeta();
        backm.setDisplayName("§8Zurück");
        lore.clear();
        lore.add("§fNavigiere ins vorherige Menü!");
        backm.setLore(lore);
        back.setItemMeta(backm);

        inv.setItem(45, close);
        inv.setItem(46, back);

        FileConfiguration config = Todolist.INSTANCE.getConfig();
        List<String> s = new ArrayList<>();
        s = config.getStringList("ToDoLists." + list);

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

        player.openInventory(inv);

    }

    public void adder(Player player) {

        player.sendMessage(Todolist.PREFIX + "§c§lBitte schreibe die Aufgabe, die du hinzufügen willst innerhalb der nächsten 2 Minuten in den Chat! §7(Farbcodes können verwendet werden)");
        chatlistener.put(player.getUniqueId(), true);
        player.closeInventory();

        taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(Todolist.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if(chatlistener.get(player.getUniqueId())) {
                    chatlistener.put(player.getUniqueId(), false);
                    player.sendMessage(Todolist.PREFIX + "§c§lDein Chat wird nun nicht mehr als Aufgabe hinzugefügt!");
                }
            }
        }, 20*120);

    }

    public void delete(Player player) {

        Inventory inv = Bukkit.createInventory(null, 6*9, "§0§lWähle ein Eintrag aus!");
        FileConfiguration config = Todolist.INSTANCE.getConfig();
        List<String> s = new ArrayList<>();

        String list = "§4ERROR";
        if(collection.get(player.getUniqueId()).equals(1)) {
            list = "Builder";
        } else if(collection.get(player.getUniqueId()).equals(2)) {
            list = "Support";
        } else if(collection.get(player.getUniqueId()).equals(3)) {
            list = "Developer";
        } else if(collection.get(player.getUniqueId()).equals(4)) {
            list = "Designer";
        } else if(collection.get(player.getUniqueId()).equals(5)) {
            list = "Allgemein";
        } else if(collection.get(player.getUniqueId()).equals(6)) {
            list = "Persönlich";

            //Close
            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closem = close.getItemMeta();
            closem.setDisplayName("§cSchlieẞen");
            List<String> lore = new ArrayList<>();
            lore.clear();
            lore.add("§fSchlieẞe das Menü!");
            closem.setLore(lore);
            close.setItemMeta(closem);

            //Back
            ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta backm = back.getItemMeta();
            backm.setDisplayName("§8Zurück");
            lore.clear();
            lore.add("§fNavigiere ins vorherige Menü!");
            backm.setLore(lore);
            back.setItemMeta(backm);

            inv.setItem(45, close);
            inv.setItem(46, back);

            s = config.getStringList("ToDoLists." + player.getUniqueId());

            for(String task : s) {

                ItemStack paper = new ItemStack(Material.PAPER);
                ItemMeta paperm = paper.getItemMeta();
                List<String> x = new ArrayList<>();
                x.add("§cKlicken zum Löschen!");
                paperm.setLore(x);
                paperm.setDisplayName(task);
                paper.setItemMeta(paperm);

                inv.addItem(paper);

            }

            player.openInventory(inv);

            return;
        }

        //Close
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closem = close.getItemMeta();
        closem.setDisplayName("§cSchlieẞen");
        List<String> lore = new ArrayList<>();
        lore.clear();
        lore.add("§fSchlieẞe das Menü!");
        closem.setLore(lore);
        close.setItemMeta(closem);

        //Back
        ItemStack back = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backm = back.getItemMeta();
        backm.setDisplayName("§8Zurück");
        lore.clear();
        lore.add("§fNavigiere ins vorherige Menü!");
        backm.setLore(lore);
        back.setItemMeta(backm);

        inv.setItem(45, close);
        inv.setItem(46, back);

        s = config.getStringList("ToDoLists." + list);

        for(String task : s) {

            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperm = paper.getItemMeta();
            List<String> x = new ArrayList<>();
            x.add("§cKlicken zum Löschen!");
            paperm.setLore(x);
            paperm.setDisplayName(task);
            paper.setItemMeta(paperm);

            inv.addItem(paper);

        }

        player.openInventory(inv);

    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        if(chatlistener.containsKey(e.getPlayer().getUniqueId())) {
            Player player = (Player) e.getPlayer();
            if(chatlistener.get(player.getUniqueId())) {

                String text = e.getMessage();
                if(text.equalsIgnoreCase("abbruch") || text.equalsIgnoreCase("abbrechen") || text.equalsIgnoreCase("stop")) {
                    chatlistener.put(player.getUniqueId(), false);
                    player.sendMessage(Todolist.PREFIX + "§c§lDein Chat wird nun nicht mehr als Aufgabe hinzugefügt!");
                    e.setCancelled(true);
                    Bukkit.getScheduler().cancelTask(taskID);
                    return;
                }

                text = text.replace("&", "§");
                text = "§6- §7" + text;

                String list = "§4ERROR";
                if(collection.get(player.getUniqueId()).equals(1)) {
                    list = "Builder";
                } else if(collection.get(player.getUniqueId()).equals(2)) {
                    list = "Support";
                } else if(collection.get(player.getUniqueId()).equals(3)) {
                    list = "Developer";
                } else if(collection.get(player.getUniqueId()).equals(4)) {
                    list = "Designer";
                } else if(collection.get(player.getUniqueId()).equals(5)) {
                    list = "Allgemein";
                } else if(collection.get(player.getUniqueId()).equals(6)) {
                    list = "Persönlich";

                    e.setCancelled(true);
                    chatlistener.put(player.getUniqueId(), false);
                    player.sendMessage(Todolist.PREFIX + "§2Du hast: §f" + text + " §2- zur To-Do Liste §6" + list + " §2hinzugefügt!");

                    FileConfiguration config = Todolist.INSTANCE.getConfig();
                    List<String> s = new ArrayList<>();
                    if(config.getList("ToDoLists." + player.getUniqueId()) != null) {
                        s = (List<String>) config.getList("ToDoLists." + player.getUniqueId());
                        s.add(text);
                    } else {
                        s.add(text);
                    }

                    config.set("ToDoLists." + player.getUniqueId(), s);
                    Todolist.INSTANCE.saveConfig();
                    Bukkit.getScheduler().cancelTask(taskID);

                    bookopener(player);

                    return;
                }

                e.setCancelled(true);
                chatlistener.put(player.getUniqueId(), false);
                player.sendMessage(Todolist.PREFIX + "§2Du hast: §f" + text + " §2- zur To-Do Liste §6" + list + " §2hinzugefügt!");

                FileConfiguration config = Todolist.INSTANCE.getConfig();
                List<String> s = new ArrayList<>();
                if(config.getList("ToDoLists." + list) != null) {
                    s = (List<String>) config.getList("ToDoLists." + list);
                    s.add(text);
                } else {
                    s.add(text);
                }

                config.set("ToDoLists." + list, s);
                Todolist.INSTANCE.saveConfig();
                Bukkit.getScheduler().cancelTask(taskID);

                bookopener(player);

            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        chatlistener.put(e.getPlayer().getUniqueId(), false);
    }

}
