package fr.smartjoinmessage;

import net.kyori.adventure.sound.Sound;

public class Message {
    String message;
    Sound sound;

    public Message(String msg, Sound sd) {
        message = msg;
        sound = sd;
    }

    public static Message createMessage(String message, Sound sound) {

        return new Message(message, sound);
    }


    public String getMessage() {
        return message;
    }

    public Sound getSound() {
        return sound;
    }
}
