package bg.sofia.uni.fmi.mjt.uno.client.output;

public enum Lines {
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3),
    FIFTH(4);

    private int line;
    private Lines(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
