package Controller;

import Model.Computer;
import Model.FileShare;
import org.apache.commons.codec.digest.DigestUtils;
import util.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class SeedingController extends Thread {
    private Computer computer;
    private FileShare fileShare;
    private ArrayList<Integer> partList;
    private FileInputStream fileInputStream;
    private int totalPart;
    private int leftPartSize;
    private boolean isEnabled = true;
    private boolean isEndTask = false;

    public SeedingController(Computer computer, FileShare fileShare, ArrayList<Integer> partList){
        this.computer = computer;
        this.fileShare = fileShare;
        this.partList = partList;
        File file = new File(this.fileShare.getPath());
        if(!file.exists()){
            System.out.println("File not exist");
        }
        totalPart = (int)file.length() / Constant.PART_SIZE;
        leftPartSize = (int)file.length() - totalPart * Constant.PART_SIZE;
        System.out.println("seed: "+totalPart+"|"+leftPartSize);
        if(leftPartSize != 0){
            totalPart++;
        }
        try{
            fileInputStream = new FileInputStream(fileShare.getPath());
        }catch (Exception e){
            System.out.println("Seed error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (!isEndTask){
            if(isEnabled){
                super.run();
                for(int i = 0; i < this.partList.size(); i++){
                    seedPart(this.partList.get(i));
                    if(i == this.partList.size() - 1){
                        this.isEndTask = true;
                    }
                }
            }
        }
    }

    private void seedPart(int index){
        try{
            byte[] data;
            if(index != totalPart - 1){
                data = new byte[Constant.PART_SIZE];
            }else{
                if(leftPartSize != 0)
                    data = new byte[this.leftPartSize];
                else
                    data = new byte[Constant.PART_SIZE];
            }
            fileInputStream.getChannel().position(index * Constant.PART_SIZE);
            fileInputStream.read(data);
            String jsonData = "[";
            for(int i = 0; i < data.length; i++){
                jsonData += data[i];
                if(i != data.length -1)
                    jsonData += ",";
            }
            jsonData += "]";
            Checksum checker = new Adler32();
            ((Adler32) checker).update(data);
            String res = "{\"type\":" + Constant.DATA_MES + ",\"md5File\":\"" + fileShare.getMd5() + "\",\"indexPart\":" + index  +",\"checksum\":\""
                    + checker.getValue() + "\",\"data\":" + jsonData + "}";
            PackageController.getInstance().sendUdpMesTo(this.computer.getIp(), this.computer.getPort(), res);
            Thread.sleep(1);
        }catch (Exception e){
            System.out.println("Seeding error: " + e.getMessage());
        }
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
}
