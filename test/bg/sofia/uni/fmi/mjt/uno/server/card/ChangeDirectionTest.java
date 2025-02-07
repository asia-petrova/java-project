package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChangeDirectionTest {
    private Card card;
    private Card on;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new ChangeDirection(Color.YELLOW);
        on = mock();
        game = mock(Game.class);
    }

    @Test
    public void testCanPlaySameCardTrue() {
        assertTrue(card.canPlay(new ChangeDirection(Color.RED), Color.RED), "On same card can be put same!");
    }

    @Test
    public void testCanPlaySameColorTrue() {
        assertTrue(card.canPlay(on, Color.YELLOW), "On same color can be put same!");
    }

    @Test
    public void testCanPlayDifferentColorTFalse() {
        assertFalse(card.canPlay(on, Color.RED), "On different color cannot be be put change direction!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("Change Direction yellow", card.getDescription());
    }

    @Test
    public void testPlay() {
        card.play().accept(game);

        verify(game, times(1)).setCurrentColor(Color.YELLOW);
        verify(game, times(1)).putInDeck(card);
        verify(game, times(1)).changeDirection();
    }
}
