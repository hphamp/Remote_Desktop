/*
 * Author Ahmed Abdelhalim - 2009
 * Email: englemo@hotmail.com
 * Please do not remove the above lines
 */
package remoteserver;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This is the entry class of the server
 */
public class ServerInitiator extends Thread{
    //UIRemote.Main server frame
    public static JFrame frame = new JFrame();
    //JDesktopPane represents the main container that will contain all
    //connected clients' screens
    public static JDesktopPane desktop = new JDesktopPane();
    private String NameDesktop;
    private int port;
    public  ServerInitiator(String NameDesktop, int port){
        this.NameDesktop=NameDesktop;
        this.port = port;
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
                    //Show Server GUI
                    drawGUI();
                    System.out.println("New client Connected to the server");
                    //Per each client create a ClientHandler
                    new ClientHandler(client,desktop);
//                    ServerSocket sc1 = new ServerSocket(port+1);
//                    Socket client1 = sc1.accept();
//                    new Chat(true,NameDesktop,client);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Draws the main server GUI
     */
    public static void drawGUI(){
            frame.add(desktop,BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Show the frame in a maximized state
            frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
    }
}
