package org.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Arrays;


public class Main extends ListenerAdapter {
    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setToken("MTA4MDg2MjgzMDA3MDMzNzU5OQ.GqohRx.JNiyLxN7WxdYX0HiYD7kGT0dlRm3Rh7v1zilhA");
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("Playing OSRS"));

        builder.build();
    }
@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String PlayerName;
        if (event.getMessage().toString().contains("!stats")) {
            PlayerName = event.getMessage().toString().split(" ").toString();
            System.out.print(PlayerName);
        }

}
}