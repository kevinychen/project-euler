
package lib;

public class Zp {

    private final int pmod;
    private final long[] inverses;
    private final long[] factorials;
    private final long[] invFactorials;

    public Zp(int limit, int pmod) {
        this.inverses = EulerLib.modInvs(limit, pmod);
        this.factorials = EulerLib.factorials(limit, pmod);
        this.invFactorials = new long[limit + 1];
        this.pmod = pmod;

        invFactorials[0] = 1;
        for (int i = 1; i <= limit; i++)
            invFactorials[i] = invFactorials[i - 1] * inverses[i] % pmod;
    }

    public long inv(int n) {
        return inverses[n];
    }

    public long factorial(int n) {
        return factorials[n];
    }

    public long invFactorial(int n) {
        return invFactorials[n];
    }

    public long nCr(int n, int r) {
        if (r < 0 || r > n)
            return 0;
        return factorials[n] * invFactorials[r] % pmod * invFactorials[n - r] % pmod;
    }
}
