package tms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Chris Liebert
 */
public class DBAdapter {

    // Configuration values for the database are stored in the DBInfo class
    final private static String dbServer = DBInfo.getDatabaseServer();
    final private static String dbName = DBInfo.getDatabaseName();
    final private static String username = DBInfo.getDatabaseUsername();
    final private static String password = DBInfo.getDatabasePassword();
    // Structures required for MySQL
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    DBAdapter() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFound Exception: " + e.getMessage());
        }
    }

    public boolean authorizeUser(Credentials credentials) throws SQLException {
        try {
            // Connect to MySQL server
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from " + dbName + ".user");
            while (resultSet.next()) {
                // If username and password match the record, return true;
                if (resultSet.getString("username").equals(credentials.getUsername())
                        && resultSet.getString("password").equals(credentials.getPassword())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDBConnection();
        }

        // User was not authorized
        return false;
    }

	// You need to close the resultSet
    private void closeDBConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
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

    // Fetch the to-do tasks from the database
    public String[] getToDoTasks(Credentials credentials) {
        int userId = getUserId(credentials);
        List<String> tasks = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("select * from " + dbName + ".task where user_id = ? and list_id = 0 ;");
            preparedStatement.setString(1, Integer.toString(userId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            closeDBConnection();
        }
        String taskArray[] = new String[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }

    // Fetch the in-progress tasks from the database
    public String[] getInProgressTasks(Credentials credentials) {
        int userId = getUserId(credentials);
        List<String> tasks = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("select * from " + dbName + ".task where user_id = ? and list_id = 1 ;");
            preparedStatement.setString(1, Integer.toString(userId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            closeDBConnection();
        }
        String taskArray[] = new String[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }

    // Fetch the done tasks from the database
    public String[] getDoneTasks(Credentials credentials) {
        int userId = getUserId(credentials);
        List<String> tasks = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("select * from " + dbName + ".task where user_id = ? and list_id = 2 ;");
            preparedStatement.setString(1, Integer.toString(userId));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            closeDBConnection();
        }
        String taskArray[] = new String[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }
    
    // Get the user ID of the database record for user
    public int getUserId(Credentials credentials) {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from " + dbName + ".user ;");
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(credentials.getUsername())) {
                	return Integer.parseInt(resultSet.getString("id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            closeDBConnection();
        }
        return -1;
    }

    // Create a task
    public boolean createTask(String s, Credentials credentials) {
        try {
            int userId = getUserId(credentials);
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("insert into " + dbName + ".task values (default, ?, ?, ?, NULL) ;");
            preparedStatement.setString(1, Integer.toString(userId));
            preparedStatement.setString(2, "0"); // to-do list id is 0
            preparedStatement.setString(3, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        } finally {
            closeDBConnection();
        }
        return true;
    }

    // Delete a task from database
    public boolean deleteTask(String s, Credentials credentials) {
        try {
            int userId = getUserId(credentials);
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("delete from " + dbName + ".task where user_id = ? and task = ? ;");
            preparedStatement.setString(1, Integer.toString(userId));
            preparedStatement.setString(2, s);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        } finally {
            closeDBConnection();
        }
        return true;
    }

    // Change the listId of a task
    public boolean updateTask(String taskString, int listID, Credentials credentials) {
        try {
            int userId = getUserId(credentials);
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("update " + dbName + ".task set list_id = ? where user_id = ? and task = ? ;");
            preparedStatement.setString(1, Integer.toString(listID));
            preparedStatement.setString(2, Integer.toString(userId));
            preparedStatement.setString(3, taskString);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        } finally {
            closeDBConnection();
        }
        return true;
    }

    public String getXHTML(String task, Credentials credentials) {
        String xhtml = null;
        try {
            int userId = getUserId(credentials);
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();           
            preparedStatement = connect.prepareStatement("select html from " + dbName + ".task where user_id = ? and task = ? ;");
            preparedStatement.setString(1, Integer.toString(userId));
            preparedStatement.setString(2, task);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	// decrypt the html
            	xhtml = resultSet.getString("html");
            	if(xhtml == null) {
            		 // Default template
                    xhtml = "<html>\n<head>\n\t<title>" + task + "</title>\n</head>\n<body>\n"
                        + "<h1>" + task + " [to-do]</h1>\n"
                        + "\n</body>\n</html>\n";
            		
            	} else {
            		xhtml = decrypt(xhtml, credentials.getPassword());
            	}
            }
            resultSet = null;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            //return false;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            closeDBConnection();
        }
        return xhtml;
    }

    void updateXHTML(String selectedTask, String text, Credentials credentials) throws Exception {
        try {
            int userId = getUserId(credentials);
            connect = DriverManager.getConnection("jdbc:mysql://" + dbServer + "/" + dbName + "?"
                    + "user=" + username + "&password=" + password);
            statement = connect.createStatement();
            preparedStatement = connect.prepareStatement("update " + dbName + ".task set html = ? where user_id = ? and task = ? ;");
            // Encrypt the text
            preparedStatement.setString(1, encrypt(text, credentials.getPassword()));
            preparedStatement.setString(2, Integer.toString(userId));
            preparedStatement.setString(3, selectedTask);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            closeDBConnection();
        }
    }
    
    // calculate SHA-256 hash of a string
    public static String getPasswordHash(String password) {
    	MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
        
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexStrBuf = new StringBuffer();
        for(int i=0; i<hash.length; i++) {
            String hexStr = Integer.toHexString(hash[i] & 0xff);
            if(hexStr.length() == 1) hexStrBuf.append('0');
            hexStrBuf.append(hexStr);
        }
		return hexStrBuf.toString();
	}
    
    private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
    	return Cipher.getInstance("AES/CTR/PKCS5Padding");
    }

    public static byte[] encrypt(byte[] input, String secret) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException {
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	byte[] keyHash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
    	byte[] keyBytes = Arrays.copyOfRange(keyHash, 0, 16);
    	SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
    	byte[] ivBytes = new byte[16];
    	randomSecureRandom.nextBytes(ivBytes);
    	SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
    	Cipher cipher = getCipher();  
    	IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
    	ByteArrayInputStream bIn = new ByteArrayInputStream(input);  
    	CipherInputStream cIn = new CipherInputStream(bIn, cipher);
    	ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    	// write IV to cipher text first 16 bytes
    	bOut.write(ivBytes);
    	int c;
    	while ((c = cIn.read()) >= 0) {
    		bOut.write(c);
    	}
    	cIn.close();
    	byte[] cipherText = bOut.toByteArray();
    	return cipherText;
    }

    public static byte[] decrypt(byte[] raw, String secret) throws IOException, InvalidKeyException,
    InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	byte[] keyHash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
    	byte[] keyBytes = Arrays.copyOfRange(keyHash, 0, 16);
    	SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
    	ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    	Cipher cipher = getCipher();
    	byte[] ivBytes = Arrays.copyOfRange(raw, 0, 16); // IV
    	byte[] cipherBytes = Arrays.copyOfRange(raw, 16, raw.length );
    	IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    	cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    	bOut = new ByteArrayOutputStream();
    	CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
    	cOut.write(cipherBytes);
    	cOut.close();
    	return bOut.toByteArray();
    }

    public static String encrypt(String message, String secret) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    	if(message == null || message.length() == 0) return null;
    	byte[] encryptedMessage = encrypt(message.getBytes(), secret);
    	// Encode encrypted bytes to base64
    	return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public static String decrypt(String ciphertext, String secret) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    	if(ciphertext == null || ciphertext.length() == 0) return null;
    	// Decode base64 after decryption
    	byte[] b64decoded = Base64.getDecoder().decode(ciphertext);
    	return new String(decrypt(b64decoded, secret));
    }

	// symmetric key encryption/decryption without iv
	public static String xorUTF8String(String input, String secret) throws Exception {
		if (secret == null || secret.length() == 0) {
			throw new IllegalArgumentException("Invalid key");
		}
		if (input == null || input.length() == 0) {
			return input;
		}
		final byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
		final byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
		final byte[] cypherBytes = new byte[inputBytes.length];
		int secretPos = 0;
		for (int i = 0; i < inputBytes.length; i++) {
			cypherBytes[i] = (byte) (inputBytes[i] ^ secretBytes[secretPos]);
			secretPos++;
			if (secretPos >= secretBytes.length) {
				secretPos = 0;
			}
		}
		return new String(cypherBytes, StandardCharsets.UTF_8);
	}
}
