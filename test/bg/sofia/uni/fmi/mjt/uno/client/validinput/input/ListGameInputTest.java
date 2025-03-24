package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListGameInputTest {
    @Test
    public void testListGameCommandTrue() {
        assertTrue(new ListGameInput("status=all").checkString(),
            "Should be correct for list game command!");
    }

    @Test
    public void testListGameCommandFalse() {
        assertFalse(new ListGameInput("--status=djdj").checkString(),
            "Should be correct for list game command!");
    }
}
