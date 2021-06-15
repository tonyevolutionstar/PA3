package UC.LoadBalancer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Responsible handle resquests 
 * @author António Ramos and Miguel Silva
 */
public class LoadBalancerRequest extends Thread {

    private Socket SOCKET_PORT;
    private final Socket MONITOR_SOCKET_PORT;
    private final String requestStr;
    private final int SERVERIDINCASECRASH;
    HashMap<Integer, Socket> allClientsSocketsConnected;
    HashMap<Integer, Socket> allServersSocketsConnected;
    HashMap<Integer, ArrayList> allRequestsOnEachServer;

    /**
     * Constructor of Load Balancer Request
     * @param requestStr - request string from client
     * @param allServersSocketsConnected - hashmap of all sockets connected
     * @param serverSocketMonitor - server port from monitor
     * @param allClientsSocketsConnected - hashmap of all clients connected
     * @param allRequestsOnEachServer - hashmap of all active requests on each server 
     * @param SERVERIDINCASECRASH - variable for checking if server dies
     */
    
    public LoadBalancerRequest(String requestStr, HashMap<Integer, Socket> allServersSocketsConnected, Socket serverSocketMonitor, HashMap<Integer, Socket> allClientsSocketsConnected, HashMap<Integer, ArrayList> allRequestsOnEachServer, int SERVERIDINCASECRASH) {
        this.requestStr = requestStr;
        this.allServersSocketsConnected = allServersSocketsConnected;
        this.MONITOR_SOCKET_PORT = serverSocketMonitor;
        this.allClientsSocketsConnected = allClientsSocketsConnected;
        this.allRequestsOnEachServer = allRequestsOnEachServer;
        this.SERVERIDINCASECRASH = SERVERIDINCASECRASH;
    }

    /**
     * thread of load balancer, created when receive infos
     */
    @Override
    public void run() {
        try {
            DataOutputStream dataOutputStream4 = new DataOutputStream(this.MONITOR_SOCKET_PORT.getOutputStream());
            dataOutputStream4.writeUTF("NeedInfoPls");
            DataInputStream dataInputStream4 = new DataInputStream(this.MONITOR_SOCKET_PORT.getInputStream());
            String infoFromMonitor = dataInputStream4.readUTF();
            int serverWithLowestWork = 0;
            int lowestWork = 5;
            //Split Servers
            String[] arrOfStr = infoFromMonitor.split("[|]", -2);

            if (SERVERIDINCASECRASH == 9999999) {
                for (String arrOfStr1 : arrOfStr) {
                    String[] arrOfStrData = arrOfStr1.split(";", -2);
                    if (arrOfStrData.length == 1) {
                        break;
                    }
                    if (parseInt(arrOfStrData[1]) <= lowestWork) {
                        lowestWork = parseInt(arrOfStrData[1]);
                        serverWithLowestWork = parseInt(arrOfStrData[0]);
                    }
                }
                System.out.println("SERVIDOR SELECIONADO! ->" + serverWithLowestWork);
            }
            else{
                serverWithLowestWork = SERVERIDINCASECRASH;
            }
            //Saving in a HashTable in case of a fail
            allRequestsOnEachServer.get(serverWithLowestWork).add(requestStr);

            OutputStream outputStream = null;
            try {
                outputStream = this.allServersSocketsConnected.get(serverWithLowestWork).getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(requestStr);
            dataOutputStream.flush();
            System.out.println("LOAD_BALANCER_REQUEST_ENVIADO->" + requestStr + SOCKET_PORT);
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
