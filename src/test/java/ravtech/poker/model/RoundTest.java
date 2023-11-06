package ravtech.poker.model;

import com.ravtech.poker.model.Round;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoundTest {

    @Test
    void testEvaluateRoundWinnerNoTieNumericCardWin() {
        String input = "Black: 2H 3D 4S 5C 6D  White: 3S 3H 3C 5C 7D  Blue: 4H 5D 6S 7C 8D";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Blue wins. - with straight: 8", result);
    }

    @Test
    void testEvaluateRoundWinnerNoTieNamedCardWin() {
        String input = "Black: 2H 3D 4S 5C 6D  White: 9H TD JS QC KD";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("White wins. - with straight: King", result);
    }

    @Test
    void testEvaluateRoundWinnerNoTieFullHouseLowThreeWin() {
        String input = "Black: 2H 3D 4S 5C 6D  White: 3S 3H 3C 4C 4D  Blue: 9H TD JS QC KD";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("White wins. - with full house: 3 over 4", result);
    }

    @Test
    void testEvaluateRoundWinnerNoTieFullHouseHighThreeWin() {
        String input = "Black: 2H 3D 4S 5C 6D  White: 3S 3H 3C 2C 2D  Blue: 9H TD JS QC KD";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("White wins. - with full house: 3 over 2", result);
    }

    @Test
    void testEvaluateRoundWinnerNoTieRoyalFlushWin() {
        String input = "Black: TD JD QD KD AD  White: 3S 3H 3C 5C 7D  Blue: 2H 3D 4S 5C 6D";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Black wins. - with royal flush: Ace", result);
    }

    @Test
    void testEvaluateRoundWinnerTie() {
        String input = "Black: 2H 3D 4S 5C 7D  White: 2S 3H 4D 5C 6D  Blue: 2C 3H 4D 5C 6D";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("White & Blue tie. - with straight: 6", result);
    }

    @Test
    void testEvaluateRoundWinnerInvalidInput() {
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AHs";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Error: No enum constant com.ravtech.poker.model.Suit.Hs", result);
    }

    @Test
    void testEvaluateRoundWinnerInvalidEnumValue() {
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AHs";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Error: No enum constant com.ravtech.poker.model.Suit.Hs", result);
    }

    @Test
    void testEvaluateRoundWinnerInvalidCharValue() {
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 18C AH";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Error: Invalid Char Value: 1", result);
    }

    @Test
    void testEvaluateRoundWinnerInvalidCardCount() {
        String input = "Black: 2H 3D 5S 9C KD  White: 2C 3H 3C 4S 8C AH";
        String result = Round.evaluateRoundWinner(input);

        assertEquals("Error: A hand must have exactly 5 cards. Input cards count: 6", result);
    }
}
