package UIRemote;

import remoteserver.ServerInitiator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.InetAddress;


public class UiRemote implements ActionListener,ItemListener{

    private JFrame frame1;
    private JPanel panelMain;
    private JTextField txtIpConect;
    private JPasswordField txtPass;
    private JTextField txtMyIp;
    private JToggleButton toggleButton;
    private ItemListener itemListener;
    private JMenuItem mnNewMenu;
    private JMenuItem mnNewMenu_1;
    private JMenuItem mnNewMenu_2;
    private JButton btnConect;

    //setting
    private JTextField txtPort;
    public JTextField txtNameDesktop;
    private JLabel lbPort;
//    private JTextField txtSetPass;
    private JLabel lbNameDesktop;
    private JLabel lbTitle;
    private JLabel lbPass;
    private JPanel panelSetting;
    private JButton btnSave;
    private JPasswordField txtsetPassword;
    private JToggleButton btnHideShowPass;
    private JToggleButton btnHideShowPass2;

    private JToggleButton btnHideShowPass3;
    private JToggleButton btnHideShowPass4;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UiRemote window = new UiRemote();
                    window.frame1.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    private String MyIp;
    private String nameDesktop;
    private int Port;
    private String password;
    public UiRemote() {
        try {
            MyIp = InetAddress.getLocalHost().getHostAddress();
            nameDesktop = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize(MyIp);
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String MyIp) {
        frame1 = new JFrame();
        frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("image\\MetroUI-Apps-Alt-3-icon.png"));
        frame1.getContentPane().setBackground(new Color(255, 255, 255));
        frame1.setBounds(100, 100, 653, 405);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //PanelSetting
        panelSetting = new JPanel();
        panelSetting.setBounds(0,0,653,405);
        frame1.add(panelSetting);
        panelSetting.setLayout(null);
        panelSetting.setVisible(false);

        lbTitle = new JLabel("Control Remote Desktop");
        lbTitle.setIcon(new ImageIcon("image\\Actions-help-about-icon-16.png"));
        lbTitle.setFont(new Font("Tahoma", Font.ITALIC, 17));
        lbTitle.setBounds(50, 20, 280, 26);
        panelSetting.add(lbTitle);

        lbPort = new JLabel("Port :");
        lbPort.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbPort.setBounds(50, 60, 116, 13);
        panelSetting.add(lbPort);

        lbNameDesktop = new JLabel("Name :");
        lbNameDesktop.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbNameDesktop.setBounds(400, 60, 116, 13);
        panelSetting.add(lbNameDesktop);

        txtPort = new JTextField("5000");
        txtPort.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtPort.setEditable(true);
        txtPort.setColumns(10);
        txtPort.setBounds(50, 80, 142, 36);
        panelSetting.add(txtPort);

        txtsetPassword = new JPasswordField();
        txtsetPassword.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtsetPassword.setEditable(true);
        txtsetPassword.setColumns(10);
        txtsetPassword.setBounds(50, 170, 200, 36);
        txtsetPassword.setText("abc@123");
        txtsetPassword.setEchoChar('*');
        panelSetting.add(txtsetPassword);

        btnHideShowPass = new JToggleButton();
        btnHideShowPass.setIcon(new ImageIcon("image\\eye.png"));
        btnHideShowPass.setSelected(false);
        btnHideShowPass.setBounds(260, 170, 36, 36);
        btnHideShowPass.addItemListener(this);

        btnHideShowPass2 = new JToggleButton();
        btnHideShowPass2.setIcon(new ImageIcon("image\\hidden.png"));
        btnHideShowPass2.setBounds(260, 170, 36, 36);
        btnHideShowPass2.addItemListener(this);
        panelSetting.add(btnHideShowPass2);


        btnSave = new JButton("SAVE");
        btnSave.setIcon(new ImageIcon("image\\ok.png"));
        btnSave.setHorizontalAlignment(SwingConstants.LEFT);
        btnSave.setForeground(new Color(46, 139, 87));
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnSave.setBounds(420, 260, 101, 36);
        btnSave.addActionListener(this);
        panelSetting.add(btnSave);

        lbPass = new JLabel("Password:");
        lbPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbPass.setBounds(50, 150, 116, 13);
        panelSetting.add(lbPass);

        txtNameDesktop = new JTextField();
        txtNameDesktop.setText(nameDesktop);
        txtNameDesktop.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtNameDesktop.setEditable(true);
        txtNameDesktop.setColumns(10);
        txtNameDesktop.setBounds(400, 80, 160, 36);
        panelSetting.add(txtNameDesktop);

        Port = Integer.parseInt(txtPort.getText());

        //panelMain

        panelMain = new JPanel();
        panelMain.setBounds(0,0,653,405);
        frame1.add(panelMain);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SystemColor.inactiveCaption);
        menuBar.setToolTipText("");
        frame1.setJMenuBar(menuBar);


