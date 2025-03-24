package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneParameterInputTest {

    @Test
    public void testCheckStringPlayTrue() {
        assertTrue(new OneParameterInput("card-id=<card-id>").checkString(), "Card id as given string!");
    }

    @Test
    public void testCheckStringSummaryTrue() {
        assertTrue(new OneParameterInput("game-id=<card-id>").checkString(), "Game id as given string!");
    }

    @Test
    public void testCheckStringSummaryFalse() {
        assertFalse(new OneParameterInput("-game-id=<card-id>").checkString(), "Game id not in right format!");
    }

    @Test
    public void testCheckStringPlayFalse() {
        assertFalse(new OneParameterInput("--card-id<card-id>").checkString(), "Card id as given string!");
    }
}
