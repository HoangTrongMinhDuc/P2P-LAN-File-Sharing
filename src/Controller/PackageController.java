package Controller;

import util.Constant;
import util.LocalSetting;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PackageController {
    public final static byte[] BUFFER = new byte[4096];
    private boolean isFirstTime = true;
    private DatagramSocket datagramSocket = null;
    volatile private Queue<DatagramPacket> queueReceivePacket = new LinkedList<>();

    private static PackageController instance = new PackageController();
    public static PackageController getInstance(){return instance;}

    private PackageController(){
        try{
                datagramSocket = new DatagramSocket(LocalSetting.getInstance().getPort());
        }catch (Exception e){
            System.out.println("Create socket: " + e.getMessage());
        }
        //start receive mess
        Thread receiveUdpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiverUdp();
            }
        });
        receiveUdpThread.start();

        //start receive tcp packet
        Thread receiveTcpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiverTcp();
            }
        });
//        receiveTcpThread.start();

        //send hello to all computer
        Thread greetThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        greetAllComputer();
                        Thread.sleep(Constant.REFRESH_GREET_TIME);
                    }catch (Exception e){
                        System.out.println("Greet thread: " + e.getMessage());
                    }
                }
            }
        });
        greetThread.start();
    }


    public void receiverUdp(){
        try{
            while (true){
                byte[] datagram = new byte[5120];
                DatagramPacket incoming = new DatagramPacket(datagram, datagram.length);
                datagramSocket.receive(incoming);
                String message = new String(incoming.getData(), incoming.getOffset(), incoming.getLength());
//                System.out.println(message.length()+ " Received: " + message + "|" + incoming.getAddress().getHostAddress() + ":" + incoming.getPort());
                this.queueReceivePacket.add(incoming);
            }

        }catch (Exception e){
            System.out.println("REC " + e.getMessage());
        }
    }



    public void receiverTcp(){
        try{
            while (true){

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void greetAllComputer(){
        try{
            String[] allAddress;
            String greetMes = MyComputer.getInstance().getHelloWord(this.isFirstTime);
            if(this.isFirstTime){
                NetworkScanner networkScanner = new NetworkScanner();
                allAddress = networkScanner.getAllAddresses();
                this.isFirstTime = false;
                System.out.println("First time hello");
            }else {
                allAddress = MyComputer.getInstance().getAllAliveConnect();
                System.out.println("Hello frequency");
            }
            if(allAddress != null)
            for (String ip : allAddress) {
                if(!ip.equals(MyComputer.getInstance().getIp()))
                {
                    sendUdpMesTo(ip, Constant.DEFAULT_PORT, greetMes);
                    System.out.println("Sent hello " + ip);
                }
            }
        }catch (Exception e){
            System.out.println("SE " + e.getMessage());
        }
    }
    public void sendDownloadRequest(String ip, int port, ArrayList<Integer> listIndex){

    }

    public void sendDownloadRangeRequest(String ip, int port, String md5File, int startIndex, int endIndex){
        String mes = "{\"type\":"+Constant.REQUEST_DOWNLOAD_FILE+",\"typeRes\":"+Constant.REQUEST_DOWNLOAD_RANGE+
                ",\"md5File\":\""+md5File+"\",\"partStart\":"+startIndex+",\"partEnd\":"+endIndex+"}";
        sendUdpMesTo(ip, port, mes);
    }


    public void sendUdpMesTo(String ip, int port, String mes){
        try{
            DatagramPacket datagramPacket = new DatagramPacket(mes.getBytes(), mes.length(), InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket);
        }catch (Exception e){
            System.out.println("Send UDP: " + e.getMessage());
        }
    }

    public Queue<DatagramPacket> getQueueReceivePacket() {
        return queueReceivePacket;
    }
}

