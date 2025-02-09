package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneParameterCommandTest {

    @Test
    public void testCheckStringPlayTrue() {
        assertTrue(new OneParameterCommand("--card-id=<card-id>").checkString(), "Card id as given string!");
    }

    @Test
    public void testCheckStringSummaryTrue() {
        assertTrue(new OneParameterCommand("--game-id=<card-id>").checkString(), "Game id as given string!");
    }

    @Test
    public void testCheckStringSummaryFalse() {
        assertFalse(new OneParameterCommand("-game-id=<card-id>").checkString(), "Game id not in right format!");
    }

    @Test
    public void testCheckStringPlayFalse() {
        assertFalse(new OneParameterCommand("--card-id<card-id>").checkString(), "Card id as given string!");
    }
}
