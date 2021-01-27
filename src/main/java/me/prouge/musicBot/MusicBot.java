package me.prouge.musicBot;

import me.prouge.musicBot.commands.CommandManager;
import me.prouge.musicBot.config.ConfigFile;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;


public class MusicBot extends ListenerAdapter {
    MusicBot musicBot; //Main class
    CommandManager commandManager; //The ServerCommand class for the command handling
    ConfigFile configFile;


    public static void main(String[] args) throws Exception {
        new MusicBot();
    }

    public MusicBot() throws IOException, URISyntaxException {
        registerInstances();
        configFile.loadConfig("musicBotConfig");
        loadBot();

    }

    public void registerInstances(){ //Registers the Instances!
        musicBot = this;
        configFile = new ConfigFile();
    }

    public void loadBot(){
        JDABuilder builder = JDABuilder.createDefault(configFile.getContent("token"));

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);

        builder.setBulkDeleteSplittingEnabled(false);

        builder.setActivity(Activity.watching("TV"));

        builder.addEventListeners(new CommandManager(musicBot));


        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public ConfigFile getConfigFile(){
        return configFile;
    }


}

