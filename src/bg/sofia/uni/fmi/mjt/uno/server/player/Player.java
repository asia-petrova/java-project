package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;

import java.util.List;

public interface Player {
    Card play(int id, Card on, Color color) throws CanNotPlayThisCardException;

    void draw(Card card);

    void acceptFate(List<Card> cards);

    String showHand();

    String getUserName();

    void winGame(String gameId);

    Deck getHand();

    void setDisplayName(String displayName);

    String getDisplayName();

    void setHand(Deck deck);

}
