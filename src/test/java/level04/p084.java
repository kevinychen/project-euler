
package level04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p084 extends EulerTest {

    final int N = 4;
    final int K = 3;
    final int L = 1000000;
    final String[] BOARD = ("GO A1 CC1 A2 T1 R1 B1 CH1 B2 B3 JAIL C1 U1 C2 C3 R2 D1 CC2 D2 D3 "
            + "FP E1 CH2 E2 E3 R3 F1 F2 U2 F3 G2J G1 G2 CC3 G3 R4 CH3 H1 T2 H2").split(" ");
    final int NUM_COMMUNITY_CHEST_CARDS = 16, NUM_CHANCE_CARDS = 16;

    /**
     * Find the 2K digit modal string that consists of the concatenation of the K most popular
     * Monopoly squares, with standard Monopoly rules, if two N-sided dice are used.
     * <p>
     * Use Monte Carlo simulation for L dice rolls.
     */
    @Test
    public void test() {
        int[] squareFreqs = new int[BOARD.length];
        int currSquare = 0;
        for (int i = 0; i < L; i++) {
            squareFreqs[currSquare]++;

            int dice1 = randRange(N) + 1, dice2 = randRange(N) + 1;
            currSquare = (currSquare + dice1 + dice2) % BOARD.length;

            if (BOARD[currSquare].startsWith("G2J"))
                currSquare = square("JAIL");
            else if (BOARD[currSquare].startsWith("CC")) {
                int ccCard = randRange(NUM_COMMUNITY_CHEST_CARDS);
                if (ccCard == 0)
                    currSquare = square("GO");
                else if (ccCard == 1)
                    currSquare = square("JAIL");
            } else if (BOARD[currSquare].startsWith("CH")) {
                int chCard = randRange(NUM_CHANCE_CARDS);
                if (chCard == 0)
                    currSquare = square("GO");
                else if (chCard == 1)
                    currSquare = square("JAIL");
                else if (chCard == 2)
                    currSquare = square("C1");
                else if (chCard == 3)
                    currSquare = square("E3");
                else if (chCard == 4)
                    currSquare = square("H2");
                else if (chCard == 5)
                    currSquare = square("R1");
                else if (chCard == 6 || chCard == 7)
                    do
                        currSquare = (currSquare + 1) % BOARD.length;
                    while (!BOARD[currSquare].startsWith("R"));
                else if (chCard == 8)
                    do
                        currSquare = (currSquare + 1) % BOARD.length;
                    while (!BOARD[currSquare].startsWith("U"));
                else if (chCard == 9)
                    currSquare = (currSquare + N - 3) % BOARD.length;
            }
        }

        List<Integer> topSquares = range(BOARD.length);
        topSquares.sort(Comparator.comparingInt(square -> -squareFreqs[square]));
        StringBuilder sb = sb();
        for (int i : range(K))
            sb.append(String.format("%02d", topSquares.get(i)));
        check(sb, "101524");
    }

    int randRange(int limit) {
        return (int) (Math.random() * limit);
    }

    int square(String s) {
        return Arrays.asList(BOARD).indexOf(s);
    }
}
