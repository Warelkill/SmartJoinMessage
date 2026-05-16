package fr.smartjoinmessage;

import fr.smartjoinmessage.Listeners.OnJoin;
import fr.smartjoinmessage.Listeners.OnQuit;
import fr.smartjoinmessage.Managers.MessageManagers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {

    private boolean isPlaceHoldersAPIValid = false;
    private MessageManagers messageManager;

    private static Main instance;

    @Override
    public void onEnable() {
        setInstance();
        messageManager = new MessageManagers();
        messageManager.loadMessage();

        checkPlaceHoldersAPI();
        registersEvents();
        instance.saveDefaultConfig();
        Bukkit.getLogger().info("SmartJoinMessage is now enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("SmartJoinMessage has been disabled.");
    }

    private void registersEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnJoin(), this);
        pm.registerEvents(new OnQuit(), this);

        Bukkit.getLogger().info("SmartJoinMessage - All events' listeners have been activated !");
    }

    private void checkPlaceHoldersAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getLogger().info("SmartJoinMessage - PlaceHolderAPI has been detected, so the features linked to this API are now available");
            isPlaceHoldersAPIValid = true;
        } else {
            Bukkit.getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
        }
    }

    private void setInstance() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public MessageManagers getMessageManager(){
        return messageManager;
    }

    public boolean isPlaceHoldersAPIValid() {
        return isPlaceHoldersAPIValid;
    }
}
