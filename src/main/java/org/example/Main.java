package org.example;

import com.baseketbandit.runeapi.RuneAPI;
import com.baseketbandit.runeapi.entity.Skill;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;


public class Main extends ListenerAdapter {

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
JDA jda = JDABuilder.createLight("Replace With Ur Bot Token")
                .addEventListeners(new Main())
                        .setActivity(Activity.playing("OSRS"))
                                .build();
jda.updateCommands().addCommands(
        Commands.slash("stats-lookup", "Lookup stats of another player")
                .addOption(OptionType.STRING, "name", "type a players osrs name", true),
        Commands.slash("ge-lookup", "lookup a item on the ge")
                .addOption(OptionType.INTEGER, "itemid", "input the itemid of the item u want to lookup", true)
).queue();
        // Disable parts of the cache
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder e = new EmbedBuilder();
        if (event.getName().equals("stats-lookup")) {
            String username = event.getOption("name").getAsString();
            event.deferReply(true).queue();
            Map<String, Skill> skills = RuneAPI.getStats(username);
            for (Skill skill : skills.values()) {
                e.addField("", "```" + "Skill: " + skill.getName() + "\n" + "Level: " + skill.getLevel() + "\n" + "Exp: " + skill.getExperience() + "\n" + "Rank: " + skill.getRank() + "```", true);
            }
            e.setTitle("Stats");
            event.getHook().sendMessageEmbeds(e.build()).setEphemeral(true).queue();
        }
        EmbedBuilder e2 = new EmbedBuilder();
        if (event.getName().equals("ge-lookup")) {
            int itemid = event.getOption("itemid").getAsInt();
            JSONObject json = null;

            try {
                json = new JSONObject(IOUtils.toString(new URL("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + itemid), Charset.forName("UTF-8")));
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            event.deferReply(true).queue();
            try {
                e2.addField("", "Price: " + json.getJSONObject("item").getJSONObject("current").get("price").toString(), true);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            try {
                e2.addField("", "Item name: " + json.getJSONObject("item").get("name").toString(), true);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            try {
                e2.addField("", "Members: " + json.getJSONObject("item").get("members").toString(), true);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            try {
                e2.setTitle("Item Lookup", String.valueOf(new URL(json.getJSONObject("item").get("icon").toString())));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            try {
                e2.setImage(json.getJSONObject("item").get("icon_large").toString());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            event.getHook().sendMessageEmbeds(e2.build()).setEphemeral(true).queue();
        }
    }}
