package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptEffectCommandTest {
    private Command command;
    private Manager manager;
    private SelectionKey key;

    @BeforeEach
    void setUp() {
        command = new AcceptEffectCommand();
        manager = mock(Manager.class);
        key = mock(SelectionKey.class);
    }

    @Test
    void testExecuteThrowsIllegalArgumentException1() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {command.execute(null, key);},
            "With null arguments should throw IllegalArgumentException");
    }

    @Test
    void testExecuteThrowsIllegalArgumentException2() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {command.execute(manager, null);},
            "With null arguments should throw IllegalArgumentException");
    }

    @Test
    void testExecuteThrowsIllegalArgumentException3() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {command.execute(null, null);},
            "With null arguments should throw IllegalArgumentException");
    }

    @Test
    void testExecuteThrowsUserNotLoggedException() throws Exception {
        when(key.attachment()).thenReturn(null);
        assertThrows(UserNotLoggedException.class, () -> {command.execute(manager, key);},
            "With user not logged exception should throw UserNotLoggedException");
    }

    @Test
    void testExecuteThrowsGameDoesNotExistException() throws Exception {
        Player player = mock(Player.class);
        when(player.inGame()).thenReturn(false);
        when(key.attachment()).thenReturn(player);
        assertThrows(GameDoesNotExistsException.class, ()->command.execute(manager, key),
            "When game doesn't exist exception should throw GameDoesNotExistsException");
    }

    //this test will not work in future
    @Test
    void testExecute() throws Exception {
        Player player = mock(Player.class);
        when(player.inGame()).thenReturn(true);
        when(key.attachment()).thenReturn(player);
        Game game = mock(Game.class);
        when(game.getTurn()).thenReturn("something");
        when(manager.getGame(player)).thenReturn(game);

        assertEquals("something", command.execute(manager, key), "It is expected something!");
        verify(manager, atLeastOnce()).getGame(player);
        verify(game, atLeastOnce()).acceptEffect(player);
    }


}
