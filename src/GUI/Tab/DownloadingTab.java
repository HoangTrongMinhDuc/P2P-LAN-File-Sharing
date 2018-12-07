package GUI.Tab;

import Controller.DownloadController;
import Controller.MyComputer;
import GUI.CustomComponent.DownloadingList;

import javax.swing.*;

public class DownloadingTab extends JPanel {
    private JList<DownloadController> list;
    public DownloadingTab(){

        initComponent();
        initLayout();
    }

    private void initComponent(){
        list = new JList<>(MyComputer.getInstance().getListDownloading());
        list.setCellRenderer(new DownloadingList());
    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new JScrollPane(list));
    }

    public JList<DownloadController> getList() {
        return list;
    }
}
