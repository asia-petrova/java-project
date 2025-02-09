package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class JoinCommand implements CheckCommand {
    private String gameId;
    private String displayName;

    public JoinCommand(String gameId, String displayName) {
        this.gameId = gameId;
        this.displayName = displayName;
    }

    @Override
    public String getDescription() {
        return """
            This command can take 1 or 2 parameters!
            join --game-id=<game-id> --display-name=<display-name> or
            join --game-id=<game-id> and the display name is the username
            """;
    }

    @Override
    public boolean checkString() {
        boolean result = gameId.matches("--game-id=.+");
        if (displayName == null) {
            return result;
        }
        return result && displayName.matches("--display-name=.+");
    }
}
