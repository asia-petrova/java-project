package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChooseColorTest {
    private Card card;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new ChooseColor();
        game = mock(Game.class);
    }

    @Test
    public void testPlay() {
        card.play().accept(game);
        verify(game, times(1)).putInDeck(card);
    }

    @Test
    public void testCanPlay() {
        assertTrue(card.canPlay(card, Color.YELLOW),
            "With arguments different from null should always return true!");
    }

    @Test
    public void testCanPlayThrowFirsArgument() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(null, Color.YELLOW),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testCanPlayThrowSecondArgument() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(card, null),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testCanPlayThrowNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(null, null),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("Choose a color", card.getDescription(),
            "Description of the card should be: Choose a color!");
    }

    @Test
    public void testPlayThrows() {
        assertThrows(NullPointerException.class, () -> card.play().accept(null),
            "Function should trow with null game");
    }
}
