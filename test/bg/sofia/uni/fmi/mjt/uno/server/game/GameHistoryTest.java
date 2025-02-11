package bg.sofia.uni.fmi.mjt.uno.server.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameHistoryTest {
    @Test
    void testGetHistoryAdded3() {
        GameHistory history = new GameHistory("game", 4, "a");
        history.addTop3Player("a");
        history.addTop3Player("b");
        history.addTop3Player("c");
        history.addTop3Player("d");
        history.addTop3Player("e");

        assertEquals("game: 4\nCreator: a\nTop 3: a, b, c\n", history.getHistory(),
            "getHistory() should return\n game: 4\nCreator: a\nTop 3: a, b, c\n");
    }

    @Test
    void testGetHistoryAdded1() {
        GameHistory history = new GameHistory("game", 2, "a");
        history.addTop3Player("a");
        history.addTop3Player("b");
        history.addTop3Player("c");
        history.addTop3Player("d");
        history.addTop3Player("e");

        assertEquals("game: 2\nCreator: a\nTop 1: a\n", history.getHistory(),
            "getHistory() should return\n game: 2\nCreator: a\nTop 1: a\n");
    }
}
