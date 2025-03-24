package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnoPlayerTest {
    private Player player;
    private Deck mockHand;
    private Card mockCard;
    private Card mockOnCard;
    private SelectionKey key;

    @BeforeEach
    void setUp() {
        mockHand = mock(Deck.class);
        mockCard = mock(Card.class);
        mockOnCard = mock(Card.class);
        key = mock();

        player = new UnoPlayer("Player1");
        player.setHand(mockHand);
    }

    @Test
    void testPlayNullArgumentsThrows() {
        assertThrows(NullPointerException.class, () -> player.play(1,null, null, 1),
            "Cannot play with null card or color!");
    }

    @Test
    void testPlayValidCard() throws CanNotPlayThisCardException {
        int cardIndex = 0;

        when(mockHand.getAt(cardIndex)).thenReturn(mockCard);
        when(mockCard.canPlay(mockOnCard, Color.RED, 1)).thenReturn(true);

        Card playedCard = player.play(cardIndex, mockOnCard, Color.RED, 1);

        assertEquals(mockCard, playedCard, "The card must be the first in the hand!");
        verify(mockHand).getAt(cardIndex);
        verify(mockCard).canPlay(mockOnCard, Color.RED, 1);
        verify(mockHand, never()).putBack(mockCard);
    }

    @Test
    void testPlayInvalidCard() {
        int cardIndex = 0;

        when(mockHand.getAt(cardIndex)).thenReturn(mockCard);
        when(mockCard.canPlay(mockOnCard, Color.BLUE, 1)).thenReturn(false);
        assertThrows(
            CanNotPlayThisCardException.class,
            () -> player.play(cardIndex, mockOnCard, Color.BLUE, 1),
            "It should throw when the card can't be played!"
        );

        verify(mockHand).putBack(mockCard);
    }

    @Test
    void testDrawNullCardThrows() {
        assertThrows(NullPointerException.class, () -> player.draw(null),
            "Cannot draw null card!");
    }

    @Test
    void testDraw() {
        player.draw(mockCard);

        verify(mockHand).putBack(mockCard);
    }
    

    @Test
    void testWinGame() {
        Deck deck = mock(Deck.class);
        when(deck.emptyDeck()).thenReturn(true);
        player.setHand(deck);
        assertTrue(player.winGame(), "With empty hand player should win!");
    }

    @Test
    void testAcceptFateNullArgumentsThrows() {
        assertThrows(IllegalArgumentException.class, ()->player.acceptFate(null),
            "Cannot accept null arguments!");
    }

    @Test
    void testAcceptFate() {
        List<Card> mockCards = List.of(mock(Card.class), mock(Card.class));
        player.acceptFate(mockCards);
        verify(mockHand).putBack(mockCards);
    }

    @Test
    void testEqualsTrue() {
        UnoPlayer samePlayer = new UnoPlayer( "Player1");
        assertEquals(player, samePlayer, "Equals should return false when it is the same player");
    }

    @Test
    void testEqualsDifferentPlayer() {
        UnoPlayer differentPlayer = new UnoPlayer("Player2");
        assertNotEquals(player, differentPlayer, "Equals should return false when it is a different player");
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(player, null, "Equals should return false when it is a null");
    }

    @Test
    void testEqualsDifferentObjet() {
        assertNotEquals(player, new Object(), "Equals should return false when it is a null");
    }



    @Test
    void testSetAndGetDisplayName() {
        player.setDisplayName("NewName");

        assertEquals("NewName", player.getDisplayName(), "The display name should be equal!");
    }
}
