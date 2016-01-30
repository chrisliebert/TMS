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
        return new Credentials("test", "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1");
    }
    
    public final static int getValidTestCredentialsId() {
		// This is the ID of the user table for valid test credtentials
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
