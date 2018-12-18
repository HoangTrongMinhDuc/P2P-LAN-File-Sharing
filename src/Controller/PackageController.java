package Controller;

import util.Constant;
import util.LocalSetting;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PackageController {
    private boolean isFirstTime = true;
    private DatagramSocket datagramSocket = null;
    volatile private BlockingQueue<DatagramPacket> queueReceivePacket = new LinkedBlockingDeque<>();
    private int port = 0;
    private static PackageController instance = new PackageController();
    public static PackageController getInstance(){return instance;}
    volatile private boolean switchThread = true;
    private PackageController(){
        while(true){
            try{
                if(this.port == 0 )
                    port = LocalSetting.getInstance().getPort();
                datagramSocket = new DatagramSocket(this.port);
                if(LocalSetting.getInstance().getPort() != this.port){
                    LocalSetting.getInstance().setPort(this.port, false);
                }
                System.out.println("create port success");
                break;
            }catch (Exception e){
                System.out.println("Create socket: " + e.getMessage());
                String portStr = JOptionPane.showInputDialog(new JFrame(), "Please enter new port:", "The port " + this.port + " already in use", JOptionPane.PLAIN_MESSAGE);
                this.port = Integer.parseInt(portStr);
            }
        }

        //start receive mess
        Thread receiveUdpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiverUdp();
            }
        });
        receiveUdpThread.start();
//        Thread receiveUdpThread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                receiverUdpSec();
//            }
//        });
//        receiveUdpThread2.start();

        //send hello to all computer
//        Thread greetThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (switchThread){
//                    try{
//                        greetAllComputer();
//                        Thread.sleep(Constant.REFRESH_GREET_TIME);
//                    }catch (Exception e){
//                        System.out.println("Greet thread: " + e.getMessage());
//                    }
//                }
//            }
//        });
//        greetThread.start();
    }


    public void receiverUdp(){
        try{
            while (true){
//                if(this.switchThread){
                byte[] datagram = new byte[Constant.PART_SIZE+512];
                DatagramPacket incoming = new DatagramPacket(datagram, datagram.length);
                    datagramSocket.receive(incoming);
//                    this.switchThread = false;
                    this.queueReceivePacket.add(incoming);
//                }
            }
        }catch (Exception e){
            System.out.println("REC1 " + e.getMessage());
        }
    }
    public void receiverUdpSec(){
        try{
            while (true){
                if(!this.switchThread){
                    byte[] datagram = new byte[Constant.PART_SIZE+256];
                    DatagramPacket incoming = new DatagramPacket(datagram, datagram.length);
                    datagramSocket.receive(incoming);
                    this.switchThread = true;
                    this.queueReceivePacket.add(incoming);
                }
            }
        }catch (Exception e){
            System.out.println("REC2 " + e.getMessage());
        }
    }
    public void greetAllComputer(){
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
                {
                    System.out.println("parsing");
                    int p = Constant.DEFAULT_PORT;
                    if(ip.split(":").length == 2) {
                        p = Integer.parseInt(ip.split(":")[1]);
                        ip = ip.split(":")[0];
                    }
                    System.out.println("parse done");
                    if(!ip.equals(MyComputer.getInstance().getIp()) || p!=MyComputer.getInstance().getPort()){
                        sendUdpMesTo(ip, p, greetMes);
                        System.out.println("Sent hello " + ip);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("SE " + e.getMessage());
        }
    }
    public void sendDownloadRequest(String ip, int port, String md5File, ArrayList<Integer> listIndex){
        String mes = "{\"type\":"+Constant.REQUEST_DOWNLOAD_FILE+",\"typeRes\":"+Constant.REQUEST_DOWNLOAD_PARTS+
                ",\"md5File\":\""+md5File+"\",\"parts\":[";
        for(int i = 0; i < listIndex.size(); i++){
            mes += listIndex.get(i).toString();
            if(i != listIndex.size()-1)
                mes += ",";
        }
        mes += "]}";
        sendUdpMesTo(ip, port, mes);
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

    public void sendDataUdpMesTo(String ip, int port, byte[] data){
        try{
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            datagramSocket.send(datagramPacket);
        }catch (Exception e){
            System.out.println("Send data UDP: " + e.getMessage());
        }
    }

    public Queue<DatagramPacket> getQueueReceivePacket() {
        return queueReceivePacket;
    }

    public void reStartSocket(int port){
        try{
            this.port = port;
            this.datagramSocket = new DatagramSocket(this.port);
            this.isFirstTime = true;
        }catch (Exception e){
            System.out.println("Restart socket error: " + e.getMessage());
        }
    }
}

