package GUI.Tab;

import GUI.CustomComponent.FileSharingList;
import Model.FileShare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SharingTab extends JPanel {
    public SharingTab(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setMaximumSize(new Dimension(100, 25));
        JButton btnAdd = new JButton("Share new file");
        buttonPanel.add(btnAdd, BorderLayout.CENTER);
        this.add(buttonPanel);

        JScrollPane jScrollPane = new JScrollPane();
        DefaultListModel<FileShare> model = new DefaultListModel<>();
        JList<FileShare> list = new JList<>(model);
        list.setCellRenderer(new FileSharingList());
//        jScrollPane.add(list);
        this.add(new JScrollPane(list));


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("addnew");
                model.addElement(new FileShare("file.mnn", "hjaksg", 12312, "dgjhasdfg"));
                list.updateUI();
            }
        });
    }

}
