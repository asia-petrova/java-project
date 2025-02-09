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
    private GameHistory gameHistory;
    private int drawCounter = 1;

    private PlayersTurn turn;
    Player creator;
    private Deck deck;
    private Color currentColor;
    private List<Player> players;

    public UnoGame(String gameId, Player creator, int countOfPlayers) {
        this.creator = creator;
        this.gameHistory = new GameHistory(gameId, countOfPlayers, creator.getUserName());
        this.players = new ArrayList<Player>(countOfPlayers);
        this.turn = new PlayersTurn(countOfPlayers);
    }

    @Override
    public void setDeck(Deck deck) throws IOException {
        if (deck == null) {
            throw new IllegalArgumentException("deck cannot be null");
        }
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
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
        drawCounter += amount;
    }

    @Override
    public void putPlayer(Player player) throws GameAlreadyFullException {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }

        if (gameHistory.getMaxNumberOfPlayers() - 1 == players.size() && !players.contains(creator) &&
            !player.equals(creator)) {
            throw new GameAlreadyFullException("Creator is not in game and the rest of the positions are taken!");
        }
        if (gameHistory.getMaxNumberOfPlayers() == players.size()) {
            throw new GameAlreadyFullException("Game already is full!");
        }
        players.add(player);
    }

    @Override
    public String getId() {
        return gameHistory.getGameID();
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
        if (index < 0 || player == null) {
            throw new IllegalArgumentException("index cannot be negative and player cannot be null");
        }

        onTurn(player);

        players.get(turn.current()).play(index, this.lastPlayedCard(), this.currentColor).play().accept(this);
        if (player.winGame()) {
            leaveGame(player);
        }
        turn.decrease();
        turn.next();

        sendToAll(messageToAll());
    }

    @Override
    public void putInDeck(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card cannot be null");
        }
        deck.putBack(card);
    }

    @Override
    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void setCurrentColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        currentColor = color;
    }

    @Override
    public int playersInGame() {
        return players.size();
    }

    @Override
    public String toString() {
        return gameHistory.getGameID() + " players: " + players.size();
    }

    @Override
    public Player getCreator() {
        return creator;
    }

    @Override
    public void acceptEffect(Player player) throws NotRightTurnOfPlayerException, IOException {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        onTurn(player);
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
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        players.remove(player);
        deck.putBack(player.leaveGame());
        turn.decrease();
    }

    @Override
    public void spectate(Player player) throws CanNotPlayThisCardException, IOException {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        if (players.contains(player)) {
            throw new CanNotPlayThisCardException("Player has not played all of his cards! Cannot execute spectate!");
        }
        String result = "";
        for (Player inGame : players) {
            result += inGame.getDisplayName() + ": " + inGame.showHand() + "\n";
        }
        player.sendMessage(result);
    }

    @Override
    public void draw(Player player)
        throws NotRightTurnOfPlayerException, IOException, CanNotPlayThisCardException {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        onTurn(player);
        if (!players.get(turn.current()).canDraw(lastPlayedCard(), getCurrentColor())) {
            throw new CanNotPlayThisCardException("Player has options to put a card! Please choose card!");
        }
        players.get(turn.current()).acceptFate(getFromDeck());
        turn.next();
    }

    @Override
    public boolean end() {
        return players.size() == 1;
    }

    @Override
    public GameHistory getHistory() {
        return gameHistory;
    }

    @Override
    public Player inGame(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        for (Player p : players) {
            if (p.equals(player)) {
                return p;
            }
        }
        return null;
    }

    private void sendToAll(String message) throws IOException {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    private String messageToAll() {
        return players.get(turn.current()).getDisplayName() + " is on turn\n";
    }

    private void onTurn(Player player) throws NotRightTurnOfPlayerException {
        if (!player.equals(players.get(turn.current()))) {
            throw new NotRightTurnOfPlayerException(
                "Player: " + players.get(turn.current()).getDisplayName() + " is on turn not you!");
        }
    }
}
