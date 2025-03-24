package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class RegisterCommand implements Command {
    private static final String MESSAGE = "Successfully registered!";

    private String username;
    private int password;

    public RegisterCommand(String username, String password) {
        this.username = username.replaceFirst("username\\s*=", "").trim();
        this.password = password.replaceFirst("password\\s*=", "").trim().hashCode();
    }

    @Override
    public String execute(Manager manager, SelectionKey key) throws UserAlreadyExistsException, IOException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }
        manager.registerUser(username, password);
        return MESSAGE;
    }
}
