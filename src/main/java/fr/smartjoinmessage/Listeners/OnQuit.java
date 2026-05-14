package fr.smartjoinmessage.Listeners;

import fr.smartjoinmessage.Main;
import fr.smartjoinmessage.Message;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class OnQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Message quit_message = Main.getInstance().getDefaultLeftMessage();


        if(Main.getInstance().isPermissionBasedLeftMessageValid()) {
            HashMap<String, Message> permission_based_left_message = Main.getInstance().getPermissionBasedLeftMessage();

            if(!permission_based_left_message.isEmpty()) {
                for (String permission : permission_based_left_message.keySet()) {
                    if (p.hasPermission(permission)) {
                        quit_message = permission_based_left_message.get(permission);
                        break;
                    }
                }
            }
        }

        if(quit_message == null) return;

        if(quit_message.getMessage() != null) {
            String message = quit_message.getMessage();

            if(Main.getInstance().isPlaceHoldersAPIValid()) {
                message = PlaceholderAPI.setPlaceholders(p, message);
            }

            MiniMessage mm = MiniMessage.miniMessage();
            Component message_component = mm.deserialize(message);
            event.quitMessage(message_component);
        }
        if(quit_message.getSound() != null) {
            for(Player online_player : Bukkit.getOnlinePlayers()) {
                Sound sound = quit_message.getSound();
                online_player.playSound(sound);
            }
        }
        event.quitMessage();
    }
}
