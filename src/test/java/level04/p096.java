
package level04;

import java.util.List;

import org.junit.Test;

import lib.EulerTest;

public class p096 extends EulerTest {

    final int N = 9;
    final int K = isqrt(N);

    /**
     * Find the sum of the first three digits of each of the specified unsolved Sudokus, when
     * solved.
     */
    @Test
    public void test() {
        List<String> lines = read();
        for (int i = 0; i < lines.size(); i += N + 1) {
            int[][] sudoku = new int[N][N];
            UsedNums usedNums = new UsedNums();
            for (int row : range(N))
                for (int col : range(N)) {
                    int val = lines.get(i + 1 + row).charAt(col) - '0';
                    sudoku[row][col] = val;
                    usedNums.update(row, col, val, true);
                }
            helper(sudoku, 0, 0, usedNums);
        }
        check(24702);
    }

    void helper(int[][] sudoku, int row, int col, UsedNums usedNums) {
        if (row == N)
            ans += Integer.parseInt("" + sudoku[0][0] + sudoku[0][1] + sudoku[0][2]);
        else if (col == N)
            helper(sudoku, row + 1, 0, usedNums);
        else if (sudoku[row][col] != 0)
            helper(sudoku, row, col + 1, usedNums);
        else {
            for (int val : rangeC(1, N))
                if (usedNums.canPlace(row, col, val)) {
                    sudoku[row][col] = val;
                    usedNums.update(row, col, val, true);
                    helper(sudoku, row, col + 1, usedNums);
                    usedNums.update(row, col, val, false);
                }
            sudoku[row][col] = 0;
        }
    }

    class UsedNums {
        boolean[][] usedByRow = new boolean[N + 1][N + 1];
        boolean[][] usedByCol = new boolean[N + 1][N + 1];
        boolean[][] usedByBox = new boolean[N + 1][N + 1];

        void update(int row, int col, int val, boolean isUsed) {
            usedByRow[row][val] = isUsed;
            usedByCol[col][val] = isUsed;
            usedByBox[getBox(row, col)][val] = isUsed;
        }

        boolean canPlace(int row, int col, int val) {
            return !usedByRow[row][val] && !usedByCol[col][val] && !usedByBox[getBox(row, col)][val];
        }

        int getBox(int row, int col) {
            return iroundDown(row, K) + col / K;
        }
    }
}
