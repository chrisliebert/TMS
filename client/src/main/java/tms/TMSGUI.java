package tms;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.w3c.dom.Document;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xml.sax.SAXException;

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
    public XHTMLPanel xhtmlPanel = null;
    public RSyntaxTextArea textArea = null;
    public String selectedTask = null;
    private Credentials credentials = null;

    /**
     * @param args the command line arguments
     */
    TMSGUI() {
        taskController = new TaskController(this);
        dbAdapter = new DBAdapter();
    }

    public void start(Credentials credentials) throws SQLException {
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
        frame.setMinimumSize(new Dimension(936, 600));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        
        // Set frame icon
        //ImageIcon logo = new ImageIcon("logo.png");
        //frame.setIconImage(logo.getImage());
        
        loginDialog = new LoginDialog(this, true);
        loginDialog.setLocationByPlatform(true);
        
        // If credentials are not provided, prompt the user to enter them
        if(credentials == null) {
        	loginDialog.setVisible(true);//
        } else {
        	loginDialog.setVisible(false);
        	loginDialog.login(credentials);
        }
        
        xhtmlPanel = new XHTMLPanel();
        xhtmlPanel.setName("Task Description");

        PropertyChangeListener pl = new EditorPropertyChangeListener(this);
        
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        //textArea.setCodeFoldingEnabled(true);

        JScrollPane scrollPane = new JScrollPane(xhtmlPanel);
        scrollPane.setName("Description");
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.tabbedPane.add(scrollPane);

        HTMLEditor editorPanel = new HTMLEditor(this);
        editorPanel.scrollPane.add(textArea);
        editorPanel.setName("HTML");
        panel.tabbedPane.add(editorPanel);
        textArea.addPropertyChangeListener(pl);
        
 
        
        /*
        // Enable Ctrl+S hotkey in HTML editor
        Action saveAction = new AbstractAction("Save") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("CONTROL S");
			}
        };
        saveAction.putValue(Action.ACCELERATOR_KEY,  KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
      */
    }

    public static void main(String[] args) {
        TMSGUI tms = new TMSGUI();
        try {
        	if(args.length == 2)
        		tms.start(new Credentials(args[0], DBAdapter.getPasswordHash(args[1])));
        	else tms.start(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

    public void setCredentials(Credentials _credentials) {
        credentials = _credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }
    
    void clearCredentials() {
        credentials = null;
    }

    String getSelectedTask() {
        return selectedTask;
    }

    public void setXHTMLDocument(String xhtml) {
		if(xhtml == null || xhtml.length() == 0) {
			xhtml = "<html></html>";
		}
    	
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(xhtml.getBytes()));
            xhtmlPanel.setDocument(doc);
        } catch (SAXException ex) {
            Logger.getLogger(TMSGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TMSGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TMSGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    
    public static class EditorPropertyChangeListener implements PropertyChangeListener {
        private TMSGUI tms;
        public EditorPropertyChangeListener(TMSGUI _tms) {
            tms = _tms;
        }

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            String xhtml = tms.textArea.getText();
            if(xhtml.length() > 0) {
               tms.setXHTMLDocument(xhtml);
            }
        }
    }
}
