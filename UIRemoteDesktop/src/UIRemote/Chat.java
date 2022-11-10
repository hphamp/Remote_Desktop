package UIRemote;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat extends Thread implements ActionListener{
    private JFrame frame;
    private JPanel panelChat;
    public JButton btnSend;
    public JTextArea txtchat;
    public JTextArea textArea_viewchat;
    private JButton btnAttach;
    public String nameDesktop;
    public boolean type;
    public Chat(boolean type,String NameDesktop){
        this.type = type;
        this.nameDesktop=NameDesktop;
        this.start();
    }

    public DataOutputStream dos;
    public DataInputStream disFile;
    public DataOutputStream dosFile;
    @Override
    public void run(){

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

        btnSend.addActionListener(this);
        btnAttach.addActionListener(this);

        panelChat.add(btnAttach);
        frame.setBounds(100, 100, 653, 405);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panelChat);
        this.frame.setVisible(true);


        if(type){
            ServerSocket serverSocket = null;
            try {
                System.out.println("Binding to port " + 2000 + ", please wait  ...");
                serverSocket = new ServerSocket(2000);
                System.out.println("Server started: " + serverSocket);
                System.out.println("Waiting for a client ...");

                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("Client accepted: " + socket);
                        while (true) {
                            dos = new DataOutputStream(socket.getOutputStream());
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                        while (true) {
                            if(dis!=null){
                                String ch = dis.readUTF();
//                                disFile = new DataInputStream(new FileInputStream("D:\\ABC.docx"));
                                textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + ch);
                                System.out.println(ch);
                            }
                        }



                        }
                    } catch (IOException e) {
                        System.err.println(" Connection Error: " + e);
                    }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        else {
            Socket socket = null;
            try {
                socket = new Socket("localhost", 2000); // Connect to server
                System.out.println("Connected: " + socket);

                dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                    while (true) {
                        if(dis!=null){
                            String ch = dis.readUTF();
//                            byte[] buffer = new byte[8192];
//                            disFile = new DataInputStream(new FileInputStream("D:\\ABC.docx"));
//                            disFile.read(buffer);
                            textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + ch);
                            System.out.println(ch);
                        }
                    }

            } catch (IOException ie) {
                System.out.println("Can't connect to server");
            }
        }
    }
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            if(e.getSource() ==btnAttach){
                int sc = fc.showOpenDialog(frame);
                if(sc == JFileChooser.APPROVE_OPTION) {
                    try {
                        JFrame f = new  JFrame ();
                        int a=JOptionPane.showConfirmDialog(f,"Do you want send " + fc.getSelectedFile().getName());
                        if(a==JOptionPane.YES_OPTION){
                            dos.writeUTF(nameDesktop +" >> send a file : " +
                                    fc.getSelectedFile().getName());
                            dosFile=new DataOutputStream(new FileOutputStream(fc.getSelectedFile().getAbsolutePath()));

                            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }

                        System.out.println("Path: " +
                                fc.getSelectedFile().getAbsolutePath());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
            if(e.getSource()==btnSend){
                        try {
                            dos.writeUTF(nameDesktop + " >> " + txtchat.getText());
                            textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + nameDesktop + " >> " + txtchat.getText());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
            }
        }
}
