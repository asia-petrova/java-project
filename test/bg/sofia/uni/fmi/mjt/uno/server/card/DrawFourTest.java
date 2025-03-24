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

public class DrawFourTest {
    private Card card;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new DrawFour();
        game = mock(Game.class);
    }

    @Test
    public void testPlay() {
        card.play().accept(game);

        verify(game, times(1)).putInDeck(card);
        verify(game, times(1)).increment(4);
    }

    @Test
    public void testCanPlayThrowFirsArgument() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(null, Color.YELLOW, 1),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testCanPlayThrowSecondArgument() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(card, null, 1),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testCanPlayThrowNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->card.canPlay(null, null, 1),
            "canPlay() should throw with null argument!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("<+4>", card.getDescription(),
            "Description of the card should be: +4!");
    }

    @Test
    public void testPlayThrows() {
        assertThrows(NullPointerException.class, () -> card.play().accept(null),
            "Function should trow with null game");
    }

    @Test
    public void testCanPlay() {
        assertTrue(card.canPlay(card, Color.YELLOW, 1),
            "With arguments different from null should always return true!");
    }
}
