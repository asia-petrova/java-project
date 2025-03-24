package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfUno implements Deck {
    private List<Card> cards;
    int counter = 1;

    public DeckOfUno(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public Card getFront() {
        Card card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    @Override
    public void putBack(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card is null");
        }

        cards.add(card);
        counter++;
        if (counter == cards.size()) {
            counter = 1;
        }
    }

    @Override
    public void putBack(List<Card> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("card is null");
        }

        this.cards.addAll(cards);
    }

    @Override
    public Card showPlayedCard() {
        return cards.getLast();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public String showPlayedCards() {
        String result = "";
        for (int i = 1; i < counter; i++) {
            result = cards.get(getSize() - 1 - i).getDescription() + " " + result;
        }
        return result;
    }

    @Override
    public Card getAt(final int index) {
        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    @Override
    public List<Card> getCards(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Cannot get negative number of cards!");
        }
        List<Card> cardsToReturn;
        if (cards.size() > count) {
            cardsToReturn = new ArrayList<>(count);
            for (int i = 0; i < count; ++i) {
                cardsToReturn.add(cards.get(i));
            }
        } else {
            cardsToReturn = new ArrayList<Card>(cards);
        }

        cards.removeAll(cardsToReturn);
        return cardsToReturn;
    }

    @Override
    public void putHandOfPlayer(Deck hand) {
        if (hand == null) {
            throw new IllegalArgumentException("Cannot put null hand!");
        }

        for (int i = 0; i < hand.getSize(); ++i) {
            cards.add(hand.getFront());
        }
    }

    @Override
    public boolean match(Card card, Color color, int incrementCount) {
        if (card == null || color == null) {
            throw new IllegalArgumentException("Cannot match null color or card!");
        }

        for (Card c : cards) {
            if (c.canPlay(card, color, incrementCount)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean emptyDeck() {
        return cards.isEmpty();
    }

    @Override
    public String showAllCards() {
        if (cards.isEmpty()) {
            return "No hand!";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cards.size(); ++i) {
            result.append(i).append(":").append(cards.get(i).getDescription()).append(" ");
        }
        return result.toString();
    }
}
