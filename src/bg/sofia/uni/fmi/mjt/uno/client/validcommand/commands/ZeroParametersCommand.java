package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class ZeroParametersCommand implements CheckCommand {
    @Override
    public String getDescription() {
        return "This command has no parameters!";
    }

    @Override
    public boolean checkString() {
        return true;
    }
}
