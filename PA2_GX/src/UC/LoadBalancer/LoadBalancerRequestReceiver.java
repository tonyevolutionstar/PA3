package UC.LoadBalancer;

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
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class LoadBalancerRequestReceiver extends Thread {

    private HashMap<Integer, Socket> allClientsSocketsConnected;
    private HashMap<Integer, ArrayList> allRequestsOnEachServer;
    private Socket s2;

    public LoadBalancerRequestReceiver(HashMap<Integer, Socket> allClientsSocketsConnected, Socket s2, HashMap<Integer, ArrayList> allRequestsOnEachServer) {
        this.allClientsSocketsConnected = allClientsSocketsConnected;
        this.s2 = s2;
        this.allRequestsOnEachServer = allRequestsOnEachServer;
    }

    @Override
    public void run() {
        while(true)
        {
        DataInputStream dataInputStream = null;
        try {
             dataInputStream = new DataInputStream(s2.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = null;
        try {
            str = dataInputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(str == null)
        {
            break;
        }
        String[] arrOfStr = str.split("[|]", -2);
        
        for(int i = 0 ; i < allRequestsOnEachServer.size() ;i++)
        {
            int match2=0;
            if(allRequestsOnEachServer.get(parseInt(arrOfStr[2])).isEmpty())
            {
                continue;
            }
            String savedRequestsServer = (String) allRequestsOnEachServer.get(parseInt(arrOfStr[2])).get(i);
            String[] arrOfStrSavedServer = str.split("[|]", -2);
            if(arrOfStrSavedServer[0].equals(arrOfStr[0]))
            {
                match2++;
            }
            if(arrOfStrSavedServer[1].equals(arrOfStr[1]))
            {
                match2++;
            }
            if(match2 == 2)
            {
                allRequestsOnEachServer.get(parseInt(arrOfStr[2])).remove(i);
                break;
            }
        }
        OutputStream outputStreamClient = null;
        try {
            outputStreamClient = allClientsSocketsConnected.get(parseInt(arrOfStr[0])).getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataOutputStream dataOutputStreamClient = new DataOutputStream(outputStreamClient);
        System.out.println(str + "?");
        try {
            dataOutputStreamClient.writeUTF(str);
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancerRequestReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("LB_REQUEST_ENVIADO_CLIENT->" + str);
        }
    }
}
