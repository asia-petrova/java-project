package bg.sofia.uni.fmi.mjt.uno.server.deck;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfUno implements Deck {
    private List<Card> cards;
    int counter = 0;

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
        cards.add(card);
        counter++;
        if (counter == cards.size()) {
            counter = 0;
        }
    }

    @Override
    public void putBack(List<Card> cards) {
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
    public String showCards() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            result.append(cards.getLast().toString()).append(" ");
        }
        return result.reverse().toString();
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
        for (int i = 0; i < hand.getSize(); ++i) {
            cards.add(hand.getFront());
        }
    }
}