        mnNewMenu = new JMenuItem("System");
        mnNewMenu.setForeground(SystemColor.activeCaptionText);
        mnNewMenu.setBackground(SystemColor.scrollbar);
        mnNewMenu.setIcon(new ImageIcon("image\\Home-icon-16.png"));
        mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        mnNewMenu.addActionListener(this);
        menuBar.add(mnNewMenu);

        mnNewMenu_1 = new JMenuItem("Setting");
        mnNewMenu_1.setForeground(SystemColor.activeCaptionText);
        mnNewMenu_1.setBackground(SystemColor.scrollbar);
        mnNewMenu_1.setIcon(new ImageIcon("image\\Help-icon-16.png"));
        mnNewMenu_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        mnNewMenu_1.addActionListener(this);
        menuBar.add(mnNewMenu_1);

        mnNewMenu_2 = new JMenuItem("Chat");
        mnNewMenu_2.setIcon(new ImageIcon("image\\User-Chat-icon.png"));
        mnNewMenu_2.setBackground(SystemColor.scrollbar);
        mnNewMenu_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        mnNewMenu_2.setForeground(SystemColor.activeCaptionText);
        mnNewMenu_2.addActionListener(this);
        menuBar.add(mnNewMenu_2);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(63, 10, 110, 100);
        lblNewLabel.setIcon(new ImageIcon("image\\logo.png"));

        JLabel lblNewLabel_1 = new JLabel("Remote Desktop");
        lblNewLabel_1.setBounds(183, 10, 292, 54);
        lblNewLabel_1.setForeground(SystemColor.textHighlight);
        lblNewLabel_1.setFont(new Font("Roboto Black", Font.BOLD, 35));

        JLabel lblNewLabel_2 = new JLabel("Connection");
        lblNewLabel_2.setBounds(183, 56, 196, 31);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
        panelMain.setLayout(null);
        panelMain.add(lblNewLabel);
        panelMain.add(lblNewLabel_1);
        panelMain.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Control Remote Desktop");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblNewLabel_3.setBounds(345, 120, 227, 36);
        panelMain.add(lblNewLabel_3);

        JLabel lblNewLabel_3_1 = new JLabel("Allow Control");
        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblNewLabel_3_1.setBounds(22, 120, 148, 36);
        panelMain.add(lblNewLabel_3_1);

        txtIpConect = new JTextField();
        txtIpConect.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtIpConect.setBounds(345, 194, 276, 36);
        txtIpConect.setEditable(false);
        panelMain.add(txtIpConect);
        txtIpConect.setColumns(10);

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtPass.setEditable(false);
        txtPass.setColumns(10);
        txtPass.setBounds(22, 261, 276, 36);
        txtPass.setText(String.valueOf(txtsetPassword.getPassword()));
        txtPass.setEchoChar('*');
        panelMain.add(txtPass);

        btnHideShowPass3 = new JToggleButton();
        btnHideShowPass3.setIcon(new ImageIcon("image\\eye.png"));
        btnHideShowPass3.setBounds(300, 261, 40, 36);
        btnHideShowPass3.addItemListener(this);

        btnHideShowPass4 = new JToggleButton();
        btnHideShowPass4.setIcon(new ImageIcon("image\\hidden.png"));
        btnHideShowPass4.setBounds(300, 261, 40, 36);
        btnHideShowPass4.addItemListener(this);
        panelMain.add(btnHideShowPass4);

        txtMyIp = new JTextField();
        txtMyIp.setEditable(false);
        txtMyIp.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtMyIp.setColumns(10);
        txtMyIp.setBounds(22, 194, 276, 36);
        txtMyIp.setText(MyIp);
        panelMain.add(txtMyIp);

