import java.io.PrintWriter;

//import java.io.*;
//import java.util.Locale;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        PrintWriter printWriter = new PrintWriter(System.out, true);
        printWriter.println("│ ┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┌────┐ │");
        printWriter.println("│ │0 ██│    │    │    │    │    │    │    │    │    │    │    │    │    │    │ │");
        printWriter.println("│ │    │    │    │    │    │    │    │    │    │    │    │    │    │    │    │ │");
        printWriter.println("│ │██ 0│    │    │    │    │    │    │    │    │    │    │    │    │    │    │ │");
        printWriter.println("│ └────└────└────└────└────└────└────└────└────└────└────└────└────└────└────┘ │");
    }
}