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

        int handTypeComparison = rank1.getHandType().getHandTypeGrade() - rank2.getHandType().getHandTypeGrade();

        if (handTypeComparison != 0) {
            return handTypeComparison;
        // A HIGH CARD tie is a special scenario.
        } else if (rank1.getHandType() == HandType.HIGH_CARD && rank2.getHandType() == HandType.HIGH_CARD) {
            return getHighestCardTieComparison(hand1, hand2);
        }

        return rank1.getHighestValue() - rank2.getHighestValue();
    }

    private static int getHighestCardTieComparison(Hand hand1, Hand hand2) {
        Card[] hand1Cards = hand1.getCards();
        Card[] hand2Cards = hand2.getCards();

        int highestValComparison = 0;
        // Cards are sorted in ascending order, so we decrement our comparison values.
        for (int i = Hand.HAND_SIZE - 1; i >= 0; i--) {
            highestValComparison = hand1Cards[i].value() - hand2Cards[i].value();

            // If we find a comparison to break the tie we return it immediately.
            if (highestValComparison > 0) {
                hand1.getHandRank().setTieBreakerValue(hand1Cards[i].value());

                return highestValComparison;
            } else if (highestValComparison < 0) {
                hand2.getHandRank().setTieBreakerValue(hand2Cards[i].value());

                return highestValComparison;
            }
        }

        return highestValComparison;
    }

    private static String constructRoundResult(boolean isTie, Set<Hand> ties, Hand bestHand) throws IllegalArgumentException {
        String roundResult;
        String playerNames = getPlayerNames(isTie, ties, bestHand);

        HandRank bestHandRank = bestHand.getHandRank();
        HandType handType = bestHandRank.getHandType();
        String handTypeStr = HandType.convertToTitleCase(handType.toString());
        String highestValueStr = Card.convertValueToString(bestHandRank.getHighestValue());

        switch (handType) {
            case FULL_HOUSE:
                Card[] bestHandCards = bestHand.getCards();
                int highCardValue = bestHandRank.getHighestValue();
                int lowerPairValue = Integer.MAX_VALUE;

                for (Card card : bestHandCards) {
                    if (card.value() != highCardValue && card.value() < lowerPairValue) {
                        lowerPairValue = card.value();
                    }
                }

                roundResult = String.format("%s wins. - with %s: %s over %s", playerNames, handTypeStr, highestValueStr, lowerPairValue);
                break;
            case STRAIGHT_FLUSH:
                roundResult = String.format("%s wins. - with %s: %s", playerNames, "royal flush", highestValueStr);
                break;
            case HIGH_CARD:
                if (!isTie) {
                    int tieBreakerValue = bestHandRank.getTieBreakerValue();
                    String valueStr = (tieBreakerValue != 0) ? Card.convertValueToString(tieBreakerValue) : highestValueStr;
                    roundResult = String.format("%s wins. - with %s: %s", playerNames, handTypeStr, valueStr);
                } else {
                    roundResult = "Tie.";
                }
                break;
            default:
                roundResult = (isTie) ? "Tie." :
                        String.format("%s wins. - with %s: %s", playerNames, handTypeStr, highestValueStr);
                break;
        }

        return roundResult;
    }

    private static String getPlayerNames(boolean isTie, Set<Hand> ties, Hand bestHand) {
        String playerNames;

        if (isTie) {
            StringBuilder tieResult = new StringBuilder();

            for (Hand hand : ties) {
                tieResult.append(hand.getPlayer().name()).append(" & ");
            }

            tieResult.setLength(tieResult.length() - 3); // Remove the trailing " & "

            playerNames = tieResult.toString();
        } else {
            playerNames = bestHand.getPlayer().name();
        }

        return playerNames;
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
