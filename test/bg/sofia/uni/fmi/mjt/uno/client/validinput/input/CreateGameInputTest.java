package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateGameInputTest {
    @Test
    public void testJCreateGameCommandTwoParamTrue() {
        assertTrue(new CreateGameInput("players=12", "game-id=jdjdj").checkString(),
            "Should be correct for create game command!");
    }
    @Test
    public void testCreateGameCommandOneParamTrue() {
        assertTrue(new CreateGameInput(null, "game-id=jdjdj").checkString(),
            "Should be correct for create game command!");
    }

    @Test
    public void testJCreateGameCommandTwoParamFalse() {
        assertFalse(new CreateGameInput("playersdsdds", "game-id=jdjdj").checkString(),
            "Should be incorrect for create game command!");
    }
    @Test
    public void testJCreateGameCommandGivenNotNumberFalse() {
        assertFalse(new CreateGameInput("players=djdj", "game-id=jdjdj").checkString(),
            "Should be incorrect for create game command if is given not number!");
    }

    @Test
    public void testJCreateGameCommandOnePlayerFalse() {
        assertFalse(new CreateGameInput("players=1", "game-id=jdjdj").checkString(),
            "Should be incorrect for create game command if is given number < 2!");
    }

    @Test
    public void testCreateGameCommandOneParamFalse() {
        assertFalse(new CreateGameInput(null, "gme-id=jdjdj").checkString(),
            "Should be incorrect for create game command!");
    }
}
