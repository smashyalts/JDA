package org.example;

import com.baseketbandit.runeapi.RuneAPI;
import com.baseketbandit.runeapi.entity.Skill;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Map;


public class Main extends ListenerAdapter {
    public static void main(String[] args) {
JDA jda = JDABuilder.createLight("MTA4MDg2MjgzMDA3MDMzNzU5OQ.GHTtES.fEfEfAubr0fkE1lzdoKGmIy-QPiNeYJktQ8nv0")
                .addEventListeners(new Main())
                        .setActivity(Activity.playing("OSRS"))
                                .build();
jda.updateCommands().addCommands(
        Commands.slash("stats-lookup", "Lookup stats of another player")
                .addOption(OptionType.STRING, "name", "type a players osrs name", true),
        Commands.slash("animal", "Finds a random animal")
                .addOptions(
                        new OptionData(OptionType.STRING, "type", "The type of animal to find")
                                .addChoice("Bird", "bird")
                                .addChoice("Big Cat", "bigcat")
                                .addChoice("Canine", "canine")
                                .addChoice("Fish", "fish")
                )
).queue();
        // Disable parts of the cache
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder e = new EmbedBuilder();
        if (event.getName().equals("stats-lookup")) {
            Map<String, Skill> skills = RuneAPI.getStats(event.getOption("name").toString());

            for(Skill skill: skills.values()) {
                e.addField("Stats", "%s: #%,d - %,d - %,dxp \n" + skill.getName() + skill.getRank() + skill.getLevel() + skill.getExperience(), false);

            }
            event.replyEmbeds(e.build()).queue();
        }
    }



}