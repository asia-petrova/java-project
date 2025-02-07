package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.util.List;

public interface Game {
    //void start();

    List<Card> getFromDeck();

    void setDeck(Deck deck);

    void changeDirection();

    void setStatus(Status status);

    void increment(int amount);

    Status getStatus();

    void putPlayer(Player player) throws GameAlreadyFullException;

    String getId();

    void executePlayersCard(int index, Player player) throws NotRightTurnOfPlayerException, CanNotPlayThisCardException;

    Card lastPlayedCard();

    void skippedTurn();

    void putInDeck(Card card);

    Color getCurrentColor();

    void setCurrentColor(Color color);

    int playersInGame();
}
