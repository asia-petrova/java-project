package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;

import java.util.List;

public interface Player {
    /**
     * Play a card from the hand of player
     *
     * @param id of card to be played
     * @param under card that is on top of stack
     * @param color current color of the game
     * @param incrementCount the count of cards that the player has to draw
     * @return Card that is played
     * @throws CanNotPlayThisCardException when card cannot be put on
     * @throws  IllegalArgumentException when on, color are null
     */
    Card play(int id, Card under, Color color, int incrementCount) throws CanNotPlayThisCardException;

    /**
     *
     * @param card to be drawn
     * @throws  IllegalArgumentException when card is null
     */
    void draw(Card card);

    /**
     * Takes given cards inside hand
     *
     * @param cards to be drawn
     * @throws  IllegalArgumentException when cards is null
     */
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

    /**
     *
     * @return the hand when leaves game
     */
    List<Card> leaveGame();

    void setCreatedGame(String id);

    String getCreatedGame();

    int getDeckSize();

    boolean canDraw(Card topCard, Color color, int incrementCount);
}
