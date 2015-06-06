package tms;

/**
 *
 * @author Chris Liebert
 */
public class Credentials {

    // Credential can not change, only new credentials can be set

    private final String username, password;

    Credentials(String _username, String _password) {
        username = _username;
        password = _password;
    }

    // Get the username
    public String getUsername() {
        return username;
    }

    // Get the password
    public String getPassword() {
        return password;
    }
}
