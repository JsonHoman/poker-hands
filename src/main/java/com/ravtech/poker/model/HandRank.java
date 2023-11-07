package com.ravtech.poker.model;

public class HandRank {
    private final int highestValue;
    private final HandType handType;
    private int tieBreakerValue;

    public HandRank(int highestValue, HandType handType) {
        this.highestValue = highestValue;
        this.handType = handType;
    }

    public void setTieBreakerValue(int tieBreakerValue) {
        this.tieBreakerValue = tieBreakerValue;
    }

    public int getHighestValue() {
        return highestValue;
    }

    public HandType getHandType() {
        return handType;
    }

    public int getTieBreakerValue() {
        return tieBreakerValue;
    }
}