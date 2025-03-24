package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;

import java.util.List;

public interface Deck {
    /**
     * It is used to draw cards, and it removes card from deck
     *
     * @return form the front of the deck
     */
    Card getFront();

    /**
     * @param card puts it in the deck
     * @throws IllegalArgumentException when card is null
     */
    void putBack(Card card);

    /**
     * @param cards puts them in the deck
     */
    void putBack(List<Card> cards);

    /**
     * @return last played card
     */
    Card showPlayedCard();

    void shuffle();

    /**
     * @return String representation of all the played cards in the order they were played
     */
    String showPlayedCards();

    /**
     * @return String representation of all cards inside the deck
     */
    String showAllCards();

    /**
     *
     * @param index position of card to take
     * @return Card at specified index from deck
     */
    Card getAt(final int index);

    int getSize();

    /**
     * Returns cards and removes them
     *
     * @param count how many cards to get from deck
     * @return return count number of cards from deck
     * @throws IllegalArgumentException when count < 0
     */
    List<Card> getCards(int count);

    /**
     * Puts players cards in deck
     *
     * @param hand cards of the player
     * @throws IllegalArgumentException when hand is null
     */
    void putHandOfPlayer(Deck hand);

    /**
     * Tells us if there are cards in the deck that can be played on top of the given card
     *
     * @param card card to play over it
     * @param color color of the previous card
     * @param incrementCount the count of cards to draw
     * @return true if there is card that can be played
     * @throws IllegalArgumentException when arguments are null
     */
    boolean match(Card card, Color color, int incrementCount);

    boolean emptyDeck();
}
