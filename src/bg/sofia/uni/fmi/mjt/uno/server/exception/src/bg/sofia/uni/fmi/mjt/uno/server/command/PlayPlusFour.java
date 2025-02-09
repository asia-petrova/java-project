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

public class PlayPlusFour implements Command {
    private Command command;

    public PlayPlusFour(String id, String color) {
        command = new PlayChoose(id, color);
    }

    @Override
    public void execute(Manager manager, SelectionKey key)
        throws UserAlreadyExistsException, UserDoesNotExistException, WrongPasswordException, UserNotLoggedException,
        IOException, GameAlreadyExistsException, GameAlreadyFullException, GameDoesNotExistsException,
        NotRightTurnOfPlayerException, CanNotPlayThisCardException, NotInGameException {
        command.execute(manager, key);
    }
}
