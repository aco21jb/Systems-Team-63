package trainStore;

import java.sql.Timestamp;
import static trainStore.util.UniqueUserIDGenerator.generateUniqueUserID;

public class User {

    private String userID;
    private String email;
    private String username;
    private String passwordHash;
    private Timestamp lastLogin;
    private int failedLoginAttempts;
    private boolean accountLocked;
    private String name;
    private int houseNumber;
    private String postcode;

    public User(String userID, String email, String username, String passwordHash, Timestamp lastLogin,
                int failedLoginAttempts, boolean accountLocked, String name, int houseNumber, String postcode) {
        this.userID = generateUniqueUserID();
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.lastLogin = lastLogin;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountLocked = accountLocked;
        this.name = name;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
    }

}