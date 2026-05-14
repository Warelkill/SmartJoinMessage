package fr.smartjoinmessage.Listeners;

import fr.smartjoinmessage.Main;
import fr.smartjoinmessage.Message;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

        if(join_message.getMessageComponent() != null) {
            event.joinMessage(join_message.getMessageComponent());
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
