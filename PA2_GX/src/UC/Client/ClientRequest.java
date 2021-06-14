package UC.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Responsible to send requests 
 * @author António Ramos and Miguel Silva
 */

public class ClientRequest extends Thread {

    private final int SOCKET_PORT;
    private final int clientId;
    private final int requestId;
    private final int numberOfIterations;
    private Socket connectedSocket;
    HashMap<Integer, String> allPendingRequests;
    JTextArea PENDINGTEXTAREA;
    JLabel pendReq;

    /**
     * Constructor of Client Request
     * @param pendReq - total of requests to display on gui
     * @param requestId - number of request
     * @param clientId - number of client
     * @param numberOfIterations - number of iterations of avogrado
     * @param socketPort - socket port number of load balance, to connect and send information
     * @param allPendingRequests - database where all pending requests are stored 
     * @param PENDINGTEXTAREA - are where displayed the pending requests
     */
    public ClientRequest(JLabel pendReq,int requestId, int clientId, int numberOfIterations, int socketPort, HashMap<Integer, String> allPendingRequests, JTextArea PENDINGTEXTAREA) {
        this.pendReq = pendReq;
        this.requestId = requestId;
        this.clientId = clientId;
        this.SOCKET_PORT = socketPort;
        this.numberOfIterations = numberOfIterations;
        this.allPendingRequests = allPendingRequests;
        this.PENDINGTEXTAREA = PENDINGTEXTAREA;
    }

    /**
     * run thread of requests 
     */
    
    @Override
    public void run() {
        try {
            // get the output stream from the socket.
            this.connectedSocket = new Socket("localhost", SOCKET_PORT);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStream outputStream = null;
        try {
            outputStream = connectedSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String str =  String.valueOf(clientId) + "|" + String.valueOf(this.requestId) + "|00|01|" + String.valueOf(this.numberOfIterations) + "|0|";
        allPendingRequests.put(requestId, str);
        pendReq.setText(String.valueOf(allPendingRequests.size()));
        StringBuilder newTextArea = new StringBuilder();
        allPendingRequests.keySet().forEach(key -> {
            newTextArea.append("Request-")
                    .append(key)
                    .append(" : ")
                    .append(allPendingRequests.get(key))
                    .append("\n");
        });
        PENDINGTEXTAREA.setText(newTextArea.toString());
        try {
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
