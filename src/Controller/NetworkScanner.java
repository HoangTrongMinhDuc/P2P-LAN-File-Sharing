package Controller;

import org.apache.commons.net.util.SubnetUtils;
import util.Helper;

import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

public class NetworkScanner {
    private String ipClient;
    private int subnetMask;
    private String[] allAddresses;
    private int[] checkList;
    public NetworkScanner(){
        getLocalAddress();
//        Thread sendGreet = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                greetAllComputer();
//            }
//        });
//        sendGreet.start();

    }

    private void getLocalAddress(){
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)){
                if(netint.isUp() && !netint.isVirtual() && !netint.isLoopback() && !netint.isPointToPoint()){
                    if(netint.getDisplayName().toLowerCase().indexOf("virtual") == -1){
                        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                            if(Helper.validateIp4(inetAddress.getHostAddress())){
                                this.ipClient = inetAddress.getHostAddress();
                                MyComputer.getInstance().setIp(this.ipClient);
                                this.subnetMask = netint.getInterfaceAddresses().get(0).getNetworkPrefixLength();
                                MyComputer.getInstance().setSubnetMask(this.subnetMask);
                                MyComputer.getInstance().setName(InetAddress.getLocalHost().getHostName());
                            }
                        }
                    }
                }
            }
            SubnetUtils utils = new SubnetUtils(this.ipClient + "/" + this.subnetMask);
            this.allAddresses = utils.getInfo().getAllAddresses();
            this.checkList = new int[this.allAddresses.length];
        }catch (Exception e){
            System.out.println("Get local address: " + e.getMessage());
        }

    }

    public String[] getAllAddresses() {
        return allAddresses;
    }
}
