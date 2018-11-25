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
        for(index = 0; index < this.listConnected.size(); index++){
            Computer com = this.listConnected.get(index);
            int res = com.compareTo(computer);
            if(res < 0){
                continue;
            }else {
                //if old connected computer
                if(res == 0){
                    com.setName(computer.getName());
                    com.setTimestamp(computer.getTimestamp());
                    com.setPort(computer.getPort());
                    com.setStatus(computer.getStatus());
                    com.setListFileShare(computer.getListFileShare());
                    System.out.println("changed " + computer.getIp());
                    break;
                }else{
                    if(res > 0){
                        this.listConnected.add(index, computer);
                        System.out.println("added " + computer.getIp());
                        break;
                    }
                }
            }
        }
        if(this.listConnected.size() == 0){
            this.listConnected.addElement(computer);
            System.out.println("added " + computer.getIp());
        }
    }

    private void refreshList(){
        for(int i = 0; i < this.listConnected.size(); i++){
            Computer computer = this.listConnected.get(i).clone();
            this.listConnected.set(i, computer);
            int time = (int)System.currentTimeMillis() - computer.getTimestamp()*1000;
            if(computer.getStatus() == Constant.ONLINE || computer.getStatus() == Constant.PENDING || computer.getStatus() == Constant.CHECKING){
                if(time > Constant.TIME_OUT_ALIVE && computer.getStatus() != Constant.OFFLINE){
                    computer.setStatus(Constant.OFFLINE);
                    this.listConnected.removeElementAt(i);
                    this.listConnected.add(i, computer);
                    System.out.println("OFFLINE "+computer.getName());
                }else{
                    if(time > Constant.TIME_PENDING && computer.getStatus() != Constant.PENDING){
                        computer.setStatus(Constant.PENDING);
                        this.listConnected.removeElementAt(i);
                        this.listConnected.add(i, computer);
                        System.out.println("PENDING "+computer.getName());
                    }
                }
            }
            if(computer.getStatus() != Constant.ONLINE && computer.getStatus() != Constant.CHECKING){
                if(time < Constant.TIME_PENDING){
                    computer.setStatus(Constant.ONLINE);
                    this.listConnected.removeElementAt(i);
                    this.listConnected.add(i, computer);
                    System.out.println("ONLINE "+computer.getName());
                }
            }
        }
    }

    public String[] getAllAliveConnect(){
        String[] aliveConnect = null;
        int count = 0;
        for(int i = 0; i < this.listConnected.size(); i++){
            Computer computer = this.listConnected.get(i);
            if(computer.getStatus() != Constant.OFFLINE){
                ++count;
            }
        }
        if(count != 0){
            aliveConnect = new String[count];
            int ii = 0;
            for(int i = 0; i < this.listConnected.size(); i++){
                Computer computer = this.listConnected.get(i);
                if(computer.getStatus() != Constant.OFFLINE){
                    aliveConnect[ii] = computer.getIp();
                    ++ii;
                }
            }
        }
        return aliveConnect;
    }

}
