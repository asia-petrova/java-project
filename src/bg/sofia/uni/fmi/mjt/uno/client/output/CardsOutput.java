package bg.sofia.uni.fmi.mjt.uno.client.output;

import java.util.HashMap;
import java.util.Map;

public class CardsOutput {
    private static final int MAX_LINES = 5;
    private static final String RESET = "\u001B[0m";
    private static final String BORDER = "  ----------  ";
    private static final String MIDDLE = " |          | ";
    private StringBuilder[] lines = new StringBuilder[MAX_LINES];
    private Map<String, String> colorMap = new HashMap<>();

    public CardsOutput() {
        colorMap.put("red", "\u001B[31m");
        colorMap.put("green", "\u001B[32m");
        colorMap.put("blue", "\u001B[34m");
        colorMap.put("yellow", "\u001B[33m");
        colorMap.put("", "\u001B[37m");

        for (int i = 0; i < MAX_LINES; i++) {
            lines[i] = new StringBuilder();
        }
    }

    public void print(String cards) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalArgumentException("Cards cannot be null or empty");
        }

        if (!(cards.contains("<") && cards.contains(">"))) {
            System.out.println(cards);
            return;
        }

        addCards(cards);
        for (StringBuilder line : lines) {
            System.out.println(line);
        }
        removeCards();
    }

    private void addCards(String cards) {
        String[] hand = cards.split(" ");
        for (String card : hand) {
            put(card);
        }
    }

    private void put(String card) {
        String[] elements = card.replaceAll("[<>]", "").split("_");
        String color = elements.length > 1 ? elements[1] : "";
        String value = getValue(elements[0]);
        String id = "";
        String colorCode = colorMap.get(color);

        if (elements[0].contains(":")) {
            String original = elements[0].split(":")[1];
            value = getValue(original);
            id = elements[0].split(":")[0];
        }

        lines[Lines.FIRST.getLine()].append(colorCode).append(replaceMiddle(BORDER, id)).append(RESET).append("  ");
        lines[Lines.SECOND.getLine()].append(colorCode).append(MIDDLE).append(RESET).append("  ");
        lines[Lines.THIRD.getLine()].append(colorCode).append(value).append(RESET).append("  ");
        lines[Lines.FOURTH.getLine()].append(colorCode).append(MIDDLE).append(RESET).append("  ");
        lines[Lines.FIFTH.getLine()].append(colorCode).append(BORDER).append(RESET).append("  ");
    }

    private String getValue(String card) {
        String value = switch (card) {
            case "Change-Direction" -> "CD";
            case "Choose-color" -> "CC";
            default -> card;
        };

        return replaceMiddle(MIDDLE, value);
    }

    private String replaceMiddle(String line, String middle) {
        int start = (line.length() - middle.length()) / 2;
        int end = start + middle.length();

        return line.substring(0, start) + middle + line.substring(end);
    }

    private void removeCards() {
        for (StringBuilder line : lines) {
            line.delete(0, line.length());
        }
    }

}

