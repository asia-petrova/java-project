package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class Skip implements Card {
    private Color color;

    public Skip(Color color) {
        this.color = color;
    }

    @Override
    public String getDescription() {
        return "Skip " + color.toString().toLowerCase();
    }

    @Override
    public boolean canPlay(Card card, Color currentColor) {
        if (card == null || currentColor == null) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        return color == currentColor || card instanceof Skip;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            if (game == null) {
                throw new NullPointerException("Game is null!");
            }
            game.skippedTurn();
            game.setCurrentColor(color);
            game.putInDeck(this);
        };
    }

}
