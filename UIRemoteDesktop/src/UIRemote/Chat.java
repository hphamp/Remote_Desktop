package UIRemote;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat  implements ActionListener {
    private JFrame frame;
    private JPanel panelChat;
    public JButton btnSend;
    public JTextArea txtchat;
    public JTextArea textArea_viewchat;
    private JButton btnAttach;
//    public static void main(String[] args) {
//        new Chat();
//    }
    public String nameDesktop;
    public Chat(boolean type,String NameDesktop){
        this.nameDesktop=NameDesktop;
        drawUI(NameDesktop);
        Classify(type);
    }

    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket soc;
    private ServerSocket serSoc;

    private void Classify(boolean type){
        if(type){
            try {
                serSoc = new ServerSocket(2000);
                soc = serSoc.accept();
                dis = new DataInputStream(soc.getInputStream());
                System.out.println(dis.readUTF());
                dos = new DataOutputStream(soc.getOutputStream());
                dos.writeUTF("xin chao Client !!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                soc = new Socket("localhost",2000);
                dis = new DataInputStream(soc.getInputStream());
                System.out.println(dis.readUTF());
                dos = new DataOutputStream(soc.getOutputStream());
                dos.writeUTF("xin chao Server !!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void drawUI(String NameDesktop){
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("image\\MetroUI-Apps-Alt-3-icon.png"));
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setBounds(100, 100, 653, 405);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //UIRemote.chat

        panelChat = new JPanel();
        panelChat.setBounds(0,0,653,405);
        panelChat.setLayout(null);

        btnSend = new JButton("Send");
        btnSend.setBackground(SystemColor.textHighlightText);
        btnSend.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSend.addActionListener(this);
        btnSend.setBounds(521, 299, 108, 37);
        panelChat.add(btnSend);

        txtchat = new JTextArea(5,10);
        txtchat.setLineWrap(true);
        txtchat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtchat.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtchat.setBounds(52, 301, 459, 38);
        panelChat.add(txtchat);

        textArea_viewchat = new JTextArea();
        textArea_viewchat.setEditable(false);
        textArea_viewchat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        textArea_viewchat.setToolTipText("");
        textArea_viewchat.setBounds(0, 0, 639, 294);
        panelChat.add(textArea_viewchat);

        btnAttach = new JButton("");
        btnAttach.setBackground(SystemColor.textHighlightText);
        btnAttach.setIcon(new ImageIcon("image\\attach.png"));
        btnAttach.setBounds(10, 299, 36, 37);
        btnAttach.addActionListener(this);
        panelChat.add(btnAttach);
        frame.setBounds(100, 100, 653, 405);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panelChat);
        this.frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==btnSend){
            textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + nameDesktop +" >> " + txtchat.getText());
        }
        else if(e.getSource() ==btnAttach){
            ClassLoader cl = getClass().getClassLoader();
            File file = new File(cl.getResource("c:\\").getFile());
        }
    }
}
