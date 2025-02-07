package bg.sofia.uni.fmi.mjt.uno.server.users;

import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileUsersException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UsersTest {
    private Users users;
    private SelectionKey key;

    @BeforeEach
    void setUp() {
        users = new Users();
        key = mock();
    }

    @Test
    void testRegisterUserSuccessfully() throws UserAlreadyExistsException {
        users.registerUser("testUser", 123);
        assertDoesNotThrow(() -> users.login("testUser", 123, key),
            "registerUser() should not fail!");
    }

    @Test
    void testRegisterUserAlreadyExists() throws UserAlreadyExistsException {
        users.registerUser("testUser", 123);
        assertThrows(UserAlreadyExistsException.class, () -> users.registerUser("testUser", 1234),
            "registerUser() should throw when try to register already existing user!"
            );
    }

    @Test
    void testLoginSuccessfully() throws UserAlreadyExistsException, UserDoesNotExistException, WrongPasswordException {
        users.registerUser("testUser", 123);
        assertNotNull(users.login("testUser", 123, key),
            "login() with existing user should return object!");
    }

    @Test
    void testLoginWithWrongPassword() throws UserAlreadyExistsException {
        users.registerUser("testUser", 123);
        assertThrows(WrongPasswordException.class, () -> users.login("testUser", 1234, key),
            "login() with wrong password user should throw!");
    }

    @Test
    void testLoginWithNonExistentUser() {
        assertThrows(UserDoesNotExistException.class, () -> users.login("nonExistent", 123, key),
            "login() with non-existent user should throw!");
    }

    @Test
    void testSaveUsersInFile() throws IOException, ProblemWithFileUsersException {
        String fileName = "users.json";
        File file = new File(fileName);

        users.saveUsersInFile();

        assertTrue(Files.exists(Paths.get(fileName)), "File should be created!");

        file.delete();
    }

}
