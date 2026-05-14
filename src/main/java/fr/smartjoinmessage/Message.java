package fr.smartjoinmessage;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class Message {
    Component message_component;
    Sound sound;

    public Message(Component msg_comp, Sound sd) {
        message_component = msg_comp;
        sound = sd;
    }

    public Message(String msg, Sound sd) {
        MiniMessage mm = MiniMessage.miniMessage();
        Component msg_comp = mm.deserialize(msg);
        message_component = msg_comp;
        sound = sd;
    }

    public static Message createMessage(String message, Sound sound) {
        MiniMessage mm = MiniMessage.miniMessage();
        Component message_component = mm.deserialize(message);
        return new Message(message_component, sound);
    }


    public Component getMessageComponent() {
        return message_component;
    }

    public Sound getSound() {
        return sound;
    }
}
