package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SettingFrame extends JFrame {
    private final int WIDTH_FRAME = 680;
    private final int HEIGHT_FRAME = 480;
    private JFrame parentFrame;
    public SettingFrame(JFrame parentFrame){
        this.parentFrame = parentFrame;
        initFrame();
        initLayout();
        addListener();
    }

    private void initFrame(){
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Setting");
        this.setLocationRelativeTo(null);
    }

    void initLayout(){
        this.setLayout(new GridLayout(5,4));
        JLabel lbPath = new JLabel("Path");
        this.add(lbPath);
    }

    private void setCallBackToParent(boolean isEnabled){
//        this.parentFrame.setEnabled(isEnabled);
//        this.setVisible(!isEnabled);
    }

    private void addListener(){
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
//                setCallBackToParent(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
//                setCallBackToParent(true);

            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

}
