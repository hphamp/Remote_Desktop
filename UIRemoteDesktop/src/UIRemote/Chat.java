
package UIRemote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static remoteserver.ServerInitiator.frame;

public class Chat extends Thread implements ActionListener{
    public JButton btnSend;
    public JTextArea txtchat;
    public JTextArea textArea_viewchat;
    public String nameDesktop;
    public boolean type;
    public xuly xl;
    public UiRemote uir;
    public Chat(UiRemote uir,boolean type){
        this.uir = uir;
        this.type = type;
        this.nameDesktop=uir.txtNameDesktop.getText();
        this.start();
        xl = new xuly(this.uir,type);
        xl.start();
    }


    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static Socket socket = null;
    @Override
    public void run(){

        String ipConect = uir.txtIpConect.getText();
        int port = Integer.parseInt(uir.txtPort.getText())-1;
        if(type){

            ServerSocket serverSocket = null;

            try {
                System.out.println("Binding to port " + port + ", please wait  ...");
                serverSocket = new ServerSocket(port);
                System.out.println("Server started: " + serverSocket);
                System.out.println("Waiting for a client ...");

                    socket = serverSocket.accept();
                drawUi();

                    System.out.println("Client accepted: " + socket);
                    dos = new DataOutputStream(socket.getOutputStream());
                    System.out.println("dos Server : ==== " +dos);
                    dis = new DataInputStream(socket.getInputStream());
                    while (true) {

                        String ch = dis.readUTF();
                        textArea_viewchat.setText(textArea_viewchat.getText()+"\n" + ch);

                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                socket = new Socket(ipConect, port); // Connect to server
                drawUi();
                System.out.println("Connected: " + socket);
                dos = new DataOutputStream(socket.getOutputStream());
                System.out.println("dos Client : ==== " +dos);
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
    public void drawUi(){
        btnSend = new JButton("Send");
        btnSend.setBackground(SystemColor.textHighlightText);
        btnSend.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSend.setBounds(474, 299, 108, 37);
        uir.panelChat.add(btnSend);

        txtchat = new JTextArea(5,10);
        txtchat.setLineWrap(true);
        txtchat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txtchat.setBorder(new LineBorder(new Color(0, 0, 0)));
        txtchat.setBounds(0, 301, 400, 38); //159
        uir.panelChat.add(txtchat);

        textArea_viewchat = new JTextArea();
        textArea_viewchat.setEditable(false);
        textArea_viewchat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        textArea_viewchat.setToolTipText("");
        textArea_viewchat.setBorder(new LineBorder(new Color(0, 0, 0)));
        textArea_viewchat.setBounds(0, 0, 400, 294); // 639
        uir.panelChat.add(textArea_viewchat);


        btnSend.addActionListener(this);

        frame.setBounds(100, 100, 653, 405);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        uir.frame1.add(uir.panelChat);
        uir.panelChat.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
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



class xuly extends Thread implements ActionListener {
    public static DataOutputStream dosFile;
    public static DataInputStream disFile;
    public static Socket socketFile = null;
    public boolean type;
    JLabel jlFileName;
    JButton jbChooseFile;
    JButton jbSendFile;
    static JPanel jPanelFile;
    JScrollPane jScrollPane;
    static UiRemote uir;
    private int port=0;
    private String ipConnect = "";
    public xuly(UiRemote uir,boolean type)
    {
        this.uir = uir;
        this.port = Integer.parseInt(uir.txtPort.getText())+1;
        this.ipConnect = uir.txtIpConect.getText();
        this.type = type;

        ///////////////////////////////////////
        jlFileName = new JLabel("Chose a file to send");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 14));
        jlFileName.setBounds(430, 0, 365, 26);
        uir.panelChat.add(jlFileName);

        jbChooseFile = new JButton("Choose File");
        jbChooseFile.setBounds(408, 40, 110, 40);

        jbChooseFile.setPreferredSize(new Dimension(100, 40));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD,12));
        jbChooseFile.addActionListener(this);
        uir.panelChat.add(jbChooseFile);

        jbSendFile = new JButton("Send File");
        jbSendFile.setBounds(512, 40, 110, 40);
        jbSendFile.addActionListener(this);
        uir.panelChat.add(jbSendFile);

        jPanelFile = new JPanel();
        jPanelFile.setBounds(408,82,220,210);
        jPanelFile.setBackground(new Color(245, 194, 194));
        jPanelFile.setLayout(new BoxLayout(jPanelFile, BoxLayout.Y_AXIS));

        jScrollPane = new JScrollPane(jPanelFile);
        jScrollPane.setBackground(new Color(232, 25, 25));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        uir.panelChat.add(jPanelFile);
        uir.panelChat.add(jScrollPane);
    }
    static ArrayList<MyFile> myfile = new ArrayList<>();
    final File[] fileToSend = new File[1];
    static JPanel jpFileRow;
    @Override
    public void run() {
        super.run();
        System.out.println("da start" + type);
        ServerSocket serverSocketFile = null;
        try {
            if(type){
                serverSocketFile = new ServerSocket(port);
                socketFile = serverSocketFile.accept();
                ReceiveFile();
            }
            else {
                socketFile = new Socket(ipConnect, port);
                ReceiveFile();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void ReceiveFile(){
        try {
            int fileId = 0;
            dosFile = new DataOutputStream(socketFile.getOutputStream());
            disFile = new DataInputStream(socketFile.getInputStream());
            while (true) {
                int fileNameLength = disFile.readInt();
                if (fileNameLength > 0) {
                    byte[] fileNameBytes = new byte[fileNameLength];
                    disFile.readFully(fileNameBytes, 0, fileNameBytes.length);
                    String fileName = new String(fileNameBytes);

                    int fileContentLength = disFile.readInt();

                    if (fileContentLength > 0) {
                        byte[] fileContentByte = new byte[fileContentLength];
                        disFile.readFully(fileContentByte, 0, fileContentLength);

                        jpFileRow = new JPanel();
                        jpFileRow.setBounds(0, 0, 10, 10);
                        jpFileRow.setBackground(new Color(245, 194, 194));
                        jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.Y_AXIS));

                        JLabel jlFileName = new JLabel(fileName);
                        jlFileName.setFont(new Font("Arial", Font.BOLD, 16));
                        jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));

                        if (getFileExtension(fileName).equalsIgnoreCase("txt")) {
                            jpFileRow.setName(String.valueOf(fileId));
                            jpFileRow.addMouseListener(getMyMouseListener());

                            jpFileRow.add(jlFileName);
                            jPanelFile.add(jpFileRow);
                            uir.panelChat.validate();
                        } else {
                            jpFileRow.setName(String.valueOf(fileId));
                            jpFileRow.addMouseListener(getMyMouseListener());

                            jpFileRow.add(jlFileName);
                            jPanelFile.add(jpFileRow);

                            uir.panelChat.validate();
                        }
                        myfile.add(new MyFile(fileId, fileName, fileContentByte, getFileExtension(fileName)));
                        fileId++;
                    }
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');

        if(i>0) {
            return fileName.substring(i + 1);
        }else {
            return "No extension found";
        }
    }
    public static MouseListener getMyMouseListener() {
        return new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                JPanel jPanel = (JPanel) e.getSource();
                int fileId = Integer.parseInt(jPanel.getName());
                for (MyFile myFile: myfile) {
                    if(myFile.getId() == fileId) {
                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
                        jfPreview.setVisible(true);
                    }
                }
            }
        };
    }

    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {
        JFrame jFrame = new JFrame("Downloader");
        jFrame.setSize(400, 400);


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        JLabel jlPrompt = new JLabel("Are you sure download " + fileName +"?");
        jlPrompt.setFont(new Font("Arial", Font.BOLD, 20));
        jlPrompt.setBounds(100,430 ,400,40);

        JButton jbYes = new JButton("Yes");
        jbYes.setFont(new Font("Arial", Font.BOLD, 20));
        jbYes.setBounds(200,500 ,200,40);

        JButton jbNo = new JButton("No");
        jbYes.setFont(new Font("Arial", Font.BOLD, 20));
        jbNo.setBounds(200,500 ,200,40);

        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20,0,10,0));
        jpButtons.add(jbYes);
        jpButtons.add(jbNo);

        if( fileExtension.equalsIgnoreCase("txt")) {
            jlFileContent.setText("<html>"+ new String(fileData) + "</html>");
        } else {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        jbYes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File fileToDowload = new File(fileName);

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDowload);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();

                    jFrame.dispose();
                }catch (IOException error) {
                    // TODO: handle exception
                    error.printStackTrace();
                }
            }
        });

        jbNo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                jFrame.dispose();
            }
        });

        jPanel.add(jlPrompt);
        jPanel.add(jlFileContent);
        jPanel.add(jpButtons);

        jFrame.add(jPanel);

        return jFrame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jbChooseFile){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Choose a file to send");

            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fileToSend[0] = jFileChooser.getSelectedFile();
                jlFileName.setText("Send file : " + fileToSend[0].getName());
            }
        }
        else if(e.getSource()==jbSendFile){
            if(fileToSend[0]==null) {
                jlFileName.setText("Please choose a file first");
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(fileToSend[0].getAbsolutePath());


                    String fileName = fileToSend[0].getName();
                    byte[] fileNameBytes = fileName.getBytes();

                    byte[] fileContentBytes = new byte[(int)fileToSend[0].length()];
                    fileInputStream.read(fileContentBytes);

                    dosFile.writeInt(fileNameBytes.length);
                    dosFile.write(fileNameBytes);

                    dosFile.writeInt(fileContentBytes.length);
                    dosFile.write(fileContentBytes);
                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
            }
        }
    }
}