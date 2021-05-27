package UC.LoadBalancer;

import UC.Client.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class LoadBalancerRequest extends Thread {

    private Socket SOCKET_PORT;
    private Socket CLIENT_SOCKET_PORT;
    private String requestStr;

    public LoadBalancerRequest(String requestStr,Socket socketPort, Socket clientSocketPort) {
        this.requestStr = requestStr;
        this.SOCKET_PORT = socketPort;
        this.CLIENT_SOCKET_PORT = clientSocketPort;
    }


    @Override
    public void run() {
 
        OutputStream outputStream = null;
        try {
            outputStream = this.SOCKET_PORT.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        try {       
            dataOutputStream.writeUTF(requestStr);
            dataOutputStream.flush();
            System.out.println("LOAD_BALANCER_REQUEST_ENVIADO->"+requestStr+SOCKET_PORT);            
          /*  String recievedRequestServer = dataInputStream.readUTF();
            System.out.println("teste->"+recievedRequestServer);
            OutputStream outPutStreamClient = this.CLIENT_SOCKET_PORT.getOutputStream();
            DataOutputStream dataOutPutStreamClient = new DataOutputStream(outPutStreamClient);
            dataOutPutStreamClient.writeUTF(recievedRequestServer);
            dataOutPutStreamClient.flush();*/
            
            //Change variables when finishing on the loadBalancer main thread
            
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
