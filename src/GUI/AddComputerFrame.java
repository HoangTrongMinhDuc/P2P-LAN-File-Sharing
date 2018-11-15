package GUI;

import javax.swing.*;
import java.awt.*;

public class AddComputerFrame extends JFrame {
    private JTextField inTextIp;
    private JTextField inTextPort;
    private JButton btnAdd;
    private JButton btnCancel;
    public AddComputerFrame(){
        initLayout();
        initFrame();
    }

    void initLayout() {
        //init component
        inTextIp = new JTextField();
        inTextPort = new JTextField();
        btnAdd = new JButton("Add");
        btnCancel = new JButton("Cancel");
        //init wrapper layout
        this.setLayout(new BorderLayout());
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        //add first line
        Box lineBox1 = Box.createHorizontalBox();
        lineBox1.add(new JLabel("IP address"));
        lineBox1.add(inTextIp);
        wrapper.add(lineBox1);
        //add second line
        Box lineBox2 = Box.createHorizontalBox();
        lineBox2.add(new JLabel("Port (optional)"));
        lineBox2.add(inTextPort);

        this.add(wrapper, BorderLayout.WEST);

        //init panel button
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(btnAdd);
        buttonBox.add(btnCancel);
        this.add(buttonBox, BorderLayout.SOUTH);
    }

    void initFrame(){
        this.setTitle("Add new computer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(360, 240);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
