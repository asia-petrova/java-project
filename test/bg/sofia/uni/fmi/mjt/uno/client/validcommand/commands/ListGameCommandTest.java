package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListGameCommandTest {
    @Test
    public void testListGameCommandTrue() {
        assertTrue(new ListGameCommand("--status=all").checkString(),
            "Should be correct for list game command!");
    }

    @Test
    public void testListGameCommandFalse() {
        assertFalse(new ListGameCommand("--status=djdj").checkString(),
            "Should be correct for list game command!");
    }
}
