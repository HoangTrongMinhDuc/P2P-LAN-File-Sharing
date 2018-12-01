package util;

import Model.FileShare;
import Model.MyComputer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FileSharedHolder {
    private File fileHolder = new File("fileHoder.json");
    private ArrayList<String> listFileSrc = new ArrayList<>();
    private static FileSharedHolder ourInstance = new FileSharedHolder();
    public static FileSharedHolder getInstance() {
        return ourInstance;
    }
    private FileSharedHolder() {
        getSaveFile();
    }

    private void getSaveFile(){
        if(this.fileHolder.exists() && !this.fileHolder.isDirectory()){
            readHolder();
        }else{
            System.out.println("File holder not found");
            try {
                PrintWriter writer = new PrintWriter(fileHolder.getName(), "UTF-8");
                writer.close();
                System.out.println("Created holder file");
            }catch (Exception e){
                System.out.println("Error create holder file: " + e.getMessage());
            }
            writeHolder();
        }
    }

    private void readHolder(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileHolder.getPath()));
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(bufferedReader.readLine());
            if(json.isJsonObject()){
                JsonObject holderObject = json.getAsJsonObject();
                JsonArray jsonArray = holderObject.get("src").getAsJsonArray();
                for(int i = 0; i < jsonArray.size(); i++){
                    listFileSrc.add(jsonArray.get(i).getAsString());
                }
            }
        }catch (Exception e){
            System.out.println("Error read holder: " + e.getMessage());
        }
    }

    private void writeHolder(){
        HashMap<String, FileShare> listFileShared = MyComputer.getInstance().getListFileShare();
        this.listFileSrc.clear();
        for(FileShare file : listFileShared.values()){
            this.listFileSrc.add(file.getPath());
        }
        String json = "{\"src\":[";
        for(int i = 0; i < this.listFileSrc.size(); i++){
            json += "\"" + this.listFileSrc.get(i) + "\"";
            if(i != this.listFileSrc.size() - 1){
                json += ",";
            }
        }
        json += "]}";

        try {
            PrintWriter writer = new PrintWriter(fileHolder.getPath(), "UTF-8");
            writer.print(json);
            writer.close();
            System.out.println("Write holder successfull");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getListFileSrc() {
        return listFileSrc;
    }

    public void setListFileSrc(ArrayList<String> listFileSrc) {
        this.listFileSrc = listFileSrc;
    }
}
