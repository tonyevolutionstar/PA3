package UC.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRequest extends Thread {

    private final String request;
    private final int id_server;
    private final int SOCKET_PORT;
    private Socket connectedSocket;
    private final int countRequests;
    HashMap<Integer, String> concurrentThreadsWorking;
    ServerSharedRegion SSR;

    public ServerRequest(int id, String request, int socketPort, HashMap<Integer, String> concurrentThreadsWorking, int countRequests, ServerSharedRegion SSR) {
        this.id_server = id;
        this.SOCKET_PORT = socketPort;
        this.request = request;
        this.concurrentThreadsWorking = concurrentThreadsWorking;
        this.countRequests = countRequests;
        this.SSR = SSR;
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
            System.out.println("request " + request);
            String[] val = request.split("[|]", 0);
            StringBuilder r = new StringBuilder();
            StringBuilder sb = new StringBuilder();

            String select;
            int niter = Integer.parseInt(val[4]);
            System.out.println("Niter " + niter);
            String na = "6.02214076";
            if (niter < 9) {
                select = na.substring(0, 2 + niter);
                sb.append(select).append(" x 10^23");

            } else {
                StringBuilder sup = new StringBuilder();
                sup.append(na);
                int count = 23;
                for (int j = 8; j < niter; j++) {
                    sup.append("0");
                }
                sb.append(sup.toString()).append(" x 10^").append(String.valueOf(count));
            }

       
            r.append(val[0]).append("|").append(val[1]).append("|").append(String.valueOf(id_server)).append("|02|").append(String.valueOf(niter)).append("|").append(sb.toString());

            System.out.println("SERVER_RESQUEST_RECEBIDO->" + request + "Port->" + SOCKET_PORT);
            sleep(1000 * niter); // 10s
            dataOutputStream.writeUTF(r.toString());
            dataOutputStream.flush();
            System.out.println("SERVER_REQUEST_ENVIADO " + r.toString());
            concurrentThreadsWorking.remove(countRequests);
            SSR.ifThreadIsDoneAndQueueUp();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
