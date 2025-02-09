package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JoinCommandTest {
    @Test
    public void testJoinCommandTwoParamTrue() {
        assertTrue(new JoinCommand("--game-id=all", "--display-name=jdjdj").checkString(),
            "Should be correct for list game command!");
    }
    @Test
    public void testJoinCommandOneParamTrue() {
        assertTrue(new JoinCommand("--game-id=all", null).checkString(),
            "Should be correct for list game command!");
    }

    @Test
    public void testJoinCommandTwoParamFalse() {
        assertFalse(new JoinCommand("--game-id=all", "--display-namejdjdj").checkString(),
            "Should be correct for list game command!");
    }
    @Test
    public void testJoinCommandOneParamFalse() {
        assertFalse(new JoinCommand("--gameid=all", null).checkString(),
            "Should be correct for list game command!");
    }
}
