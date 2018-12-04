package util;

import Model.FileSeed;
import Model.FileShare;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Helper {
    private static final Pattern IP_PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern PORT_PATTERN = Pattern.compile("[0-9]{1,5}$");

    public static boolean validateIp4(final String ip){
        return IP_PATTERN.matcher(ip).matches();
    }

    public static boolean validatePort(final String port){
        return PORT_PATTERN.matcher(port).matches();
    }

    public static FileShare convertToFileShare(JsonObject jsonObject){
        return new FileShare(jsonObject.get("name").getAsString(), jsonObject.get("md5").getAsString(), jsonObject.get("size").getAsInt(), "Not set");
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static void insertElement(DefaultListModel<FileSeed> model, FileSeed fileSeed){
        if(model.size() == 0) {
            model.addElement(fileSeed);
        }else{
            for(int i = 0; i < model.size(); i++){
                FileSeed fileSeed1 = model.get(i);
                int compareRes = fileSeed.getName().toLowerCase().compareTo(fileSeed1.getName().toLowerCase());
                if(compareRes > 0){
                    if(i == model.size() - 1){
                        model.addElement(fileSeed);
                        break;
                    }
                    continue;
                }else {
                    if(compareRes < 0){
                        model.add(i, fileSeed);
                        break;
                    }else {
                        model.setElementAt(fileSeed, i);
                        break;
                    }
                }
            }
        }
    }

    public static String getStatus(int id){
        switch (id){
            case Constant.SHARING:
                return "Shared";
            case Constant.SHARED:
                return "Shared";
            case Constant.OFFLINE:
                return "Offline";
            default:
                return "";
        }
    }
}
