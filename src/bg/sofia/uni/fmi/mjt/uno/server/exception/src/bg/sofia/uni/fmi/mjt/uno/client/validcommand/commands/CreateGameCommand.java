package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class CreateGameCommand implements CheckCommand {
    //check if it is a digit
    private String numberOfPlayers;
    private String gameId;

    public CreateGameCommand(String numberOfPlayers, String gameId) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = gameId;
    }

    @Override
    public String getDescription() {
        return """
            This command can take 1 or 2 parameters!
            create-game --players=<number> --game-id=<game-id> or
            create-game --game-id=<game-id> and the number of players is 2
            """;
    }

    @Override
    public boolean checkString() {
        boolean result = gameId.matches("--game-id=.+");
        if (numberOfPlayers == null) {
            return result;
        }
        return result && numberOfPlayers.matches("--players=.+");
    }
}
