package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.util.List;

public interface Game {
    //void start();

    List<Card> getFromDeck();

    void setDeck(Deck deck) throws IOException;

    void changeDirection();

    void increment(int amount);

    void putPlayer(Player player) throws GameAlreadyFullException;

    String getId();

    void executePlayersCard(int index, Player player)
        throws NotRightTurnOfPlayerException, CanNotPlayThisCardException, IOException, NotInGameException;

    Card lastPlayedCard();

    void skippedTurn();

    void putInDeck(Card card);

    Color getCurrentColor();

    void setCurrentColor(Color color);

    int playersInGame();

    Player getCreator();

    void acceptEffect(Player player) throws NotRightTurnOfPlayerException, IOException;

    String getLastPlayedCards();

    void leaveGame(Player player) throws NotInGameException;
}
