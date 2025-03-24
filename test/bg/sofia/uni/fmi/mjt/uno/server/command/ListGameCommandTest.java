package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListGameCommandTest {
    private Command command;
    private Manager manager;
    private SelectionKey key;

    @BeforeEach
    void setUp() {
        command = new ListGamesCommand("status=all");
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

    //will not work in the future
    @Test
    void testExecute() throws Exception {
        Manager manager = mock(Manager.class);
        SelectionKey key = mock(SelectionKey.class);

        when(manager.getWithStatus(Status.ALL)).thenReturn("something");

        assertEquals("something", command.execute(manager, key), "Expected message something");
        verify(manager, atLeastOnce()).getWithStatus(Status.ALL);
    }

}
