package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class ZeroParametersInput implements CheckInput {
    private static final String MESSAGE = "This command has no parameters!";

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    @Override
    public boolean checkString() {
        return true;
    }
}
