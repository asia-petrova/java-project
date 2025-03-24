package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class RegisterInput implements CheckInput {
    private static final String MESSAGE = """
            This command has 2 parameters!
            --username=<username> --password=<password>
            """;
    private String userName;
    private String password;

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    public RegisterInput(String userName, String password) {
        this.userName = userName.trim();
        this.password = password.trim();
    }

    @Override
    public boolean checkString() {
        return userName.matches("username\\s*=.+") && password.matches("password\\s*=.+");
    }
}
