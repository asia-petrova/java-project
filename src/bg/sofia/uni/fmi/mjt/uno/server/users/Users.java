package bg.sofia.uni.fmi.mjt.uno.server.users;

import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import bg.sofia.uni.fmi.mjt.uno.server.player.UnoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private Map<String, Integer> users;
    private static final String FILE_NAME = "users.json";

    public Users() {
        try (FileReader file = new FileReader(FILE_NAME)) {
            users = new Gson().fromJson(file, new TypeToken<Map<String, Integer>>() {
            }.getType());
        } catch (IOException e) {
            users = new HashMap<>();
        }
    }

    public void registerUser(String username, int password) throws UserAlreadyExistsException {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("Username: " + username + " is already registered.");
        }
        users.put(username, password);
    }

    public Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException {
        if (!users.containsKey(username)) {
            throw new UserDoesNotExistException("The given username: " + username + " does not exist.");
        }
        if (password != users.get(username)) {
            throw new WrongPasswordException("Wrong password.");
        }
        return new UnoPlayer(username, key);
    }

    public void saveUsersInFile() throws ProblemWithFileException {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            Gson json = new Gson();
            file.write(json.toJson(users));
        } catch (IOException e) {
            throw new ProblemWithFileException("USERS NOT SAVED!\n" + e.getMessage());
        }
    }
}

