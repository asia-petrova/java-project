package bg.sofia.uni.fmi.mjt.uno.server.player;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UnoPlayer implements Player {
    private Deck hand;
    private String userName;
    private String displayName;
    private SelectionKey key;

    List<String> wonGames;
    String currentGame = "";
    String createdGame = "";

    public UnoPlayer(String userName, SelectionKey key) {
        this.userName = userName;
        this.displayName = userName;
        wonGames = new ArrayList<>();
        this.key = key;
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
    public void winGame() {
        wonGames.add(currentGame);
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
        this.displayName = displayName == null ? userName : displayName;
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
    public List<Card> leaveGame() throws NotInGameException {
        currentGame = "";
        return hand.getCards(hand.getSize());
    }

    @Override
    public void sendMessage(String message) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
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

}
