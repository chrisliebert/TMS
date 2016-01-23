package tms;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Chris
 */
public class TestData {
    public final static Credentials getValidTestCredentials() {
        return new Credentials("test", "pass");
    }
    
    public final static int getValidTestCredentialsId() {
        return 1;
    }

    public static TMSGUI getTMSTestInstance() {
        TMSGUI tms = new TMSGUI();
        // Simulate TMSGUI.start()
        tms.frame = new JFrame();
        tms.panel = new TMSPanel(tms);
        tms.frame.add(tms.panel);
        return tms;
    }

    public static void closeTMSTestInstance(TMSGUI tms) {
        WindowEvent wev = new WindowEvent(tms.frame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }
}
