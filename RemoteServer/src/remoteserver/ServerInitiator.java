package remoteserver;

import UIRemote.UiRemote;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ServerInitiator extends Thread{
    public static JFrame frame = new JFrame();
    public static JDesktopPane desktop = new JDesktopPane();
    private String NameDesktop;
    private int port;
    private UiRemote uir;
    public  ServerInitiator(UiRemote uir){
        this.uir = uir;
        this.NameDesktop = uir.txtNameDesktop.getText();
        this.port = Integer.parseInt(uir.txtPort.getText());
        this.start();
    }

    @Override
    public void run(){
        try {
            System.out.println(port);
            ServerSocket sc = new ServerSocket(port);
            Socket client = sc.accept();
            //Listen to server port and accept clients connections
                    DataInputStream dis = new DataInputStream(client.getInputStream());
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                    String pass = JOptionPane.showInputDialog("Please enter password is provided by Client");
                    String getPassByClient = dis.readUTF();
                    do{
                        if(pass.equalsIgnoreCase("") || !pass.equals(getPassByClient))
                        {
                            JFrame f = new  JFrame ();
                            JOptionPane.showMessageDialog (f, "Error Password ! Please enter again !","Error",JOptionPane.WARNING_MESSAGE);
                            pass = JOptionPane.showInputDialog("Please enter password is provided by Client");
                        }
                        else break;
                    }while (true);
                    dos.writeUTF(uir.txtNameDesktop.getText());
                    if(dis.readBoolean()){
                        drawGUI();
                        System.out.println("New client Connected to the server");

                        new ClientHandler(client,desktop);
                    }
                    else
                    client.close();
        } catch (IOException ex) {
            new UiRemote();
            ex.printStackTrace();
        }
    }
    public static void drawGUI(){
            frame.add(desktop,BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
    }
}
