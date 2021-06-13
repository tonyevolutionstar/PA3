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
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MonitorHeartBeat extends Thread {

    private final Socket serverSocket;
    private final int lbSocketPort;
    private final HashMap<Integer, Socket> allServerSocketsConnected;
    private final HashMap<Integer, String> allServerSocketsDisconnected;
    private final HashMap<Integer, Integer> numberOfWorkLoadEachThreadServer;
    private final int id;
    JTextArea EXECUTEDTEXTAREA;
    JTextArea EXECUTEDTEXTAREA1;
    JTextArea REQUESTSEACHSERVER;
    HashMap<Integer, ArrayList> allRequestsOnEachServer;

    /**
     * Constructor of Monitor Heart beat
     * @param s2 - information of socket
     * @param allServerSocketsConnected - hashmap for saves all servers connected 
     * @param id - id 
     * @param EXECUTEDTEXTAREA - servers offline
     * @param EXECUTEDTEXTAREA1 - servers online
     * @param allServerSocketsDisconnected - hashmap for saves all servers disconnected 
     * @param lbSocketPort - socket port of load balancer
     * @param numberOfWorkLoadEachThreadServer 
     * @param allRequestsOnEachServer
     * @param REQUESTSEACHSERVER 
     */
    
    public MonitorHeartBeat(Socket s2, HashMap<Integer, Socket> allServerSocketsConnected, int id, JTextArea EXECUTEDTEXTAREA, JTextArea EXECUTEDTEXTAREA1, HashMap<Integer, String> allServerSocketsDisconnected, int lbSocketPort, HashMap<Integer, Integer> numberOfWorkLoadEachThreadServer, HashMap<Integer, ArrayList> allRequestsOnEachServer, JTextArea REQUESTSEACHSERVER) {
        this.serverSocket = s2;
        this.lbSocketPort = lbSocketPort;
        this.id = id;
        this.allServerSocketsConnected = allServerSocketsConnected;
        this.EXECUTEDTEXTAREA = EXECUTEDTEXTAREA;
        this.EXECUTEDTEXTAREA1 = EXECUTEDTEXTAREA1;
        this.allServerSocketsDisconnected = allServerSocketsDisconnected;
        this.numberOfWorkLoadEachThreadServer = numberOfWorkLoadEachThreadServer;
        this.allRequestsOnEachServer = allRequestsOnEachServer;
        this.REQUESTSEACHSERVER = REQUESTSEACHSERVER;
    }

    /**
    * thread 
    */
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
                sleep(50);
                dataOutputStream.writeUTF("AreYouAlive?");
                dataOutputStream.flush();
                String str = dataInputStream.readUTF();
                String[] arrOfStr = str.split(";", -2);
                String[] requestsArrOfStr = arrOfStr[1].split(",", -2);

                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < requestsArrOfStr.length; i++) {
                    temp.add(requestsArrOfStr[i]);
                }
                int flagRepetition = 0;
                if (allRequestsOnEachServer.containsKey(id)) {
                    if (temp.equals(allRequestsOnEachServer.get(id))) {
                        flagRepetition++;
                    } else {
                        allRequestsOnEachServer.replace(id, temp);
                    }
                } else {
                    allRequestsOnEachServer.put(id, temp);
                }

                if (flagRepetition == 0) {
                    StringBuilder newTextArea3 = new StringBuilder();
                    for (Integer key : allRequestsOnEachServer.keySet()) {
                        newTextArea3.append("Server ID:")
                                .append(key)
                                .append(" = ");
                        for (int i = 0; i < allRequestsOnEachServer.get(key).size(); i++) {
                            newTextArea3.append(allRequestsOnEachServer.get(key).get(i).toString())
                            .append("-");
                        }
                        newTextArea3.append("\n");
                    }
                    REQUESTSEACHSERVER.setText(newTextArea3.toString());
                }

                if (!numberOfWorkLoadEachThreadServer.containsKey(id)) {
                    numberOfWorkLoadEachThreadServer.put(id, parseInt(arrOfStr[0]));
                } else {
                    numberOfWorkLoadEachThreadServer.replace(id, parseInt(arrOfStr[0]));
                }
            }

        } catch (IOException ex) {
            this.allServerSocketsDisconnected.put(id, " DEAD");
            StringBuilder newTextArea2 = new StringBuilder();
            allServerSocketsDisconnected.keySet().forEach(key -> {
                newTextArea2.append("Server ID:")
                        .append(key)
                        .append(" = ")
                        .append(allServerSocketsDisconnected.get(key))
                        .append("\n");
            });
            EXECUTEDTEXTAREA1.setText(newTextArea2.toString());
            this.allServerSocketsConnected.remove(id);
            this.allRequestsOnEachServer.remove(id);
            this.numberOfWorkLoadEachThreadServer.remove(id);
            StringBuilder newTextArea = new StringBuilder();
            allServerSocketsConnected.keySet().forEach(key -> {
                newTextArea.append("Server ID:")
                        .append(key)
                        .append(" = ")
                        .append(allServerSocketsConnected.get(key))
                        .append("\n");
            });
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
