package GUI.Frame;

import Model.Computer;
import util.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddComputerFrame extends JDialog implements ActionListener {
    private final int WIDTH_FRAME = 300;
    private final int HEIGHT_FRAME = 160;
    private JTextField inTextIp;
    private JTextField inTextPort;
    private JButton btnAdd;
    private JButton btnCancel;
    private JFrame parent;
    private DefaultListModel<Computer> model;
    public AddComputerFrame(JFrame parentFrame, DefaultListModel<Computer> model){
        super(parentFrame, "Add computer", false);
        this.parent = parentFrame;
        this.model = model;
        initLayout();
        setListener();
        initFrame();
    }

    void initLayout() {
        //init component
        inTextIp = new JTextField(16);
        inTextPort = new JTextField(16);
        btnAdd = new JButton("Add");
        btnCancel = new JButton("Cancel");
        //init wrapper layout
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //add first line
        Box lineBox1 = Box.createHorizontalBox();
        lineBox1.setBorder(BorderFactory.createEmptyBorder(20,10,10, 10));
        JLabel lb1 = new JLabel("IP address");
        lb1.setMaximumSize(lb1.getPreferredSize());
        lineBox1.add(lb1);
        lineBox1.add(Box.createRigidArea(new Dimension(10, 0)));
        inTextIp.setMaximumSize(inTextIp.getPreferredSize());
        lineBox1.add(inTextIp);
        this.add(lineBox1);
        //add second line
        Box lineBox2 = Box.createHorizontalBox();
        JLabel lb2 = new JLabel("Port (optional)");
        lb1.setMaximumSize(lb2.getPreferredSize());
        lineBox2.add(lb2);
        lineBox2.add(Box.createRigidArea(new Dimension(10, 0)));
        inTextPort.setMaximumSize(inTextPort.getPreferredSize());
        inTextPort.setText("6969");
        inTextPort.setForeground(Color.LIGHT_GRAY);
        lineBox2.add(inTextPort);
        this.add(lineBox2);

        this.add(Box.createRigidArea(new Dimension(0, 10)));

        //init panel button
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(btnAdd);
        buttonBox.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonBox.add(btnCancel);
        this.add(buttonBox);
    }

    void initFrame(){
        this.setTitle("Add new computer");
        this.setModal(true);
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    void setListener(){
        this.btnAdd.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.inTextPort.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textField = (JTextField)e.getSource();
                if(textField.getText().equals("6969")){
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField = (JTextField)e.getSource();
                if(textField.getText().length() == 0){
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText("6969");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(button == this.btnAdd){
            if(validInput()){
                this.model.addElement(new Computer("Searching", this.inTextIp.getText(), Integer.parseInt(this.inTextPort.getText())
                        , 0, 1, null));
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }

        if(button == this.btnCancel){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private boolean validInput(){
        if(!Helper.validateIp4(this.inTextIp.getText())){
            JOptionPane.showMessageDialog(this.parent, "Your IP not valid");
            return false;
        }
        if(!Helper.validatePort(this.inTextPort.getText())){
            JOptionPane.showMessageDialog(this.parent, "Your port not valid");
            return false;
        }
        return true;
    }
}
