package util;

import Controller.PackageController;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class LocalSetting {
    private File settingFile = new File("setting.json");
    private String downFolderPath = System.getProperty("user.home")+"\\Downloads";
    private int port = 7878;
    private boolean isEnaleSpread = false;

    private static LocalSetting ourInstance = new LocalSetting();

    public static LocalSetting getInstance() {
        return ourInstance;
    }

    private LocalSetting() {
        getSavedSetting();
    }

    private void getSavedSetting(){
        if(this.settingFile.exists() && !this.settingFile.isDirectory()) {
            System.out.println("Found setting file");
            readSetting();
        }else{
            System.out.println("Not found setting file");
            try {
                PrintWriter writer = new PrintWriter("setting.json", "UTF-8");
                writer.close();
                System.out.println("Created setting file");
            }catch (Exception e){
                System.out.println("Create setting: " + e.getMessage());
            }
            writeSetting();
        }
    }

    private void writeSetting(){
        String json = "{\"DownFolder\": \""+this.downFolderPath.replace("\\", "\\\\") +
                        "\", \"Port\":"+this.port+
                        ",\"EnableSpread\":"+this.isEnaleSpread+"}";
        try {
            PrintWriter writer = new PrintWriter("setting.json", "UTF-8");
            writer.print(json);
            writer.close();
            System.out.println("Write setting successfull");
        }catch (Exception e){
            System.out.println("Write setting: " + e.getMessage());
        }

    }

    private void readSetting(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.settingFile));
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(bufferedReader.readLine());
            if(json.isJsonObject()){
                JsonObject settingObject = json.getAsJsonObject();
                this.downFolderPath = settingObject.get("DownFolder").getAsString();
                this.port = settingObject.get("Port").getAsInt();
                this.isEnaleSpread = settingObject.get("EnableSpread").getAsBoolean();
            }
        }catch (Exception e){
            System.out.println("Read setting: " + e.getMessage());
        }
    }

    public String getDownFolderPath() {
        return downFolderPath;
    }

    public void setDownFolderPath(String downFolderPath) {
        this.downFolderPath = downFolderPath;
        writeSetting();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port, boolean isRestart) {
        this.port = port;
        writeSetting();
    }

    public boolean isEnaleSpread() {
        return isEnaleSpread;
    }

    public void setEnaleSpread(boolean enaleSpread) {
        isEnaleSpread = enaleSpread;
        writeSetting();
    }

    public void resetSetting(){
        this.downFolderPath = System.getProperty("user.home")+"\\Downloads";
        this.port = 7878;
        this.isEnaleSpread = false;
        writeSetting();
    }
}
