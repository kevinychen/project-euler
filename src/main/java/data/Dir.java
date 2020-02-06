
package data;

import lombok.Data;

@Data
public class Dir {

    public static final Dir[] CARDINALS = { new Dir(1, 0), new Dir(0, 1), new Dir(-1, 0), new Dir(0, -1) };
    public static final Dir[] ALL = { new Dir(1, 0), new Dir(1, 1), new Dir(0, 1), new Dir(-1, 1), new Dir(-1, 0),
            new Dir(-1, -1), new Dir(0, -1), new Dir(1, -1) };

    public final int di, dj;
    public char key;

    public Dir(int di, int dj) {
        this.di = di;
        this.dj = dj;

        if (di == 1 && dj == 0)
            key = 'D';
        if (di == 0 && dj == 1)
            key = 'R';
        if (di == -1 && dj == 0)
            key = 'U';
        if (di == 0 && dj == -1)
            key = 'L';
    }

    public boolean inBounds(int i, int j, int limit) {
        return inBounds(i, j, 1, limit);
    }

    public boolean inBounds(int i, int j, int mult, int limit) {
        return inBounds(i, j, mult, limit, limit);
    }

    public boolean inBounds(int i, int j, int mult, int limitI, int limitJ) {
        int newI = i + mult * di, newJ = j + mult * dj;
        return newI >= 0 && newI < limitI && newJ >= 0 && newJ < limitJ;
    }
}
