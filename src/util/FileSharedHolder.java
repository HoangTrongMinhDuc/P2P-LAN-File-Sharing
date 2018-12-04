package util;

import Model.FileShare;
import Controller.MyComputer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

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
            validFile();
        }catch (Exception e){
            System.out.println("Error read holder: " + e.getMessage());
        }
    }

    public void writeHolder(){
        DefaultListModel<FileShare> listFileShared = MyComputer.getInstance().getSharingList();
        this.listFileSrc.clear();
        for(int i = 0; i < listFileShared.size(); i++){
            this.listFileSrc.add(listFileShared.get(i).getPath());
        }
        String json = "{\"src\":[";
        for(int i = 0; i < this.listFileSrc.size(); i++){
            json += "\"" + this.listFileSrc.get(i).replace("\\", "\\\\") + "\"";
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
            System.out.println("Write holder: " + e.getMessage());
        }
    }

    private void validFile(){
        for(String path : this.listFileSrc){
            File file = new File(path);
            if(!file.exists()){
                this.listFileSrc.remove(path);
            }
        }
    }

    public ArrayList<String> getListFileSrc() {
        return listFileSrc;
    }

    public void setListFileSrc(ArrayList<String> listFileSrc) {
        this.listFileSrc = listFileSrc;
    }
}
