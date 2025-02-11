package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;

import java.io.IOException;
import java.util.List;

public interface Player {
    Card play(int id, Card on, Color color) throws CanNotPlayThisCardException;

    void draw(Card card);

    void acceptFate(List<Card> cards);

    String showHand();

    String getUserName();

    boolean winGame();

    void setDisplayName(String displayName);

    String getDisplayName();

    void setHand(Deck deck);

    void joinGame(String id);

    boolean inGame();

    String getCurrentGame();

    List<Card> leaveGame() throws NotInGameException;

    void sendMessage(String message) throws IOException;

    void setCreatedGame(String id);

    String getCreatedGame();

    int getDeckSize();

    boolean canDraw(Card topCard, Color color);
}
