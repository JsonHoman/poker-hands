package com.ravtech.poker.model;

public enum Suit {
    C("CLUBS"),
    D("DIAMONDS"),
    H("HEARTS"),
    S("SPADES");

    private final String expandedSuit;

    Suit(String expandedSuit) {
        this.expandedSuit = expandedSuit;
    }

    public String getExpandedSuit() {
        return expandedSuit;
    }
}
