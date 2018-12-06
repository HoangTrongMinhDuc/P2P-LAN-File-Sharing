package Controller;

import GUI.Frame.MainFrame;
import Model.Computer;
import Model.FileSeed;
import Model.FileShare;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.net.ntp.TimeStamp;
import util.Constant;
import util.FileSharedHolder;
import util.Helper;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MyComputer extends Computer {
    private TimeStamp timeStamp = new TimeStamp(System.currentTimeMillis());
    private int subnetMask;
    volatile public DefaultListModel<Computer> listConnected = new DefaultListModel<>();
    volatile private DefaultListModel<FileShare> sharingList = new DefaultListModel<>();
    private DefaultListModel<FileSeed> sharedList = new DefaultListModel<>();
    private DefaultListModel<DownloadController> listDownloading = new DefaultListModel<>();
//    private HashMap<>
    private static MyComputer instance = new MyComputer();
    public static MyComputer getInstance(){return instance;}
    private MyComputer(){
        ArrayList<String> listSrc = FileSharedHolder.getInstance().getListFileSrc();
        for(String src : listSrc){
            try {
                File file = new File(src);
                String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(new FileInputStream(file.getPath()));
                FileShare fileShare = new FileShare(file.getName(), md5, (int)file.length(), file.getPath());
                this.sharingList.addElement(fileShare);
                System.out.println("add " + fileShare.getName());
            }catch (Exception e){
                System.out.println("Error import file share: " + e.getMessage());
            }
        }
        Thread refreshListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        refreshComputerList();
                        updateSharedList();
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println("Refresh: " + e.getMessage());
                }
            }
        });
        refreshListThread.start();
    }

    public void start(){
        new MainFrame(listConnected);

    }

    public String getHelloWord(boolean replyReq){
        String json =  "{\"type\":" + Constant.HELLO_MES + ", \"replyReq\":" + replyReq
                + ", \"name\":\"" + this.name + "\""
                + ", \"timeStamp\":" + System.currentTimeMillis()/1000
                + ", \"ListFile\":[";
        int count = 0;
        if(this.listFileShare != null)
        for(int i =0; i < this.sharingList.size(); i++){
            json += this.sharingList.get(i).getObjectString();
            if(i != this.sharingList.size() - 1)
                json += ",";
        }
        json += "]}";
        return json;
    }

    public void addComputer(Computer computer){
        if(this.listConnected.size() == 0){
            this.listConnected.addElement(computer);
            System.out.println("added " + computer.getIp());
        }else {
            int index = 0;
            for(index = 0; index < this.listConnected.size(); index++){
                Computer com = this.listConnected.get(index);
                int res = computer.compareTo(com);
                if(res > 0){
                    if(index == this.listConnected.size()-1){
                        this.listConnected.addElement(computer);
                        break;
                    }
                    continue;
                }else {
                    //if old connected computer
                    if(res < 0){
                        this.listConnected.add(index, computer);
                        System.out.println("added " + computer.getIp());
                        break;
                    }else{
                        com.setName(computer.getName());
                        com.setTimestamp(computer.getTimestamp());
                        com.setPort(computer.getPort());
                        com.setStatus(computer.getStatus());
                        com.setListFileShare(computer.getListFileShare());
                        System.out.println("changed " + computer.getIp());
                        break;
                    }
                }
            }
        }


    }

    public void addNewDownloadTask(String md5){
        FileSeed fileSeed = null;
        for(int i = 0; i < this.sharedList.size(); i++){
            if(this.sharedList.get(i).getMd5().equals(md5)){
                fileSeed = this.sharedList.get(i);
                this.sharedList.get(i).setStatus(Constant.DOWNLOADING);
            }
        }
        if(fileSeed != null){
            DownloadController downloadController = new DownloadController(fileSeed);
            downloadController.start();
            this.listDownloading.addElement(downloadController);
        }
    }

    public DownloadController getDownloadControllerBy(String md5File){
        for(int i = 0; i < this.listDownloading.size(); i++){
            if(this.listDownloading.get(i).getFileDownload().getMd5().toLowerCase().equals(md5File)){
                return this.listDownloading.get(i);
            }
        }
        return null;
    }

    private void refreshComputerList(){
        for(int i = 0; i < this.listConnected.size(); i++){
            Computer computer = this.listConnected.get(i).clone();
            this.listConnected.set(i, computer);
            int time = (int)System.currentTimeMillis() - computer.getTimestamp()*1000;
            if(computer.getStatus() == Constant.ONLINE || computer.getStatus() == Constant.PENDING || computer.getStatus() == Constant.CHECKING){
                if(time > Constant.TIME_OUT_ALIVE && computer.getStatus() != Constant.OFFLINE){
                    computer.setStatus(Constant.OFFLINE);
                    this.listConnected.removeElementAt(i);
                    this.listConnected.add(i, computer);
                    System.out.println("OFFLINE "+computer.getName());
                }else{
                    if(time > Constant.TIME_PENDING && computer.getStatus() != Constant.PENDING){
                        computer.setStatus(Constant.PENDING);
                        this.listConnected.removeElementAt(i);
                        this.listConnected.add(i, computer);
                        System.out.println("PENDING "+computer.getName());
                    }
                }
            }
            if(computer.getStatus() != Constant.ONLINE && computer.getStatus() != Constant.CHECKING){
                if(time < Constant.TIME_PENDING){
                    computer.setStatus(Constant.ONLINE);
                    this.listConnected.removeElementAt(i);
                    this.listConnected.add(i, computer);
                    System.out.println("ONLINE "+computer.getName());
                }
            }
        }
    }

    public String[] getAllAliveConnect(){
        String[] aliveConnect = null;
        int count = 0;
        for(int i = 0; i < this.listConnected.size(); i++){
            Computer computer = this.listConnected.get(i);
            if(computer.getStatus() != Constant.OFFLINE){
                ++count;
            }
        }
        if(count != 0){
            aliveConnect = new String[count];
            int ii = 0;
            for(int i = 0; i < this.listConnected.size(); i++){
                Computer computer = this.listConnected.get(i);
                if(computer.getStatus() != Constant.OFFLINE){
                    aliveConnect[ii] = computer.getIp();
                    ++ii;
                }
            }
        }
        return aliveConnect;
    }

    public void updateSharedList(){
        HashMap<String, FileSeed> listFile = new HashMap<>();
        for (int i = 0; i < this.listConnected.size(); i++){
            //loop all computer ALIVE
            if(this.listConnected.get(i).getStatus() != Constant.OFFLINE){
                HashMap<String, FileShare> list = this.listConnected.get(i).getListFileShare();
                if(list == null) continue;
                //loop all file in computer
                for (String fileKey : list.keySet()){
                    //if not recorded then add new file seed to list
                    if(!listFile.containsKey(fileKey)){
                        ArrayList<Computer> computers = new ArrayList<>();
                        computers.add(this.listConnected.get(i));
                        FileShare f = this.listConnected.get(i).getListFileShare().get(fileKey);
                        FileSeed seed = new FileSeed(f.getName(), f.getMd5(), f.getSize(), f.getPath(), computers, Constant.SHARED);
                        listFile.put(fileKey, seed);
                    }else {
                        //if recorded then add this computer to list share the file seed
                        listFile.get(fileKey).getListComputer().add(this.listConnected.get(i));
                    }
                }
            }
        }
        for(int i = 0; i < this.sharingList.size(); i++){
            FileShare fileShare = this.sharingList.get(i);
            if(listFile.containsKey(fileShare.getMd5())){
                listFile.get(fileShare.getMd5()).setStatus(Constant.SHARING);
            }
//            else{
//                FileSeed fileSeed = new FileSeed();
//                fileSeed.setName(fileShare.getName());
//                fileSeed.setSize(fileShare.getSize());
//                fileSeed.setMd5(fileShare.getMd5());
//                fileSeed.addComputer(this);
//                fileSeed.setStatus(Constant.SHARING);
//                listFile.put(fileShare.getMd5(), fileSeed);
//            }
        }

        for (int i = 0; i < this.listDownloading.size(); i++){
            if(this.listDownloading.get(i).isAlive()){
                FileShare fileShare = this.listDownloading.get(i).getFileDownload();
                if(listFile.containsKey(fileShare.getMd5())){
                    listFile.get(fileShare.getMd5()).setStatus(Constant.DOWNLOADING);
                }
            }
        }
        this.sharedList.clear();
        DefaultListModel<FileSeed> tempList = new DefaultListModel<>();
        for (FileSeed fileSeed : listFile.values()){
            Helper.insertElement(this.sharedList, fileSeed);
        }
    }

    public void removeFileSharing(int i){
        this.sharingList.removeElementAt(i);
        FileSharedHolder.getInstance().writeHolder();
    }

    public FileShare getFileShareBy(String md5){
        for(int i = 0; i < this.sharingList.size(); i++){
            if(this.sharingList.get(i).getMd5().equals(md5))
                return this.sharingList.get(i);
        }
        return null;
    }

    public FileSeed getFileSeedBy(String md5){
        for(int i = 0; i < this.sharedList.size(); i++){
            if(this.sharedList.get(i).getMd5().equals(md5))
                return this.sharedList.get(i);
        }
        return null;
    }

    public DefaultListModel<FileShare> getSharingList() {
        return sharingList;
    }

    public DefaultListModel<FileSeed> getSharedList() {
        return sharedList;
    }

    public DefaultListModel<DownloadController> getListDownloading() {
        return listDownloading;
    }

    public int getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(int subnetMask) {
        this.subnetMask = subnetMask;
    }
}
