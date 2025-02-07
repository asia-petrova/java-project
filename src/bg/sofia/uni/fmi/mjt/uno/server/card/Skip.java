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
        return color == currentColor || card instanceof Skip;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            game.skippedTurn();
            game.setCurrentColor(color);
            game.putInDeck(this);
        };
    }

}
