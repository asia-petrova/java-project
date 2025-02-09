package bg.sofia.uni.fmi.mjt.uno.client.validcommand;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.CreateGameCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.JoinCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.ListGameCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.OneParameterCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.PlaySpecialCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.RegisterCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands.ZeroParametersCommand;

public interface CheckCommand {
    // check all classes for null in string or empty string to throw
    // think about how u say what is the problem to the client =
    String getDescription();

    boolean checkString();

    static CheckCommand of(String command) throws NoSuchCommand, InvalidCountOfParameters {
        String[] elements = command.trim().split("\\s+");
        ValidCommand cmd = ValidCommand.getCommand(elements[0]);
        if (elements.length != cmd.getMaxArguments() + 1) {
            if ((cmd == ValidCommand.JOIN || cmd == ValidCommand.CREATE_GAME) &&
                (elements.length < 2 || elements.length > cmd.getMaxArguments() + 1)) {
                throw new InvalidCountOfParameters("This command takes " + cmd.getMaxArguments() + " arguments!");
            } else if (!(cmd == ValidCommand.JOIN || cmd == ValidCommand.CREATE_GAME)) {
                throw new InvalidCountOfParameters("This command takes " + cmd.getMaxArguments() + " arguments!");
            }
        }
        return create(cmd, elements);
    }

    private static CheckCommand create(ValidCommand cmd, String[] elements) {
        return switch (cmd) {
            case LOGOUT, START, SHOW_HAND, SHOW_LAST_CARD, ACCEPT_EFFECT, SHOW_PLAYED_CARDS, LEAVE, SPECTATE, DRAW ->
                new ZeroParametersCommand();
            case PLAY, SUMMARY -> new OneParameterCommand(elements[1]);
            case REGISTER, LOGIN -> new RegisterCommand(elements[1], elements[2]);
            case PLAY_CHOOSE, PLAY_PLUS_FOUR -> new PlaySpecialCommand(elements[1], elements[2]);
            case JOIN -> {
                if (elements.length == 2) {
                    yield new JoinCommand(elements[1], null);
                }
                yield new JoinCommand(elements[1], elements[2]);
            }
            case CREATE_GAME -> {
                if (elements.length == 2) {
                    yield new CreateGameCommand(null, elements[1]);
                }
                yield new CreateGameCommand(elements[1], elements[2]);
            }
            case LIST_GAMES -> new ListGameCommand(elements[1]);
        };
    }
}
