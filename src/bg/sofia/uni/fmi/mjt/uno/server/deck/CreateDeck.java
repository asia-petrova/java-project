package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.ChangeDirection;
import bg.sofia.uni.fmi.mjt.uno.server.card.ChooseColor;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.card.DrawFour;
import bg.sofia.uni.fmi.mjt.uno.server.card.DrawTwo;
import bg.sofia.uni.fmi.mjt.uno.server.card.OrdinaryCard;
import bg.sofia.uni.fmi.mjt.uno.server.card.Skip;

import java.util.ArrayList;
import java.util.List;

public class CreateDeck {
    private static final int MAX_ORDINARY_COUNT = 18;
    private static final int MAX_ORDINARY_NUMBER = 9;
    private static final int MAX_JOKERS = 4;

    public static Deck createDeck() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(createOrdinary(Color.RED));
        cards.addAll(createOrdinary(Color.YELLOW));
        cards.addAll(createOrdinary(Color.GREEN));
        cards.addAll(createOrdinary(Color.BLUE));
        cards.addAll(createEight());
        cards.addAll(createEight());
        cards.addAll(createJokers());
        return new DeckOfUno(cards);
    }

    private static List<Card> createOrdinary(Color color) {
        List<Card> deck = new ArrayList<>();
        deck.add(new OrdinaryCard(color, 0));
        for (int i = 1; i <= MAX_ORDINARY_COUNT; i++) {
            if (i % MAX_ORDINARY_NUMBER == 0) {
                deck.add(new OrdinaryCard(color, MAX_ORDINARY_NUMBER));
                continue;
            }
            deck.add(new OrdinaryCard(color, i % MAX_ORDINARY_NUMBER));
        }
        return deck;
    }

    private static List<Card> createEight() {
        List<Card> deck = new ArrayList<>();
        for (Color c : Color.values()) {
            deck.add(new Skip(c));
            deck.add(new DrawTwo(c));
            deck.add(new ChangeDirection(c));
        }
        return deck;
    }

    private static List<Card> createJokers() {
        List<Card> deck = new ArrayList<>();
        for (int i = 1; i <= MAX_JOKERS; i++) {
            deck.add(new ChooseColor());
            deck.add(new DrawFour());
        }
        return deck;
    }

}
