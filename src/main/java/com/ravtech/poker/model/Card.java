package com.ravtech.poker.model;

public record Card(int value, Suit suit) {
    public Card {
        if (value < 2 || value > 14) throw new IllegalArgumentException("Invalid Int Value: " + value);
    }

    public static int convertValueToInt(char value) {
        int intValue = 0;
        if (Character.isDigit(value)) intValue = value - '0';

        if (intValue >= 2 && intValue <= 9) return intValue;

        return switch (value) {
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            case 'A' -> 14;
            default -> throw new IllegalArgumentException("Invalid Char Value: " + value);
        };
    }

    public static String convertValueToString(int value) {

        if (value >= 2 && value <= 9) return Integer.toString(value);

        return switch (value) {
            case 10 -> "10";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            case 14 -> "Ace";
            default -> throw new IllegalArgumentException("Invalid Int Value: " + value);
        };
    }
}
