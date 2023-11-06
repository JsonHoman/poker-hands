package com.ravtech.poker.model;

public enum HandType {
    HIGH_CARD(1),
    PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9);

    private final int handTypeGrade;

    HandType(int handTypeGrade) {
        this.handTypeGrade = handTypeGrade;
    }

    public int getHandTypeGrade() {
        return handTypeGrade;
    }

    public static String convertToTitleCase(String input) {
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(part.toLowerCase());
        }

        return result.toString();
    }
}
