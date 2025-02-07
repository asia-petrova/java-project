package bg.sofia.uni.fmi.mjt.uno.server.manager;

import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotInGameException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.NotRightTurnOfPlayerException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.game.UnoGame;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import bg.sofia.uni.fmi.mjt.uno.server.users.Users;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SystemManager implements Manager {
    private Map<String, Game> availableGames;
    private Map<String, Game> startedGames;
    private Map<String, Integer> endedGames;
    private Users users;

    public SystemManager() {
        availableGames = new HashMap<>();
        startedGames = new HashMap<>();
        endedGames = new HashMap<>();
        users = new Users();
    }
//
//    @Override
//    public Game findGame(String gameId) {
//        return games.get(gameId);
//    }

    @Override
    public void addGame(String gameId, Player creator, int countOfPlayers) throws GameAlreadyExistsException {
        if (availableGames.containsKey(gameId) || startedGames.containsKey(gameId) || endedGames.containsKey(gameId)) {
            throw new GameAlreadyExistsException(gameId + "this id is already in use");
        }
        Game newGame = new UnoGame(gameId, creator, countOfPlayers);
        creator.setCreatedGame(gameId);
        availableGames.put(gameId, newGame);
    }

    @Override
    public void registerUser(String username, int password) throws UserAlreadyExistsException {
        users.registerUser(username, password);
    }

    @Override
    public Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException {
        return users.login(username, password, key);
    }

    @Override
    public void leaveGame(Player player) throws NotInGameException, GameDoesNotExistsException {
        String game = checkInStarted(player);
        startedGames.get(game).leaveGame(player);
    }

    @Override
    public String getWithStatus(Status status) {
        String result = status.toString() + ":\n";
        return switch (status) {
            case STARTED -> getGames(this.startedGames.values(), result);
            case AVAILABLE -> getGames(this.availableGames.values(), result);
            case ENDED -> getEndedGames(result);
            case ALL -> getGames(this.startedGames.values(), result + "------started:------\n")
                + getGames(this.availableGames.values(), "------available:------\n")
                + getEndedGames("------ended:------\n");
        };

    }

    @Override
    public void joinGame(Player player, String gameId, String displayName)
        throws GameDoesNotExistsException, GameAlreadyFullException {
        if (!availableGames.containsKey(gameId)) {
            throw new GameDoesNotExistsException(gameId + " is not available!");
        }
        player.setDisplayName(displayName);
        player.joinGame(gameId);
        availableGames.get(gameId).putPlayer(player);
    }

    @Override
    public Game startGame(Player player) throws GameDoesNotExistsException {
        String gameId = player.getCreatedGame();
        if (!availableGames.containsValue(gameId)) {
            throw new GameDoesNotExistsException(gameId + " is not available!");
        }
        Game game = availableGames.get(gameId);
        Player creator = game.getCreator();
        if (!creator.equals(player)) {
            throw new GameDoesNotExistsException(
                creator.getDisplayName() + " is the creator of the game. Only they can start it!");
        }
        startedGames.put(gameId, game);
        availableGames.remove(gameId);
        return game;
    }

    @Override
    public String showLastCard(String game) throws GameDoesNotExistsException {
        if (!startedGames.containsKey(game)) {
            throw new GameDoesNotExistsException(game + " this game does not exist!");
        }
        return startedGames.get(game).lastPlayedCard().toString();
    }

    @Override
    public void acceptEffect(Player player) throws GameDoesNotExistsException, IOException,
        NotRightTurnOfPlayerException {
        String game = checkInStarted(player);
        startedGames.get(game).acceptEffect(player);
    }

    @Override
    public void playOrdinaryCard(Player player, int index)
        throws GameDoesNotExistsException, NotRightTurnOfPlayerException,
        CanNotPlayThisCardException, IOException {
        String game = checkInStarted(player);
        startedGames.get(game).executePlayersCard(index, player);
    }

    @Override
    public void playSpecialCard(Player player, int index, Color color)
        throws GameDoesNotExistsException, NotRightTurnOfPlayerException, CanNotPlayThisCardException, IOException {
        playOrdinaryCard(player, index);
        startedGames.get(player.getCreatedGame()).setCurrentColor(color);
    }

    @Override
    public String showPlayedCards(Player player) throws GameDoesNotExistsException {
        String game = checkInStarted(player);
        return startedGames.get(game).getLastPlayedCards();
    }

    private String getGames(Collection<Game> games, String result) {
        StringBuilder resultBuilder = new StringBuilder(result);
        for (Game game : games) {
            resultBuilder.append(game.toString()).append("\n");
        }
        return resultBuilder.toString();
    }

    private String getEndedGames(String result) {
        StringBuilder resultBuilder = new StringBuilder(result);
        for (String game : endedGames.keySet()) {
            resultBuilder.append(game).append(" players: ").append(endedGames.get(game)).append("\n");
        }
        return resultBuilder.toString();
    }

    private String checkInStarted(Player player) throws GameDoesNotExistsException {
        String game = player.getCurrentGame();
        if (!startedGames.containsKey(game)) {
            throw new GameDoesNotExistsException(game + " this game does not exist!");
        }
        return game;
    }

}
