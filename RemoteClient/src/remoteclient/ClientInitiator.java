
package remoteclient;

import UIRemote.UiRemote;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientInitiator  extends Thread{

    Socket socket = null;
    private String NameDesktop;
    private String password;
    private String ip;
    private int port;
    private UiRemote uir;


    public ClientInitiator(UiRemote uir) {
        this.uir = uir;
        this.NameDesktop = uir.txtNameDesktop.getText();
        this.port = Integer.parseInt(uir.txtPort.getText());
        this.ip = uir.txtIpConect.getText();
        this.password = String.valueOf(uir.txtPass.getPassword());
        this.start();
    }
    @Override
    public void run(){
        Robot robot = null; //Used to capture the screen
        Rectangle rectangle = null; //Used to represent screen dimensions
        DataOutputStream dos;

        try {

            System.out.println("Connecting to server ..........");
            socket = new Socket(ip, port);
            System.out.println("Connection Established.");


            //send password
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(password);



            //Get default screen device
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            //Get screen dimensions
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            //Prepare Robot object
            robot = new Robot(gDev);

            //draw client gui
            //ScreenSpyer sends screenshots of the client screen
            new ScreenSpyer(socket,robot,rectangle);
            //ServerDelegate recieves server commands and execute them
            new ServerDelegate(socket,robot);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
