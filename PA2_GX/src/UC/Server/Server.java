package UC.Server;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final ServerSharedRegion SSR = new ServerSharedRegion();

    public Server() throws IOException {
        this.serverQueue = new ArrayList<>();
        initComponents();
        STATUSLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ConBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IDLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PORTTextField = new javax.swing.JTextField();
        STATUSLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        monitorPort = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ConBut.setText("Connect");
        ConBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConButActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("Server");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Server Id:");

        IDLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        IDLabel.setText("Waiting");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Ip/Port:");

        PORTTextField.setText("8888");

        STATUSLabel.setFont(new java.awt.Font("Arial Black", 0, 10)); // NOI18N
        STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
        STATUSLabel.setText("Ready!");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Monitor:");

        monitorPort.setText("1111");
        monitorPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorPortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(ConBut, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(STATUSLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(monitorPort, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConBut)
                    .addComponent(STATUSLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(monitorPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConButActionPerformed

        if (this.connectedSocket != null) {
            //To avoid spamming connect key
            return;
        }

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
                    //É preciso criar threads do server (teste o que esta em baixo) e falta um loop infinito
                    String requestInfo = dataInputStream.readUTF();
                    countRequests++;
                    if (concurrentThreadsWorking.size() < 3) {
                        concurrentThreadsWorking.put(countRequests, "..");
                        ServerRequest serverRequest = new ServerRequest(id,requestInfo, portId, concurrentThreadsWorking, countRequests,SSR);
                        serverRequest.start();
                    } else if (serverQueue.size() < 2) {
                        serverQueue.add(requestInfo);
                    } else {
                        //Rejected
                        String[] val = requestInfo.split("[|]", 0);
                        System.out.println(val.length);
                        String rejectedRequest = val[0] + "|" + val[1] + "|" + String.valueOf(0) + id + "|" + String.valueOf(0) + String.valueOf(3) + "|" + val[4] + "|" + val[5] + "|";
                        Socket s2 = null;
                        try {
                            // get the output stream from the socket.
                            s2 = new Socket("localhost", portId);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        OutputStream outputStream3 = null;
                        try {
                            outputStream3 = s2.getOutputStream();
                        } catch (IOException ex) {
                            Logger.getLogger(ServerRequest.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        DataOutputStream dataOutputStream3 = new DataOutputStream(outputStream3);
                        dataOutputStream3.writeUTF(rejectedRequest);
                                    dataOutputStream3.flush(); 
                    }
                }

            }
            

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();

        //Monitor Thread
        SwingWorker worker2 = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                monitorPortId = parseInt(monitorPort.getText());
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
                STATUSLabel.setText("Ready to Send Requests!");
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

                // create a data output stream from the output stream so we can send data through it
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
                    dataOutputStream.writeUTF(request);
                }

            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker2.execute();
        
            //Queue
            SwingWorker worker3 = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                while(true)
                {
                    SSR.threadThatChecksConcurrentWorkingThreads();
                    if(concurrentThreadsWorking.size()<3 && serverQueue.size()>0)
                    {
                        ServerRequest serverRequest = new ServerRequest(id,serverQueue.get(0), portId, concurrentThreadsWorking, countRequests, SSR);
                        concurrentThreadsWorking.put(countRequests, "...");
                        serverRequest.start();
                        serverQueue.remove(0);
                    }
                }
            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }

            private void sleep(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        worker3.execute();
    }//GEN-LAST:event_ConButActionPerformed

    private void monitorPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorPortActionPerformed
        // TODO add your handling code here:
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
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
    private javax.swing.JButton ConBut;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JTextField PORTTextField;
    private javax.swing.JLabel STATUSLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField monitorPort;
    // End of variables declaration//GEN-END:variables
}
