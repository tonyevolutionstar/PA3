package UC.Server;

import UC.Client.Client;
import UC.Monitor.Monitor;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;


public class Server extends javax.swing.JFrame {

    private int id;
    private int portId;
    private Socket connectedSocket;
    
    public Server() throws IOException {
        initComponents();
        STATUSLabel.setVisible(false);        
    }
    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ConBut = new javax.swing.JButton();
        Label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IDLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PORTTextField = new javax.swing.JTextField();
        STATUSLabel = new javax.swing.JLabel();
        DiscBut = new javax.swing.JButton();

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
        STATUSLabel.setText("Ready to Receive Requests!");

        DiscBut.setText("Disconnect");
        DiscBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiscButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DiscBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ConBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(STATUSLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
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
                .addComponent(DiscBut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
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
            protected Boolean doInBackground() throws Exception {
                portId = parseInt(PORTTextField.getText());
                Socket s = null;
                try {
                    s = new Socket("localhost", portId);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No LoadBalancer Found on Port " + portId);
                    STATUSLabel.setForeground(Color.red);
                    STATUSLabel.setText("Error Connecting to Ip/Port");
                    STATUSLabel.setVisible(true);
                    connectedSocket = null;
                    return false;
                }
                STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
                STATUSLabel.setText("Ready to Send Requests!");
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
                    String[] arrOfStr = str.split(";",-2);
                    if(!arrOfStr[1].equals("Server"))
                    {
                        System.out.println("MISTAKE-CLIENT PORT");
                        STATUSLabel.setForeground(Color.red);
                        STATUSLabel.setText("Error/Dont pick Client Port");
                        STATUSLabel.setVisible(true);
                        connectedSocket = null;   
                        return false;
                    }
                    id = parseInt(arrOfStr[0]);
                    System.out.println("My Id is->" + id);
                    
                } catch (IOException e) {
                }
                while(true)
                {                    
                    //É preciso criar threads do server (teste o que esta em baixo) e falta um loop infinito
                    System.out.println("PRESO1"+connectedSocket);
                    String requestInfo = dataInputStream.readUTF(); 
                    System.out.println("SERVER->"+requestInfo);
                    ServerRequest serverRequest = new ServerRequest(requestInfo,portId);
                    serverRequest.start();                    
                }

            }

            protected void process(Integer chunks) {
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();
    }//GEN-LAST:event_ConButActionPerformed

    private void DiscButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiscButActionPerformed

    }//GEN-LAST:event_DiscButActionPerformed

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
                    new Server().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConBut;
    private javax.swing.JButton DiscBut;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JLabel Label;
    private javax.swing.JTextField PORTTextField;
    private javax.swing.JLabel STATUSLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
