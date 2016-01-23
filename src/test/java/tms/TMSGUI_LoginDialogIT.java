package tms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class TMSGUI_LoginDialogIT {

    public TMSGUI tms = null;

    public TMSGUI_LoginDialogIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class TMSGUI.
     */
    @Test
    public void testAccessTasks() {
        System.out.println("testAccessTasks");

        // Arrange
        Credentials credentials = new Credentials("test", "pass");
        TMSGUI tms = TestData.getTMSTestInstance();
        String s = "CreateTaskIntegrationTestString";
        boolean foundTask = false;

        // Simulate tms.start()
        try {
            tms.loginDialog = new LoginDialog(tms, true);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Act
        // Simulate login from GUI
        try {
            tms.loginDialog.login(credentials);
        } catch(Exception e) {
            fail(e.getMessage());
        }
        

        // Create a task
        try {
            tms.taskController.createTask(s);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Get all to-do tasks
        for (String task : tms.getDBAdapter().getToDoTasks(credentials)) {
            if (task.equals(s)) {
                foundTask = true;
                break;
            }
        }
        
        try {
            tms.taskController.deleteTask(s);
        } catch (TaskList.NoTaskExistsException e) {
            fail(e.getMessage());
        }

        //Assert
        assertTrue(foundTask);
        
        // Clean up
        
        // Send a window close event
        TestData.closeTMSTestInstance(tms);
    }
}
