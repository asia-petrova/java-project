package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.util.List;

public interface Deck {
    Card getFront();

    void putBack(Card card);

    void putBack(List<Card> cards);

    Card showPlayedCard();

    void shuffle();

    String showCards();

    Card getAt(final int index);

    int getSize();

    List<Card> getCards(int count);

    void putHandOfPlayer(Deck hand);
}
