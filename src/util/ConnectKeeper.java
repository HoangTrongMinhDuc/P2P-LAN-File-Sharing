package util;

import GUI.Frame.MainFrame;
import Model.Computer;
import org.apache.commons.net.ntp.TimeStamp;
import sun.applet.Main;

import javax.swing.*;

public class ConnectKeeper {
    volatile public DefaultListModel<Computer> listConnected = new DefaultListModel<>();
    private TimeStamp timeStamp = new TimeStamp(System.currentTimeMillis());

    private static ConnectKeeper ourInstance = new ConnectKeeper();
    public static ConnectKeeper getInstance() {
        return ourInstance;
    }
    private ConnectKeeper() {
        new MainFrame(listConnected);
        Thread refreshListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        refreshList();
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        refreshListThread.start();

    }

    public void addComputer(Computer computer){
        int index = 0;
//        for(index = 0; index < this.listConnected.size(); index++){
//            if()
//        }
        this.listConnected.addElement(computer);
//        MainFrame.getInstance().addNewCom(computer);
        System.out.println("added " + computer.getIp());
//        MainFrame.getInstance().getJList().setModel(this.listConnected);
    }

    private void refreshList(){
        System.out.println("Scanning list");
        for(int i = 0; i < this.listConnected.size(); i++){
            Computer computer = this.listConnected.get(i).clone();
            this.listConnected.set(i, computer);
            int time = (int)System.currentTimeMillis() - computer.getTimestamp()*1000;
            if(time > Constant.TIME_OUT_ALIVE && computer.getStatus() != Constant.OFFLINE){
                computer.setStatus(Constant.OFFLINE);
                this.listConnected.removeElementAt(i);
                this.listConnected.add(i, computer);
                System.out.println("Pendding "+computer.getName());
            }
        }
    }

    public String[] getAllAliveConnect(){
        return null;
    }

}
