package bg.sofia.uni.fmi.mjt.uno.client.validcommand.commands;

import bg.sofia.uni.fmi.mjt.uno.client.validcommand.CheckCommand;

public class RegisterCommand implements CheckCommand {
    private String userName;
    private String password;

    @Override
    public String getDescription() {
        return """
            This command has 2 parameters!
            --username=<username> --password=<password>
            """;
    }

    public RegisterCommand(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public boolean checkString() {
        return userName.matches("--username=.+") && password.matches("--password=.+");
    }
}
