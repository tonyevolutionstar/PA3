package UC.Server;

import UC.Monitor.*;
import UC.LoadBalancer.LoadBalancer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.SwingWorker;

public class Server extends javax.swing.JFrame {

    private int id = 9999999;
    private int portId;
    private int monitorPortId;
    private int countRequests;
    private final ArrayList<String> serverQueue;
    HashMap<Integer, String> concurrentThreadsWorking = new HashMap<>();
    private Socket connectedSocket;
    private Socket s = null;
    private ServerSharedRegion SSR = new ServerSharedRegion();
    private OutputStream outPutStreamLB;
    private ArrayList<String> allProcessedRequests = new ArrayList<>();
    private ArrayList<String> allRequests = new ArrayList<>();

    public Server() throws IOException {
        this.serverQueue = new ArrayList<>();
        initComponents();
        STATUSLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JButton();
        Label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PORTTextField = new javax.swing.JTextField();
        STATUSLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ALLREQTEXTAREA = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        ALLEXECUTEDTEXTAREA = new javax.swing.JTextArea();
        monitorPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        IDLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        QUEUESIZELABEL = new javax.swing.JLabel();
        THWORKINGLABEL = new javax.swing.JLabel();
        TOTALREQUESTSRECE = new javax.swing.JLabel();
        TOTALREQUESTPRO = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setBackground(java.awt.Color.green);
        startButton.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        startButton.setText("Connect");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("SERVER");

        PORTTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PORTTextField.setText("8888");
        PORTTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PORTTextFieldActionPerformed(evt);
            }
        });

        STATUSLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
        STATUSLabel.setText("ONLINE");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Ip/Port:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("All Requests Received:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("All Requests Processed:");

        ALLREQTEXTAREA.setColumns(20);
        ALLREQTEXTAREA.setRows(5);
        jScrollPane1.setViewportView(ALLREQTEXTAREA);

        ALLEXECUTEDTEXTAREA.setColumns(20);
        ALLEXECUTEDTEXTAREA.setRows(5);
        jScrollPane2.setViewportView(ALLEXECUTEDTEXTAREA);

        monitorPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        monitorPort.setText("1111");
        monitorPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorPortActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Monitor Port:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Server ID:");

        IDLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        IDLabel.setText("Waiting");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("QueueSize:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("ThreadsWorking:");

        QUEUESIZELABEL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        QUEUESIZELABEL.setText("0");

        THWORKINGLABEL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        THWORKINGLABEL.setText("0");

        TOTALREQUESTSRECE.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TOTALREQUESTSRECE.setText("0");

        TOTALREQUESTPRO.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TOTALREQUESTPRO.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TOTALREQUESTSRECE, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TOTALREQUESTPRO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(monitorPort)
                                    .addComponent(PORTTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDLabel)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(QUEUESIZELABEL, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(THWORKINGLABEL, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(STATUSLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(IDLabel)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(QUEUESIZELABEL)
                            .addComponent(THWORKINGLABEL))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(startButton)
                                .addGap(0, 8, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(monitorPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(STATUSLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(TOTALREQUESTSRECE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 21, Short.MAX_VALUE)
                                .addComponent(TOTALREQUESTPRO))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed

        if (this.connectedSocket != null) {
            //To avoid spamming connect key
            return;
        }

        //Thread that comunicates with the LoadBalancer
        SwingWorker worker = new SwingWorker<Boolean, Integer>() {

            @Override
            public Boolean doInBackground() throws Exception {
                portId = parseInt(PORTTextField.getText());
                //monitor
                try {
                    s = new Socket("localhost", portId);

                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No LoadBalancer Found on Port " + portId);

                    STATUSLabel.setForeground(Color.red);
                    STATUSLabel.setText("Error Connecting to Ip/Port");
                    STATUSLabel.setVisible(true);
                    connectedSocket = null;
                    return false;
                }
                STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
                STATUSLabel.setText("ONLINE!");
                STATUSLabel.setVisible(true);
                connectedSocket = s;

                // get the output stream from the socket.
                OutputStream outputStream = null;
                try {
                    outputStream = s.getOutputStream();
                    outPutStreamLB = outputStream;
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputStream inputStream = null;
                try {
                    inputStream = s.getInputStream();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

                // create a data output stream from the output stream so we can send data through it
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                String request = "ImAliveServer";
                System.out.println("Sending ImAliveServer to LoadBalancer");

                try {
                    dataOutputStream.writeUTF(request);
                    dataOutputStream.flush();
                    String str = dataInputStream.readUTF();
                    String[] arrOfStr = str.split(";", -2);
                    if (!arrOfStr[1].equals("Server")) {
                        System.out.println("MISTAKE-CLIENT PORT");
                        STATUSLabel.setForeground(Color.red);
                        STATUSLabel.setText("Error/Dont pick Client Port");
                        STATUSLabel.setVisible(true);
                        connectedSocket = null;
                        return false;
                    }
                    id = parseInt(arrOfStr[0]);
                    System.out.println("My Id is->" + id);
                    IDLabel.setText(arrOfStr[0]);
                } catch (IOException e) {
                }
                while (true) {
                    String requestInfo = dataInputStream.readUTF();
                    allRequests.add(requestInfo);
                    updateGui();
                    if (concurrentThreadsWorking.size() < 3) {
                        //Process Request
                        countRequests++;
                        concurrentThreadsWorking.put(countRequests, requestInfo);
                        updateNumbersGui();
                        ServerRequest serverRequest = new ServerRequest(requestInfo, portId, concurrentThreadsWorking, countRequests, SSR, outPutStreamLB, 0, id, ALLEXECUTEDTEXTAREA, allProcessedRequests, TOTALREQUESTPRO);
                        serverRequest.start();
                    } else if (serverQueue.size() < 2) {
                        //Queue
                        serverQueue.add(requestInfo);
                        updateNumbersGui();
                    } else {
                        //Rejected
                        ServerRequest serverRequest = new ServerRequest(requestInfo, portId, concurrentThreadsWorking, countRequests, SSR, outPutStreamLB, 1, id, ALLEXECUTEDTEXTAREA, allProcessedRequests, TOTALREQUESTPRO);
                        serverRequest.start();
                    }
                }
            }

            public void updateGui() {
                StringBuilder newTextArea = new StringBuilder();
                for (int i = 0; i < allRequests.size(); i++) {
                    newTextArea.append("Request-")
                            .append(allRequests.get(i))
                            .append("\n");
                }
                ALLREQTEXTAREA.setText(newTextArea.toString());
                TOTALREQUESTSRECE.setText(String.valueOf(allRequests.size()));
            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();

        //Monitor Thread to comunicate with the Monitor
        SwingWorker worker2 = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                monitorPortId = 1111;
                //monitor
                Socket s2;
                try {
                    s2 = new Socket("localhost", monitorPortId);

                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No Monitor Found on Port " + Integer.parseInt(monitorPort.getText()));

                    STATUSLabel.setForeground(Color.red);
                    STATUSLabel.setText("Error Connecting to Ip/Port");
                    STATUSLabel.setVisible(true);
                    connectedSocket = null;
                    return false;
                }
                STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
                STATUSLabel.setText("ONLINE!");
                STATUSLabel.setVisible(true);

                // get the output stream from the socket.
                OutputStream outputStream = null;
                try {
                    outputStream = s2.getOutputStream();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputStream inputStream = null;
                try {
                    inputStream = s2.getInputStream();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                String request = "ImAliveMonitor";

                try {
                    while (true) {
                        if (id != 9999999) {
                            //make sure there is an ip on this server, otherwise just wait
                            break;
                        }
                    }
                    dataOutputStream.writeUTF(request + ";" + String.valueOf(id));
                    dataOutputStream.flush();
                    String str = dataInputStream.readUTF();
                    System.out.println(str + monitorPortId);
                    if (!"MonitorAc".equals(str)) {
                        System.out.println("MISTAKE-CLIENT-SERVER PORT");
                        STATUSLabel.setForeground(Color.red);
                        STATUSLabel.setText("Error/Dont pick Client Port");
                        STATUSLabel.setVisible(true);
                        connectedSocket = null;
                        return false;
                    }

                } catch (IOException e) {
                }
                while (true) {
                    //Sending Response to hearbeat
                    String requestInfo = dataInputStream.readUTF();
                    StringBuilder sendInfoToMonitor = new StringBuilder();
                    for (Integer key : concurrentThreadsWorking.keySet()) {
                        sendInfoToMonitor
                                .append(concurrentThreadsWorking.get(key))
                                .append(",");
                    }
                    System.out.println(sendInfoToMonitor);
                    dataOutputStream.writeUTF(String.valueOf(concurrentThreadsWorking.size() + serverQueue.size()) + ";" +sendInfoToMonitor);
                }

            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker2.execute();

        //Thread to see when a thread finishes the work
        SwingWorker worker3 = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                while (true) {
                    SSR.threadThatChecksConcurrentWorkingThreads();
                    updateNumbersGui();
                    if (concurrentThreadsWorking.size() < 3 && serverQueue.size() > 0) {
                        countRequests++;
                        ServerRequest serverRequest = new ServerRequest(serverQueue.get(0), portId, concurrentThreadsWorking, countRequests, SSR, outPutStreamLB, 0, id, ALLEXECUTEDTEXTAREA, allProcessedRequests, TOTALREQUESTPRO);
                        serverRequest.start();
                        concurrentThreadsWorking.put(countRequests, serverQueue.get(0));
                        serverQueue.remove(0);
                        updateNumbersGui();
                    }
                }
            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }

        };
        worker3.execute();
    }//GEN-LAST:event_startButtonActionPerformed

    public void updateNumbersGui() {
        QUEUESIZELABEL.setText(String.valueOf(serverQueue.size()));
        THWORKINGLABEL.setText(String.valueOf(concurrentThreadsWorking.size()));
    }

    private void PORTTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PORTTextFieldActionPerformed

    }//GEN-LAST:event_PORTTextFieldActionPerformed

    private void monitorPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorPortActionPerformed

    }//GEN-LAST:event_monitorPortActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Server().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ALLEXECUTEDTEXTAREA;
    private javax.swing.JTextArea ALLREQTEXTAREA;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JLabel Label;
    private javax.swing.JTextField PORTTextField;
    private javax.swing.JLabel QUEUESIZELABEL;
    private javax.swing.JLabel STATUSLabel;
    private javax.swing.JLabel THWORKINGLABEL;
    private javax.swing.JLabel TOTALREQUESTPRO;
    private javax.swing.JLabel TOTALREQUESTSRECE;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField monitorPort;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
