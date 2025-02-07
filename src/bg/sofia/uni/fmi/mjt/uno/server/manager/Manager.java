package bg.sofia.uni.fmi.mjt.uno.server.manager;

import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Manager {
//    Game findGame(String gameId);

    void addGame(String gameId, Player creator, int countOfPlayers) throws GameAlreadyExistsException;

    void registerUser(String username, int password) throws UserAlreadyExistsException;
    // void executeGame(Game game);

    Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException;

    void leaveGame(Player player) throws NotInGameException, GameDoesNotExistsException;

    String getWithStatus(Status status);

    void joinGame(Player player, String gameId, String displayName)
        throws GameDoesNotExistsException, GameAlreadyFullException;

    Game startGame(Player player) throws GameDoesNotExistsException;

    String showLastCard(String game) throws GameDoesNotExistsException;

    void acceptEffect(Player player) throws GameDoesNotExistsException, IOException,
        NotRightTurnOfPlayerException;

    void playOrdinaryCard(Player player, int index)
        throws GameDoesNotExistsException, NotRightTurnOfPlayerException,
        CanNotPlayThisCardException, IOException;

    void playSpecialCard(Player player, int index, Color color)
        throws GameDoesNotExistsException, NotRightTurnOfPlayerException,
        CanNotPlayThisCardException, IOException;

    String showPlayedCards(Player player) throws GameDoesNotExistsException;
}
