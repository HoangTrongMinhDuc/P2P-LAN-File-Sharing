package Model;

import java.io.File;
import java.util.HashMap;

public class Computer {
    protected String name = "CON";
    protected String ip;
    protected int port;
    protected int timestamp;
    protected int status;
    protected HashMap<String, FileShare> listFileShare = new HashMap<>();
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
        if(this.ip.equals(computer.getIp()))
            return true;
        else
            return false;
    }

    public int compareTo(Computer computer){
        if(computer.getIp().equals("localhost")){
            return (this.ip+this.port).compareTo(computer.getIp()+computer.getPort());
        }
        String ip1 = this.ip;
        String ip2 = computer.getIp();
        if(ip1.length() != 11)
            ip1 = reCompleteIp(ip1);
        if(computer.getIp().length() != 11)
            ip2 = reCompleteIp(ip2);
        ip1 = ip1+":"+this.port;
        ip2 = ip2+":"+computer.getPort();
        return ip1.compareTo(ip2);
    }

    public String reCompleteIp(String ip){
        String[] ips = ip.split("[.]");
        for (int i = 0; i < ips.length; i++){
            if(ips[i].length() < 3){
                for(int j = 0; j <= (3-ips[i].length()); j++){
                    ips[i] = "0" + ips[i];
                }
            }
        }
        return ips[0]+"."+ips[1]+"."+ips[2]+"."+ips[3];
    }

    public Computer clone(){
        return new Computer(this.name, this.ip, this.port, this.timestamp, this.status, this.listFileShare);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
