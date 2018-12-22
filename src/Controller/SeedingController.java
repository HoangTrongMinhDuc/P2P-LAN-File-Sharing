package Controller;

import Model.Computer;
import Model.DataPack;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
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
    volatile private BlockingQueue<ArrayList<Integer>> listIndexResend = new LinkedBlockingDeque<>();

    private long oldIndex = -2;
    private int idReq = 0;

    public SeedingController(Computer computer, FileShare fileShare, int idReq, ArrayList<Integer> partList){
        this.computer = computer;
        this.fileShare = fileShare;
        this.partList = partList;
        this.idReq = idReq;
        File file = new File(this.fileShare.getPath());
        if(!file.exists()){
            System.out.println("File not exist");
        }
        totalPart = (int)(file.length() / Constant.PART_SIZE);
        leftPartSize = (int)(file.length() -(long) (totalPart * Constant.PART_SIZE));
        System.out.println("seed: "+file.length()+"|"+totalPart+"|"+leftPartSize);
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
        try {
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
//            while (isEnabled){
//                sendEndTask();
//                Thread.sleep(1);
//            }

            System.out.println("seed done");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void seedPart(long index){
        try{
            byte[] data;
            if(index != totalPart - 1){
                data = new byte[(int)Constant.PART_SIZE];
            }else{
                if(leftPartSize != 0)
                    data = new byte[this.leftPartSize];
                else
                    data = new byte[(int)Constant.PART_SIZE];
            }
            if(oldIndex != index - 1){
                fileInputStream.getChannel().position(index * Constant.PART_SIZE);
                oldIndex = index;
            }
            fileInputStream.read(data);
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
//            if(this.partList.size() < 200){
//                System.out.println(index+":"+Arrays.toString(data));
//            }
//            Thread.sleep(0,1);
        }catch (Exception e){
            System.out.println("Seeding error: " + e.getMessage());
        }
    }

    private void sendEndTask(){
        byte[] markBytes = {2, 0, 1, 9};
        PackageController.getInstance().sendDataUdpMesTo(
                this.computer.getIp(),
                this.computer.getPort(),
                Helper.combineByteArray(
                        Helper.convertNumber2Bytes(Constant.DATA_MES),
                        markBytes,
                        this.idReq,
                        fileShare.getMd5().getBytes()
                )
        );
    }

    public void addIndexToList(ArrayList<Integer> listNew){
        this.listIndexResend.add(listNew);
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
}
