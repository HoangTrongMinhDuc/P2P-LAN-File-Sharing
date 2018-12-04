package Controller;

import Model.Computer;
import Model.FileShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import util.Constant;
import util.Helper;

import java.net.DatagramPacket;
import java.util.HashMap;

public class SolvePacketController {
    JsonParser parser = new JsonParser();

    private static SolvePacketController ourInstance = new SolvePacketController();
    public static SolvePacketController getInstance() {
        return ourInstance;
    }

    private SolvePacketController() {
        Thread solver = new Thread(new Runnable() {
            @Override
            public void run() {
//                while (true){
                    SolveUdpPacket();
//                }
            }
        });
        solver.start();
    }


    private void SolveUdpPacket(){
        while(true){
            if(PackageController.getInstance().getQueueReceivePacket().size() == 0)
                continue;
            //get first packet on queue
            DatagramPacket received = PackageController.getInstance().getQueueReceivePacket().poll();
            String ip = received.getAddress().getHostAddress();
            int port = received.getPort();
            if(received != null){
                String message = new String(received.getData(), 0, received.getLength());
                JsonElement jsonElement = parser.parse(message);
                if(jsonElement.isJsonObject()){
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
                            solveDataPacket();
                            break;
                        }
                        case Constant.CONTROL_MES:
                        {
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

    private void solveDataPacket(){

    }
}
