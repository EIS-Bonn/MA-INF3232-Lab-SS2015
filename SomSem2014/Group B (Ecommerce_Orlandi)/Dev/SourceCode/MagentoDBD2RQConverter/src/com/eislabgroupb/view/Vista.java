/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eislabgroupb.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 *
 * @author Group B
 */

public class Vista extends javax.swing.JFrame {

    /**
     * Creates new form Vista
     */
    public Vista() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jtxt_DBName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxt_DBUser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxt_DBPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxt_DBPort = new javax.swing.JTextField();
        jbtnClosePort = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jcboxReviews = new javax.swing.JCheckBox();
        jcboxProducts = new javax.swing.JCheckBox();
        jcboxUsers = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jlblOutputURL = new javax.swing.JLabel();
        jbtnOpenURL = new javax.swing.JButton();
        jlblBannerImg = new javax.swing.JLabel();
        jbtnCancel = new javax.swing.JButton();
        jbtnGenerate = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmenuFile = new javax.swing.JMenu();
        jmenuItemSave = new javax.swing.JMenuItem();
        jmenuItemOpen = new javax.swing.JMenuItem();
        jmenuItemExit = new javax.swing.JMenuItem();
        jmenuItemAbout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Magento DB Publisher");
        setResizable(false);

        jPanel1.setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Database Settings"));
        jPanel2.setLayout(null);

