package Controller;

import Model.Computer;
import Model.FileDownload;
import Model.FileSeed;
import Model.FileShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import util.Constant;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class DownloadController extends Thread {
    private FileDownload fileDownload;
    private RandomAccessFile randomAccessFile;
    private ArrayList<Computer> listCom;
    private HashMap<Integer, Integer> listIndex = new HashMap<>();
    private Queue<JsonObject> listPart = new LinkedList<>();
    private boolean isEndTask = false;
//    private ArrayList
    public DownloadController(FileSeed fileSeed){
        this.fileDownload = new FileDownload(fileSeed);
        this.listCom = fileSeed.getListComputer();
        File file = new File(fileDownload.getPath());
        try {
            if(!file.exists()){
                file.createNewFile();
                this.randomAccessFile = new RandomAccessFile(fileDownload.getPath(), "rw");
                this.randomAccessFile.setLength(fileDownload.getSize());
//                randomAccessFile.close();
            }
        }catch (Exception e){
            System.out.println("Create empty error: " + e.getMessage());
        }
        if(listCom.size() == 1){
            PackageController.getInstance().sendDownloadRangeRequest(listCom.get(0).getIp(), listCom.get(0).getPort(), fileDownload.getMd5(),
                    0, fileDownload.getTotalPart()-1);
        }
        for (int i = 0; i < this.fileDownload.getTotalPart(); i++){
            this.listIndex.put(i, 0);
        }
//        int leftNumPart = fileDownload.getTotalPart() % this.listCom.size();
//        if()
//        int numCom = this.listCom.size();
//        for(int i = 0; i < numCom; i++){
//            int startIndex = numCom*i;
//            int endIndex = numCom*(i+1);
//        }


    }

    @Override
    public void run() {
        super.run();
        while (!this.isEndTask){
            if(this.listPart.size() == 0) continue;
            JsonObject jsonObject = this.listPart.poll();
            JsonArray dataJson = jsonObject.get("data").getAsJsonArray();
            byte[] data = new byte[dataJson.size()];
            for(int i = 0; i < dataJson.size(); i++){
                data[i] = (byte)dataJson.get(i).getAsInt();
            }
            Checksum checker = new Adler32();
            System.out.println("wr");
            ((Adler32) checker).update(data);
            long checksum = jsonObject.get("checksum").getAsLong();
            int indexPart = jsonObject.get("indexPart").getAsInt();
            if(checker.getValue() == checksum){
                System.out.println("+++++++");
                addPartToFile(indexPart, data);
            }
        }
        if(isEndTask){
            FileShare fileShare = new FileShare();
            fileShare.setName(fileDownload.getName());
            fileShare.setMd5(fileDownload.getMd5());
            fileShare.setPath(fileDownload.getPath());
            fileShare.setSize(fileDownload.getSize());
            MyComputer.getInstance().getSharingList().addElement(fileShare);
        }
    }

    public void addDataPartToQueue(JsonObject jsonObject){
        this.listPart.add(jsonObject);
    }

    public void addPartToFile(int index, byte[] data){
        try {
            if(this.listIndex.containsKey(index)){
                System.out.println("write " + index);
                randomAccessFile.seek(index * Constant.PART_SIZE);
                randomAccessFile.write(data);
                this.listIndex.remove(index);
                this.fileDownload.addDownloadedSize(data.length);
                if(this.listIndex.isEmpty()){
                    this.isEndTask = true;
                    this.fileDownload.setStatus(Constant.DOWNLOADED);
                    System.out.println("write done");
                    randomAccessFile.close();
                }
            }
        }catch (Exception e){
            System.out.println("Write part error: " + e.getMessage());
        }
    }

    public FileDownload getFileDownload() {
        return fileDownload;
    }
}
