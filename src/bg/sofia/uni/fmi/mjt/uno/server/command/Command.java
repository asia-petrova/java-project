package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Command {
    String REGEX = "\\+s";

    void execute(Manager manager, SelectionKey key) throws UserAlreadyExistsException, UserDoesNotExistException,
        WrongPasswordException, UserNotLoggedException, IOException, GameAlreadyExistsException,
        GameAlreadyFullException, GameDoesNotExistsException, NotRightTurnOfPlayerException,
        CanNotPlayThisCardException, NotInGameException;

    static Command of(String command) {
        String[] parts = command.split(REGEX);
        return switch (parts[0]) {
            case "register" -> new Register(parts[1], parts[2]);
            case "login" -> new LogIn(parts[1], parts[2]);
            case "logout" -> new LogOut();
            case "list-games" -> new ListGames(parts[1]);
            case "create-game" ->
                parts.length > 2 ? new CreateGame(parts[1], parts[2]) : new CreateGame(null, parts[1]);
            case "join" -> parts.length > 2 ? new Join(parts[1], parts[2]) : new Join(parts[1], null);
            case "start" -> new Start();
            case "show-hand" -> new ShowHand();
            case "show-last-hand" -> new ShowLastCard();
            case "accept-effect" -> new AcceptEffect();
            case "play" -> new Play(parts[1]);
            case "play-choose" -> new PlayChoose(parts[1], parts[2]);
            case "play-plus-four" -> new PlayPlusFour(parts[1], parts[2]);
            case "show-played-cards" -> new ShowPlayedCards();
            case "leave" -> new Leave();
            default -> throw new IllegalStateException("Unexpected value: " + parts[0]);
        };
    }
}
