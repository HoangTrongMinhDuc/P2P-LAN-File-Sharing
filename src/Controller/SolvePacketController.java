package Controller;

import Model.Computer;
import Model.FileShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;
import util.Constant;
import util.Helper;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SolvePacketController {
    JsonParser parser = new JsonParser();
    private HashMap<String, ArrayList<SeedingController>> listSeeding = new HashMap<>();
    Queue<JsonObject> listDataObject = new LinkedList<>();
    private static SolvePacketController ourInstance = new SolvePacketController();
    public static SolvePacketController getInstance() {
        return ourInstance;
    }

    private SolvePacketController() {
        Thread solver = new Thread(new Runnable() {
            @Override
            public void run() {
                    SolveUdpPacket();
            }
        });
        solver.start();
        Thread dataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dataRoute();
            }
        });
        dataThread.start();
    }


    private void SolveUdpPacket(){
        while(true){
            cleanSeedList();
            if(PackageController.getInstance().getQueueReceivePacket().size() == 0)
                continue;
            //get first packet on queue
            DatagramPacket received = PackageController.getInstance().getQueueReceivePacket().poll();
            if(received == null) continue;
            String ip = received.getAddress().getHostAddress();
            int port = received.getPort();
            if(received != null){
                String message = new String(received.getData(), received.getOffset(), received.getLength());
                JsonElement jsonElement = null;
                try {
                    jsonElement = parser.parse(message);

                }catch (Exception e){
                    System.out.println("Packet error: " + message);
                }
                if(jsonElement != null && jsonElement.isJsonObject()){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    //check type packet
                    switch (jsonObject.get("type").getAsInt()){
                        case Constant.HELLO_MES:
                        {
                            solveHelloPacket(ip, port, jsonObject);
                            break;
                        }
                        case Constant.DATA_MES:
                        {
                            this.listDataObject.add(jsonObject);
//                            solveDataPacket(ip, port, jsonObject);
                            break;
                        }
                        case Constant.CONTROL_MES:
                        {
                            solveControlPacket(ip, port, jsonObject);
                            break;
                        }
                        case Constant.REQUEST_DOWNLOAD_FILE:
                        {
                            solveRequestData(ip, port, jsonObject);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void solveHelloPacket(String ip, int port, JsonObject jsonObject){
        boolean isReplyReq = jsonObject.get("replyReq").getAsBoolean();
        if(isReplyReq){
            PackageController.getInstance().sendUdpMesTo(ip, port, MyComputer.getInstance().getHelloWord(false));
        }
        String comName = jsonObject.get("name").getAsString();
        int timeStamp = jsonObject.get("timeStamp").getAsInt();
        JsonArray jsonArray = jsonObject.get("ListFile").getAsJsonArray();
        HashMap<String, FileShare> listShare = new HashMap<>();
        for(int i = 0; i < jsonArray.size(); i++){
            FileShare fileShare = Helper.convertToFileShare(jsonArray.get(i).getAsJsonObject());
            listShare.put(fileShare.getMd5(), fileShare);
        }
        System.out.println("Solve hello from " + ip);
        MyComputer.getInstance().addComputer(new Computer(comName, ip, port, timeStamp, Constant.ONLINE, listShare));
    }

    private void dataRoute(){
        while (true){
            if(this.listDataObject.size() == 0) continue;
            JsonObject jsonObject = this.listDataObject.poll();
            String md5File = jsonObject.get("md5File").getAsString();
            System.out.println("routed");
            DownloadController downloadController = MyComputer.getInstance().getDownloadControllerBy(md5File);
            if(downloadController != null && downloadController.isAlive()){
                downloadController.addDataPartToQueue(jsonObject);
            }
        }
    }

    private void solveControlPacket(String ip, int port, JsonObject jsonObject){
        String md5File = jsonObject.get("md5File").getAsString();
        ArrayList<SeedingController> listSeedingForFile = this.listSeeding.get(md5File + ip + port);
        boolean isEnabled = jsonObject.get("isEnabled").getAsBoolean();
        if(listSeedingForFile != null){
            for(int i = 0; i < listSeedingForFile.size(); i++){
                SeedingController seedingController = listSeedingForFile.get(i);
                if(seedingController.isAlive())
                    seedingController.setEnabled(isEnabled);
            }
        }
    }

    private void solveRequestData(String ip, int port, JsonObject jsonObject){
        Computer computer = new Computer();
        computer.setIp(ip);
        computer.setPort(port);
        int typeRes = jsonObject.get("typeRes").getAsInt();
        String md5File = jsonObject.get("md5File").getAsString();
        FileShare fileShare = MyComputer.getInstance().getFileShareBy(md5File);
        ArrayList<Integer> listIndex = new ArrayList<>();
        switch (typeRes){
            case Constant.REQUEST_DOWNLOAD_PARTS:
            {
                JsonArray jsonArray = jsonObject.get("parts").getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++){
                    listIndex.add(jsonArray.get(i).getAsInt());
                }
                break;
            }
            case Constant.REQUEST_DOWNLOAD_RANGE:
            {
                int partStart = jsonObject.get("partStart").getAsInt();
                int partEnd = jsonObject.get("partEnd").getAsInt();
                for(int i = partStart; i <= partEnd; i++){
                    listIndex.add(i);
                }
                break;
            }
        }
        SeedingController seedingController = new SeedingController(computer, fileShare, listIndex);
        seedingController.start();
        if(this.listSeeding.containsKey(md5File + ip + port)){
            this.listSeeding.get(md5File + ip + port).add(seedingController);
        }else{
            ArrayList<SeedingController> listSeedForFile = new ArrayList<>();
            listSeedForFile.add(seedingController);
            this.listSeeding.put(md5File + ip + port, listSeedForFile);
        }
    }

    private void cleanSeedList(){
        for(ArrayList<SeedingController> seedingControllers : this.listSeeding.values()){
            for(int i = 0; i < seedingControllers.size(); i++){
                if(!seedingControllers.get(i).isAlive()){
                    seedingControllers.remove(i);
                }
            }
            if(seedingControllers.size() == 0){
                this.listSeeding.remove(seedingControllers);
            }
        }
    }
}
