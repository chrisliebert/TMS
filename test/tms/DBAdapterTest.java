package tms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
public class DBAdapterTest {

    final private static Credentials validTestCredentials = TestData.getValidTestCredentials();
    final private static int validTestCredentialsId = TestData.getValidTestCredentialsId();
    final private static String dbServer = DBInfo.getDatabaseServer();
    final private static String dbName = DBInfo.getDatabaseName();
    final private static String username = DBInfo.getDatabaseUsername();
    final private static String password = DBInfo.getDatabasePassword();

    @BeforeClass
    public static void setUpClass() {
        clearTaskTestData(validTestCredentials, validTestCredentialsId, dbServer, dbName, username, password);
    }

    @AfterClass
    public static void tearDownClass() {
        clearTaskTestData(validTestCredentials, validTestCredentialsId, dbServer, dbName, username, password);
    }

    // Clear the database for test user
    public static void clearTaskTestData(Credentials validTestCredentials, int validTestCredentialsId, String dbServer, String dbName, String username, String password) {
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement;
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("delete from " + dbName + ".task where user_id = ? ;");
            preparedStatement.setString(1, Integer.toString(validTestCredentialsId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail(e.getMessage());
        } finally {
            // Close database connection
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                System.err.println("Unable to close database connection: " + e.getMessage());
            }
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of authorizeUser method, of class DBAdapter.
     */
    @Test
    public void testAuthorizeValidUser() {
        System.out.println("authorizeValidUser");

        // Arrange
        DBAdapter dbAdapter = new DBAdapter();
        boolean expResult = true;
        boolean result = false;

        // Act
        try {
            result = dbAdapter.authorizeUser(validTestCredentials);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Assert
        assertEquals(expResult, result);
    }
    
        /**
     * Test of authorizeUser method, of class DBAdapter.
     */
    @Test
    public void testAuthorizeInvalidvalidUser() {
        System.out.println("authorizeInvalidUser");

        // Arrange
        Credentials credentials = new Credentials("test", "invalid_pass");
        DBAdapter dbAdapter = new DBAdapter();
        boolean expResult = false;
        boolean result = true;
        // Act
        try {
            result = dbAdapter.authorizeUser(credentials);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Assert
        assertEquals(expResult, result);
        assertNotSame(credentials, validTestCredentials);
    }

    /**
     * Test of getUserId method, of class DBAdapter.
     */
    @Test
    public void testGetUserId() {
        System.out.println("getUserId");

        DBAdapter dbAdapter = new DBAdapter();
        int expResult = validTestCredentialsId;
        int result = dbAdapter.getUserId(validTestCredentials);
        assertEquals(expResult, result);
    }

    /**
     * Test of createTask method, of class DBAdapter.
     */
    @Test
    public void testCreateTask() {
        System.out.println("createTask");

        // Arrange
        String s = "CreateTaskDBAdapterTestString";
        DBAdapter dbAdapter = new DBAdapter();
        boolean result;
        boolean testStringFound = false;

        // Act;
        result = dbAdapter.createTask(s, validTestCredentials);
        for (String listItem : dbAdapter.getToDoTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                testStringFound = true;
                break;
            }
        }

        // Assert
        assertTrue(result);
        assertTrue(testStringFound);
    }

    /**
     * Test of deleteTask method, of class DBAdapter.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");

        // Arrange
        String s = "DeleteTaskDBAdapterTestString";
        DBAdapter dbAdapter = new DBAdapter();
        boolean createResult, deleteResult;
        boolean testStringFound = false;

        // Act
        createResult = dbAdapter.createTask(s, validTestCredentials);
        deleteResult = dbAdapter.deleteTask(s, validTestCredentials);
        for (String listItem : dbAdapter.getToDoTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                testStringFound = true;
                break;
            }
        }
        for (String listItem : dbAdapter.getInProgressTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                testStringFound = true;
                break;
            }
        }
        for (String listItem : dbAdapter.getDoneTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                testStringFound = true;
                break;
            }
        }

        // Assert
        assertTrue(createResult);
        assertTrue(deleteResult);
        assertFalse(testStringFound);
    }

    /**
     * Test of updateTask method, of class DBAdapter.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        
        // Arrange
        String s = "UpdateTaskDBAdapterTestString";
        int listID = 1; // in-progress
        DBAdapter dbAdapter = new DBAdapter();
        boolean inProgressTestStringFound = false;
        boolean doneTestStringFound = false;
        boolean updateResult1, updateResult2;
        boolean createResult;
        
        // Act
        createResult = dbAdapter.createTask(s, validTestCredentials);
        updateResult1 = dbAdapter.updateTask(s, listID, validTestCredentials);
        
        
        // Assert
        for (String listItem : dbAdapter.getInProgressTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                inProgressTestStringFound = true;
                break;
            }
        }
        
        listID = 2; // done
        updateResult2 = dbAdapter.updateTask(s, listID, validTestCredentials);
        
        // Assert
        for (String listItem : dbAdapter.getDoneTasks(validTestCredentials)) {
            if (listItem.equals(s)) {
                doneTestStringFound = true;
                break;
            }
        }
        
        assertTrue(createResult);
        assertTrue(updateResult1);
        assertTrue(updateResult2);
        assertTrue(inProgressTestStringFound);
        assertTrue(doneTestStringFound);
    }
}
