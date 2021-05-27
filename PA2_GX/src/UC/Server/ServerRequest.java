package UC.Server;

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

public class ServerRequest extends Thread {

    private String request;
    private int SOCKET_PORT;
    private Socket connectedSocket;

    public ServerRequest(String request, int socketPort) {
        this.SOCKET_PORT = socketPort;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            // get the output stream from the socket.
            this.connectedSocket = new Socket("localhost", SOCKET_PORT);
        } catch (IOException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStream outputStream = null;
        try {
            outputStream = connectedSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        try {       
            //DO Calculations
            
            System.out.println("SERVER_RESQUEST_RECEBIDO->"+request+"Port->"+SOCKET_PORT);
            
            dataOutputStream.writeUTF(request+"|SERVER|");
            dataOutputStream.flush();      
            System.out.println("SERVER_REQUEST_ENVIADO");
        } catch (IOException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
}
