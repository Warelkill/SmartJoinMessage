package fr.smartjoinmessage;

import fr.smartjoinmessage.Listeners.OnJoin;
import fr.smartjoinmessage.Listeners.OnQuit;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.SoundGroup;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public final class Main extends JavaPlugin {

    private HashMap<String, Message> permission_based_joint_message = new HashMap<>();
    private boolean isJoinMapValid = true;
    private HashMap<String, Message> permission_based_left_message = new HashMap<>();
    private boolean isQuitMapValid = true;
    private Message welcome_message;
    private Message default_joint_message;
    private Message default_left_message;

    private static Main instance;

    @Override
    public void onEnable() {
        setInstance();
        loadPermissionBasedMessage();

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

    private void setInstance() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public boolean isPermissionBasedJointMessageValid() {
        return isJoinMapValid;
    }


    public boolean isPermissionBasedLeftMessageValid() {
        return isQuitMapValid;
    }

    public Message getWelcomeMessage() {
        return welcome_message;
    }

    public Message getDefaultJointMessage() {
        return default_joint_message;
    }

    public HashMap<String, Message> getPermissionBasedJointMessage() {
        return permission_based_joint_message;
    }

    public Message getDefaultLeftMessage() {
        return default_left_message;
    }

    public HashMap<String, Message> getPermissionBasedLeftMessage() {
        return permission_based_left_message;
    }


    public void loadPermissionBasedMessage() {
        loadWelcomeMessage();

        loadDefaultJointMessage();
        loadPermissionBasedJointMessage();

        loadDefaultLeftMessage();
        LoadPermissionBasedLeftMessage();
    }

    public Message getCurrentMessage(ConfigurationSection cs) {
        if(cs==null) return null;

        String message_content;
        if(cs.getKeys(false).isEmpty()) {
            if(cs.get("") != null) {
                message_content = cs.getString("");
                return new Message(message_content, null);
            }
            return null;
        }
        message_content = cs.getString("content");
        String sound_name = cs.getString("sound");

        Sound sound = null;

        if(message_content==null && sound_name==null) return null;
        if(sound_name != null) {
            Key key = Key.key(sound_name.toUpperCase());
            sound = Sound.sound(key, Sound.Source.PLAYER, 1f, 1f);
        }

        return new Message(message_content, sound);
    }

    public void loadWelcomeMessage() {
        FileConfiguration config = instance.getConfig();
        ConfigurationSection cs = config.getConfigurationSection("join.welcome");
        welcome_message = getCurrentMessage(cs);
    }

    public void loadDefaultJointMessage() {
        FileConfiguration config = instance.getConfig();
        ConfigurationSection cs = config.getConfigurationSection("join.default");
        default_joint_message = getCurrentMessage(cs);
    }

    public void loadPermissionBasedJointMessage() {
        FileConfiguration config = instance.getConfig();
        ConfigurationSection permissions_section = config.getConfigurationSection("join.permissions_based");

        if(permissions_section == null) return;
        if(permissions_section.getKeys(false).isEmpty()) return;

        for(String permission : permissions_section.getKeys(false)) {
            ConfigurationSection cs = permissions_section.getConfigurationSection(permission);
            Message msg = getCurrentMessage(cs);
            if(msg == null) return;

            permission_based_joint_message.put(permission, msg);
        }


        if(permission_based_joint_message.isEmpty()) {
            isJoinMapValid = false;
        }
    }

    public void loadDefaultLeftMessage() {
        FileConfiguration config = instance.getConfig();
        ConfigurationSection cs = config.getConfigurationSection("quit.default");
        default_left_message = getCurrentMessage(cs);
    }

    public void LoadPermissionBasedLeftMessage() {
        FileConfiguration config = instance.getConfig();
        ConfigurationSection permissions_section = config.getConfigurationSection("quit.permissions_based");

        if(permissions_section == null) return;
        if(permissions_section.getKeys(false).isEmpty()) return;

        for(String permission : permissions_section.getKeys(false)) {
            ConfigurationSection cs = permissions_section.getConfigurationSection(permission + ".content");
            Message msg = getCurrentMessage(cs);
            if(msg == null) return;

            permission_based_joint_message.put(permission, msg);
        }

        if(permission_based_left_message.isEmpty()) {
            isQuitMapValid = false;
        }
    }
}
