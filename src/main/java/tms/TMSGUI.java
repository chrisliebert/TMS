package tms;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Chris Liebert
 */
public class TMSGUI {

    // The window
    public JFrame frame = null;
    // The panel inside the window
    public TMSPanel panel = null;
    public TaskController taskController = null;
    public LoginDialog loginDialog = null;
    private DBAdapter dbAdapter = null;

    /**
     * @param args the command line arguments
     */
    TMSGUI() {
        taskController = new TaskController(this);
        dbAdapter = new DBAdapter();
    }

    public void start() {
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
            java.util.logging.Logger.getLogger(TMSGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TMSGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TMSGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TMSGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        // Create the UI objects: frame invisible by default
        frame = new JFrame();
        panel = new TMSPanel(this);
        frame.setTitle("TMS");
        frame.add(panel);
        frame.setMinimumSize(new Dimension(936, 500));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        loginDialog = new LoginDialog(this, true);
        loginDialog.setLocationByPlatform(true);
        showLoginDialog();
    }

    public static void main(String[] args) {
        TMSGUI tms = new TMSGUI();
        tms.start();
    }

    public void showLoginDialog() {
        loginDialog.setVisible(true);
    }

    // Load tasks from the database to the UI
    public void loadUserTasks(Credentials credentials) {
        try {
            taskController.loadToDoTasks(getDBAdapter().getToDoTasks(credentials));
            taskController.loadInProgressTasks(getDBAdapter().getInProgressTasks(credentials));
            taskController.loadDoneTasks(getDBAdapter().getDoneTasks(credentials));
            panel.toDoList.updateUI();
            panel.inProgressList.updateUI();
            panel.doneList.updateUI();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public DBAdapter getDBAdapter() {
        return dbAdapter;
    }
}
