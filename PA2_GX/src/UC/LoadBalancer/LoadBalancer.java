package UC.LoadBalancer;

import UC.Client.Client;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;


public class LoadBalancer extends javax.swing.JFrame {
    
              
    private int numberOfClients = 0;
    private int serverPort;
    private ServerSocket serverSocket; 
    
    public LoadBalancer() throws IOException {
        initComponents();
        STATUSLabel.setVisible(false);   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PORTTextField = new javax.swing.JTextField();
        STATUSLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("LoadBalancer");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Entry Port:");

        PORTTextField.setText("7777");

        STATUSLabel.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
        STATUSLabel.setText("Online");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(STATUSLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(PORTTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(STATUSLabel)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

          if(serverSocket != null)
          {
              return;
          }
          
          
         try {
            serverPort = parseInt(PORTTextField.getText());      
          } catch (NumberFormatException ex) {
            Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
            STATUSLabel.setForeground(Color.red);
            STATUSLabel.setText("Error Creating Server :/");
            STATUSLabel.setVisible(true); 
            return;
          }
        
          SwingWorker worker = new SwingWorker<Boolean, Integer>() {

          @Override
          protected Boolean doInBackground() throws Exception {
              {      
                ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(serverPort);       
                } catch (IOException ex) {
                    Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
                    STATUSLabel.setForeground(Color.red);
                    STATUSLabel.setText("Error Creating Server :/");
                    STATUSLabel.setVisible(true); 
                    return false;
                }
                
                STATUSLabel.setForeground(new java.awt.Color(0, 100, 0));
                STATUSLabel.setText("ONLINE!");
                STATUSLabel.setVisible(true);                
                
                
                while(true)
                {
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
                    try {  
                        String str = dataInputStream2.readUTF();                    
                        String[] arrOfStr = str.split(";",-2);
                        if("ImAlive".equals(str))
                        {
                            System.out.println("Sending new Client ID->"+numberOfClients);
                            dataOutputStream.writeUTF(String.valueOf(numberOfClients));
                            numberOfClients++;
                        }
                        else
                        {
                            //Create a thread to send request to server
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(LoadBalancer.class.getName()).log(Level.INFO, null, ex);
                        try {
                            s2.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }  
            }
          }

          protected void process(Integer chunks) {
          }

          @Override
          protected void done() {
            System.out.println("Done");
          }
        };
        worker.execute();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LoadBalancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadBalancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadBalancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadBalancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LoadBalancer().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField PORTTextField;
    private javax.swing.JLabel STATUSLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
