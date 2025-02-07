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
        return true;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            game.increment(draw);
            game.putInDeck(this);
        };
    }

}
