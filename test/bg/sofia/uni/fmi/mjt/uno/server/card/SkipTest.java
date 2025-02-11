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

public class SkipTest {
    private Card card;
    private Card on;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new Skip(Color.YELLOW);
        on = mock();
        game = mock(Game.class);
    }

    @Test
    public void testCanPlaySameCardTrue() {
        assertTrue(card.canPlay(new Skip(Color.RED), Color.RED), "On same card can be put same!");
    }

    @Test
    public void testCanPlaySameColorTrue() {
        assertTrue(card.canPlay(on, Color.YELLOW), "On same color can be put same!");
    }

    @Test
    public void testCanPlayDifferentColorTFalse() {
        assertFalse(card.canPlay(on, Color.RED), "On different color cannot be be put skip!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("Skip yellow", card.getDescription(),
            "Description of the card should be: Skip yellow!");
    }

    @Test
    public void testPlay() {
        card.play().accept(game);

        verify(game, times(1)).setCurrentColor(Color.YELLOW);
        verify(game, times(1)).putInDeck(card);
        verify(game, times(1)).skippedTurn();
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
