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
public class TaskController_DBAdapterIT {

    public DBAdapter dbAdapter = null;
    public TMSGUI tms = null;

    public TaskController_DBAdapterIT() {
        dbAdapter = new DBAdapter();
        tms = TestData.getTMSTestInstance();
        tms.taskController.setCredentials(TestData.getValidTestCredentials());
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
        try {
            // Clear task lists
            TaskController taskController = tms.taskController;
            while (taskController.toDoList.getSize() > 0) {
                taskController.deleteTask(taskController.toDoList.getElementAt(0));
            }
            while (taskController.inProgressList.getSize() > 0) {
                taskController.deleteTask(taskController.inProgressList.getElementAt(0));
            }
            while (taskController.doneList.getSize() > 0) {
                taskController.deleteTask(taskController.doneList.getElementAt(0));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Test of createTask method, of class TaskController.
     */
    @Test
    public void testCreateTask() throws Exception {
        System.out.println("createTask");

        // Arrange
        String taskString = "CreateTaskTest";
        TaskController taskController = tms.taskController;
        boolean foundItem = false;

        // Act
        taskController.createTask(taskString);

        // Assert
        for (String i : dbAdapter.getToDoTasks(TestData.getValidTestCredentials())) {
            if (i.equals(taskString)) {
                foundItem = true;
            }
        }
        assertTrue(foundItem);
    }

    /**
     * Test of deleteTask method, of class TaskController.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");

        // Arrange
        // Create test string and load taskController from global test object
        String taskString = "TestDeleteString";
        TaskController taskController = tms.taskController;
        boolean foundItem = false;
        
        // Act
        // Attempt to create then delete a task
        try {
            taskController.createTask(taskString);
            taskController.deleteTask(taskString);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Assert
        for (String i : dbAdapter.getToDoTasks(TestData.getValidTestCredentials())) {
            if (i.equals(taskString)) {
                foundItem = true;
            }
        }
        assertFalse(foundItem);
    }

    /**
     * Test of updateTask method, of class TaskController.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        
        // Arrange
        // Load task controller from global test object
        TaskController taskController = tms.taskController;
        taskController.setCredentials(TestData.getValidTestCredentials());
        String taskString = "UpdateTaskString";
        boolean foundTask = false;
        
        // A string is added then moved to in-progress
        try {
            taskController.createTask(taskString);
            taskController.updateTask(taskString, 1); // 1 = in-progress list
        } catch (TaskList.TaskExistsException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            fail(e.getMessage());
        }
        
        // Check to see if the task made it into the database
        for(String s : tms.getDBAdapter().getInProgressTasks(TestData.getValidTestCredentials())) {
            if(s.equals(taskString)) {
                foundTask = true;
            }
        }
        
        // Assert task was created in the database
        assertTrue(foundTask);
    }
}
