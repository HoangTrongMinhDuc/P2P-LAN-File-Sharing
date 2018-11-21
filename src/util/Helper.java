package util;

import java.util.regex.Pattern;

public class Helper {
    private static final Pattern IP_PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern PORT_PATTERN = Pattern.compile("[0-9]{1,5}$");

    public static boolean validateIp(final String ip){
        return IP_PATTERN.matcher(ip).matches();
    }

    public static boolean validatePort(final String port){
        return PORT_PATTERN.matcher(port).matches();
    }
}
