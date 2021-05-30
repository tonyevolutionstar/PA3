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
            String[] val = request.split("[|]",0);
            StringBuilder r = new StringBuilder();
             StringBuilder sb = new StringBuilder();

            String select = "";
            int niter = Integer.parseInt(val[4]);
            System.out.println("Niter "+ niter);
            String na = "6.02214076";
            if(niter < 9){
                select = na.substring(0, 2+niter);
                sb.append(select).append(" x 10^23");

            }else{
                StringBuilder sup = new StringBuilder();
                sup.append(na);
                int count = 23;
                for(int j = 8; j < niter; j ++)
                {
                    
                   sup.append("0");
                   count--;
                }
                sb.append(sup.toString()).append(" x 10^").append(String.valueOf(count));
            }
    
            for(int i = 0; i < 5; i ++){
                r.append(val[i]).append("|");
            }
            r.append(sb.toString()).append("|");
            
            System.out.println("SERVER_RESQUEST_RECEBIDO->"+request+"Port->"+SOCKET_PORT);
            sleep(10000*niter); // 10s
            dataOutputStream.writeUTF(r.toString()+"|SERVER|");
            dataOutputStream.flush();      
            System.out.println("SERVER_REQUEST_ENVIADO " + r.toString());
        } catch (IOException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
}
