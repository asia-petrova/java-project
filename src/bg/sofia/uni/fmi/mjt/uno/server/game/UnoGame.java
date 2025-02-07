package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.deck.DeckOfUno;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.game.playersturn.PlayersTurn;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnoGame implements Game {
    private static final int FIRST_DRAW = 7;

    private int drawCounter = 1;
    private int countOfPlayers;
    private String id;

    private PlayersTurn turn;
    private Player creator;
    private Deck deck;
    private Color currentColor;
    private List<Player> players;

    public UnoGame(String gameId, Player creator, int countOfPlayers) {
        this.id = gameId;
        this.players = new ArrayList<Player>(countOfPlayers);
        this.creator = creator;
        this.countOfPlayers = countOfPlayers;
        this.turn = new PlayersTurn(countOfPlayers);
    }

    @Override
    public void setDeck(Deck deck) throws IOException {
        this.deck = deck;
        deck.shuffle();
        for (Player player : players) {
            player.setHand(new DeckOfUno(deck.getCards(FIRST_DRAW)));
        }
        sendToAll(messageToAll());
    }

    @Override
    public List<Card> getFromDeck() {
        List<Card> cards = deck.getCards(drawCounter);
        drawCounter = 1;
        return cards;
    }

    @Override
    public void changeDirection() {
        turn.changeDirection();
    }

    @Override
    public void increment(int amount) {
        drawCounter += amount;
    }

    @Override
    public void putPlayer(Player player) throws GameAlreadyFullException {
        if (countOfPlayers - 1 == players.size() && !players.contains(creator) && !player.equals(creator)) {
            throw new GameAlreadyFullException("Creator is not in game and the rest of the positions are taken!");
        }
        if (countOfPlayers == players.size()) {
            throw new GameAlreadyFullException("Game already is full!");
        }
        players.add(player);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Card lastPlayedCard() {
        return deck.showPlayedCard();
    }

    @Override
    public void skippedTurn() {
        turn.next();
    }

    @Override
    public void executePlayersCard(int index, Player player) throws NotRightTurnOfPlayerException,
        CanNotPlayThisCardException, IOException, NotInGameException {
        if (!player.equals(players.get(turn.current()))) {
            throw new NotRightTurnOfPlayerException(
                "Player: " + players.get(turn.current()).getDisplayName() + " is on turn not you!");
        }

        players.get(turn.current()).play(index, this.lastPlayedCard(), this.currentColor).play().accept(this);
        if (players.get(turn.current()).getDeckSize() == 0) {
            leaveGame(player);
            player.winGame();
        }
        turn.next();

        sendToAll(messageToAll());
    }

    @Override
    public void putInDeck(Card card) {
        deck.putBack(card);
    }

    @Override
    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    @Override
    public int playersInGame() {
        return players.size();
    }

    @Override
    public String toString() {
        return this.id + " players: " + players.size();
    }

    @Override
    public Player getCreator() {
        return creator;
    }

    @Override
    public void acceptEffect(Player player) throws NotRightTurnOfPlayerException, IOException {
        if (!player.equals(players.get(turn.current()))) {
            throw new NotRightTurnOfPlayerException(player.getDisplayName() + "is not on turn");
        }
        players.get(turn.current()).acceptFate(getFromDeck());
        turn.next();
        sendToAll(messageToAll());
    }

    @Override
    public String getLastPlayedCards() {
        return deck.showCards();
    }

    @Override
    public void leaveGame(Player player) throws NotInGameException {
        players.remove(player);
        deck.putBack(player.leaveGame());
        turn.decrease();
    }

    private void sendToAll(String message) throws IOException {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    private String messageToAll() {
        return players.get(turn.current()).getDisplayName() + " is on turn\n";
    }
}
