package fr.smartjoinmessage.Listeners;

import fr.smartjoinmessage.Main;
import fr.smartjoinmessage.Message;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
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

        if(quit_message.getMessageComponent() != null) {
            event.quitMessage(quit_message.getMessageComponent());
        }
        if(quit_message.getSound() != null) {
            for(Player online_player : Bukkit.getOnlinePlayers()) {
                Sound sound = quit_message.getSound();
                online_player.playSound(sound);
            }
        }
    }
}
