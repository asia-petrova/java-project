package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class OrdinaryCard implements Card {
    private Color color;
    private int number;
    private static final String SEPARATOR = "_";

    public OrdinaryCard(Color color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public String getDescription() {
        return "<" + number + SEPARATOR + color.toString().toLowerCase() + ">";
    }

    @Override
    public boolean canPlay(Card card, Color currentColor, int incrementCount) {
        if (card == null || currentColor == null) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        if (card instanceof OrdinaryCard) {
            int number = Integer.parseInt(card.getDescription()
                .replaceAll("[<|>]", "").split(SEPARATOR)[0]);
            return number == this.number || currentColor == this.color;
        }
        return currentColor == this.color && incrementCount == 1;
    }

    @Override
    public Consumer<Game> play() {
        return game -> {
            if (game == null) {
                throw new NullPointerException("Game is null!");
            }
            game.setCurrentColor(color);
            game.putInDeck(this);
        };
    }
}
