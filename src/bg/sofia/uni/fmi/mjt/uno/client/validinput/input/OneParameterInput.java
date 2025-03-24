package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class OneParameterInput implements CheckInput {
    private static final String MESSAGE = """
            The syntax is incorrect!
            You might have meant one of those:
            play --card-id=<card-id>
            summary --game-id=<game-id>
            """;
    private String parameter;

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    public OneParameterInput(String parameter) {
        this.parameter = parameter.trim();
    }

    @Override
    public boolean checkString() {
        return parameter.matches("(card-id|game-id)\\s*=.+");
    }
}
