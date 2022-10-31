import remoteclient.ClientInitiator;
import remoteserver.ServerInitiator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;

public class UiRemote{

    private JFrame frame1;
    private JTextField txtIpConect;
    private JTextField txtPass;
    private JTextField txtMyIp;
    private JToggleButton toggleButton;
    private ItemListener itemListener;

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
    public String MyIp;
    public int Port;
    public UiRemote() {
        try {
            Port = 5000;
            MyIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize(MyIp);
        getMyIP();
    }

    public static void getMyIP(){

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String MyIp) {

        frame1 = new JFrame();
        frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\PBL4\\RemoteDesktop\\icon\\MetroUI-Apps-Alt-3-icon.png"));
        frame1.getContentPane().setBackground(new Color(255, 255, 255));
        frame1.setBounds(100, 100, 653, 405);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SystemColor.inactiveCaption);
        menuBar.setToolTipText("");
        frame1.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("System");
        mnNewMenu.setForeground(SystemColor.activeCaptionText);
        mnNewMenu.setBackground(SystemColor.scrollbar);
        mnNewMenu.setIcon(new ImageIcon("D:\\PBL4\\RemoteDesktop\\icon\\Home-icon-16.png"));
        mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("Setting");
        mnNewMenu_1.setForeground(SystemColor.activeCaptionText);
        mnNewMenu_1.setBackground(SystemColor.scrollbar);
        mnNewMenu_1.setIcon(new ImageIcon("D:\\PBL4\\RemoteDesktop\\icon\\Help-icon-16.png"));
        mnNewMenu_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuBar.add(mnNewMenu_1);

        JMenu mnNewMenu_2 = new JMenu("Chat");
        mnNewMenu_2.setIcon(new ImageIcon("D:\\PBL4\\RemoteDesktop\\icon\\User-Chat-icon.png"));
        mnNewMenu_2.setBackground(SystemColor.scrollbar);
        mnNewMenu_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        mnNewMenu_2.setForeground(SystemColor.activeCaptionText);
        menuBar.add(mnNewMenu_2);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(63, 10, 110, 100);
        lblNewLabel.setIcon(new ImageIcon("D:\\PBL4\\RemoteDesktop\\icon\\logo.png"));

        JLabel lblNewLabel_1 = new JLabel("Remote Desktop");
        lblNewLabel_1.setBounds(183, 10, 292, 54);
        lblNewLabel_1.setForeground(SystemColor.textHighlight);
        lblNewLabel_1.setFont(new Font("Roboto Black", Font.BOLD, 35));

        JLabel lblNewLabel_2 = new JLabel("Connection");
        lblNewLabel_2.setBounds(183, 56, 196, 31);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
        frame1.getContentPane().setLayout(null);
        frame1.getContentPane().add(lblNewLabel);
        frame1.getContentPane().add(lblNewLabel_1);
        frame1.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Control Remote Desktop");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblNewLabel_3.setBounds(345, 120, 227, 36);
        frame1.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_3_1 = new JLabel("Allow Control");
        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblNewLabel_3_1.setBounds(22, 120, 148, 36);
        frame1.getContentPane().add(lblNewLabel_3_1);

        txtIpConect = new JTextField();
        txtIpConect.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtIpConect.setBounds(345, 194, 276, 36);
        txtIpConect.setEditable(false);
        frame1.getContentPane().add(txtIpConect);
        txtIpConect.setColumns(10);

        txtPass = new JTextField();
        txtPass.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtPass.setEditable(false);
        txtPass.setColumns(10);
        txtPass.setBounds(22, 261, 276, 36);
        txtPass.setText("remote123");
        frame1.getContentPane().add(txtPass);

        txtMyIp = new JTextField();
        txtMyIp.setEditable(false);
        txtMyIp.setFont(new Font("Tahoma", Font.PLAIN, 19));
        txtMyIp.setColumns(10);
        txtMyIp.setBounds(22, 194, 276, 36);
        txtMyIp.setText(MyIp);
        frame1.getContentPane().add(txtMyIp);

        JLabel lblNewLabel_4 = new JLabel("Your IP Address");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4.setBounds(22, 166, 116, 13);
        frame1.getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_4_1 = new JLabel("Password ");
        lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4_1.setBounds(22, 238, 116, 13);
        frame1.getContentPane().add(lblNewLabel_4_1);

        JButton btnNewButton = new JButton("CONNECT");
        btnNewButton.setForeground(SystemColor.textHighlight);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
        btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(toggleButton.isSelected()){
                    if (txtIpConect != null) {
                        frame1.dispose();
                        new ClientInitiator(txtIpConect.getText(), Port);
                    }
                }
                else {
                    frame1.dispose();
                    new ServerInitiator();
                }
            }
        });
        btnNewButton.setIcon(new ImageIcon("D:\\PBL4\\RemoteDesktop\\icon\\MetroUI-Apps-Alt-3-icon.png"));
        btnNewButton.setBounds(345, 261, 160, 36);
        frame1.getContentPane().add(btnNewButton);

        JLabel lblNewLabel_4_2 = new JLabel("IP Address");
        lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_4_2.setBounds(345, 166, 116, 13);
        frame1.getContentPane().add(lblNewLabel_4_2);

        toggleButton = new JToggleButton("Off");
        toggleButton.setBounds(570, 125, 60, 30);
        toggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    toggleButton.setText("On");
                    txtIpConect.setEditable(true);
                } else {
                    toggleButton.setText("Off");
                    txtIpConect.setEditable(false);
                }
            }
        });
        frame1.getContentPane().add(toggleButton);
    }
}