        JLabel lblNewLabel_4 = new JLabel("Your IP Address");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4.setBounds(22, 166, 116, 13);
        panelMain.add(lblNewLabel_4);

        JLabel lblNewLabel_4_1 = new JLabel("Password ");
        lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4_1.setBounds(22, 238, 116, 13);
        panelMain.add(lblNewLabel_4_1);

        btnConect = new JButton("CONNECT");
        btnConect.setForeground(SystemColor.textHighlight);
        btnConect.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnConect.setHorizontalAlignment(SwingConstants.LEFT);
        btnConect.addActionListener(this);
        btnConect.setIcon(new ImageIcon("image\\MetroUI-Apps-Alt-3-icon.png"));
        btnConect.setBounds(345, 261, 160, 36);
        panelMain.add(btnConect);

        JLabel lblNewLabel_4_2 = new JLabel("IP Address");
        lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4_2.setBounds(345, 166, 116, 13);
        panelMain.add(lblNewLabel_4_2);

        toggleButton = new JToggleButton("Off");
        toggleButton.setBounds(570, 125, 60, 30);
        toggleButton.addItemListener(this);
        panelMain.add(toggleButton);
    }


    public void chat(boolean state){
//        try {
//        if(state){
//            System.out.println("server1");
//                sc = new ServerSocket(Port-1);
//                Socket client = sc.accept();
//                DataInputStream dis= new DataInputStream(client.getInputStream());
//                if(dis.equals(null)){
//                    String input = dis.readUTF();
//                    textArea_viewchat.setText(textArea_viewchat.getText() + "\n"+ input);
//                }
//        }
//        else {
//            System.out.println("client");
//
//
//                socket = new Socket(txtIpConect.getText(),Port-1);
//                DataInputStream dis= new DataInputStream(socket.getInputStream());
//                String input = dis.readUTF();
//                textArea_viewchat.setText(textArea_viewchat.getText() + "\n"+ input);
//            System.out.println(txtIpConect.getText());
//        }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mnNewMenu_1)
        {
            panelMain.setVisible(false);
            panelSetting.setVisible(true);
        }
        else if(e.getSource() == mnNewMenu){
            panelMain.setVisible(true);
            panelSetting.setVisible(false);
        }
        else if(e.getSource() == btnConect){
//            if(toggleButton.isSelected()){
//                if (txtIpConect != null) {
////                    UIRemote.chat(false);
//                    password = String.valueOf(txtsetPassword.getPassword());
//                    new ClientInitiator(password,txtIpConect.getText(), Port);
//
//                }
//            }
//            else {
//                UIRemote.chat(true);
                new ServerInitiator(txtNameDesktop.getText(),Port);

//            }
        }
        else if (e.getSource() == btnSave) {
            txtPort.setText(txtPort.getText());
            txtsetPassword.setText(String.valueOf(txtsetPassword.getPassword()));
            txtNameDesktop.setText(txtNameDesktop.getText());
            txtPass.setText(String.valueOf(txtsetPassword.getPassword()));
            Port = Integer.parseInt(txtPort.getText());
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == toggleButton){
            int state = e.getStateChange();
            if (state == ItemEvent.SELECTED) {
                toggleButton.setText("On");
                txtIpConect.setEditable(true);
            } else {
                toggleButton.setText("Off");
                txtIpConect.setEditable(false);
            }
        }
        else if(e.getSource() == btnHideShowPass){
                btnHideShowPass.setVisible(false);
                btnHideShowPass2.setVisible(true);
                txtsetPassword.setEchoChar('*');
                panelSetting.add(btnHideShowPass2);
        }
        else if(e.getSource() == btnHideShowPass2){
            btnHideShowPass2.setVisible(false);
            btnHideShowPass.setVisible(true);
            txtsetPassword.setEchoChar((char)0);
            panelSetting.add(btnHideShowPass);
        }
        else if(e.getSource() == btnHideShowPass3){
            btnHideShowPass3.setVisible(false);
            btnHideShowPass4.setVisible(true);
            txtPass.setEchoChar('*');
            panelMain.add(btnHideShowPass4);
        }
        else if(e.getSource() == btnHideShowPass4){
            btnHideShowPass4.setVisible(false);
            panelMain.add(btnHideShowPass3);
            btnHideShowPass3.setVisible(true);
            txtPass.setEchoChar((char)0);
        }
    }

}
