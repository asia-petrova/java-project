package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterCommandTest {
    @Test
    public void testRegisterCommandTrue() {
        assertTrue(new RegisterCommand("--username=ajajaj", "--password= yellow").checkString(),
            "Should be correct for register command!");
    }

    @Test
    public void testRegisterCommandFalse() {
        assertFalse(new RegisterCommand("-username=ajajaj", "--password= yellow").checkString(),
            "Should be correct for register command!");
    }
}
