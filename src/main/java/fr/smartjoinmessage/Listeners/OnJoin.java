package fr.smartjoinmessage.Listeners;

import fr.smartjoinmessage.Main;
import fr.smartjoinmessage.Message;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.HashMap;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Message join_message = Main.getInstance().getDefaultJointMessage();

        if(!p.hasPlayedBefore()) {
            join_message = Main.getInstance().getWelcomeMessage();
        } else if(Main.getInstance().isPermissionBasedJointMessageValid()) {
            HashMap<String, Message> permission_based_joint_message = Main.getInstance().getPermissionBasedJointMessage();
            for (String permission : permission_based_joint_message.keySet()) {
                if (p.hasPermission(permission)) {
                    join_message = permission_based_joint_message.get(permission);
                    break;
                }
            }
        }

        if(join_message == null) {
            event.joinMessage();
            return;
        }

        if(join_message.getMessage() != null) {
            String message = join_message.getMessage();

            if(Main.getInstance().isPlaceHoldersAPIValid()) {
                message = PlaceholderAPI.setPlaceholders(p, message);
            }

            MiniMessage mm = MiniMessage.miniMessage();
            Component message_component = mm.deserialize(message);
            event.joinMessage(message_component);
        }
        if(join_message.getSound() != null) {
            for(Player online_player : Bukkit.getOnlinePlayers()) {
                Sound sound = join_message.getSound();
                online_player.playSound(sound);
            }
        }
        event.joinMessage();
    }
}
