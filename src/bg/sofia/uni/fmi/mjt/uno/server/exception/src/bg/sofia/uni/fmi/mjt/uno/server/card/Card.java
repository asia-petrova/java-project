package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public interface Card {
    String getDescription();

    //check for null in every class
    boolean canPlay(Card card, Color currentColor);

    Consumer<Game> play();

}
