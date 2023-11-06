package com.ravtech.poker.model;

import java.util.*;
import java.util.Map.Entry;

public class Hand {
    private final Card[] cards;
    private Player player;
    private HandRank rank;
    public static final int HAND_SIZE = 5;

    public Hand(Card[] cards, Player player) throws IllegalArgumentException {
        if (cards == null) {
            throw new IllegalArgumentException("A hand must have exactly " + HAND_SIZE + " cards. Input cards can't be null");
        } else if (cards.length != HAND_SIZE) {
            throw new IllegalArgumentException("A hand must have exactly " + HAND_SIZE + " cards. Input cards count: " + cards.length);
        }

        Arrays.sort(cards, Comparator.comparingInt(Card::value));

        this.cards = cards;
        this.player = player;
    }

    public HandRank evaluateHand() throws IllegalStateException {
        if (this.rank != null) return this.rank;

        Map<Integer, Integer> cardValueFreq = calculateCardValueFrequency();
        int valuesInARow = calculateValuesInARow();
        int suitsInARow = calculateSuitsInARow();

        int defaultHighCardVal = cards[HAND_SIZE - 1].value();
        int multiples = cardValueFreq.size();

        if (isStraightFlush(valuesInARow, suitsInARow)) {
            this.rank = new HandRank(defaultHighCardVal, HandType.STRAIGHT_FLUSH);
        } else if (multiples == 1) {
            // Pair (Two of a Kind), Three of a Kind, Four of a Kind
            this.rank = evaluateMultiplesOfAKind(cardValueFreq);
        } else if (multiples == 2) {
            this.rank = evaluateTwoPairOrFullHouse(cardValueFreq);
        } else if (suitsInARow == 5) {
            this.rank = new HandRank(defaultHighCardVal, HandType.FLUSH);
        } else if (valuesInARow == 5) {
            this.rank = new HandRank(defaultHighCardVal, HandType.STRAIGHT);
        } else {
            this.rank = new HandRank(defaultHighCardVal, HandType.HIGH_CARD);
        }

        if (this.rank.highestValue() == -1) {
            throw new IllegalStateException("Invalid High Card Value");
        }

        return this.rank;
    }

    private HandRank evaluateTwoPairOrFullHouse(Map<Integer, Integer> cardValueFreq) throws InvalidFreqOfAKindException {
        int cardFreq;
        int cardVal;
        int maxFreq = 0;
        int highCardVal = -1;

        for (Entry<Integer, Integer> cardEntry : cardValueFreq.entrySet()) {

            cardFreq = cardEntry.getValue();
            cardVal = cardEntry.getKey();

            if (cardFreq >= maxFreq) {
                maxFreq = cardFreq;

                // Full House
                if (maxFreq == 3) {
                    highCardVal = cardVal;
                }

                // Two Pair
                if (maxFreq == 2) {
                    highCardVal = Math.max(cardVal, highCardVal);
                }
            }
        }

        return switch (maxFreq) {
            case 2 -> new HandRank(highCardVal, HandType.TWO_PAIR);
            case 3 -> new HandRank(highCardVal, HandType.FULL_HOUSE);
            default -> throw new InvalidFreqOfAKindException("Invalid Frequency of a Kind: " + maxFreq);
        };
    }

    private HandRank evaluateMultiplesOfAKind(Map<Integer, Integer> cardValueFreq) throws InvalidMultiplesFreqException, InvalidFreqOfAKindException {
        int highCardVal = -1;
        int valueFreq = 0;

        // Only should loop once.
        if (cardValueFreq.size() == 1) {
            for (Map.Entry<Integer, Integer> entry : cardValueFreq.entrySet()) {
                valueFreq = entry.getValue();
                highCardVal = entry.getKey();
            }
        } else {
            throw new InvalidMultiplesFreqException("Too Many Multiples: " + cardValueFreq.size());
        }

        return switch (valueFreq) {
            case 2 -> new HandRank(highCardVal, HandType.PAIR);
            case 3 -> new HandRank(highCardVal, HandType.THREE_OF_A_KIND);
            case 4 -> new HandRank(highCardVal, HandType.FOUR_OF_A_KIND);
            default -> throw new InvalidFreqOfAKindException("Invalid Frequency of a Kind: " + valueFreq);
        };
    }

    private boolean isStraightFlush(int valuesInARow, int suitsInARow) {
        return valuesInARow == 5 && suitsInARow == 5;
    }

    private int calculateSuitsInARow() {
        int suitsInARow = 1;
        Suit prevCardSuit;
        Suit cardSuit;

        for (int i = 1; i < HAND_SIZE; i++) {
            prevCardSuit = cards[i - 1].suit();
            cardSuit = cards[i].suit();

            if (prevCardSuit == cardSuit) {
                suitsInARow++;
            } else {
                suitsInARow = 1;  // Reset if not in a row.
            }
        }

        return suitsInARow;
    }

    private int calculateValuesInARow() {
        int valuesInARow = 1;
        int prevCardVal;
        int cardVal;

        for (int i = 1; i < HAND_SIZE; i++) {
            prevCardVal = cards[i - 1].value();
            cardVal = cards[i].value();

            if (prevCardVal + 1 == cardVal) {
                valuesInARow++;
            } else {
                valuesInARow = 1;  // Reset if not in a row.
            }
        }

        return valuesInARow;
    }

    private Map<Integer, Integer> calculateCardValueFrequency() {
        Map<Integer, Integer> cardValueFreq = new HashMap<>();
        int prevCardVal;
        int cardVal;

        for (int i = 1; i < HAND_SIZE; i++) {
            prevCardVal = cards[i - 1].value();
            cardVal = cards[i].value();

            if (prevCardVal == cardVal) {
                // If null, create key with value of 2 since it's a pair.
                cardValueFreq.compute(cardVal, (key, value) -> (value == null) ? 2 : value + 1);
            }
        }

        return cardValueFreq;
    }

    public Card[] getCards() {
        return cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HandRank getHandRank() {
        return rank;
    }
}
