package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class OneParameterCommand implements CheckCommand {
    private String parameter;

    @Override
    public String getDescription() {
        return """
            The syntax is incorrect!
            You might have meant one of those:
            play --card-id=<card-id>
            summary --game-id=<game-id>
            """;
    }

    public OneParameterCommand(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean checkString() {
        return parameter.matches("--(card-id|game-id)=.+");
    }
}
