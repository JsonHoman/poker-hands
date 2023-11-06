package com.ravtech.poker.model;

import java.util.*;
import java.util.logging.Level;

import static com.ravtech.poker.Main.log;

public final class Round {
    private Round() {
        throw new AssertionError("This class should not be instantiated.");
    }

    public static String evaluateRoundWinner(String input) {
        String roundResult;

        try {
            List<Hand> hands = parseHands(input);

            Hand bestHand = hands.get(0);

            boolean isTie = false;
            int comparisonVal;
            Set<Hand> ties = new LinkedHashSet<>();
            for (int i = 1; i < hands.size(); i++) {
                Hand currentHand = hands.get(i);

                comparisonVal = compareHands(currentHand, bestHand);
                if (comparisonVal > 0) {
                    isTie = false;
                    ties.clear();

                    bestHand = currentHand;
                } else if (comparisonVal == 0) {
                    isTie = true;
                    ties.add(bestHand);
                    ties.add(currentHand);
                }
            }

            roundResult = constructRoundResult(isTie, ties, bestHand);

            log(Level.INFO, roundResult);

        } catch (Exception e) {
            roundResult = "Error: " + e.getMessage();

            log(Level.SEVERE, "An exception occurred: " + e);
        }

        return roundResult;
    }

    private static int compareHands(Hand hand1, Hand hand2) {
        HandRank rank1 = hand1.evaluateHand();
        HandRank rank2 = hand2.evaluateHand();

        int handTypeComparison = rank1.handType().getHandTypeGrade() - rank2.handType().getHandTypeGrade();

        if (handTypeComparison != 0) {
            return handTypeComparison;
        }

        return rank1.highestValue() - rank2.highestValue();
    }

    private static String constructRoundResult(boolean isTie, Set<Hand> ties, Hand bestHand) throws IllegalArgumentException {
        String roundResult;
        String playerNames;

        if (isTie) {
            StringBuilder tieResult = new StringBuilder();
            for (Hand hand : ties) {
                tieResult.append(hand.getPlayer().name()).append(" & ");
            }
            tieResult.setLength(tieResult.length() - 3); // Remove the trailing " & "
            tieResult.append(" tie.");
            playerNames = tieResult.toString();
        } else {
            playerNames = bestHand.getPlayer().name();
        }

        String handType = HandType.convertToTitleCase(bestHand.getHandRank().handType().toString());
        String highestValue = Card.convertValueToString(bestHand.getHandRank().highestValue());

        if (bestHand.getHandRank().handType() == HandType.FULL_HOUSE) {
            Card[] bestHandCards = bestHand.getCards();
            int highCardValue = bestHand.getHandRank().highestValue();
            int lowerPairValue = Integer.MAX_VALUE;

            for (Card card : bestHandCards) {
                if (card.value() != highCardValue && card.value() < lowerPairValue) {
                    lowerPairValue = card.value();
                }
            }

            roundResult = String.format("%s wins. - with %s: %s over %s", playerNames, handType, highestValue, lowerPairValue);
        } else if (bestHand.getHandRank().handType() == HandType.STRAIGHT_FLUSH) {
            roundResult = String.format("%s wins. - with %s: %s", playerNames, "royal flush", highestValue);
        } else if (!isTie) {
            roundResult = String.format("%s wins. - with %s: %s", playerNames, handType, highestValue);
        } else {
            roundResult = String.format("%s - with %s: %s", playerNames, handType, highestValue);
        }

        return roundResult;
    }

    private static List<Hand> parseHands(String input) {
        String[] playerHands = input.split(" {2}");

        return Arrays.stream(playerHands)
                .map(playerHand -> {
                    String[] parts = playerHand.split(":");
                    String playerName = parts[0].trim();
                    Player player = new Player(playerName);
                    String[] cardStrings = parts[1].trim().split(" ");

                    Card[] cards = Arrays.stream(cardStrings)
                            .map(Round::parseCard)
                            .toArray(Card[]::new);

                    return new Hand(cards, player);
                })
                .toList();
    }

    private static Card parseCard(String cardString) {
        int value = Card.convertValueToInt(cardString.charAt(0));
        Suit suit = Suit.valueOf(cardString.substring(1));

        return new Card(value, suit);
    }
}
