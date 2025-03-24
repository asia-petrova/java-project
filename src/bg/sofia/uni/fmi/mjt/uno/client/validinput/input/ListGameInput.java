package bg.sofia.uni.fmi.mjt.uno.client.validinput.input;

import bg.sofia.uni.fmi.mjt.uno.client.validinput.CheckInput;

public class ListGameInput implements CheckInput {
    private static final String MESSAGE = """
            This command takes one parameter!
            --status=<started/ended/available/all>
            The status must be form the above!
            """;
    private String status;

    public ListGameInput(String status) {
        this.status = status.trim();
    }

    @Override
    public String getDescription() {
        return MESSAGE;
    }

    @Override
    public boolean checkString() {
        return status.matches("status\\s*=\\s*(all|avalable|ended|started)");
    }
}
