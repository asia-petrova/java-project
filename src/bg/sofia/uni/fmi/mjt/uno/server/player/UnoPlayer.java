package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UnoPlayer implements Player {
    private static final int BUFFER_SIZE = 1024;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

    private Deck hand;
    private String userName;
    private String displayName;

    List<String> wonGames;
    String currentGame = "";
    String createdGame = "";

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
    public Card play(int id, Card under, Color color, int incrementCount) throws CanNotPlayThisCardException {
        if (under == null || color == null) {
            throw new NullPointerException("Can not play Card with null color");
        }

        Card cardToPut = hand.getAt(id);
        if (cardToPut.canPlay(under, color, incrementCount)) {
            return cardToPut;
        }
        hand.putBack(cardToPut);
        throw new CanNotPlayThisCardException("This card cannot be played on top of the deck");
    }

    @Override
    public void draw(Card card) {
        if (card == null) {
            throw new NullPointerException("Can not draw null card");
        }
        hand.putBack(card);
    }

    @Override
    public String showHand() {
        return hand.showAllCards();
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public boolean winGame() {
        if (hand.emptyDeck()) {
            wonGames.add(currentGame);
            return true;
        }
        return false;
    }

    @Override
    public void acceptFate(List<Card> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("Can not accept null cards");
        }
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
        this.displayName = displayName.isEmpty() ? userName : displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void joinGame(String id) {
        currentGame = id;
    }

    @Override
    public boolean inGame() {
        return !currentGame.isEmpty();
    }

    @Override
    public String getCurrentGame() {
        return currentGame;
    }

    @Override
    public List<Card> leaveGame() {
        if (hand == null) {
            return new ArrayList<>();
        }
        return hand.getCards(hand.getSize());
    }

    @Override
    public void setCreatedGame(String id) {
        createdGame = id;
    }

    @Override
    public String getCreatedGame() {
        return createdGame;
    }

    @Override
    public int getDeckSize() {
        return hand.getSize();
    }

    @Override
    public boolean canDraw(Card topCard, Color color, int incrementedCount) {
        return !hand.match(topCard, color, incrementedCount);
    }

}
