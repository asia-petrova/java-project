package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testPutBackMultiple() {
        List<Card> newCards = List.of(mock(Card.class), mock(Card.class));
        deck.putBack(newCards);
        assertEquals(5, deck.getSize(), "putBack() should return 5 cards when we add 2 cards!");
    }

    @Test
    void testShowPlayedCard() {
        assertEquals(mockCard3, deck.showPlayedCard(), "showPlayedCard() should return the right card");
    }

//  other logic for showing
//    @Test
//    void testShowCards() {
//        when(mockCard1.toString()).thenReturn("Card1");
//        when(mockCard2.toString()).thenReturn("Card2");
//        when(mockCard3.toString()).thenReturn("Card3");
//
//        assertEquals("Card1 Card2 Card3 ", deck.showCards(), "showCards() should return Card1 Card2 Card3");
//    }

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
    void testPutHandOfPlayer() {
        Deck mockHand = mock(Deck.class);
        when(mockHand.getSize()).thenReturn(2);
        when(mockHand.getFront()).thenReturn(mockCard1, mockCard2);

        deck.putHandOfPlayer(mockHand);

        assertEquals(5, deck.getSize(), "After we put the get other deck the size should increase!");
        verify(mockHand, times(2)).getFront();
    }
}
