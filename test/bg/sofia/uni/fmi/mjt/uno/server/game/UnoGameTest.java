package bg.sofia.uni.fmi.mjt.uno.server.game;

import bg.sofia.uni.fmi.mjt.uno.server.card.Card;
import bg.sofia.uni.fmi.mjt.uno.server.card.Color;
import bg.sofia.uni.fmi.mjt.uno.server.deck.Deck;
import bg.sofia.uni.fmi.mjt.uno.server.deck.DeckOfUno;
import bg.sofia.uni.fmi.mjt.uno.server.exception.CanNotPlayThisCardException;
import bg.sofia.uni.fmi.mjt.uno.server.exception.GameAlreadyFullException;
import bg.sofia.uni.fmi.mjt.uno.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnoGameTest {
    private Deck deck;
    private Player player;
    private Card card;
    private Consumer cardEffect;
    private UnoGame game;
    private UnoGame game2;

    @BeforeEach
    void setUp() throws Exception {
        deck = mock(Deck.class);
        player = mock(Player.class);
        card = mock(Card.class);
        cardEffect = mock(Consumer.class);

        when(player.getDisplayName()).thenReturn("Creator");

        game = new UnoGame("1", player, 4);
        game2 = new UnoGame("2", player, 4);
        Player player1 = mock(Player.class);
        game.putPlayer(player);
        game.putPlayer(player1);
        game.putPlayer(player1);
        game.putPlayer(player1);
    }

    @Test
    void testSetDeckThrows() {
        assertThrows(IllegalArgumentException.class, () -> game.setDeck(null),
            "setDeck(null) should throw an exception");
    }

    @Test
    void testSetDeck() throws Exception{
        game.setDeck(deck);
        verify(deck, times(1)).shuffle();
    }

    @Test
    void testGetFromDeck() throws Exception {
        game.setDeck(deck);
        when(deck.getCards(1)).thenReturn(List.of(card));

        assertEquals(1, game.getFromDeck().size(), "Should return one hand when drawCounter not incremented");
    }

    @Test
    void testIncrement() throws Exception {
        game.setDeck(deck);
        game.increment(2);

        game.getFromDeck();
        verify(deck, times(1)).getCards(3);
    }

    @Test
    void testPutPlayerWhenGameNotStartedShouldAddPlayer() throws GameAlreadyFullException {
        game2.putPlayer(player);
        assertEquals(1, game2.playersInGame(), "There should be one player in game");
    }


    @Test
    void testPutPlayerShouldNotPutPlayer() throws GameAlreadyFullException {
        Player player1 = mock(Player.class);
        when(player.getDisplayName()).thenReturn("Player");
        game2.putPlayer(player1);
        game2.putPlayer(player1);
        game2.putPlayer(player1);
        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player1),
            "When capacity is full and the creator is not in there you game should not take more!");
    }

    @Test
    void testPutPlayerShouldNotPutPlayerWhenFull() throws GameAlreadyFullException {
        Player player1 = mock(Player.class);
        when(player.getDisplayName()).thenReturn("Player");
        game2.putPlayer(player1);
        game2.putPlayer(player1);
        game2.putPlayer(player1);
        game2.putPlayer(player);
        assertThrows(GameAlreadyFullException.class, () -> game.putPlayer(player1),
            "When capacity is full game should not take more!");
    }

    @Test
    void testPutPlayerThrowsWhenPlayerNull() throws Exception {
        assertThrows(IllegalArgumentException.class, ()->game2.putPlayer(null),
            "putPlayer(null) should throw an exception");
    }

    @Test
    void testGetId() {
        assertEquals(game.getId(), "1", "getId() should return 1");
    }

    @Test
    void testLastPlayedCard() throws Exception {
        game.setDeck(deck);
        game.lastPlayedCard();
        verify(deck, times(1)).showPlayedCard();
    }

    @Test
    void testExecutePlayersCardNullPlayer() {
        assertThrows(IllegalArgumentException.class, ()->game.executePlayersCard(1, null),
            "executePlayersCard(null) should throw an exception");
    }

    @Test
    void testExecutePlayersCardInvalidIndex() {
        assertThrows(IllegalArgumentException.class, ()->game.executePlayersCard(-1, player),
            "executePlayersCard(null) should throw an exception");
    }

    @Test
    void testExecutePlayersCardInvalidArguments() {
        assertThrows(IllegalArgumentException.class, ()->game.executePlayersCard(-21, null),
            "executePlayersCard(null) should throw an exception");
    }

    @Test
    void testPutInDeckThrows() {
        assertThrows(IllegalArgumentException.class, ()->game.putInDeck(null),
            "putInDeck(null) should throw an exception");
    }

    @Test
    void testPutInDeck() throws Exception {
        game.setDeck(deck);
        game.putInDeck(card);
        verify(deck, times(1)).putBack(card);
    }

    @Test
    void testSetCurrentColorThrows() {
        assertThrows(IllegalArgumentException.class, ()->game.setCurrentColor(null),
            "setCurrentColor(null) should throw an exception");
    }

    @Test
    void testSetCurrentColor() {
        game.setCurrentColor(Color.RED);
        assertEquals(Color.RED, game.getCurrentColor(), "The color should be red of the current game");
    }

    @Test
    void testToString() {
        assertEquals("1 players: 4", game.toString(), "game should be 1 players: 4");
    }

    @Test
    void testAcceptEffectThrows() {
        assertThrows(IllegalArgumentException.class, ()->game.acceptEffect(null),
            "acceptEffect(null) should throw an exception");
    }

    @Test
    void testGetLastPlayedCards() throws Exception {
        game.setDeck(deck);
        when(deck.showCards()).thenReturn("a");
        assertEquals(game.getLastPlayedCards(), "a", "getLastPlayedCards() should return a");
    }

    @Test
    void testLeaveGameThrowsNullPlayer() {
        assertThrows(IllegalArgumentException.class, ()->game.leaveGame(null),
            "leaveGame(null) should throw an exception");
    }

    @Test
    void testLeaveGame() throws Exception {
        game.setDeck(deck);

        List<Card> cards = mock(List.class);
        when(player.leaveGame()).thenReturn(cards);

        game.leaveGame(player);
        verify(deck, times(1)).putBack(cards);
    }

    @Test
    void testSpectateThrowsNullPlayer() {
        assertThrows(IllegalArgumentException.class, ()->game.spectate(null),
            "spectate(null) should throw an exception");
    }

    @Test
    void testSpectateThrowsWhenPlayerInGame() {
        assertThrows(CanNotPlayThisCardException.class, () -> game.spectate(player),
        "When player in game spectate should throw");
    }

    @Test
    void testSpectate() throws Exception {
        Game game1 = new UnoGame("2", player, 2);

        List<Card> cards = mock(List.class);
        when(player.leaveGame()).thenReturn(cards);
        game1.putPlayer(player);

        Player player2 = mock(Player.class);
        when(player2.showHand()).thenReturn("2");
        when(player2.getDisplayName()).thenReturn("Player2");
        game1.putPlayer(player2);

        game1.setDeck(deck);
        game1.leaveGame(player);
        game1.spectate(player);

        verify(player, times(1)).sendMessage("Player2: 2\n");
    }

    @Test
    void TestDrawThrowsNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> game.draw(null),
            "draw(null) should throw an exception");
    }

    @Test
    void testEndFalse() {
        assertFalse(game.end(), "game has not finished!");
    }

    @Test
    void testEndTrue() throws Exception {
        Game game1 = new UnoGame("2", player, 2);

        List<Card> cards = mock(List.class);
        when(player.leaveGame()).thenReturn(cards);
        game1.putPlayer(player);

        Player player2 = mock(Player.class);
        game1.putPlayer(player2);

        game1.setDeck(deck);
        game1.leaveGame(player);
        assertTrue(game1.end(), "game has ended");
    }

    @Test
    void TestInGameThrowsNullPlayer() {
        assertThrows(IllegalArgumentException.class, ()->game.inGame(null),
            "inGame(null) should throw an exception");
    }

    @Test
    void EstInGameTrue() {
        assertEquals(player, game.inGame(player), "If it is in game should return the player");
    }

    @Test
    void EstInGameFalse() {
        Player player2 = mock(Player.class);
        assertNull(game.inGame(player2), "If it is not in game should return null");
    }
}
