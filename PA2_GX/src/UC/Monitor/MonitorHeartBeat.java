package UC.Monitor;

import UC.LoadBalancer.LoadBalancerRequest;
import UC.Server.ServerRequest;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MonitorHeartBeat extends Thread {

    private Socket serverSocket;
    private int lbSocketPort;
    private HashMap<Integer, Socket> allServerSocketsConnected;
    private HashMap<Integer, String> allServerSocketsDisconnected;
    private int id;
    JTextArea EXECUTEDTEXTAREA;
    JTextArea EXECUTEDTEXTAREA1;

    public MonitorHeartBeat(Socket s2, HashMap<Integer, Socket> allServerSocketsConnected, int id, JTextArea EXECUTEDTEXTAREA, JTextArea EXECUTEDTEXTAREA1, HashMap<Integer, String> allServerSocketsDisconnected, int lbSocketPort) {
        this.serverSocket = s2;
        this.lbSocketPort = lbSocketPort;
        this.id = id;
        this.allServerSocketsConnected = allServerSocketsConnected;
        this.EXECUTEDTEXTAREA = EXECUTEDTEXTAREA;
        this.EXECUTEDTEXTAREA1 = EXECUTEDTEXTAREA1;
        this.allServerSocketsDisconnected = allServerSocketsDisconnected;
    }

    @Override
    public void run() {

        OutputStream outputStream = null;
        try {
            outputStream = this.serverSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        InputStream inputStream = null;
        try {
            inputStream = this.serverSocket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        try {
            while (true) {
                sleep(500);
                dataOutputStream.writeUTF("AreYouAlive?");
                dataOutputStream.flush();

                String str = dataInputStream.readUTF();
                System.out.println(str);

            }

        } catch (IOException ex) {
            this.allServerSocketsDisconnected.put(id, "-DEAD");
            StringBuilder newTextArea2 = new StringBuilder();
            for (Integer key : allServerSocketsDisconnected.keySet()) {
                newTextArea2.append("Server ID:")
                        .append(key)
                        .append(" = ")
                        .append(allServerSocketsDisconnected.get(key))
                        .append("\n");
            }
            EXECUTEDTEXTAREA1.setText(newTextArea2.toString());
            this.allServerSocketsConnected.remove(id);
            StringBuilder newTextArea = new StringBuilder();
            for (Integer key : allServerSocketsConnected.keySet()) {
                newTextArea.append("Server ID:")
                        .append(key)
                        .append(" = ")
                        .append(allServerSocketsConnected.get(key))
                        .append("\n");
            }
            EXECUTEDTEXTAREA.setText(newTextArea.toString());

           
                 Socket lbSocket = null;
            try {
                // get the output stream from the socket.
                lbSocket = new Socket("localhost", this.lbSocketPort);
            } catch (IOException ex1) {
                Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex1);
            }


            OutputStream outputStream2 = null;
            try {
                outputStream2 = lbSocket.getOutputStream();
            } catch (IOException ex1) {
                Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex1);
            }

            DataOutputStream dataOutputStream2 = new DataOutputStream(outputStream2);

            String info = "Dead;" + id;
            System.out.println(lbSocket);
            try {
                dataOutputStream2.writeUTF(info);
            } catch (IOException ex1) {
                Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {

            Logger.getLogger(MonitorHeartBeat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
