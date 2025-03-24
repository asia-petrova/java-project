package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JoinInputTest {
    @Test
    public void testJoinCommandTwoParamTrue() {
        assertTrue(new JoinInput("game-id=all", "display-name=jdjdj").checkString(),
            "Should be correct for join command!");
    }
    @Test
    public void testJoinCommandOneParamTrue() {
        assertTrue(new JoinInput("game-id=all", null).checkString(),
            "Should be correct for join command!");
    }

    @Test
    public void testJoinCommandTwoParamFalse() {
        assertFalse(new JoinInput("--game-id=all", "--display-namejdjdj").checkString(),
            "Should be correct for join command!");
    }
    @Test
    public void testJoinCommandOneParamFalse() {
        assertFalse(new JoinInput("--gameid=all", null).checkString(),
            "Should be correct for join command!");
    }
}
