package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class ShowHandCommand implements Command {
    private static final int BUFFER_SIZE = 1024;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

    @Override
    public String execute(Manager manager, SelectionKey key)
        throws UserAlreadyExistsException, UserDoesNotExistException, WrongPasswordException, UserNotLoggedException,
        IOException, GameAlreadyExistsException, GameAlreadyFullException, GameDoesNotExistsException {
        if (manager == null || key == null) {
            throw new IllegalArgumentException("Arguments cannot be null!");
        }

        Object obj = key.attachment();
        if (obj == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }
        Player player = (Player) obj;
        if (!player.inGame()) {
            throw new GameDoesNotExistsException("Player should be in game to have a hand to show");
        }
        return player.showHand();
    }
}
