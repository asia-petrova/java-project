package bg.sofia.uni.fmi.mjt.uno.server.deck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateDeckTest {

    @Test
    void testCreateDeck() {
        assertTrue(CreateDeck.createDeck().getSize() == 108, "deck must be with 108 cards");
    }
}
