package UC.LoadBalancer;

import UC.Client.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class LoadBalancerRequest extends Thread {

    private Socket SOCKET_PORT;
    private Socket MONITOR_SOCKET_PORT;
    private String requestStr;
    private int SERVERIDINCASECRASH;
    HashMap<Integer, Socket> allClientsSocketsConnected;
    HashMap<Integer, Socket> allServersSocketsConnected;
    HashMap<Integer, ArrayList> allRequestsOnEachServer;

    public LoadBalancerRequest(String requestStr, HashMap<Integer, Socket> allServersSocketsConnected, Socket serverSocketMonitor, HashMap<Integer, Socket> allClientsSocketsConnected, HashMap<Integer, ArrayList> allRequestsOnEachServer, int SERVERIDINCASECRASH) {
        this.requestStr = requestStr;
        this.allServersSocketsConnected = allServersSocketsConnected;
        this.MONITOR_SOCKET_PORT = serverSocketMonitor;
        this.allClientsSocketsConnected = allClientsSocketsConnected;
        this.allRequestsOnEachServer = allRequestsOnEachServer;
        this.SERVERIDINCASECRASH = SERVERIDINCASECRASH;
    }

    @Override
    public void run() {

        try {
            DataOutputStream dataOutputStream4 = new DataOutputStream(this.MONITOR_SOCKET_PORT.getOutputStream());
            dataOutputStream4.writeUTF("NeedInfoPls");
            DataInputStream dataInputStream4 = new DataInputStream(this.MONITOR_SOCKET_PORT.getInputStream());
            String infoFromMonitor = dataInputStream4.readUTF();
            System.out.println("NOVA INFO->" + infoFromMonitor);
            int serverWithLowestWork = 0;
            int lowestWork = 5;
            //Split Servers
            String[] arrOfStr = infoFromMonitor.split("[|]", -2);
            System.out.println(Arrays.toString(arrOfStr) + arrOfStr.length);

            if (SERVERIDINCASECRASH == 9999999) {
                for (int i = 0; i < arrOfStr.length; i++) {
                    String[] arrOfStrData = arrOfStr[i].split(";", -2);
                    if (arrOfStrData.length == 1) {
                        break;
                    }
                    System.out.println(Arrays.toString(arrOfStrData) + arrOfStrData.length);
                    if (parseInt(arrOfStrData[1]) <= lowestWork) {
                        lowestWork = parseInt(arrOfStrData[1]);
                        serverWithLowestWork = parseInt(arrOfStrData[0]);
                    }

                }
                System.out.println("SERVIDOR SELECIONADO! ->" + serverWithLowestWork);
            }
            else
            {
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

            InputStream inputStream = null;
            try {
                inputStream = this.allServersSocketsConnected.get(serverWithLowestWork).getInputStream();
            } catch (IOException ex) {
                Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            dataOutputStream.writeUTF(requestStr);
            dataOutputStream.flush();
            System.out.println("LOAD_BALANCER_REQUEST_ENVIADO->" + requestStr + SOCKET_PORT);

        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
