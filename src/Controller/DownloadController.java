package Controller;

import Model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private long oldIndex = -2;
    private float oldSpeed = 1;
    private int[][] chunkIndex;
//    private ArrayList
    public DownloadController(FileSeed fileSeed, ArrayList<Computer> listCom){
        this.fileDownload = new FileDownload(fileSeed);
        this.listCom = listCom;
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
        if(this.listCom.size() != 1){
            int numOfChunks = (int)Math.ceil((double)this.fileDownload.getTotalPart() / this.listCom.size());
            chunkIndex = new int[numOfChunks][];
            for(int i = 0; i < numOfChunks; ++i) {
                int[] temp = new int[2];
                int start = i * this.listCom.size();
                int length = Math.min(this.fileDownload.getTotalPart() - start, this.listCom.size());
                temp[0] = start;
                temp[1] = start+length-1;
                chunkIndex[i] = temp;
            }
        }
    }

    public void startRequest(){
        if(listCom.size() == 1){
            System.out.println("first");
            PackageController.getInstance().sendDownloadRangeRequest(
                    listCom.get(0).getIp(),
                    listCom.get(0).getPort(),
                    0,
                    fileDownload.getMd5(),
                    0,
                    fileDownload.getTotalPart()-1);
        }else {
            System.out.println("sec");
            for(int i = 0; i < this.listCom.size(); i++){
                PackageController.getInstance().sendDownloadRangeRequest(
                        listCom.get(i).getIp(),
                        listCom.get(i).getPort(),
                        i,
                        fileDownload.getMd5(),
                        this.chunkIndex[i][0],
                        chunkIndex[i][1]);
            }
        }
    }

    @Override
    public void run() {
        startRequest();
        super.run();
        while (!this.isEndTask){
            try{
                if(pastTime == 0){
                    pastTime = System.currentTimeMillis();
                }
                if(System.currentTimeMillis() - pastTime > 1000l){
                    this.fileDownload.upDateSpeed();
                    if(this.fileDownload.getSpeed() == 0 && oldSpeed > 0){
                        requestAgain();
                    }
                    oldSpeed = this.fileDownload.getSpeed();
                    pastTime = System.currentTimeMillis();
                }
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
//                    System.out.println("index"+indexPart);
                    byte[] dt = new byte[dtLength-data[37]-1];
                    System.arraycopy(data,data[37]+1, dt, 0, dt.length);
                    this.oldSpeed = -1;
                    addPartToFile(indexPart, dt);
                }else
                    System.out.println("data null");
            }catch (Exception e){
                System.out.println("fail packet"+e.getMessage());
                break;
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

    public void addDataPartToQueue(DataPack dataPack){
        try{
//            this.listParts.add(dataPack);
            this.listPartData.add(dataPack);
        }catch (Exception e){
            System.out.println("Add part err: " + e.getMessage());
        }
    }

    public void addPartToFile(long index, byte[] data){
        try {
            if(this.listIndex.containsKey((int)index)){
//                System.out.println("write " + index);
                if(oldIndex != index - 1l){
                    randomAccessFile.seek(index * Constant.PART_SIZE);
                    oldIndex = index;
                }
                randomAccessFile.write(data);
                this.listIndex.remove((int)index);
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
        int count = 0;
        for(int index : this.listIndex.keySet()){
            count++;
            arrayList.add(index);
            if(count == 50){
                PackageController.getInstance().sendDownloadRequest(listCom.get(0).getIp(), listCom.get(0).getPort(),0, fileDownload.getMd5(), arrayList);
                count = 0;
                arrayList.clear();
            }
//            System.out.println("accc");
        }
        PackageController.getInstance().sendDownloadRequest(listCom.get(0).getIp(), listCom.get(0).getPort(),0, fileDownload.getMd5(), arrayList);
//        if(arrayList.size() > 50){
//            while(true){
//                int count = 0;
//
//            }
//
//        }else
//            PackageController.getInstance().sendDownloadRequest(listCom.get(0).getIp(), listCom.get(0).getPort(),0, fileDownload.getMd5(), arrayList);
    }
}
