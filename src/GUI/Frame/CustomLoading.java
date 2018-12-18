package GUI.Frame;

import Controller.MyComputer;
import Model.FileShare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;

public class CustomLoading extends JFrame {
    private File file;
    public CustomLoading(JFrame parentFrame, File file){
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Getting file definition..."), BorderLayout.CENTER);
        this.file = file;
        this.setTitle("Importing information");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try{
                        FileInputStream fileInputStream = new FileInputStream(file);
                        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fileInputStream);
                        fileInputStream.close();
                        FileShare fileShare = new FileShare(file.getName(), md5, (int)file.length(), file.getPath());
                        MyComputer.getInstance().addNewSharingFile(fileShare);
                        notify();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        thread.start();
        this.setVisible(true);
        synchronized (thread){
            try{
                thread.wait();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        close();
    }
    public void close(){
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
