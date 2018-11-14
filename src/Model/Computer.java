package Model;

import java.io.File;
import java.util.HashMap;

public class Computer {
    private String name;
    private String ip;
    private int port;
    private int timestamp;
    private int status;
    private HashMap<String, FileShare> listFileShare;
    public Computer(){}

    public Computer(String name, String ip, int port, int timestamp, int status, HashMap<String, FileShare> listFileShare){
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.timestamp = timestamp;
        this.status = status;
        this.listFileShare = listFileShare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, FileShare> getListFileShare() {
        return listFileShare;
    }

    public void setListFileShare(HashMap<String, FileShare> listFileShare) {
        this.listFileShare = listFileShare;
    }

    public FileShare getFileBy(String md5){
        return this.listFileShare.get(md5);
    }

    public void addFileShare(FileShare file){
        this.listFileShare.put(file.getMd5(), file);
    }

    public void removeFileShare(FileShare file){
        this.listFileShare.remove(file.getMd5());
    }

    public void removeFileShare(String md5){
        this.listFileShare.remove(md5);
    }

    public void updateListFile(){

    }

    public boolean isSame(Computer computer){

        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
