package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class DrawFour implements Card {
    private final int draw = 4;

    @Override
    public String getDescription() {
        return "+4";
    }

    @Override
    public boolean canPlay(Card card, Color currentColor) {
        if (card == null || currentColor == null) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        return true;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            if (game == null) {
                throw new NullPointerException("Game is null!");
            }
            game.increment(draw);
            game.putInDeck(this);
        };
    }

}
