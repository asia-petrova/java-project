package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Command {
    /**
     * Executes a certain command
     *
     * @param manager is used for the management of all games
     * @param key is used to have communication with the user
     * @return message for the client
     * @throws UserAlreadyExistsException when user try to create new user with the existing username
     * @throws UserDoesNotExistException when user try to log in before creating account
     * @throws WrongPasswordException when user does not give the right password
     * @throws UserNotLoggedException when we try to access game, but
     * we have not joined one or have not logged in
     * @throws IOException when there is problem with the communication
     * @throws GameAlreadyExistsException when we try to create game with id that already exist
     * @throws GameAlreadyFullException when we try to enter game that does not take more players
     * @throws GameDoesNotExistsException when we try to access game id that is not registered
     * @throws NotRightTurnOfPlayerException when a player tries to play card when is not on turn
     * @throws CanNotPlayThisCardException when player tries to put a card on the stack that can not
     * be accepted
     * @throws IllegalArgumentException when arguments are null
     */
    String execute(Manager manager, SelectionKey key) throws UserAlreadyExistsException, UserDoesNotExistException,
        WrongPasswordException, UserNotLoggedException, IOException, GameAlreadyExistsException,
        GameAlreadyFullException, GameDoesNotExistsException, NotRightTurnOfPlayerException,
        CanNotPlayThisCardException;

    /**
     * Factory method that creates Commands
     *
     * @param command users input
     * @return Command that will be executed
     */
    static Command of(String command) {
        String[] parts = command.trim().split("--");
        return switch (parts[0].trim()) {
            case "register" -> new RegisterCommand(parts[1], parts[2]);
            case "login" -> new LogInCommand(parts[1], parts[2]);
            case "logout" -> new LogOutCommand();
            case "list-games" -> new ListGamesCommand(parts[1]);
            case "create-game" ->
                parts.length > 2 ? new CreateGameCommand(parts[1], parts[2]) : new CreateGameCommand(null, parts[1]);
            case "join" -> parts.length > 2 ? new JoinCommand(parts[1], parts[2]) : new JoinCommand(parts[1], null);
            case "start" -> new StartCommand();
            case "show-hand" -> new ShowHandCommand();
            case "show-last-card" -> new ShowLastCardCommand();
            case "accept-effect" -> new AcceptEffectCommand();
            case "play" -> new PlayCommand(parts[1]);
            case "play-choose", "play-plus-four" -> new PlayWildCommand(parts[1], parts[2]);
            case "show-played-cards" -> new ShowPlayedCardsCommand();
            case "leave" -> new LeaveCommand();
            case "spectate" -> new SpectateCommand();
            case "draw" -> new DrawCommand();
            case "summary" -> new SummaryCommand(parts[1]);
            default -> throw new IllegalStateException("No such command: " + parts[0]);
        };
    }
}
