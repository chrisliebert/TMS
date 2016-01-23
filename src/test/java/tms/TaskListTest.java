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
public class TaskListTest {
    
    public TaskListTest() {
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
     * Test of add method, of class TaskList.
     */
    @Test
    public void testAdd() {
        // Test that an empty string can not be added
        System.out.println("add");
                
        // Arrange
        String s = "AddTestString";
        TaskList taskList = new TaskList();

        // Act
        try {
            taskList.addItem(s);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // Assert
        assertTrue(taskList.containsTask(s));
    }
       
    /**
     * Test of add method, of class TaskList.
     */
    @Test
    public void testAddEmptyString() {
        // Test that an empty string can not be added
        System.out.println("add empty string"); 
        
        // Arrange
        String s = "";
        boolean caughtException = false;
        TaskList taskList = new TaskList();

        // Act
        try {
            taskList.addItem(s);
        } catch (TaskList.AddEmptyStringException e) {
            caughtException = true;
        } catch (Exception e) {
            fail("Exception should be caught");
        }
        
        // Assert
        assertFalse(taskList.containsTask(s));
        assertTrue(caughtException);
    }
       
    /**
     * Test of add method, of class TaskList.
     */
    @Test
    public void testAddDuplicateString() {
        // Test that a duplicate string can't be added
        System.out.println("add duplicate string"); 
        
        // Arrange
        String s = "DuplicateStringTest";
        boolean caughtException = false;
        TaskList taskList = new TaskList();

        // Act
        try {
            taskList.addItem(s);
            taskList.addItem(s);
        } catch(TaskList.TaskExistsException e) {
            caughtException = true;
        } catch(Exception e) {
            fail("Unhandled Exception: " + e.getMessage());
        }
        
        // Assert
        assertTrue(taskList.containsTask(s));
        assertTrue(caughtException);
    }
    
    /**
     * Test of containsTask method, of class TaskList.
     */
    @Test
    public void testContainsTask() {
        // Test that containsTask method is correct
        System.out.println("containsTask");
        
        // Arrange
        String s = "ContainsTaskTestString";
        boolean containsTask = false;
        boolean functionResult;
        
        // Act
        TaskList taskList = new TaskList();
        
        try {
            taskList.add(s);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        functionResult = taskList.containsTask(s);
        for( String item : taskList.items) {
            if(item.equals(s)) {
                containsTask = true;
                break;
            }
        }
        
        // Assert
        assertTrue(containsTask);
        assertEquals(containsTask, functionResult);
    }

    /**
     * Test of delete method, of class TaskList.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        // Arrange
        String s = "DeleteTestString";
        TaskList taskList = new TaskList();
        boolean caughtException = false;
        
        // Act
        // Delete the test string
        try {
            taskList.add(s);
            taskList.delete(s);
        } catch(Exception e) {
            fail(e.getMessage());
        }
        
        // Attempt to delete the test string again, this should throw exception
        try {
            taskList.delete(s);
        } catch(TaskList.NoTaskExistsException e) {
            caughtException = true;
        } catch(Exception e) {
            fail(e.getMessage());
        }
        
        // Assert
        assertFalse(taskList.containsTask(s));
        assertTrue(caughtException);
    }
}
