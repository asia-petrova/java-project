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
import static org.mockito.Mockito.when;

public class OrdinaryCardTest {
    private Card card;
    private Game game;

    @BeforeEach
    void setUp() {
        card = new OrdinaryCard(Color.YELLOW, 1);
        game = mock(Game.class);
        when(game.getCurrentColor()).thenReturn(Color.YELLOW);
    }

    @Test
    public void testCanPlaySameColorTrue() {
        Card on = mock();
        assertTrue(card.canPlay(on, Color.YELLOW), "Should return true when color is YELLOW!");
    }

    @Test
    public void testCanPlayDifferentColorFalse() {
        Card on = mock();
        assertFalse(card.canPlay(on, Color.BLUE), "Should return false when color is BLUE!");
    }

    @Test
    public void testCanPlayDifferentColorSameNumberTrue() {
        Card on = new OrdinaryCard(Color.BLUE, 1);
        assertTrue(card.canPlay(on, Color.BLUE),
            "Should return true when the number is the same!");
    }

    @Test
    public void testGetDescription() {
        assertEquals("yellow 1", card.getDescription(), "The description should be: yellow 1");
    }

    @Test
    public void testPlay() {
        card.play().accept(game);

        verify(game, times(1)).setCurrentColor(Color.YELLOW);
        verify(game, times(1)).putInDeck(card);
    }

}
