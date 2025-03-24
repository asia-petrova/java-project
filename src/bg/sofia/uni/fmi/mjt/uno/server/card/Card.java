package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public interface Card {
    /**
     * @return the description of a certain card
     */
    String getDescription();

    /**
     * Checks if card can be put on the stack of played cards
     * in the game
     *
     * @param card to be put on the current
     * @param currentColor of the last played card
     * @param incrementCount the count of cards to draw
     * @return true if the card can be put on the stack of cards
     */
    boolean canPlay(Card card, Color currentColor, int incrementCount);

    /**
     * Use lambda function to access the game that the card is played
     * in order to put it in the stack of cards or to be refused
     *
     * @return lambda function of type Consumer that takes as argument game
     */
    Consumer<Game> play();

}
