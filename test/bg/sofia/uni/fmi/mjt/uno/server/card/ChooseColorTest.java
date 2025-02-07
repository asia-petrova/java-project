package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
