package UC.Monitor;

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

public class Monitor extends javax.swing.JFrame {

    private int portId;
    private int monitorPort;
    private Socket connectedSocket;
    private Socket lbSocket;
    HashMap<Integer, Socket> allServerSocketsConnected = new HashMap<Integer, Socket>();
    HashMap<Integer, String> allServerSocketsDisconnected = new HashMap<Integer, String>();
    HashMap<Integer, Integer> numberOfWorkLoadEachThreadServer = new HashMap<Integer, Integer>();
    HashMap<Integer, ArrayList> allRequestsOnEachServer = new HashMap<Integer, ArrayList>();

    public Monitor() throws IOException {
        initComponents();
        CONNECTIONREADYLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JButton();
        Label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PORTTextField = new javax.swing.JTextField();
        CONNECTIONREADYLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EXECUTEDTEXTAREA1 = new javax.swing.JTextArea();
        TEXTFIELDMONITORS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        EXECUTEDTEXTAREA = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        REQUESTSEACHSERVER = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("Connect");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("Monitor");

        PORTTextField.setText("6666");
        PORTTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PORTTextFieldActionPerformed(evt);
            }
        });

        CONNECTIONREADYLabel.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        CONNECTIONREADYLabel.setForeground(new java.awt.Color(0, 100, 0));
        CONNECTIONREADYLabel.setText("Online");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Ip/Port:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Servers online:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Servers that Went Offline:");

        EXECUTEDTEXTAREA1.setColumns(20);
        EXECUTEDTEXTAREA1.setRows(5);
        jScrollPane1.setViewportView(EXECUTEDTEXTAREA1);

        TEXTFIELDMONITORS.setText("1111");
        TEXTFIELDMONITORS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEXTFIELDMONITORSActionPerformed(evt);
            }
        });

        jLabel5.setText("Monitor Socket:");

        EXECUTEDTEXTAREA.setColumns(20);
        EXECUTEDTEXTAREA.setRows(5);
        jScrollPane3.setViewportView(EXECUTEDTEXTAREA);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Requests Each Server:");

        REQUESTSEACHSERVER.setColumns(20);
        REQUESTSEACHSERVER.setRows(5);
        jScrollPane4.setViewportView(REQUESTSEACHSERVER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(70, 70, 70))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(15, 15, 15)
                                        .addComponent(TEXTFIELDMONITORS, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(43, 43, 43)
                                        .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(CONNECTIONREADYLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(283, 283, 283)
                        .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(TEXTFIELDMONITORS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(startButton)
                                    .addComponent(CONNECTIONREADYLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        SwingWorker worker = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                portId = parseInt(PORTTextField.getText());
                Socket s = null;
                try {
                    s = new Socket("localhost", portId);
                    lbSocket = s;
                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No LoadBalancer Found on Port " + portId);
                    CONNECTIONREADYLabel.setForeground(Color.red);
                    CONNECTIONREADYLabel.setText("Error Connecting to Ip/Port");
                    CONNECTIONREADYLabel.setVisible(true);
                    connectedSocket = null;
                    return false;
                }
                CONNECTIONREADYLabel.setForeground(new java.awt.Color(0, 100, 0));
                CONNECTIONREADYLabel.setText("ONLINE!");
                CONNECTIONREADYLabel.setVisible(true);
                connectedSocket = s;

                // get the output stream from the socket.
                OutputStream outputStream = null;
                try {
                    outputStream = s.getOutputStream();

                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputStream inputStream = null;
                try {
                    inputStream = s.getInputStream();
                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }

                // create a data output stream from the output stream so we can send data through it
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String request = "ImAliveMonitor";
                System.out.println("Sending ImAliveMonitor to LoadBalancer");

                try {
                    dataOutputStream.writeUTF(request);
                    dataOutputStream.flush();
                    String str = dataInputStream.readUTF();
                    String[] arrOfStr = str.split(";", -2);
                    if (!arrOfStr[1].equals("Monitor")) {
                        System.out.println("MISTAKE-SERVER/Client PORT");
                        CONNECTIONREADYLabel.setForeground(Color.red);
                        CONNECTIONREADYLabel.setText("Error/Dont Pick Server/Client Port");
                        CONNECTIONREADYLabel.setVisible(true);
                        connectedSocket = null;
                        return false;
                    }

                } catch (IOException e) {
                }

                while (true) {
                    String requestInfo = dataInputStream.readUTF();
                    System.out.println("LB_MONITOR->" + requestInfo);
                    String info = null;

                    StringBuilder newTextArea = new StringBuilder();
                    for (Integer key : numberOfWorkLoadEachThreadServer.keySet()) {
                        newTextArea.append("")
                                .append(key)
                                .append(";")
                                .append(numberOfWorkLoadEachThreadServer.get(key))
                                .append("|");
                    }
                    System.out.println("STRING A SER ENVIADA->" + newTextArea.toString());
                    dataOutputStream.writeUTF(newTextArea.toString());
                }
            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();
        
        //Thread to comunicate with Servers
        SwingWorker worker2 = new SwingWorker<Boolean, Integer>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                monitorPort = parseInt(TEXTFIELDMONITORS.getText());
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(monitorPort);
                } catch (IOException ex) {
                    Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
                    CONNECTIONREADYLabel.setForeground(Color.red);
                    CONNECTIONREADYLabel.setText("Error Creating Server :/");
                    CONNECTIONREADYLabel.setVisible(true);
                    return false;
                }

                CONNECTIONREADYLabel.setForeground(new java.awt.Color(0, 100, 0));
                CONNECTIONREADYLabel.setText("ONLINE!");
                CONNECTIONREADYLabel.setVisible(true);

                while (true) {
                    Socket s2 = null;
                    try {
                        s2 = serverSocket.accept();
                    } catch (IOException ex) {
                        Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(1);
                    }

                    InputStream inputStream2 = null;
                    try {
                        inputStream2 = s2.getInputStream();
                    } catch (IOException ex) {
                        Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    DataInputStream dataInputStream2 = new DataInputStream(inputStream2);
                    OutputStream outputStream = s2.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                    String str = dataInputStream2.readUTF();
                    System.out.println(str + monitorPort);
                    String[] arrOfStr = str.split(";", -2);
                    if ("ImAliveMonitor".equals(arrOfStr[0])) {
                        System.out.println("SERVER VIVO!" + arrOfStr[1]);
                        dataOutputStream.writeUTF("MonitorAc");
                        allServerSocketsConnected.put(parseInt(arrOfStr[1]), connectedSocket);
                        StringBuilder newTextArea = new StringBuilder();
                        for (Integer key : allServerSocketsConnected.keySet()) {
                            newTextArea.append("Server ID:")
                                    .append(key)
                                    .append(" = ")
                                    .append(allServerSocketsConnected.get(key))
                                    .append("\n");
                        }
                        System.out.println(newTextArea.toString());
                        EXECUTEDTEXTAREA1.setText(newTextArea.toString());
                        MonitorHeartBeat HB = new MonitorHeartBeat(s2, allServerSocketsConnected, parseInt(arrOfStr[1]), EXECUTEDTEXTAREA1, EXECUTEDTEXTAREA, allServerSocketsDisconnected, portId, numberOfWorkLoadEachThreadServer,allRequestsOnEachServer, REQUESTSEACHSERVER);
                        HB.start();
                    }
                }
            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker2.execute();
    }//GEN-LAST:event_startButtonActionPerformed

    private void PORTTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PORTTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PORTTextFieldActionPerformed

    private void TEXTFIELDMONITORSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEXTFIELDMONITORSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TEXTFIELDMONITORSActionPerformed

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
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Monitor().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CONNECTIONREADYLabel;
    private javax.swing.JTextArea EXECUTEDTEXTAREA;
    private javax.swing.JTextArea EXECUTEDTEXTAREA1;
    private javax.swing.JLabel Label;
    private javax.swing.JTextField PORTTextField;
    private javax.swing.JTextArea REQUESTSEACHSERVER;
    private javax.swing.JTextField TEXTFIELDMONITORS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
