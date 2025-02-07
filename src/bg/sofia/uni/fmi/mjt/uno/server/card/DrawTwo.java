package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class DrawTwo implements Card {
    private Color color;
    private final int draw = 2;

    public DrawTwo(Color color) {
        this.color = color;
    }

    @Override
    public String getDescription() {
        return "+2 " + color.toString().toLowerCase();
    }

    @Override
    public boolean canPlay(Card card, Color currentColor) {
        return color == currentColor || card instanceof DrawTwo;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            game.increment(draw);
            game.setCurrentColor(color);
            game.putInDeck(this);
        };
    }

}
