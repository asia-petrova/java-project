package bg.sofia.uni.fmi.mjt.uno.server.game.playersturn;

import java.util.Random;

public class PlayersTurn {
    int index;
    int max;
    int increment = 1;
    boolean change = false;

    public PlayersTurn(int countOfPlayers) {
        this.max = countOfPlayers - 1;
        this.index = new Random().nextInt(this.max);
    }

    public int next() {
        if (change && max == 1) {
            change = false;
            return index;
        }
        if (index == max && increment > 0) {
            index = 0;
            return index;
        } else if (index == 0 && increment < 0) {
            index = max;
            return index;
        }

        index += increment;
        return index;
    }

    public void changeDirection() {
        increment *= -1;
        change = true;
    }

    public int current() {
        return index;
    }

    public void decrease() {
        max--;
        if (index >= max) {
            index = 0;
        }
    }

    public int getMax() {
        return max;
    }
}
