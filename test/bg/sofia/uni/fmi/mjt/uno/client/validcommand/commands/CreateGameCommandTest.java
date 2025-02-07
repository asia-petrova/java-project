package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateGameCommandTest {
    @Test
    public void testJCreateGameCommandTwoParamTrue() {
        assertTrue(new CreateGameCommand("--players=dsdds", "--game-id=jdjdj").checkString(),
            "Should be correct for list game command!");
    }
    @Test
    public void testCreateGameCommandOneParamTrue() {
        assertTrue(new CreateGameCommand(null, "--game-id=jdjdj").checkString(),
            "Should be correct for list game command!");
    }

    @Test
    public void testJCreateGameCommandTwoParamFalse() {
        assertFalse(new CreateGameCommand("--playersdsdds", "--game-id=jdjdj").checkString(),
            "Should be correct for list game command!");
    }
    @Test
    public void testCreateGameCommandOneParamFalse() {
        assertFalse(new CreateGameCommand(null, "--gme-id=jdjdj").checkString(),
            "Should be correct for list game command!");
    }
}
