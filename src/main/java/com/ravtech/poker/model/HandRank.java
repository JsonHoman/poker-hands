package com.ravtech.poker.model;

public record HandRank(int highestValue, HandType handType) {
    // TODO: Could probably be moved into Hand.
}