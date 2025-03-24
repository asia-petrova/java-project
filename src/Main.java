import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String RESET = "\u001B[0m";
    private static final Map<Character, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put('R', "\u001B[31m"); // Червено
        COLOR_MAP.put('G', "\u001B[32m"); // Зелено
        COLOR_MAP.put('B', "\u001B[34m"); // Синьо
        COLOR_MAP.put('Y', "\u001B[33m"); // Жълто
        COLOR_MAP.put('W', "\u001B[37m"); // Бяло (Уайлд карти)
    }

    public static void visualizeUnoCards(String input) {
        String[] cards = input.split(" ");
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        StringBuilder line4 = new StringBuilder();
        StringBuilder line5 = new StringBuilder();

        for (String card : cards) {
            if (card.length() < 2) continue; // Пропускаме невалидни входове
            char color = card.charAt(0);
            String value = card.substring(1);
            String colorCode = COLOR_MAP.getOrDefault(color, RESET);

            line1.append(colorCode).append("  --------  ").append(RESET).append("  ");
            line2.append(colorCode).append(" | " + value + "      | ").append(RESET).append("  ");
            line3.append(colorCode).append(" |        | ").append(RESET).append("  ");
            line4.append(colorCode).append(" |      " + value + " | ").append(RESET).append("  ");
            line5.append(colorCode).append("  --------  ").append(RESET).append("  ");
        }

        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
        System.out.println(line4);
        System.out.println(line5);
    }

    public static void main(String[] args) {
        String cards = "R1 B2 Y3 G4 W+";
        visualizeUnoCards(cards);
    }
}
