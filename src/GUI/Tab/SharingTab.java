package GUI.Tab;

import GUI.CustomComponent.FileSharingList;
import GUI.Frame.CustomLoading;
import GUI.Frame.SettingFrame;
import Model.FileShare;
import Controller.MyComputer;
import javafx.scene.control.DialogPane;
import util.FileSharedHolder;
import util.LocalSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;

public class SharingTab extends JPanel {
    private JFileChooser fileChooser;
    private JList<FileShare> list;
    private DefaultListModel<FileShare> model;
    private JButton btnAdd;
    private JFrame parentFrame;
    public SharingTab(JFrame parentFrame){
        this.parentFrame = parentFrame;
        initComponent();
        initLayout();
        addListener();
    }

    private void initComponent(){

        fileChooser = new JFileChooser(LocalSetting.getInstance().getDownFolderPath());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        list = new JList<>(MyComputer.getInstance().getSharingList());
        model = (DefaultListModel<FileShare>)list.getModel();
        list.setCellRenderer(new FileSharingList());

    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(100, 25));
        btnAdd = new JButton("Add file");
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btnAdd);
        jPanel.add(buttonPanel);
        jPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        jPanel.add(new JScrollPane(list));
        this.add(jPanel);
    }

    private void addListener(){
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = fileChooser.showDialog(getParent(), "Add file");

                if(p == JFileChooser.APPROVE_OPTION){
                    File file = new File(fileChooser.getSelectedFile().toString());
//                    JDialog dialog = new JDialog();
//                    dialog.setUndecorated(true);
//                    dialog.setLocationRelativeTo(null);
//                    JPanel panel = new JPanel();
//                    panel.setBackground(new Color(255, 202, 197));
//                    panel.setLayout(new BorderLayout());
//                    panel.setPreferredSize(new Dimension(250, 50));
//                    panel.setMinimumSize(panel.getPreferredSize());
//                    panel.setMaximumSize(panel.getPreferredSize());
//                    dialog.setContentPane(panel);
//                    dialog.setAlwaysOnTop(true);
//                    dialog.getContentPane().add(new JLabel("qwerty", SwingConstants.CENTER), BorderLayout.CENTER);
//                    dialog.setBounds(0, 0, 250, 50);
//                    dialog.setVisible(true);
//                    System.out.println(dialog.isShowing()?"showing":"not show");
//                    JOptionPane.showMessageDialog(getRootPane(), "Loading", "load", JOptionPane.PLAIN_MESSAGE);

                    addNewFile(file);

//                    dialog.setVisible(false);
//                    dialog.dispose();
                }
            }
        });
        this.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getPoint().x > 670){
                    int i = list.locationToIndex(e.getPoint());
                    Object[] options = {"Delete", "Cancel"};
                    int n = JOptionPane.showOptionDialog(getParent(),
                            "Do you delete from sharing list?",
                            "Confirm your action",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[1]);
                    if(n == 0){
                        System.out.println("del "+i);
                        MyComputer.getInstance().removeFileSharing(i);
                    }

                }
            }
        });
    }

    private void addNewFile(File file){
        try{
            CustomLoading customLoading = new CustomLoading(parentFrame, file);
            this.updateUI();
        }catch (Exception e){
            System.out.println("Error add share: " + e.getMessage());
        }
    }

}
