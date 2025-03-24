package bg.sofia.uni.fmi.mjt.uno.client.validinput;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.CreateGameInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.JoinInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.ListGameInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.OneParameterInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.PlaySpecialInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.RegisterInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.ZeroParametersInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckInputTest {
    @Test
    public void testOfCreatesRegisterCommand() {
        try {
            CheckInput cmd = CheckInput.of("register --username=<username> --password=<password>");
            assertTrue(cmd instanceof RegisterInput, "Should create register command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesZeroParametersCommand() {
        try {
            CheckInput cmd = CheckInput.of("logout");
            assertTrue(cmd instanceof ZeroParametersInput, "Should create zero parameters command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesOneParameterCommand() {
        try {
            CheckInput cmd = CheckInput.of("play --card-id=<card-id>");
            assertTrue(cmd instanceof OneParameterInput, "Should create one parameter command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesPlaySpecialCommand() {
        try {
            CheckInput cmd = CheckInput.of("play-choose --card-id=<card-id> --color=<red/green/blue/yellow>");
            assertTrue(cmd instanceof PlaySpecialInput, "Should create play special command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesListGameCommand() {
        try {
            CheckInput cmd = CheckInput.of("list-games --status=<started/ended/available/all>");
            assertTrue(cmd instanceof ListGameInput, "Should create list game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesJoinCommand() {
        try {
            CheckInput cmd = CheckInput.of("join --game-id=<game-id> --display-name=<display-name>");
            assertTrue(cmd instanceof JoinInput, "Should create join command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommand() {
        try {
            CheckInput cmd = CheckInput.of("create-game --number-of-players=<number> --game-id=<game-id>");
            assertTrue(cmd instanceof CreateGameInput, "Should create game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreatesJoinCommandWithOneParam() {
        try {
            CheckInput cmd = CheckInput.of("join --game-id=<game-id>");
            assertTrue(cmd instanceof JoinInput, "Should create join command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommandWithOneParam() {
        try {
            CheckInput cmd = CheckInput.of("create-game --game-id=<game-id>");
            assertTrue(cmd instanceof CreateGameInput, "Should create game command");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOfCreateGameCommandExceptionThrown() {
        assertThrows(InvalidCountOfParameters.class, () -> CheckInput.of("create-game"));
    }

    @Test
    public void testOfNormalCommandExceptionThrown() {
        assertThrows(InvalidCountOfParameters.class, () -> CheckInput.of("register"));
    }
}
