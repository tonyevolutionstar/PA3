package UC.Client;

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

public class ClientRequest extends Thread {

    private final int SOCKET_PORT;
    private final int clientId;
    private final int requestId;
    private final int numberOfIterations;
    private Socket connectedSocket;
    HashMap<Integer, String> allPendingRequests;
    JTextArea PENDINGTEXTAREA;

    public ClientRequest(int requestId, int clientId, int numberOfIterations, int socketPort, HashMap<Integer, String> allPendingRequests, JTextArea PENDINGTEXTAREA) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.SOCKET_PORT = socketPort;
        this.numberOfIterations = numberOfIterations;
        this.allPendingRequests = allPendingRequests;
        this.PENDINGTEXTAREA = PENDINGTEXTAREA;
    }

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
        System.out.println("str " + str);
        allPendingRequests.put(requestId, str);

        StringBuilder newTextArea = new StringBuilder();
        System.out.println("all " + allPendingRequests.get(0));
        for (Integer key : allPendingRequests.keySet()) {
            newTextArea
                    .append(allPendingRequests.get(key))
                    .append("\n");
        }
        PENDINGTEXTAREA.setText(newTextArea.toString());
        try {
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
