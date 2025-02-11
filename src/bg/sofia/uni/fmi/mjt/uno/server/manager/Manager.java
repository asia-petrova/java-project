package bg.sofia.uni.fmi.mjt.uno.server.manager;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Manager {

    void addGame(String gameId, Player creator, int countOfPlayers) throws GameAlreadyExistsException;

    void registerUser(String username, int password) throws UserAlreadyExistsException;
    // void executeGame(Game game);

    Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException;

    String getWithStatus(Status status);

    void joinGame(Player player, String gameId, String displayName)
        throws GameDoesNotExistsException, GameAlreadyFullException;

    Game startGame(Player player) throws GameDoesNotExistsException, IOException;

    String showLastCard(String game) throws GameDoesNotExistsException;

    String showPlayedCards(Player player) throws GameDoesNotExistsException;

    Game getGame(Player player) throws GameDoesNotExistsException;

    String summary(String id) throws GameDoesNotExistsException;

    public void saveInFile() throws ProblemWithFileException;
}
