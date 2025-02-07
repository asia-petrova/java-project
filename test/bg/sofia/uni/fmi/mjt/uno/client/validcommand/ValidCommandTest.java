package bg.sofia.uni.fmi.mjt.uno.client.validcommand;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidCommandTest {

    @Test
    public void testGetsCorrectCommand() {
        try {
            ValidCommand actual = ValidCommand.getCommand("list-games");
            assertEquals(ValidCommand.LIST_GAMES, actual, "It is supposed to create the right enum!");
        }
        catch (NoSuchCommand e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetsInvalidCommandThrowsException() {
        assertThrows(NoSuchCommand.class, () -> {ValidCommand.getCommand("ala bala ");});
    }

    @Test
    public void testJoinCommandThrowsExceptionMoreParameters() {
        assertThrows(NoSuchCommand.class, () -> {ValidCommand.getCommand("join ala bala portocala");});
    }
}
