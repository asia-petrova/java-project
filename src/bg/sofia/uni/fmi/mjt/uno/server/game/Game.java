package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.util.List;

public interface Game {

    /**
     * @return Cards that the player should draw
     */
    List<Card> getFromDeck();

    /**
     * Sets the deck at the begging of the game and gives 7 cards to each
     * player
     *
     * @param deck the deck of the new game
     * @throws IllegalArgumentException when deck is null
     */
    void setDeck(Deck deck);

    void changeDirection();

    /**
     * Increments the amount of cards that will be drawn by future player
     *
     * @param amount how much we increment it +2 or +4 cards
     * @throws IllegalArgumentException when amount < 0
     */
    void increment(int amount);

    /**
     *
     * @param player to be out in game
     * @throws GameAlreadyFullException when there is no space in the game
     * @throws IllegalArgumentException when player is null
     */
    void putPlayer(Player player) throws GameAlreadyFullException;

    String getId();

    /**
     *
     * @param index id of the card to be executed
     * @param player that executes card
     * @throws NotRightTurnOfPlayerException when player who is not on turn try to play a card
     * @throws CanNotPlayThisCardException when player tries to play a card that is not
     * accepted by the game
     * @throws IllegalArgumentException when index < 0 and player is null
     */
    void executePlayersCard(int index, Player player)
        throws NotRightTurnOfPlayerException, CanNotPlayThisCardException;

    Card lastPlayedCard();

    void skippedTurn();

    /**
     *
     * @param card to be put
     * @throws IllegalArgumentException when card is null
     */
    void putInDeck(Card card);

    Color getCurrentColor();


    /**
     *
     * @param color to be set
     * @throws IllegalArgumentException when color is null
     */
    void setCurrentColor(Color color);

    int playersInGame();

    Player getCreator();

    /**
     * Player takes cards from deck or skips turn
     *
     * @param player to take cards
     * @throws NotRightTurnOfPlayerException when player tries to take before someone else
     * @throws IOException when there is problem with sending message
     * @throws IllegalArgumentException when player is null
     */
    void acceptEffect(Player player) throws NotRightTurnOfPlayerException, IOException;

    String getLastPlayedCards();

    /**
     *
     * @param player to leave game
     * @throws IllegalArgumentException when player is null
     */
    void leaveGame(Player player) ;

    /**
     *
     * @param player to play spectate
     * @return String for user
     * @throws CanNotPlayThisCardException when player is still in the game and has
     * not played all of his cards
     * @throws IOException when there is problem with sending data
     * @throws IllegalArgumentException when player is null
     */
    String spectate(Player player) throws CanNotPlayThisCardException, IOException;

    /**
     * Player draws cards from deck
     *
     * @param player player to draw
     * @throws NotRightTurnOfPlayerException when player tries to draw before someone else
     * @throws IOException problem with sending data
     * @throws CanNotPlayThisCardException when player can put a card on the deck but decide
     * to draw cards
     * @throws IllegalArgumentException when player is null
     */
    void draw(Player player) throws NotRightTurnOfPlayerException, IOException, CanNotPlayThisCardException;

    /**
     *
     * @return if the game has already ended
     */
    boolean end();

    /**
     *
     * @return if the game is ready to start
     */
    boolean start();

    GameHistory getHistory();

    /**
     *
     * @param player to search in game
     * @return player if it is found otherwise it returns null
     * @throws IllegalArgumentException when player is null
     */
    Player inGame(Player player);

    String getTurn();

}
