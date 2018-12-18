package Controller;

import javax.swing.*;
import java.awt.*;

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
        JWindow window = new JWindow();
        window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.getContentPane().add(Box.createVerticalGlue());
        Box b1 = Box.createHorizontalBox();
        b1.add(Box.createHorizontalGlue());
        JLabel lbLoading = new JLabel("Initializing services...", SwingConstants.CENTER);
        lbLoading.setFont(new Font(lbLoading.getFont().getName(), Font.PLAIN, 20));
        b1.add(lbLoading);
        b1.add(Box.createHorizontalGlue());
        window.getContentPane().add(b1);
        Box b2 = Box.createHorizontalBox();
        b2.add(Box.createHorizontalGlue());
        JLabel lbload = new JLabel("abc...");
        b2.add(lbload);
        b2.add(Box.createHorizontalGlue());
        window.getContentPane().add(b2);
        window.getContentPane().add(Box.createVerticalGlue());
        window.setBounds(0, 0, 300, 100);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(new Color(197, 225, 136));
        window.setVisible(true);
        lbload.setText("Creating socket...");
        PackageController.getInstance();
        lbload.setText("Import file share information...");
        MyComputer.getInstance();
        lbload.setText("Creating transfer service...");
        SolvePacketController.getInstance();
        window.setVisible(false);
        MyComputer.getInstance().start();
    }


    public MainController(){}
}
