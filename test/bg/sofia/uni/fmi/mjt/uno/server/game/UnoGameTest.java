package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnoGameTest {
    private Deck deck;
    private Player player;
    private Card card;
    private Consumer cardEffect;
    private UnoGame game;

    @BeforeEach
    void setUp() {
        deck = mock(Deck.class);
        player = mock(Player.class);
        card = mock(Card.class);
        cardEffect = mock(Consumer.class);

        when(player.getDisplayName()).thenReturn("Creator");

        game = new UnoGame(1, player, 4);
    }

    @Test
    void testGetFromDeck() {
        game.setDeck(deck);
        game.getFromDeck();
        verify(deck, times(1)).getCards(1);
    }

    @Test
    void testIncrement() {
        game.setDeck(deck);
        game.increment(2);

        game.getFromDeck();
        verify(deck, times(1)).getCards(3);
    }

    @Test
    void testPutPlayerWhenGameNotStartedShouldAddPlayer() throws GameAlreadyFullException {
        game.putPlayer(player);
        assertEquals(1, game.playersInGame(), "There should be one player in game");
    }

    @Test
    void testPutPlayerWhenGameStartedShouldNotAddPlayer() throws GameAlreadyFullException {
        game.setStatus(Status.STARTED);

        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player),
            "When status of the game is STARTED it should throw an exception!");
    }

    @Test
    void testPutPlayerWhenGameEndedShouldNotAddPlayer() throws GameAlreadyFullException {
        game.setStatus(Status.ENDED);

        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player),
            "When status of the game is ENDED it should throw an exception!");
    }

    @Test
    void testPutPlayerShouldNotPutPlayer() throws GameAlreadyFullException {
        Player player1 = mock(Player.class);
        when(player.getDisplayName()).thenReturn("Player");
        game.putPlayer(player1);
        game.putPlayer(player1);
        game.putPlayer(player1);
        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player1),
            "When capacity is full and the creator is not in there you game should not take more!");
    }

    @Test
    void testPutPlayerShouldNotPutPlayerWhenFull() throws GameAlreadyFullException {
        Player player1 = mock(Player.class);
        when(player.getDisplayName()).thenReturn("Player");
        game.putPlayer(player1);
        game.putPlayer(player1);
        game.putPlayer(player1);
        game.putPlayer(player);
        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player1),
            "When capacity is full game should not take more!");
    }

    @Test
    void testLastPlayedCard() {
        game.setDeck(deck);
        when(deck.showPlayedCard()).thenReturn(card);

        Card expected = game.lastPlayedCard();
        assertEquals(expected, card, "Should return the right card!");
        verify(deck, times(1)).showPlayedCard();
    }

    @Test
    void testPutInDeck() {
        game.setDeck(deck);
        game.putInDeck(card);
        verify(deck, times(1)).putBack(card);
    }
}
