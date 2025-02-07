package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class ChangeDirection implements Card {
    private Color color;

    public ChangeDirection(Color color) {
        this.color = color;
    }

    @Override
    public String getDescription() {
        return "Change Direction " + color.toString().toLowerCase();
    }

    @Override
    public boolean canPlay(Card card, Color currentColor) {
        return color == currentColor || card instanceof ChangeDirection;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            game.changeDirection();
            game.setCurrentColor(color);
            game.putInDeck(this);
        };
    }

}
