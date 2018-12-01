package Controller;

import GUI.Frame.MainFrame;
import org.apache.commons.net.util.SubnetUtils;
import util.ConnectKeeper;
import util.FileSharedHolder;
import util.Helper;

import javax.swing.*;

public class MainController {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        PackageController.getInstance();
        SolvePacketController.getInstance();
        FileSharedHolder.getInstance();
//        new NetworkScanner();
//        MainFrame.getInstance();
    }


    public MainController(){}
}
