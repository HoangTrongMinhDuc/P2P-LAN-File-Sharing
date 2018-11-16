package Controller;

import GUI.MainFrame;
import sun.applet.Main;

import javax.swing.*;

public class MainController {
    public static void main(String[] args){
        try {
            // Set cross-platform Java L&F (also called "Metal")
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
       new MainFrame();
    }
    public MainController(){}
}
