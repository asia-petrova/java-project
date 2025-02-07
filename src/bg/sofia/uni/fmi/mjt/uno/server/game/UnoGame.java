package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.game.playersturn.PlayersTurn;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnoGame implements Game {

    private int drawCounter = 1;
    private int countOfPlayers;
    private String id;

    private PlayersTurn turn;
    private Player creator;
    private Status status;
    private Deck deck;
    private Color currentColor;
    private List<Player> players;

    public UnoGame(int current, Player creator, int countOfPlayers) {
        this.id = current + "-" + LocalDateTime.now();
        this.players = new ArrayList<Player>(countOfPlayers);
        this.creator = creator;
        this.countOfPlayers = countOfPlayers;
        this.turn = new PlayersTurn(countOfPlayers);
        this.status = Status.AVAILABLE;
    }

    @Override
    public void setDeck(Deck deck) {
        this.deck = deck;
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
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void putPlayer(Player player) throws GameAlreadyFullException {
        if (status == Status.STARTED || status == Status.ENDED) {
            throw new GameAlreadyFullException("This game cannot take more players!");
        }
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
        CanNotPlayThisCardException {
        if (!player.equals(players.get(turn.current()))) {
            throw new NotRightTurnOfPlayerException(
                "Player: " + players.get(turn.current()).getDisplayName() + " is on turn not you!");
        }

        player.play(index, this.lastPlayedCard(), this.currentColor).play().accept(this);
        turn.next();
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
}
