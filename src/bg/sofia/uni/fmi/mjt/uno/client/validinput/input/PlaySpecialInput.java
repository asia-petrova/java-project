package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class PlaySpecialInput implements CheckInput {
    private static final String MESSAGE = """
            This command has 2 parameters!
            --card-id=<card-id> --color=<red/green/blue/yellow>
            The color must be from the specified""";
    private String cardId;
    private String color;

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    public PlaySpecialInput(String cardId, String color) {
        this.cardId = cardId.trim();
        this.color = color.trim();
    }

    @Override
    public boolean checkString() {
        if (color.matches("color\\s*=.+")) {
            String[] elements = color.split("=");
            if (elements.length != 2) {
                return false;
            }
            if (!elements[1].trim().matches("(red|green|blue|yellow)")) {
                return false;
            }
        } else {
            return false;
        }
        return cardId.matches("card-id=.+");
    }
}
