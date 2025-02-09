package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class ListGameCommand implements CheckCommand {
    private String status;

    public ListGameCommand(String status) {
        this.status = status;
    }

    @Override
    public String getDescription() {
        return """
            This command takes one parameter!
            --status=<started/ended/available/all>
            The status must be form the above!
            """;
    }

    @Override
    public boolean checkString() {
        return status.replaceAll(" ", "").matches("--status=(all|avalable|ended|started)");
    }
}
