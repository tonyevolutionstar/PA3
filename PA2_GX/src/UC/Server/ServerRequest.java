package UC.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ServerRequest extends Thread {

    private final String request;
    private final int SOCKET_PORT;
    private Socket connectedSocket;
    private final int countRequests;
    HashMap<Integer, String> concurrentThreadsWorking;
    ServerSharedRegion SSR;
    OutputStream outputStream;
    private int processRejectedFlag;
    private int serverId;
    private JTextArea ALLEXECUTEDTEXTAREA;
    private JLabel TOTALREQUESTPRO;
    private ArrayList<String> allProcessedRequests;

    public ServerRequest(String request, int socketPort, HashMap<Integer, String> concurrentThreadsWorking, int countRequests, ServerSharedRegion SSR, OutputStream outputStream, int processRejectedFlag, int serverId, JTextArea ALLEXECUTEDTEXTAREA, ArrayList<String> allProcessedRequests, JLabel TOTALREQUESTPRO) {
        this.SOCKET_PORT = socketPort;
        this.request = request;
        this.concurrentThreadsWorking = concurrentThreadsWorking;
        this.countRequests = countRequests;
        this.SSR = SSR;
        this.outputStream = outputStream;
        this.processRejectedFlag = processRejectedFlag;
        this.serverId = serverId;
        this.allProcessedRequests = allProcessedRequests;
        this.ALLEXECUTEDTEXTAREA = ALLEXECUTEDTEXTAREA;
        this.TOTALREQUESTPRO = TOTALREQUESTPRO;
    }


    @Override
    public void run() {

        OutputStream outputStream = null;

        outputStream = this.outputStream;

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        System.out.println("REJECTED FLAG->" + this.processRejectedFlag);
        if (this.processRejectedFlag == 1) {
            String[] val = request.split("[|]", 0);
            System.out.println(val.length);
            String rejectedRequest = val[0] + "|" + val[1] + "|" + String.valueOf(0) + serverId + "|" + String.valueOf(0) + String.valueOf(3) + "|" + val[4] + "|" + val[5] + "|";
            try {
                dataOutputStream.writeUTF(rejectedRequest + "|SERVER|");
            } catch (IOException ex) {
                Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                dataOutputStream.flush();
            } catch (IOException ex) {
                Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("SERVER_REQUEST_ENVIADO_REJECTED " + rejectedRequest);
            return;
        }

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

            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    r.append(serverId).append("|");
                }
                r.append(val[i]).append("|");
            }
            r.append(sb.toString()).append("|");

            System.out.println("SERVER_RESQUEST_RECEBIDO->" + request + "Port->" + SOCKET_PORT);
            sleep(5000 * niter); // 5s
            dataOutputStream.writeUTF(r.toString());
            dataOutputStream.flush();
            System.out.println("SERVER_REQUEST_ENVIADO " + r.toString());
            allProcessedRequests.add(r.toString());
            updateGui();
            concurrentThreadsWorking.remove(countRequests);
            SSR.ifThreadIsDoneAndQueueUp();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateGui() {
        StringBuilder newTextArea = new StringBuilder();
        for (int i = 0; i < allProcessedRequests.size(); i++) {
            newTextArea.append("Request-")
                    .append(allProcessedRequests.get(i))
                    .append("\n");
        }
        ALLEXECUTEDTEXTAREA.setText(newTextArea.toString());
        TOTALREQUESTPRO.setText(String.valueOf(allProcessedRequests.size()));
    }
}
