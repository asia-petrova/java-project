package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class PlaySpecialCommand implements CheckCommand {
    private String cardId;
    private String color;

    @Override
    public String getDescription() {
        return """
            This command has 2 parameters!
            --card-id=<card-id> --color=<red/green/blue/yellow>
            The color must be from the specified""";
    }

    public PlaySpecialCommand(String cardId, String color) {
        this.cardId = cardId;
        this.color = color;
    }

    @Override
    public boolean checkString() {
        if (color.matches("--color=.+")) {
            String[] elements = color.split("=");
            if (elements.length != 2) {
                return false;
            }
            if (!elements[1].matches("(red|green|blue|yellow)")) {
                return false;
            }
        } else {
            return false;
        }
        return cardId.matches("--card-id=.+");
    }
}
