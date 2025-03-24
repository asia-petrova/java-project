package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class CreateGameInput implements CheckInput {
    private static final String MESSAGE = """
            This command can take 1 or 2 parameters!
            create-game --players=<number> --game-id=<game-id> or
            create-game --game-id=<game-id> and the number of players is 2
            the <number> if its given should be more than 1
            """;
    private String numberOfPlayers;
    private String gameId;

    public CreateGameInput(String numberOfPlayers, String gameId) {
        this.numberOfPlayers = numberOfPlayers == null ? null : numberOfPlayers.trim();
        this.gameId = gameId.trim();
    }

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    @Override
    public boolean checkString() {
        boolean result = gameId.matches("game-id\\s*=.+");
        if (numberOfPlayers == null) {
            return result;
        }
        return result && numberOfPlayers.matches("players\\s*=\\s*([3-9]|[1-9]\\d+)");
    }
}
