package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeckOfUnoTest {
    private Deck deck;
    private Card mockCard1, mockCard2, mockCard3;

    @BeforeEach
    void setUp() {
        mockCard1 = mock(Card.class);
        mockCard2 = mock(Card.class);
        mockCard3 = mock(Card.class);

        when(mockCard1.toString()).thenReturn("mockCard1");
        when(mockCard2.toString()).thenReturn("mockCard2");
        when(mockCard3.toString()).thenReturn("mockCard3");

        List<Card> initialCards = new ArrayList<>(List.of(mockCard1, mockCard2, mockCard3));
        deck = new DeckOfUno(initialCards);
    }

    @Test
    void testGetFrontReturnsCard() {
        assertEquals(mockCard1, deck.getFront(), "It should return the right card");
    }

    @Test
    void testGetFrontRemovesTheCard() {
        deck.getFront();
        assertEquals(2, deck.getSize(), "getFront() should remove card from the deck");
    }

    @Test
    void testPutBack() {
        Card newCard = mock(Card.class);
        deck.putBack(newCard);
        assertEquals(4, deck.getSize(), "putBack() should return 4 cards when we add new card!");
    }

    @Test
    void testPutBackThrows() {
        assertThrows(IllegalArgumentException.class, () -> deck.putBack((Card) null),
            "putBack(null) should throw IllegalArgumentException");
    }

    @Test
    void testPutBackMultiple() {
        List<Card> newCards = List.of(mock(Card.class), mock(Card.class));
        deck.putBack(newCards);
        assertEquals(5, deck.getSize(), "putBack() should return 5 cards when we add 2 cards!");
    }

    @Test
    void testPutBackMultipleThrows() {
        assertThrows(IllegalArgumentException.class, () -> deck.putBack((List<Card>) null),
            "putBack(null) should throw IllegalArgumentException");
    }

    @Test
    void testShowPlayedCard() {
        assertEquals(mockCard3, deck.showPlayedCard(), "showPlayedCard() should return the right card");
    }

    @Test
    void testGetAtReturnsCard() {
        assertEquals(mockCard2, deck.getAt(1), "getAt() should return the right card");
    }

    @Test
    void testGetAtDecreaseSize() {
        deck.getAt(1);
        assertEquals(2, deck.getSize(), "getAt() should change size");
    }

    @Test
    void testGetSize() {
        assertEquals(3, deck.getSize(), "getSize() should return 3");
    }

    @Test
    void testGetCardsReturnsListOf2Cards() {
        List<Card> takenCards = deck.getCards(2);
        assertEquals(2, takenCards.size(), "getCards() should return 2 cards");
    }

    @Test
    void testGetCardsReturnsListOf2CardsRest() {
        deck.getCards(2);
        assertEquals(1, deck.getSize(), "getCards(2) should leave the deck with 1 card!");
    }

    @Test
    void testGetCardsReturnsAllCards() {
        deck.getCards(3);
        assertEquals(0, deck.getSize(), "getCards(3) should leave the deck with 0 card!");
    }

    @Test
    void testGetCardsThrows() {
         assertThrows(IllegalArgumentException.class, ()->deck.getCards(-1),
             "getCards(-1) should throw IllegalArgumentException");
    }

    @Test
    void testPutHandOfPlayer() {
        Deck mockHand = mock();
        when(mockHand.getSize()).thenReturn(2);
        when(mockHand.getFront()).thenReturn(mockCard1, mockCard2);

        deck.putHandOfPlayer(mockHand);

        assertEquals(5, deck.getSize(), "After we put the get other deck the size should increase!");
        verify(mockHand, times(2)).getFront();
    }

    @Test
    void testPutHandOfPlayerNullThrows() {
        assertThrows(IllegalArgumentException.class, ()-> deck.putHandOfPlayer(null),
            "putHandOfPlayer(null) should throw IllegalArgumentException");
    }

    @Test
    void testMatchThrowsCardNull() {
        assertThrows(IllegalArgumentException.class, ()->deck.match(null, Color.RED, 1),
        "match should throw ith null argument!");
    }

    @Test
    void testMatchThrowsColorNull() {
        assertThrows(IllegalArgumentException.class, ()->deck.match(mockCard1, null, 1),
            "match should throw ith null argument!");
    }

    @Test
    void testMatchThrowsBothNull() {
        assertThrows(IllegalArgumentException.class, ()->deck.match(null, null, 1),
            "match should throw ith null argument!");
    }

    @Test
    void testMatchTrue() {
        when(mockCard2.canPlay(mockCard3, Color.RED, 1)).thenReturn(true);
        when(mockCard1.canPlay(mockCard3, Color.RED, 1)).thenReturn(false);

        Deck deck1 = new DeckOfUno(List.of(mockCard1, mockCard2));
        assertTrue(deck1.match(mockCard3, Color.RED, 1), "match should return true!");

    }

    @Test
    void testMatchFalse() {
        when(mockCard2.canPlay(mockCard3, Color.RED, 1)).thenReturn(false);
        when(mockCard1.canPlay(mockCard3, Color.RED, 1)).thenReturn(false);

        Deck deck1 = new DeckOfUno(List.of(mockCard1, mockCard2));
        assertFalse(deck1.match(mockCard3, Color.RED, 1), "match should return false!");
    }

}
