/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TaskController_TaskListIT {
    
    public TMSGUI tms = null;
    
    public TaskController_TaskListIT() {
        tms = TestData.getTMSTestInstance();
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
        // Clear task lists
        try {
        TaskController taskController = tms.taskController;
        while(taskController.toDoList.getSize() > 0) {
            taskController.deleteTask(taskController.toDoList.getElementAt(0));
        }
        while(taskController.inProgressList.getSize() > 0) {
            taskController.deleteTask(taskController.inProgressList.getElementAt(0));
        }
        while(taskController.doneList.getSize() > 0) {
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
        String taskString = "CreateTaskString";
        TaskController taskController = tms.taskController;
        taskController.setCredentials(TestData.getValidTestCredentials());
        
        // Act
        // Attempt to create the task
        try {
            taskController.createTask(taskString);
        } catch(Exception e) {
            fail(e.getMessage());
        }
        
        // Assert task was created
        assertTrue(taskController.toDoList.containsTask(taskString));
    }

    /**
     * Test of deleteTask method, of class TaskController.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");
        
        String taskString = "DeleteTaskString";
        TaskController taskController = tms.taskController;
        taskController.setCredentials(TestData.getValidTestCredentials());
        
        try {
            taskController.createTask(taskString);
            assertTrue(taskController.toDoList.containsTask(taskString));
            taskController.deleteTask(taskString);
        } catch(Exception e) {
            fail(e.getMessage());
        }
        
        // Assert 
        assertFalse(taskController.toDoList.containsTask(taskString));
    }

    /**
     * Test of updateTask method, of class TaskController.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        
        // Arrange
        String taskString = "UpdateTaskString";
        TaskController taskController = tms.taskController;
        taskController.setCredentials(TestData.getValidTestCredentials());

        // Act/Assert
        try {
            taskController.createTask(taskString);
            assertTrue(taskController.toDoList.containsTask(taskString));
            taskController.updateTask(taskString, 1);
            assertTrue(taskController.inProgressList.containsTask(taskString));
            taskController.updateTask(taskString, 2);
            assertTrue(taskController.doneList.containsTask(taskString));
            // Assert the task is 
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
}
