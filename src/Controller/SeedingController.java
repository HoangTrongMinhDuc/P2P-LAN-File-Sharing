package Controller;

import Model.Computer;
import Model.FileShare;
import org.apache.commons.codec.digest.DigestUtils;
import util.Constant;
import util.Helper;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    volatile private boolean isEndSuccessed = false;
    private int oldIndex = -2;

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
        try {
            File file = new File("H:\\ss.txt");
            file.createNewFile();
            System.out.println("seed done");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        while (!isEndSuccessed){
//            if(!isEndSuccessed){
//
//            }
//        }
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
            if(oldIndex != index - 1){
                fileInputStream.getChannel().position(index * Constant.PART_SIZE);
                oldIndex = index;
            }
            fileInputStream.read(data);
//            Checksum checker = new Adler32();
//            ((Adler32) checker).update(data);
            PackageController.getInstance().sendDataUdpMesTo(
                    this.computer.getIp(),
                    this.computer.getPort(),
                    Helper.combineByteArray(
                            Helper.convertNumber2Bytes(Constant.DATA_MES),
                            fileShare.getMd5().getBytes(),
                            Helper.convertNumber2Bytes(index),
                            data
                    )
            );
            if(this.partList.size() < 200){
                System.out.println(index+":"+Arrays.toString(data));
            }
//            Thread.sleep(0,1);
        }catch (Exception e){
            System.out.println("Seeding error: " + e.getMessage());
        }
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
}
