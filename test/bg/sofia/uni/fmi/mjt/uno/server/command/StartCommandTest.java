package bg.sofia.uni.fmi.mjt.uno.server.command;

import bg.sofia.uni.fmi.mjt.uno.server.exception.UserNotLoggedException;
import bg.sofia.uni.fmi.mjt.uno.server.manager.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartCommandTest {
    private Command command;
    private Manager manager;
    private SelectionKey key;

    @BeforeEach
    void setUp() {
        command = new StartCommand();
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

}
