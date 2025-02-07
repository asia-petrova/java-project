package bg.sofia.uni.fmi.mjt.uno.client.validcommand;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.CreateGameCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.JoinCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.ListGameCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.OneParameterCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.PlaySpecialCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.RegisterCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.ZeroParametersCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckCommandTest {
    @Test
    public void testOfCreatesRegisterCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("register --username=<username> --password=<password>");
            assertTrue(cmd instanceof RegisterCommand, "Should create register command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesZeroParametersCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("logout");
            assertTrue(cmd instanceof ZeroParametersCommand, "Should create zero parameters command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesOneParameterCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("play --card-id=<card-id>");
            assertTrue(cmd instanceof OneParameterCommand, "Should create one parameter command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesPlaySpecialCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("play-choose --card-id=<card-id> --color=<red/green/blue/yellow>");
            assertTrue(cmd instanceof PlaySpecialCommand, "Should create play special command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesListGameCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("list-games --status=<started/ended/available/all>");
            assertTrue(cmd instanceof ListGameCommand, "Should create list game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesJoinCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("join --game-id=<game-id> --display-name=<display-name>");
            assertTrue(cmd instanceof JoinCommand, "Should create join command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommand() {
        try {
            CheckCommand cmd = CheckCommand.of("create-game --number-of-players=<number> --game-id=<game-id>");
            assertTrue(cmd instanceof CreateGameCommand, "Should create game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesJoinCommandWithOneParam() {
        try {
            CheckCommand cmd = CheckCommand.of("join --game-id=<game-id>");
            assertTrue(cmd instanceof JoinCommand, "Should create join command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommandWithOneParam() {
        try {
            CheckCommand cmd = CheckCommand.of("create-game --game-id=<game-id>");
            assertTrue(cmd instanceof CreateGameCommand, "Should create game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommandExceptionThrown() {
        assertThrows(InvalidCountOfParameters.class, () -> CheckCommand.of("create-game"));
    }

    @Test
    public void testOfNormalCommandExceptionThrown() {
        assertThrows(InvalidCountOfParameters.class, () -> CheckCommand.of("register"));
    }
}
