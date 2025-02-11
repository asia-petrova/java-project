package bg.sofia.uni.fmi.mjt.uno.server.manager;

import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameDoesNotExistsException;
import bg.sofia.uni.fmi.mjt.uno.server.game.Status;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.SelectionKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SystemManagerTest {
    private Manager systemManager;
    private Player player;
    private Player player1;
    private SelectionKey key;

    @BeforeEach
    public void setUp() {
        systemManager = new SystemManager();
        player = mock(Player.class);
        key = mock(SelectionKey.class);
        player1 = mock(Player.class);
    }

    @Test
    public void testAddGameGameIdNull() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.addGame(null, player, 2),
            "Invalid arguments in addGame");
    }

    @Test
    public void testAddCreatorNull() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.addGame("s", null, 2),
            "Invalid arguments in addGame");
    }

    @Test
    public void testAddGamePlayersInvalid() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.addGame("s", player, 1),
            "Invalid arguments in addGame");
    }

    @Test
    public void testAddGame() throws Exception {
        systemManager.addGame("a", player, 2);
        verify(player, times(1)).setCreatedGame("a");
    }

    @Test
    public void testAddGameThrowsWhenGameExist() throws Exception {
        systemManager.addGame("a", player, 2);
        assertThrows(GameAlreadyExistsException.class, ()->systemManager.addGame("a", player, 2),
            "Same game cannot be added");
    }

    @Test
    public void testLogInThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.login(null, 1, null),
            "Invalid arguments in login");
    }

    @Test
    void testRegisterUserThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.registerUser(null, 1),
            "Invalid arguments in registerUser");
    }

    @Test
    void testLogInPlayer() throws Exception {
        systemManager.registerUser("a", 1);
        assertNotNull(systemManager.login("a", 1, key),
            "Should create user!");
    }

    @Test
    void testGetWithStatusThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.getWithStatus(null),
            "Invalid arguments in getWithStatus");
    }

    @Test
    void testGetWithStatusEmpty() {
        assertEquals("ALL:\n------started:------\n------available:------\n------ended:------\n",
            systemManager.getWithStatus(Status.ALL));
    }

    @Test
    void testJoinGameThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.joinGame(null, null, null),
            "Invalid arguments in joinGame");
    }

    @Test
    void testJoinGameThrowWhenGameDoesNotExist() {
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.joinGame(player, "a", "a"),
            "joinGame should throw when game does not exist");
    }

    @Test
    void testJoinGame() throws Exception {
        systemManager.addGame("a", player, 2);
        systemManager.joinGame(player, "a", "a");
        verify(player, times(1)).setDisplayName("a");
        verify(player, times(1)).joinGame("a");
    }

    @Test
    void testStartGameThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.startGame(null),
            "Invalid arguments in startGame");
    }

    @Test
    void testStartGameThrowsWhenGameDoesNotExist() {
        when(player.getCreatedGame()).thenReturn("a");
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.startGame(player),
            "When game does not exists it cannot be started throws");
    }

    @Test
    void testStartGameThrowsWhenThereAreNotEnoughPlayers() throws Exception {
        systemManager.addGame("a",player, 2);
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.startGame(player),
            "When game does not have enough players throws");
    }

    @Test
    void testStartGame() throws Exception {
        when(player.getCreatedGame()).thenReturn("a");
        systemManager.addGame("a",player, 2);
        systemManager.joinGame(player, "a", "a");
        Player player1 = mock(Player.class);
        systemManager.joinGame(player1, "a", "b");

        assertNotNull(systemManager.startGame(player), "startGame should create game");
    }

    @Test
    void testShowLastCardThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.showLastCard(null),
            "Invalid arguments in showLastCard");
    }

    @Test
    void testShowLastCardThrowsGameDoesNotExist() {
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.showLastCard("null"),
            "showLastCard should throw when game does not exist");
    }

    @Test
    void testShowLastCard() throws Exception {
        startGame();
        assertNotNull(systemManager.showLastCard("a"), "When game is stated showLastCard should return card");
    }

    @Test
    void testShowPlayedCardThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.showPlayedCards(null),
            "Invalid arguments in showPlayedCards");
    }

    @Test
    void testShowPlayedCards() throws Exception {
        startGame();
        assertNotNull(systemManager.showPlayedCards(player), "When game is stated showLastCard should return card");
    }

    @Test
    void testShowPlayedCardsThrowsWhenGameNotThere() throws Exception {
        when(player.getCurrentGame()).thenReturn("b");
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.showPlayedCards(player),
            "When game does not exist showLastCard should throw");
    }

    @Test
    void testShowPlayedCardsThrowsWhenGameEnded() throws Exception {
        endGame();
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.showPlayedCards(player),
            "When game ended showLastCard should throw");
    }

    @Test
    void testGetGameThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.getGame(null),
            "Invalid arguments in getGame");
    }

    @Test
    void testGetGameThrowsWhenGameDoesNotExist() {
        when(player.getCurrentGame()).thenReturn("a");
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.getGame(player),
            "Game does not exist in stated!");
    }

    @Test
    void testGetGame() throws Exception {
        startGame();
        assertNotNull(systemManager.getGame(player), "When game is stated getGame should return game");
    }

    @Test
    void testSummaryThrowsNullArguments() {
        assertThrows(IllegalArgumentException.class, ()->systemManager.summary(null),
            "Invalid arguments in summary");
    }

    @Test
    void testSummaryThrowsWhenGameNotInEnded() throws Exception {
        startGame();
        assertThrows(GameDoesNotExistsException.class, ()->systemManager.summary("a"),
            "Game has not ended!");
    }

    @Test
    void testSummary() throws Exception {
        endGame();

        assertThrows(GameDoesNotExistsException.class, ()->systemManager.getGame(player1));

        assertNotNull(systemManager.summary("a"),
            "When game has ended it should return summary");
    }


    private void startGame() throws Exception {
        when(player.getCurrentGame()).thenReturn("a");
        when(player.getCreatedGame()).thenReturn("a");
        when(player1.getCurrentGame()).thenReturn("a");
        systemManager.addGame("a",player, 2);
        systemManager.joinGame(player, "a", "a");
        systemManager.joinGame(player1, "a", "b");
        systemManager.startGame(player);
    }

    private void endGame() throws Exception {
        startGame();
        systemManager.getGame(player).leaveGame(player);
    }
}
