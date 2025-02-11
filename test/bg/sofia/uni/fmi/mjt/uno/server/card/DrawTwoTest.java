package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DrawTwoTest {
    private Card card;
    private Card on;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new DrawTwo(Color.YELLOW);
        on = mock();
        game = mock(Game.class);
    }

    @Test
    public void testCanPlayDrawTwoDifferentColorTrue() {
        assertTrue(card.canPlay(new DrawTwo(Color.BLUE), Color.BLUE), "Draw 2 should be stacked!");
    }

    @Test
    public void testCanPlayDifferentColorFalse() {
        assertFalse(card.canPlay(on, Color.BLUE), "Draw 2 cannot be played on different color of different card!");
    }

    @Test
    public void testCanPlaySameColorTrue() {
        assertTrue(card.canPlay(on, Color.YELLOW), "Draw 2 cannot be played on different color of different card!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("+2 yellow", card.getDescription(), "The description should be: +2 yellow");
    }

    @Test
    public void testPlay() {
        card.play().accept(game);

        verify(game, times(1)).setCurrentColor(Color.YELLOW);
        verify(game, times(1)).putInDeck(card);
        verify(game, times(1)).increment(2);
    }

    @Test
    public void testPlayThrows() {
        assertThrows(NullPointerException.class, () -> card.play().accept(null),
            "Function should trow with null game");
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
}
