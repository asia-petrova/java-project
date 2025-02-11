package bg.sofia.uni.fmi.mjt.uno.server.game;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    private String gameID;
    private String creator;
    private int maxNumberOfPlayers;
    private List<String> top3Players;
    private int counter = 0;
    private static final int MAX_TOP_PLAYERS = 3;

    public GameHistory(String gameID, int maxNumberOfPlayers, String creator) {
        this.gameID = gameID;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.creator = creator;
        top3Players = new ArrayList<String>();
    }

    public String getGameID() {
        return gameID;
    }

    public String getCreator() {
        return creator;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public List<String> getTop3Players() {
        return top3Players;
    }

    public void addTop3Player(String playerID) {
        if (counter < MAX_TOP_PLAYERS && counter < maxNumberOfPlayers - 1) {
            top3Players.add(playerID);
        }
        counter++;
    }

    public String getHistory() {
        String top = maxNumberOfPlayers <= MAX_TOP_PLAYERS ? String.valueOf(maxNumberOfPlayers - 1) : "3";
        return gameID + ": " + maxNumberOfPlayers + "\n" +
            "Creator: " + creator + "\n" +
            "Top " + top + ": " + String.join(", ", top3Players) + "\n";
    }
}
