package UC.LoadBalancer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Responsible to handle, connections, requests, replies 
 * Saves information about clients connected and servers 
 * Main socket server from all process (Monitor, Client, Server)
 * @author António Ramos and Miguel Silva
 */
public class LoadBalancerRequestReceiver extends Thread {

    private final HashMap<Integer, Socket> allClientsSocketsConnected;
    private final HashMap<Integer, ArrayList> allRequestsOnEachServer;
    private final Socket s2;
    private String str;
    
    /**
     * Constructor from requests received 
     * @param allClientsSocketsConnected - hashmap of clients connected
     * @param s2 - info of socket 
     * @param allRequestsOnEachServer - hashmap of requests on each servers
     */
    public LoadBalancerRequestReceiver(HashMap<Integer, Socket> allClientsSocketsConnected, Socket s2, HashMap<Integer, ArrayList> allRequestsOnEachServer) {
        this.allClientsSocketsConnected = allClientsSocketsConnected;
        this.s2 = s2;
        this.allRequestsOnEachServer = allRequestsOnEachServer;
    }

  
    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream dataInputStream = new DataInputStream(s2.getInputStream());
                str = dataInputStream.readUTF();
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            System.out.println("LOAD_BALANCER_RECEIVED->" + str);
            if (str == null) {
                break;
            }
            String[] arrOfStr = str.split("[|]", -2);

            for (int i = 0; i < allRequestsOnEachServer.size(); i++) {
                int match2 = 0;
                if (allRequestsOnEachServer.get(parseInt(arrOfStr[2])).isEmpty()) {
                    continue;
                }
                String savedRequestsServer = (String) allRequestsOnEachServer.get(parseInt(arrOfStr[2])).get(i);
                System.out.println(savedRequestsServer);
                String[] arrOfStrSavedServer = str.split("[|]", -2);
                System.out.println(arrOfStr[0] + "-" + arrOfStrSavedServer[0]);
                if (arrOfStrSavedServer[0].equals(arrOfStr[0])) {
                    match2++;
                }
                System.out.println(arrOfStr[1] + "-" + arrOfStrSavedServer[1]);
                if (arrOfStrSavedServer[1].equals(arrOfStr[1])) {
                    match2++;
                }
                if (match2 == 2) {
                    allRequestsOnEachServer.get(parseInt(arrOfStr[2])).remove(i);
                    break;
                }
            }

            System.out.println(allClientsSocketsConnected.get(parseInt(arrOfStr[0])).toString());
            OutputStream outputStreamClient = null;
            try {
                outputStreamClient = allClientsSocketsConnected.get(parseInt(arrOfStr[0])).getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataOutputStream dataOutputStreamClient = new DataOutputStream(outputStreamClient);
            try {
                dataOutputStreamClient.writeUTF(str);
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("LB_REQUEST_ENVIADO_CLIENT->" + str);
        }
    }
}
