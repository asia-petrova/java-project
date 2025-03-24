package bg.sofia.uni.fmi.mjt.uno.client.validinput;

import bg.sofia.uni.fmi.mjt.uno.client.exceptions.InvalidCountOfParameters;
import bg.sofia.uni.fmi.mjt.uno.client.exceptions.NoSuchCommand;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.CreateGameInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.JoinInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.ListGameInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.OneParameterInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.PlaySpecialInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.RegisterInput;
import bg.sofia.uni.fmi.mjt.uno.client.validinput.input.ZeroParametersInput;

public interface CheckInput {

    /**
     * @return the syntax of the command
     */
    String getDescription();

    /**
     * @return true when the syntax is correct
     */
    boolean checkString();

    /**
     * Factory method: creates CheckCommand that is used to validate users input
     * @param command the users input
     * @return CheckCommand
     * @throws NoSuchCommand when the given input does not contain right
     * form of command
     * @throws InvalidCountOfParameters when the number of the arguments
     * does not match
     */
    static CheckInput of(String command) throws NoSuchCommand, InvalidCountOfParameters {
        String[] elements = command.trim().split("--");
        ValidInput cmd = ValidInput.getCommand(elements[0].trim());
        if (elements.length != cmd.getMaxArguments() + 1) {
            if ((cmd == ValidInput.JOIN || cmd == ValidInput.CREATE_GAME) &&
                (elements.length < 2 || elements.length > cmd.getMaxArguments() + 1)) {
                throw new InvalidCountOfParameters("This command takes " + cmd.getMaxArguments() + " arguments!");
            } else if (!(cmd == ValidInput.JOIN || cmd == ValidInput.CREATE_GAME)) {
                throw new InvalidCountOfParameters("This command takes " + cmd.getMaxArguments() + " arguments!");
            }
        }
        return create(cmd, elements);
    }

    private static CheckInput create(ValidInput cmd, String[] elements) {
        return switch (cmd) {
            case LOGOUT, START, SHOW_HAND, SHOW_LAST_CARD, ACCEPT_EFFECT, SHOW_PLAYED_CARDS, LEAVE, SPECTATE, DRAW ->
                new ZeroParametersInput();
            case PLAY, SUMMARY -> new OneParameterInput(elements[1]);
            case REGISTER, LOGIN -> new RegisterInput(elements[1], elements[2]);
            case PLAY_CHOOSE, PLAY_PLUS_FOUR -> new PlaySpecialInput(elements[1], elements[2]);
            case JOIN -> {
                if (elements.length == 2) {
                    yield new JoinInput(elements[1], null);
                }
                yield new JoinInput(elements[1], elements[2]);
            }
            case CREATE_GAME -> {
                if (elements.length == 2) {
                    yield new CreateGameInput(null, elements[1]);
                }
                yield new CreateGameInput(elements[1], elements[2]);
            }
            case LIST_GAMES -> new ListGameInput(elements[1]);
            default -> null;
        };
    }
}
