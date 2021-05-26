package UC.Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class ClientRequest extends Thread {

    private int SOCKET_PORT;
    private int clientId;
    private int requestId;
    private int numberOfIterations;
    private Socket connectedSocket;

    public ClientRequest(int requestId, int clientId, int numberOfIterations, int socketPort) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.SOCKET_PORT = socketPort;
        this.numberOfIterations = numberOfIterations;
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
        InputStream inputStream = null;
        try {
            inputStream = connectedSocket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        String str = String.valueOf(clientId) + "|" + String.valueOf(this.requestId) + "|00|01|" + String.valueOf(this.numberOfIterations) + "|0|";
        try {        System.out.println("teste1");
            dataOutputStream.writeUTF(str);
                    System.out.println("teste2");
            dataOutputStream.flush();
                                System.out.println("teste3");
            String recievedRequest = dataInputStream.readUTF();
            System.out.println("teste->"+recievedRequest);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
