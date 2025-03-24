package bg.sofia.uni.fmi.mjt.uno.client.validinput;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidInputTest {

    @Test
    public void testGetsCorrectCommand() {
        try {
            ValidInput actual = ValidInput.getCommand("list-games");
            assertEquals(ValidInput.LIST_GAMES, actual, "It is supposed to create the right enum!");
        }
        catch (NoSuchCommand e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetsInvalidCommandThrowsException() {
        assertThrows(NoSuchCommand.class, () -> {
            ValidInput.getCommand("ala bala ");});
    }

    @Test
    public void testJoinCommandThrowsExceptionMoreParameters() {
        assertThrows(NoSuchCommand.class, () -> {
            ValidInput.getCommand("join ala bala portocala");});
    }
}
