package UC.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


public class ClientRequest extends Thread{
 
    int SOCKET_PORT;
    int id;
    JLabel Thread1;
    
    public ClientRequest(int requestId, int SOCKET_PORT){
        this.SOCKET_PORT = SOCKET_PORT;      
    }

    @Override
    public void run()
    {      
        Socket s = null;
        try {
            s = new Socket("localhost",SOCKET_PORT);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }   
}
