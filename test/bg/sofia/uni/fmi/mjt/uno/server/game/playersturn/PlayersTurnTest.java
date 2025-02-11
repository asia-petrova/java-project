package bg.sofia.uni.fmi.mjt.uno.server.game.playersturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayersTurnTest {
    private PlayersTurn turn;

    @BeforeEach
    void setUp() {
        turn = new PlayersTurn(10);
    }

    @Test
    public void testNext() {
        int expected = turn.next();
        for (int i = 0; i < 9; i++) {
            turn.next();
        }
        assertEquals(expected, turn.next(), "Full cycle it should be the same index!");
    }

    @Test
    public void testNextChangedDirection() {
        int expected = turn.next();
        turn.changeDirection();
        for (int i = 0; i < 9; i++) {
            turn.next();
        }
        assertEquals(expected, turn.next(), "Full cycle it should be the same index!");
    }

    @Test
    public void testChangeDirection() {
        int expected = turn.next();
        turn.next();
        turn.changeDirection();

        assertEquals(expected, turn.next(), "Changed direction we should return the same index!");
    }

    @Test
    public void testDecrease() {
        int expected = turn.getMax() - 1;
        turn.decrease();
        assertEquals(expected, turn.getMax(), "Decrease should change max!");
    }
}
