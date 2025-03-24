package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class ChooseColor implements Card {
    @Override
    public String getDescription() {
        return "<Choose-color>";
    }

    @Override
    public boolean canPlay(Card card, Color currentColor, int incrementCount) {
        if (card == null || currentColor == null) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        return incrementCount == 1;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            if (game == null) {
                throw new NullPointerException("Game is null!");
            }
            game.putInDeck(this);
        };
    }

}
