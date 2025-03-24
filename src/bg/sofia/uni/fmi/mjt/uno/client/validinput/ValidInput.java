package bg.sofia.uni.fmi.mjt.uno.client.validinput;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;

public enum ValidInput {
    REGISTER("register", 2),
    LOGIN("login", 2),
    LOGOUT("logout", 0),
    LIST_GAMES("list-games", 1),
    CREATE_GAME("create-game", 2),
    JOIN("join", 2),
    START("start", 0),
    SHOW_HAND("show-hand", 0),
    SHOW_LAST_CARD("show-last-card", 0),
    ACCEPT_EFFECT("accept-effect", 0),
    PLAY("play", 1),
    PLAY_CHOOSE("play-choose", 2),
    PLAY_PLUS_FOUR("play-plus-four", 2),
    SHOW_PLAYED_CARDS("show-played-cards", 0),
    LEAVE("leave", 0),
    SPECTATE("spectate", 0),
    DRAW("draw", 0),
    SUMMARY("summary", 1);

    private final String command;
    private final int maxArguments;

    ValidInput(String command, int arguments) {
        this.command = command;
        this.maxArguments = arguments;
    }

    public int getMaxArguments() {
        return maxArguments;
    }

    /**
     * Creates a ValidCommand that is used for validation
     * @param command the user input
     * @return ValidCommand
     * @throws NoSuchCommand when the given input does not match any of
     * the possible commands
     */
    public static ValidInput getCommand(String command) throws NoSuchCommand {
        for (ValidInput e : ValidInput.values()) {
            if (e.command.equalsIgnoreCase(command)) {
                return e;
            }
        }
        throw new NoSuchCommand("Given command not found!");
    }
}
