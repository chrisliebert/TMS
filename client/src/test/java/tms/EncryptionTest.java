package tms;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class EncryptionTest {

	@Test
	public void test() throws Exception {
		String message = "Hello, world";
		String secret = "password";
		
		String cyphertext = DBAdapter.xorUTF8String(message, secret);
		assertTrue(secret.length() > 0);
		
		// The text has changed, and is the same length
		assertEquals(message.length(), cyphertext.length());
		assertNotEquals(message, cyphertext);
		
		String decrypted = DBAdapter.xorUTF8String(cyphertext, secret);
		assertEquals(message, decrypted);
	}
	
	@Test
	public void testEncrypt() throws Exception {
		String message = "Hello, world";
		String secret = "password";
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		byte[] ciphertext = DBAdapter.encrypt(messageBytes, secret);
		String ciphertextStr = DBAdapter.encrypt(message,  secret);
		assertTrue(secret.length() > 0);
		
		assertNotEquals(message, ciphertext);
		
		byte[] decrypted = DBAdapter.decrypt(ciphertext, secret);
		String decryptedString = DBAdapter.decrypt(ciphertextStr, secret);
		Assert.assertArrayEquals(decrypted,  messageBytes);
		assertEquals(new String(decrypted, StandardCharsets.UTF_8), message);
		assertEquals(message, decryptedString);
	}
}
