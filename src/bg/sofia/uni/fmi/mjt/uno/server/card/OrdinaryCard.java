package bg.sofia.uni.fmi.mjt.uno.server.card;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;

import java.util.function.Consumer;

public class OrdinaryCard implements Card {
    private Color color;
    //make validation for number
    private int number;

    public OrdinaryCard(Color color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public String getDescription() {
        return color.toString().toLowerCase() + " " + number;
    }

    @Override
    public boolean canPlay(Card card, Color currentColor) {
        if (card == null || currentColor == null) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        if (card instanceof OrdinaryCard) {
            int number = Integer.parseInt(card.getDescription().split(" ")[1]);
            return number == this.number || currentColor == this.color;
        }
        return currentColor == this.color;
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
