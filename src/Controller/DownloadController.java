package Controller;

import Model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;
import util.Constant;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class DownloadController extends Thread {
    private FileDownload fileDownload;
    private RandomAccessFile randomAccessFile;
    private ArrayList<Computer> listCom;
    private HashMap<Integer, Integer> listIndex = new HashMap<>();
    volatile private LinkedList<DataPack> listParts = new LinkedList<>();
    BlockingQueue<DataPack> listPartData = new LinkedBlockingDeque<>();
    private boolean isEndTask = false;
    private long pastTime = 0;
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
            }else {
                this.randomAccessFile = new RandomAccessFile(fileDownload.getPath(), "rw");
            }
        }catch (Exception e){
            System.out.println("Create empty error: " + e.getMessage());
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

    public void startRequest(){
        if(listCom.size() == 1){
            PackageController.getInstance().sendDownloadRangeRequest(listCom.get(0).getIp(), listCom.get(0).getPort(), fileDownload.getMd5(),
                    0, fileDownload.getTotalPart()-1);
        }
    }

    @Override
    public void run() {
        startRequest();
        super.run();
        byte[] d = null;
        while (!this.isEndTask){
            try{
                if(pastTime == 0){
                    pastTime = System.currentTimeMillis();
                }
                if(System.currentTimeMillis() - pastTime > 1000l){
                    this.fileDownload.upDateSpeed();
                    pastTime = System.currentTimeMillis();
                }
//                if(this.listParts.size() == 0) continue;
//                if(this.listPartData.size() == 0) {
//                    System.out.println("empty queue");
//                    continue;
//                }
//                DataPack dataPack = this.listParts.poll();
                DataPack dataPack = this.listPartData.poll();
                if(dataPack == null){
//                    System.out.println("data pack in download is null");
                    continue;
                }
                byte[] data = dataPack.getData();
                int dtLength = dataPack.getLength();

                if(data != null){
                    int indexPart = 0;
                    for(int i = 38; i <= data[37]; i++){
                        indexPart = indexPart*100 + data[i];
                    }
                    System.out.println("idex"+indexPart);
//                    if(!this.listIndex.containsKey(0) && indexPart==0 || indexPart==21){
//                        System.out.println("zero"+Arrays.toString(data));
//                    }
                    long cSum = 0;
                    for(int i = data[37]+2; i <= data[data[37]+1]; i++){
                        cSum = cSum*100 + data[i];
                    }
                    System.out.println("==="+cSum);
                    byte[] dt = new byte[dtLength-data[data[37]+1]-1];
                    System.arraycopy(data,data[data[37]+1]+1, dt, 0, dt.length);
                    Checksum checker = new Adler32();
                    ((Adler32) checker).update(dt);
                    if(checker.getValue() == cSum){
                        System.out.println("+++++++++++++++++++++++++");
                        addPartToFile(indexPart, dt);
                    }

                }else
                    System.out.println("data null");
            }catch (Exception e){
                System.out.println("fail packet"+e.getMessage()+ Arrays.toString(d));
            }
//            System.out.println("ssss");

//            if(checker.getValue() == checksum){
//                System.out.println("+++++++");
//            }
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

    public void addDataPartToQueue(DataPack dataPack){
        try{
//            this.listParts.add(dataPack);
            this.listPartData.add(dataPack);
        }catch (Exception e){
            System.out.println("Add part err: " + e.getMessage());
        }
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

    public HashMap<Integer, Integer> getListIndex() {
        return listIndex;
    }

    public void requestAgain(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int index : this.listIndex.values()){
            arrayList.add(index);
        }
        PackageController.getInstance().sendDownloadRequest(listCom.get(0).getIp(), listCom.get(0).getPort(), fileDownload.getMd5(), arrayList);
    }
}
