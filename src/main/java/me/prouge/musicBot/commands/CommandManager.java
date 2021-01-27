package me.prouge.musicBot.commands;

import me.prouge.musicBot.MusicBot;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager extends ListenerAdapter{

    public ConcurrentHashMap<String, ServerCommand> commands;
    private MusicBot musicbot;

    public CommandManager(MusicBot musicBot){
        this.musicbot = musicBot;
        this.commands = new ConcurrentHashMap<>();

        //ADD THE COMMANDS UNDERNEATH
       // this.commands.put("nameOfCMD", new CLASS);

    }

    public boolean perform(String command, Member member, TextChannel textChannel, Message message){
        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null){
            cmd.performCommand(member, textChannel, message);
        }
        return false;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String message = event.getMessage().getContentDisplay();
        if(event.getAuthor().isBot()){
            return;
        }

        if(event.isFromType(ChannelType.TEXT)) {
            TextChannel textChannel = event.getTextChannel();
            if(message.startsWith(musicbot.getConfigFile().getContent("prefix"))){
                String[] args = message.substring(1).split(" ");
                if(args.length > 0){
                    if(!perform(args[0], event.getMember(), textChannel, event.getMessage())){
                        textChannel.sendMessage("Unbekanntes Kommando").queue();
                    }
                }
            }
        }
    }





}
