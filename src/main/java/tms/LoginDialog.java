package tms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris Liebert
 */
public class LoginDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginDialog
     */
    private static TMSGUI tms;

    public LoginDialog(TMSGUI _tms, boolean modal) {
        super(_tms.frame, modal);
        tms = _tms;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        passwordTextField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        usernameTextField.setNextFocusableComponent(passwordTextField);

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginButtonKeyPressed(evt);
            }
        });

        passwordTextField.setNextFocusableComponent(loginButton);
        passwordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameTextField)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // The login button was pressed
		login(new Credentials(usernameTextField.getText(), new String(passwordTextField.getPassword())));
    	
    }//GEN-LAST:event_loginButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void loginButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginButtonKeyPressed
        // Trigger login button if key is pressed and button is selected
    	login(new Credentials(usernameTextField.getText(), new String(passwordTextField.getPassword())));
    }//GEN-LAST:event_loginButtonKeyPressed

    private void passwordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextFieldActionPerformed
        // Trigger login button if return is pressed in the password field
    	login(new Credentials(usernameTextField.getText(), new String(passwordTextField.getPassword())));
    }//GEN-LAST:event_passwordTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton loginButton;
    public javax.swing.JPasswordField passwordTextField;
    public javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    // Validate and set the credentials
    // Show the main UI once tasks are downloaded
    public void login(Credentials credentials) {
        try {
            if (tms.getDBAdapter().authorizeUser(credentials)) {
                // Hide the dialog
                setVisible(false);
                tms.setCredentials(credentials);
                // Set valid credentials for TMS UI object and display the window
                tms.taskController.setCredentials(credentials);
                // Load tasks from database
                tms.loadUserTasks(credentials);
                tms.frame.setVisible(true);
            } else {
                // Error dialog
                JOptionPane.showMessageDialog(this,
                        "Unable to validate password for " + credentials.getUsername(),
                        "Invalid Login",
                        JOptionPane.ERROR_MESSAGE);
                // Clear the password field
                passwordTextField.setText("");
            }
        } catch (SQLException e) {
            // Error dialog
            JOptionPane.showMessageDialog(this,
                    "Unable to connect to TMS database: " + e.getLocalizedMessage(),
                    "Network Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
