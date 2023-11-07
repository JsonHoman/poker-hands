package com.ravtech.poker.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HandTest {

    @Test
    void testEvaluateHandStraightFlush() {
        // given
        Hand hand = createHandWithStraightFlush();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.STRAIGHT_FLUSH, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandPair() {
        // given
        Hand hand = createHandWithPair();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.PAIR, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandThreeOfAKind() {
        // given
        Hand hand = createHandWithThreeOfAKind();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.THREE_OF_A_KIND, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandFourOfAKind() {
        // given
        Hand hand = createHandWithFourOfAKind();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.FOUR_OF_A_KIND, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandTwoPair() {
        // given
        Hand hand = createHandWithTwoPair();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.TWO_PAIR, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandFullHouseLowerThree() {
        // High Card will be derived from the 3 low cards.

        // given
        Hand hand = createHandWithFullHouseLowerThree();
        int expectedHighCardVal = 4;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.FULL_HOUSE, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandFullHouseHigherThree() {
        // High Card will be derived from the 3 high cards.

        // given
        Hand hand = createHandWithFullHouseHigherThree();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.FULL_HOUSE, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandFlush() {
        // given
        Hand hand = createHandWithFlush();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.FLUSH, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandStraight() {
        // given
        Hand hand = createHandWithStraight();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.STRAIGHT, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    @Test
    void testEvaluateHandHighCard() {
        // given
        Hand hand = createHandWithHighCard();
        int expectedHighCardVal = 8;

        // when
        HandRank result = hand.evaluateHand();

        // then
        assertEquals(HandType.HIGH_CARD, result.getHandType());
        assertEquals(expectedHighCardVal, result.getHighestValue());
    }

    private Hand createHandWithStraightFlush() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.S);
        cards[1] = new Card(5, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(7, Suit.S);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithPair() {
        Card[] cards = new Card[5];

        cards[0] = new Card(3, Suit.C);
        cards[1] = new Card(4, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(8, Suit.H);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithThreeOfAKind() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.C);
        cards[1] = new Card(5, Suit.S);
        cards[2] = new Card(8, Suit.D);
        cards[3] = new Card(8, Suit.S);
        cards[4] = new Card(8, Suit.H);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithFourOfAKind() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.C);
        cards[1] = new Card(8, Suit.S);
        cards[2] = new Card(8, Suit.D);
        cards[3] = new Card(8, Suit.H);
        cards[4] = new Card(8, Suit.C);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithTwoPair() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.C);
        cards[1] = new Card(4, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(8, Suit.C);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithFullHouseHigherThree() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.C);
        cards[1] = new Card(4, Suit.S);
        cards[2] = new Card(8, Suit.S);
        cards[3] = new Card(8, Suit.C);
        cards[4] = new Card(8, Suit.D);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithFullHouseLowerThree() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.C);
        cards[1] = new Card(4, Suit.S);
        cards[2] = new Card(4, Suit.D);
        cards[3] = new Card(8, Suit.C);
        cards[4] = new Card(8, Suit.D);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithFlush() {
        Card[] cards = new Card[5];

        cards[0] = new Card(3, Suit.S);
        cards[1] = new Card(5, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(7, Suit.S);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithStraight() {
        Card[] cards = new Card[5];

        cards[0] = new Card(4, Suit.D);
        cards[1] = new Card(5, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(7, Suit.S);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }

    private Hand createHandWithHighCard() {
        Card[] cards = new Card[5];

        cards[0] = new Card(3, Suit.D);
        cards[1] = new Card(5, Suit.S);
        cards[2] = new Card(6, Suit.S);
        cards[3] = new Card(7, Suit.S);
        cards[4] = new Card(8, Suit.S);

        return new Hand(cards, new Player("TestName"));
    }
}