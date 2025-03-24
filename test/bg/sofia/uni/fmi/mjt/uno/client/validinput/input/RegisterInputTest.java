package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterInputTest {
    @Test
    public void testRegisterCommandTrue() {
        assertTrue(new RegisterInput("username=ajajaj", "password= yellow").checkString(),
            "Should be correct for register command!");
    }

    @Test
    public void testRegisterCommandFalse() {
        assertFalse(new RegisterInput("-username=ajajaj", "password= yellow").checkString(),
            "Should be correct for register command!");
    }
}
