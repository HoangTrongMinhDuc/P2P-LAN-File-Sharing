package Model;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.net.ntp.TimeStamp;
import util.Constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyComputer extends Computer {
    private TimeStamp timeStamp = new TimeStamp(System.currentTimeMillis());
    private int subnetMask;
    private static MyComputer instance = new MyComputer();
    private LinkedHashMap<String, Computer> listComputerConnected = new LinkedHashMap<>();
    public static MyComputer getInstance(){return instance;}
    private MyComputer(){

    }

    public int getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(int subnetMask) {
        this.subnetMask = subnetMask;
    }

    public static void setInstance(MyComputer instance) {
        MyComputer.instance = instance;
    }

    public String getHelloWord(boolean replyReq){
        String json =  "{\"type\":" + Constant.HELLO_MES + ", \"replyReq\":" + replyReq
                + ", \"name\":\"" + this.name + "\""
                + ", \"timeStamp\":" + System.currentTimeMillis()/1000
                + ", \"ListFile\":[";
        int count = 0;
        if(this.listFileShare != null)
        for(FileShare fileShare: this.listFileShare.values()){
            json += fileShare.getObjectString();
            count++;
            if(count < this.listFileShare.size())
                json += ",";
        }
        json += "]}";
        return json;
    }

    public HashMap<String, Computer> getListComputerConnected() {
        return listComputerConnected;
    }

    public void setListComputerConnected(LinkedHashMap<String, Computer> listComputerConnected) {
        this.listComputerConnected = listComputerConnected;
    }

    public void addNewComputerConnect(Computer newCom){
        if(!this.listComputerConnected.containsKey(newCom.getIp())){
            this.listComputerConnected.put(newCom.getIp(), newCom);
        }
    }
}