        jtxt_DBName.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jtxt_DBName.setForeground(new java.awt.Color(96, 94, 94));
        jtxt_DBName.setText("magento");
        jtxt_DBName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxt_DBNameFocusGained(evt);
            }
        });
        jPanel2.add(jtxt_DBName);
        jtxt_DBName.setBounds(150, 40, 170, 30);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Database Name:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 40, 130, 30);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("DB Username:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 90, 130, 30);

        jtxt_DBUser.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jtxt_DBUser.setForeground(new java.awt.Color(96, 94, 94));
        jtxt_DBUser.setText("root");
        jtxt_DBUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_DBUserActionPerformed(evt);
            }
        });
        jtxt_DBUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxt_DBUserFocusGained(evt);
            }
        });
        jPanel2.add(jtxt_DBUser);
        jtxt_DBUser.setBounds(150, 90, 170, 30);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("DB Password:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 140, 130, 30);

        jtxt_DBPassword.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jtxt_DBPassword.setForeground(new java.awt.Color(96, 94, 94));
        jtxt_DBPassword.setText("root");
        jtxt_DBPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_DBPasswordActionPerformed(evt);
            }
        });
        jtxt_DBPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxt_DBPasswordFocusGained(evt);
            }
        });
        jPanel2.add(jtxt_DBPassword);
        jtxt_DBPassword.setBounds(150, 140, 170, 30);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Server port:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 190, 130, 30);

        jtxt_DBPort.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jtxt_DBPort.setForeground(new java.awt.Color(96, 94, 94));
        jtxt_DBPort.setText("2020");
        jtxt_DBPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxt_DBPortFocusGained(evt);
            }
        });
        jPanel2.add(jtxt_DBPort);
        jtxt_DBPort.setBounds(150, 190, 120, 30);

        jbtnClosePort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/block_icon.png"))); // NOI18N
        jbtnClosePort.setToolTipText("Close inserted port");
        jPanel2.add(jbtnClosePort);
        jbtnClosePort.setBounds(280, 190, 40, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 90, 350, 240);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Tables to publish"));
        jPanel3.setLayout(null);

        jcboxReviews.setText("Reviews");
        jPanel3.add(jcboxReviews);
        jcboxReviews.setBounds(20, 60, 120, 24);

        jcboxProducts.setText("Products");
        jcboxProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboxProductsActionPerformed(evt);
            }
        });
        jPanel3.add(jcboxProducts);
        jcboxProducts.setBounds(20, 110, 120, 24);

        jcboxUsers.setText("Users");
        jPanel3.add(jcboxUsers);
        jcboxUsers.setBounds(20, 160, 120, 24);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(370, 90, 180, 240);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Output URL"));
        jPanel4.setLayout(null);

        jlblOutputURL.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jlblOutputURL.setForeground(new java.awt.Color(1, 1, 1));
        jlblOutputURL.setText("http://localhost/...");
        jPanel4.add(jlblOutputURL);
        jlblOutputURL.setBounds(20, 30, 370, 30);

        jbtnOpenURL.setText("Open URL");
        jbtnOpenURL.setToolTipText("Open URL on default web browser");
        jPanel4.add(jbtnOpenURL);
        jbtnOpenURL.setBounds(410, 30, 110, 30);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(10, 340, 540, 80);

        jlblBannerImg.setBackground(new java.awt.Color(240, 165, 90));
        jlblBannerImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/banner.png"))); // NOI18N
        jPanel1.add(jlblBannerImg);
        jlblBannerImg.setBounds(0, 0, 560, 80);

        jbtnCancel.setText("Cancel");
        jbtnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jbtnCancel);
        jbtnCancel.setBounds(420, 430, 130, 30);

        jbtnGenerate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/generate.png"))); // NOI18N
        jbtnGenerate.setText("Generate");
        jbtnGenerate.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(jbtnGenerate);
        jbtnGenerate.setBounds(260, 430, 140, 30);

        jmenuFile.setText("File");

        jmenuItemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/save.png"))); // NOI18N
        jmenuItemSave.setText("Save");
        jmenuFile.add(jmenuItemSave);

        jmenuItemOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/open.png"))); // NOI18N
        jmenuItemOpen.setText("Open");
        jmenuFile.add(jmenuItemOpen);

        jmenuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eislabgroupb/view/cancel.png"))); // NOI18N
        jmenuItemExit.setText("Exit");
        jmenuFile.add(jmenuItemExit);

        jMenuBar1.add(jmenuFile);

        jmenuItemAbout.setText("About");
        
        jMenuBar1.add(jmenuItemAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jtxt_DBUserActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jtxt_DBPasswordActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jcboxProductsActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jtxt_DBNameFocusGained(java.awt.event.FocusEvent evt) {                                        
        //jtxt_DBName.setText(""); 
    }                                       

    private void jtxt_DBUserFocusGained(java.awt.event.FocusEvent evt) {                                        
        jtxt_DBUser.setText(""); 
    }                                       

    private void jtxt_DBPasswordFocusGained(java.awt.event.FocusEvent evt) {                                            
        jtxt_DBPassword.setText(""); 
    }                                           

    private void jtxt_DBPortFocusGained(java.awt.event.FocusEvent evt) {                                        
        jtxt_DBPort.setText(""); 
    }                                               

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
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnClosePort;
    private javax.swing.JButton jbtnGenerate;
    private javax.swing.JButton jbtnOpenURL;
    private javax.swing.JCheckBox jcboxProducts;
    private javax.swing.JCheckBox jcboxReviews;
    private javax.swing.JCheckBox jcboxUsers;
    private javax.swing.JLabel jlblBannerImg;
    private javax.swing.JLabel jlblOutputURL;
    private javax.swing.JMenu jmenuFile;
    private javax.swing.JMenu jmenuItemAbout;
    private javax.swing.JMenuItem jmenuItemExit;
    private javax.swing.JMenuItem jmenuItemOpen;
    private javax.swing.JMenuItem jmenuItemSave;
    private javax.swing.JTextField jtxt_DBName;
    private javax.swing.JTextField jtxt_DBPassword;
    private javax.swing.JTextField jtxt_DBPort;
    private javax.swing.JTextField jtxt_DBUser;
    // End of variables declaration                   

    public javax.swing.JTextField getJtxt_DBUser() {
        return jtxt_DBUser;
    }

    public void setJtxt_DBUser(javax.swing.JTextField jtxt_DBUser) {
        this.jtxt_DBUser = jtxt_DBUser;
    }

    public javax.swing.JButton getJbtnCancel() {
        return jbtnCancel;
    }

    public void setJbtnCancel(javax.swing.JButton jbtnCancel) {
        this.jbtnCancel = jbtnCancel;
    }

    public javax.swing.JButton getJbtnGenerate() {
        return jbtnGenerate;
    }

    public void setJbtnGenerate(javax.swing.JButton jbtnGenerate) {
        this.jbtnGenerate = jbtnGenerate;
    }

    public javax.swing.JTextField getJtxt_DBName() {
        return jtxt_DBName;
    }

    public void setJtxt_DBName(javax.swing.JTextField jtxt_DBName) {
        this.jtxt_DBName = jtxt_DBName;
    }

    public javax.swing.JTextField getJtxt_DBPassword() {
        return jtxt_DBPassword;
    }

    public void setJtxt_DBPassword(javax.swing.JTextField jtxt_DBPassword) {
        this.jtxt_DBPassword = jtxt_DBPassword;
    }

    public javax.swing.JTextField getJtxt_DBPort() {
        return jtxt_DBPort;
    }

    public void setJtxt_DBPort(javax.swing.JTextField jtxt_DBPort) {
        this.jtxt_DBPort = jtxt_DBPort;
    }

    public JButton getJbtnClosePort() {
        return jbtnClosePort;
    }

    public void setJbtnClosePort(JButton jbtnClosePort) {
        this.jbtnClosePort = jbtnClosePort;
    }

    public JButton getJbtnOpenURL() {
        return jbtnOpenURL;
    }

    public void setJbtnOpenURL(JButton jbtnOpenURL) {
        this.jbtnOpenURL = jbtnOpenURL;
    }

    public JCheckBox getJcboxProducts() {
        return jcboxProducts;
    }

    public void setJcboxProducts(JCheckBox jcboxProducts) {
        this.jcboxProducts = jcboxProducts;
    }

    public JCheckBox getJcboxReviews() {
        return jcboxReviews;
    }

    public void setJcboxReviews(JCheckBox jcboxReviews) {
        this.jcboxReviews = jcboxReviews;
    }

    public JCheckBox getJcboxUsers() {
        return jcboxUsers;
    }

    public void setJcboxUsers(JCheckBox jcboxUsers) {
        this.jcboxUsers = jcboxUsers;
    }

    public JLabel getJlblOutputURL() {
        return jlblOutputURL;
    }

    public void setJlblOutputURL(JLabel jlblOutputURL) {
        this.jlblOutputURL = jlblOutputURL;
    }

    public JMenu getJmenuItemAbout() {
        return jmenuItemAbout;
    }

    public void setJmenuItemAbout(JMenu jmenuItemAbout) {
        this.jmenuItemAbout = jmenuItemAbout;
    }

    public JMenuItem getJmenuItemExit() {
        return jmenuItemExit;
    }

    public void setJmenuItemExit(JMenuItem jmenuItemExit) {
        this.jmenuItemExit = jmenuItemExit;
    }

    public JMenuItem getJmenuItemOpen() {
        return jmenuItemOpen;
    }

    public void setJmenuItemOpen(JMenuItem jmenuItemOpen) {
        this.jmenuItemOpen = jmenuItemOpen;
    }

    public JMenuItem getJmenuItemSave() {
        return jmenuItemSave;
    }

    public void setJmenuItemSave(JMenuItem jmenuItemSave) {
        this.jmenuItemSave = jmenuItemSave;
    }

    public JLabel getJlblBannerImg() {
        return jlblBannerImg;
    }

    public void setJlblBannerImg(JLabel jlblBannerImg) {
        this.jlblBannerImg = jlblBannerImg;
    }
    
    
    
}
