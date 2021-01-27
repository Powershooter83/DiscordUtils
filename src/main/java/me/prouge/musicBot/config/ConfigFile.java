package me.prouge.musicBot.config;

import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ConfigFile {
    String botName;

    public void loadConfig(String botConfigurationName){
        File file = new File(botConfigurationName + ".json");
        botName = botConfigurationName;
        if(!file.exists()){
            try {
                copyFile(getFileFromResource(botConfigurationName + ".json"), file);
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    public void copyFile(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }


    }
    public String getContent(String string){
        File file = new File(botName + ".json");
        try{
            String content = null;
            try {
                content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            JSONObject json = new JSONObject(content);

            return json.getString(string);

        }catch (Exception e){
            e.printStackTrace();
        }
    return null;
    }


}
