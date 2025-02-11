package bg.sofia.uni.fmi.mjt.uno.server.manager;

import bg.sofia.uni.fmi.mjt.uno.server.deck.CreateDeck;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.ProblemWithFileException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.UserDoesNotExistException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.WrongPasswordException;

import bg.sofia.uni.fmi.mjt.uno.server.game.Game;
import bg.sofia.uni.fmi.mjt.uno.server.game.GameHistory;
import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.game.UnoGame;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import bg.sofia.uni.fmi.mjt.uno.server.users.Users;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SystemManager implements Manager {
    private static final String NAME_OF_FILE = "game-history.json";

    private Map<String, Game> availableGames;
    private Map<String, Game> startedGames;
    private Map<String, GameHistory> endedGames;
    private Users users;

    public SystemManager() {
        availableGames = new HashMap<>();
        startedGames = new HashMap<>();
        endedGames = new HashMap<>();
        users = new Users();
    }

    @Override
    public void addGame(String gameId, Player creator, int countOfPlayers) throws GameAlreadyExistsException {
        if (gameId == null || creator == null || countOfPlayers < 2) {
            throw new IllegalArgumentException("Illegal argument");
        }

        if (availableGames.containsKey(gameId) || startedGames.containsKey(gameId) || endedGames.containsKey(gameId)) {
            throw new GameAlreadyExistsException(gameId + "this id is already in use");
        }
        Game newGame = new UnoGame(gameId, creator, countOfPlayers);
        creator.setCreatedGame(gameId);
        availableGames.put(gameId, newGame);
    }

    @Override
    public void registerUser(String username, int password) throws UserAlreadyExistsException {
        if (username == null ) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        users.registerUser(username, password);
    }

    @Override
    public Player login(String username, int password, SelectionKey key)
        throws UserDoesNotExistException, WrongPasswordException {
        if (username == null || key == null) {
            throw new IllegalArgumentException("Username and key cannot be null");
        }
        Player player = users.login(username, password, key);
        for (Map.Entry<String, Game> entry : startedGames.entrySet()) {
            if (entry.getValue().inGame(player) != null) {
                return entry.getValue().inGame(player);
            }
        }
        return player;
    }

    @Override
    public String getWithStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

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
        if (player == null || gameId == null || displayName == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        if (!availableGames.containsKey(gameId)) {
            throw new GameDoesNotExistsException(gameId + " is not available!");
        }
        player.setDisplayName(displayName);
        player.joinGame(gameId);
        availableGames.get(gameId).putPlayer(player);
    }

    @Override
    public Game startGame(Player player) throws GameDoesNotExistsException, IOException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        String gameId = player.getCreatedGame();
        if (!availableGames.containsKey(gameId)) {
            throw new GameDoesNotExistsException(gameId + " is not available!");
        }
        Game game = availableGames.get(gameId);
        if (!game.start()) {
            throw new GameDoesNotExistsException("Game does not have enough players to start!");
        }
        game.setDeck(CreateDeck.createDeck());
        startedGames.put(gameId, game);
        availableGames.remove(gameId);
        return game;
    }

    @Override
    public String showLastCard(String game) throws GameDoesNotExistsException {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        if (!startedGames.containsKey(game)) {
            throw new GameDoesNotExistsException(game + " this game does not exist!");
        }
        return startedGames.get(game).lastPlayedCard().toString();
    }

    @Override
    public String showPlayedCards(Player player) throws GameDoesNotExistsException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        String game = checkInStarted(player);
        return startedGames.get(game).getLastPlayedCards();
    }

    @Override
    public Game getGame(Player player) throws GameDoesNotExistsException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return startedGames.get(checkInStarted(player));
    }

    @Override
    public  String summary(String id) throws GameDoesNotExistsException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (!endedGames.containsKey(id)) {
            throw new GameDoesNotExistsException("This game: " + id + " is not in ended games!");
        }
        return endedGames.get(id).getHistory();
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
            resultBuilder.append(game).append(" players: ").append(endedGames.get(game).getMaxNumberOfPlayers())
                .append("\n");
        }
        return resultBuilder.toString();
    }

    private String checkInStarted(Player player) throws GameDoesNotExistsException {
        String game = player.getCurrentGame();
        if (!startedGames.containsKey(game)) {
            throw new GameDoesNotExistsException(game + " this game does not exist!");
        }
        if (startedGames.get(game).end()) {
            endedGames.put(startedGames.get(game).getId(), startedGames.get(game).getHistory());
            startedGames.remove(game);
            throw new GameDoesNotExistsException(game + " this game has ended!");
        }
        return game;
    }

    @Override
    public void saveInFile() throws ProblemWithFileException {
        users.saveUsersInFile();
    }

    private void saveGameHistory() throws ProblemWithFileException {
        try (FileWriter file = new FileWriter(NAME_OF_FILE)) {
            Gson json = new Gson();
            file.write(json.toJson(users));
        } catch (IOException e) {
            throw new ProblemWithFileException("GAME HISTORY NOT SAVED!\n" + e.getMessage());
        }
    }

}
