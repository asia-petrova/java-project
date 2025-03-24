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

    /**
     * Crates available game
     *
     * @param gameId the id of the game that should be unique
     * @param creator the user who created the game
     * @param countOfPlayers haw many players will be inside the game
     * @throws GameAlreadyExistsException when it is given id that already exist
     * @throws  IllegalArgumentException when gameId, creator are null and countOfPlayers < 2
     */
    void addGame(String gameId, Player creator, int countOfPlayers) throws GameAlreadyExistsException;

    /**
     *
     * @param username
     * @param password
     * @throws UserAlreadyExistsException when user that tries to register is already in database
     * @throws  IllegalArgumentException when username is null
     */
    void registerUser(String username, int password) throws UserAlreadyExistsException;

    /**
     * When a user is logged it creates a player that represents it
     * @param username
     * @param password
     * @param key use it for connection and sending data
     * @return new Player
     * @throws UserDoesNotExistException when user tries to log with username that is not registered
     * @throws WrongPasswordException when user gives wrong password
     * @throws IllegalArgumentException when username or key are null
     * @throws UserAlreadyExistsException when logged user try to log in again before logout
     */
    Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException,
        UserAlreadyExistsException;

    /**
     * Returns all games with given status represented as String
     * @param status that is given
     * @return games with that status
     * @throws  IllegalArgumentException when status is null
     */
    String getWithStatus(Status status);

    /**
     * Joins player in available game
     *
     * @param player to be joined
     * @param gameId the game where to join
     * @param displayName the name to be seen in game
     * @throws GameDoesNotExistsException when we try to join player inside non-available game
     * @throws GameAlreadyFullException when we try to join player in game that is already full
     * @throws  IllegalArgumentException when player, gameId, displayName are null
     */
    void joinGame(Player player, String gameId, String displayName)
        throws GameDoesNotExistsException, GameAlreadyFullException;

    /**
     *
     * @param player that stats the game
     * @return Game that is started
     * @throws GameDoesNotExistsException when game is not inside available or does
     * not have enough players to start
     * @throws IOException when there is problem with sending data
     * @throws  IllegalArgumentException when player is null
     */
    Game startGame(Player player) throws GameDoesNotExistsException, IOException;

    /**
     *
     * @param game from witch, we want last played card
     * @return String representation of last played card
     * @throws GameDoesNotExistsException when given game is not available
     * @throws  IllegalArgumentException when game is null
     */
    String showLastCard(String game) throws GameDoesNotExistsException;

    /**
     *
     * @param player to execute the command
     * @return String representation of last played cards
     * @throws GameDoesNotExistsException when player is not inside game
     * @throws  IllegalArgumentException when player is null
     */
    String showPlayedCards(Player player) throws GameDoesNotExistsException;

    /**
     * Returns the game that player is inside and checks if the game has ended
     * and if so puts it inside history
     *
     * @param player that is inside
     * @return game that player is inside
     * @throws GameDoesNotExistsException when player is inside non-available game or non
     * @throws  IllegalArgumentException when player is null
     */
    Game getGame(Player player) throws GameDoesNotExistsException;

    /**
     *
     * @param id game
     * @return String representation of the summary
     * @throws GameDoesNotExistsException when game is not inside ended
     * @throws  IllegalArgumentException when id is null
     */
    String summary(String id) throws GameDoesNotExistsException;

    /**
     *
     * @throws ProblemWithFileException when cannot be saved
     */
    void saveInFile() throws ProblemWithFileException;

    /**
     *
     * @param player to be logged out
     * @throws UserDoesNotExistException when we try to log out user that does not exist
     * @throws UserAlreadyExistsException when we try to log out user that is not logged
     */
    void logout(Player player) throws UserDoesNotExistException, UserAlreadyExistsException;

    /**
     * Removes player form given available or started game
     * @param player to be removed
     * @param gameId from where
     * @throws GameDoesNotExistsException if there is no such game
     */
    void leaveGame(Player player, String gameId) throws GameDoesNotExistsException;

}
