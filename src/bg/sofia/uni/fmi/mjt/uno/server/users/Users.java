package bg.sofia.uni.fmi.mjt.uno.server.users;

import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileUsersException;
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
import java.util.HashMap;
import java.util.Map;

public class Users {
    private Map<String, Integer> users;
    private final String fileName = "users.json";

    public Users() {
        try (FileReader file = new FileReader(fileName)) {
            users = new Gson().fromJson(file, new TypeToken<Map<String, Integer>>() {
            }.getType());
        } catch (IOException e) {
            users = new HashMap<>();
        }
    }

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("Username: " + username + " is already registered.");
        }
        users.put(username, password.hashCode());
    }

    public Player login(String username, String password) throws UserDoesNotExistException, WrongPasswordException {
        if (!users.containsKey(username)) {
            throw new UserDoesNotExistException("The given username: " + username + " does not exist.");
        }
        Integer givenPass = password.hashCode();
        if (!givenPass.equals(users.get(username))) {
            throw new WrongPasswordException("Wrong password.");
        }

        return new UnoPlayer(username);
    }

    void saveUsersInFile() throws ProblemWithFileUsersException {
        try (FileWriter file = new FileWriter(fileName)) {
            Gson json = new Gson();
            file.write(json.toJson(users));
        } catch (IOException e) {
            throw new ProblemWithFileUsersException(e.getMessage());
        }
    }
}

