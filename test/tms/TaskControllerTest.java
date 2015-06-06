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
public class TaskControllerTest {

    private TMSGUI tms;
    private TaskController taskController;

    public TaskControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tms = TestData.getTMSTestInstance();
        tms.taskController.setCredentials(TestData.getValidTestCredentials());
        taskController = tms.taskController;
    }

    @After
    public void tearDown() {
        // Clear task lists
        try {
        while(taskController.toDoList.getSize() > 0) {
            taskController.deleteTask(taskController.toDoList.getElementAt(0));
        }
        while(taskController.inProgressList.getSize() > 0) {
            taskController.deleteTask(taskController.inProgressList.getElementAt(0));
        }
        while(taskController.doneList.getSize() > 0) {
            taskController.deleteTask(taskController.doneList.getElementAt(0));
        }
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test of createTask method, of class TaskController.
     */
    @Test
    public void testCreateTask() {
        System.out.println("createTask");

        // Arrange
        String s = "CreateTaskTestString";

        // Act
        try {
            taskController.createTask(s);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Assert
        assertTrue(taskController.toDoList.containsTask(s));
    }

    /**
     * Test of updateTask method, of class TaskController.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        
        // Arrange
        String s = "UpdateTaskTestString";

        try {
            taskController.createTask(s);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
        // Act
        taskController.updateTask(s, 1); // move to in-progress list;
        
        // Assert
        assertTrue(taskController.inProgressList.containsTask(s));
        
        // Act
        taskController.updateTask(s, 2); // move to in-progress list;
        
        // Assert
        assertTrue(taskController.doneList.containsTask(s));
    }
    
    /**
     * Test of deleteTask method, of class TaskController.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");
        
        // Arrange
        String s = "DeleteTaskTestString";
        try {
            taskController.createTask(s);
            taskController.deleteTask(s);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // Assert
        assertFalse(taskController.toDoList.containsTask(s));
        assertFalse(taskController.inProgressList.containsTask(s));
        assertFalse(taskController.doneList.containsTask(s));
    }

}
