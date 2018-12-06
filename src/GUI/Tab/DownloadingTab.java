package GUI.Tab;

import Controller.DownloadController;
import Controller.MyComputer;
import GUI.CustomComponent.FileSharingList;
import Model.FileShare;
import util.LocalSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DownloadingTab extends JPanel {
    private JList<DownloadController> list;
    private DefaultListModel<DownloadController> model;
    private JButton btnAdd;
    public DownloadingTab(){
        initComponent();
        initLayout();
    }

    private void initComponent(){
        list = new JList<>(MyComputer.getInstance().getListDownloading());
        model = (DefaultListModel<DownloadController>)list.getModel();
//        list.setCellRenderer();

    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new JScrollPane(list));

    }

}
