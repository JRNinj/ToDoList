package de.jrninj.todolist;

import de.jrninj.todolist.commands.ToDoListCommand;
import de.jrninj.todolist.listener.InvListener;
import de.jrninj.todolist.utils.ToDoListAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Todolist extends JavaPlugin {

    public static String PREFIX = "§f[§2To§4Do§f] ";
    public static Todolist INSTANCE;

    @Override
    public void onEnable() {

        log("§fPlugin §aerfolgreich §fgeladen!");

        register();

    }

    @Override
    public void onDisable() {

        log("§fPlugin §cerfolgriech §fentladen!");

    }

    public Todolist() {
        INSTANCE = this;
    }

    private void register() {

        //Commands
        Bukkit.getPluginCommand("todo").setExecutor(new ToDoListCommand());
        Bukkit.getPluginCommand("todo").setTabCompleter(new ToDoListCommand());

        //Listener
        Bukkit.getPluginManager().registerEvents(new InvListener(), this);

    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }
}
