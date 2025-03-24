package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class JoinInput implements CheckInput {
    private static final String MESSAGE = """
            This command can take 1 or 2 parameters!
            join --game-id=<game-id> --display-name=<display-name> or
            join --game-id=<game-id> and the display name is the username
            """;
    private String gameId;
    private String displayName;

    public JoinInput(String gameId, String displayName) {
        this.gameId = gameId;
        this.displayName = displayName;
    }

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    @Override
    public boolean checkString() {
        boolean result = gameId.matches("game-id\\s*=.+");
        if (displayName == null) {
            return result;
        }
        return result && displayName.matches("display-name\\s*=.+");
    }
}
