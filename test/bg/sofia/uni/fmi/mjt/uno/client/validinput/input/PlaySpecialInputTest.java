package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaySpecialInputTest {
    @Test
    public void testPlaySpecialCommandTrue() {
        assertTrue(new PlaySpecialInput("card-id=ajajaj", "color=yellow").checkString(),
            "Should be correct for play special command!");
    }

    @Test
    public void testPlaySpecialCommandMoreArgsInColorFalse() {
        assertFalse(new PlaySpecialInput("--card-id=ajajaj", "--color=yellow = ejcnkjdnc =sdcjkn").checkString(),
            "Should be incorrect for play special command!");
    }

    @Test
    public void testPlaySpecialCommandNoSuchColorFalse() {
        assertFalse(new PlaySpecialInput("--card-id=ajajaj", "--color=pink").checkString(),
            "Should be incorrect for play special command!");
    }

    @Test
    public void testPlaySpecialCommandNotRightRegexForColorFalse() {
        assertFalse(new PlaySpecialInput("--card-id=ajajaj", "-color=yellow").checkString(),
            "Should be correct for play special command!");
    }
}
