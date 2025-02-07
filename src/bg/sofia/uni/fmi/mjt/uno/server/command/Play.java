package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;

import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class Play implements Command {
    private int index;

    public Play(String index) {
        this.index = Integer.parseInt(index.replaceFirst("--card-id=", ""));
    }

    @Override
    public void execute(Manager manager, SelectionKey key)
        throws UserNotLoggedException, IOException, GameDoesNotExistsException,
        NotRightTurnOfPlayerException, CanNotPlayThisCardException {
        Object obj = key.attachment();
        if (obj == null) {
            throw new UserNotLoggedException("User not logged in cannot create game");
        }
        Player player = (Player) obj;
        if (!player.inGame()) {
            throw new GameDoesNotExistsException("Player should be in game to have a hand to show");
        }
        manager.playOrdinaryCard(player, index);
    }
}
