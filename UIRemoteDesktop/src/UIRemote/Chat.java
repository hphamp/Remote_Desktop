
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
    public xuly xl;
    public Chat(boolean type,String NameDesktop){
        this.type = type;
        this.nameDesktop=NameDesktop;
        this.start();
        xl = new xuly(type);
        xl.start();
    }


    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static Socket socket = null;
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
                    socket = serverSocket.accept();
                    System.out.println("Client accepted: " + socket);
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    while (true) {
                        String ch = dis.readUTF();
                        textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + ch);
                    }

                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        else {

            try {
                socket = new Socket("localhost", 2000); // Connect to server
                System.out.println("Connected: " + socket);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                while (true) {
                    String ch = dis.readUTF();
                    textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + ch);
                }
            } catch (Exception e) {
                System.out.println("Can't connect to server");
                throw new RuntimeException(e);
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
//                       xuly xl = new xuly(fc.getSelectedFile().getAbsolutePath(),type);
                        xl.sendFile(fc.getSelectedFile().getAbsolutePath());
                        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }

                    System.out.println("Path: " +
                            fc.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
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



class xuly extends Thread{
    public static Socket socket;

    private static DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    public boolean type;
    public xuly(boolean type){
        this.type = type;
    }
    public int numberFile = 0;
    @Override
    public void run() {
        super.run();
        System.out.println("da start" + type);
        try {
            if(type){
                ServerSocket serverSc = new ServerSocket(2001);
                socket = serverSc.accept();
                receiveFile("Client" + numberFile+".pdf");
            }
            else {
                socket = new Socket("localhost",2001);
                receiveFile("Server" + numberFile+".pdf");
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    public void receiveFile(String path)
    {
        System.out.println("ham send file");
        try {
            String linkPath;
            while (true) {
                linkPath = "newFile"+numberFile+path;
                System.out.println("numberFile ================= " + numberFile);
                dataInputStream = new DataInputStream(
                        socket.getInputStream());
                int bytes = 0;

                long size
                        = dataInputStream.readLong();
                if(size!=0){
                    FileOutputStream fileOutputStream
                            = new FileOutputStream(linkPath);
                    numberFile++;

                    // read file size
                    byte[] buffer = new byte[4 * 1024];
                    while (size > 0
                            && (bytes = dataInputStream.read(
                            buffer, 0,
                            (int)Math.min(buffer.length, size)))
                            != -1) {
                        // Here we write the file using write method
                        fileOutputStream.write(buffer, 0, bytes);
                        size -= bytes; // read upto file size
                    }
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }
    public static void sendFile(String path)
            throws Exception
    {
        dataOutputStream = new DataOutputStream(
                socket.getOutputStream());
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : " + path);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : " + dataOutputStream);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa : "+ socket);


        // Here we send the File to Server
        dataOutputStream.writeLong(file.length());
        // Here we  break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            // Send the file to Server Socket
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        // close the file here
        fileInputStream.close();
    }
}
