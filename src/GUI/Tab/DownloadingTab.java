package GUI.Tab;

import Controller.DownloadController;
import Controller.MyComputer;
import Controller.PackageController;
import GUI.CustomComponent.DownloadingList;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DownloadingTab extends JPanel {
    private JList<DownloadController> list = null;
    public DownloadingTab(){

        initComponent();
        initLayout();
        addListener();
    }

    private void initComponent(){
        list = new JList<>(MyComputer.getInstance().getListDownloading());
        list.setCellRenderer(new DownloadingList());
    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new JScrollPane(list));
    }

    private void addListener(){
//        this.list.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                if(list.getModel().getSize() == 0) return;
//                HashMap<Integer, Integer> indexs = list.getModel().getElementAt(list.locationToIndex(e.getPoint())).getListIndex();
//                System.out.println("list index");
//                ArrayList<Integer> arrayList = new ArrayList<>();
//                if(!indexs.isEmpty()){
//                    for(int index : indexs.keySet()){
//                        System.out.print(index+"|");
//                        arrayList.add(index);
//                    }
//                    list.getModel().getElementAt(list.locationToIndex(e.getPoint())).requestAgain();
//                }
//                System.out.println("--------");
//            }
//        });
    }

    public void updateListUI(){
        if(this.list != null){
            this.updateUI();
        }
    }

    public JList<DownloadController> getList() {
        return list;
    }
}
