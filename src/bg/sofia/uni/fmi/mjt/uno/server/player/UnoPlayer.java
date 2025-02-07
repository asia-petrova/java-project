package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;

import java.util.ArrayList;
import java.util.List;

public class UnoPlayer implements Player {
    private Deck hand;
    private String userName;
    private String displayName;
    List<String> wonGames;
    String currentGame;
    boolean logged;

    public UnoPlayer(String userName) {
        this.userName = userName;
        this.displayName = userName;
        wonGames = new ArrayList<>();
    }

    @Override
    public void setHand(Deck hand) {
        this.hand = hand;
    }

    @Override
    public Card play(int id, Card on, Color color) throws CanNotPlayThisCardException {
        Card cardToPut = hand.getAt(id);
        if (cardToPut.canPlay(on, color)) {
            return cardToPut;
        }
        hand.putBack(cardToPut);
        throw new CanNotPlayThisCardException("This card cannot be played on top of the deck");
    }

    @Override
    public void draw(Card card) {
        hand.putBack(card);
    }

    @Override
    public String showHand() {
        return hand.showCards();
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void winGame(String gameId) {
        wonGames.add(gameId);
    }

    @Override
    public Deck getHand() {
        return hand;
    }

    @Override
    public void acceptFate(List<Card> cards) {
        hand.putBack(cards);
    }

    @Override
    public boolean equals(Object player) {
        if (player == null) {
            return false;
        }
        if (player instanceof UnoPlayer) {
            return userName.equals(((UnoPlayer) player).userName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
