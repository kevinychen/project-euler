
package lib;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import data.BPoint;
import data.LPoint;
import lombok.Data;

/**
 * Solve various Pell equations of the form x² - D y² = N.
 * <p>
 * http://www.jpr2718.org/pell.pdf
 */
public final class Pell extends EulerLib {

    /**
     * Solve for all positive solutions of x² - D y² = 1.
     */
    public static Generator<LPoint> standard(long D) {
        return new Generator<LPoint>() {
            @Override
            public void generateUntil(Function<LPoint, Boolean> f) {
                List<PQaStep> steps = list();
                PQa(0, D, 1).generateUntil(step -> {
                    steps.add(step);
                    return step.a == 2 * steps.get(0).a;
                });
                removeLast(steps);
                LPoint baseSol = new LPoint(last(steps).G, last(steps).B), sol = baseSol;
                for (int e = 1; true; e++) {
                    if ((steps.size() % 2 == 0 || e % 2 == 0) && f.apply(sol))
                        break;
                    sol = multiply(sol, baseSol, D);
                }
            }
        };
    }

    /**
     * Solve for all positive solutions of x² - D y² = -1.
     */
    public static Generator<LPoint> negative(long D) {
        return new Generator<LPoint>() {
            @Override
            public void generateUntil(Function<LPoint, Boolean> f) {
                List<PQaStep> steps = list();
                PQa(0, D, 1).generateUntil(step -> {
                    steps.add(step);
                    return step.a == 2 * steps.get(0).a;
                });
                removeLast(steps);
                LPoint baseSol = new LPoint(last(steps).G, last(steps).B), sol = baseSol;
                if (steps.size() % 2 == 0)
                    return;
                for (int e = 1; true; e++) {
                    if (e % 2 == 1 && f.apply(sol))
                        break;
                    sol = multiply(sol, baseSol, D);
                }
            }
        };
    }

    /**
     * Solve for all positive solutions of x² - D y² = -4.
     */
    public static Generator<LPoint> negativeFermatDifferenceEquation(long D) {
        return new Generator<LPoint>() {
            @Override
            public void generateUntil(Function<LPoint, Boolean> f) {
                List<PQaStep> steps = list();
                PQa(1, D, 2).generateUntil(step -> {
                    steps.add(step);
                    return step.a == 2 * steps.get(0).a - 1 && steps.size() > 1;
                });
                removeLast(steps);
                LPoint baseSol = new LPoint(last(steps).G, last(steps).B), sol = baseSol;
                if (steps.size() % 2 == 0)
                    return;
                for (int e = 1; true; e++) {
                    if (e % 2 == 1 && f.apply(sol))
                        break;
                    sol = multiply(sol, baseSol, D).divide(2);
                }
            }
        };
    }

    /**
     * Solve for all positive solutions of x² - D y² = N.
     */
    public static Generator<BPoint> general(long D, long N) {
        return general(D, stdSol -> {
            int L1, L2;
            if (N > 0) {
                L1 = 0;
                L2 = isqrt((double) N * (stdSol.x - 1) / (2 * D));
            } else {
                L1 = isqrt(-N / D);
                L2 = isqrt(-(double) N * (stdSol.x + 1) / (2 * D));
            }
            Set<LPoint> baseSols = set();
            for (long y = L1; y <= L2; y++)
                if (isSq(N + D * sq(y)))
                    baseSols.add(new LPoint(isqrt(N + D * sq(y)), y));
            return baseSols;
        });
    }

    /**
     * Solve for all positive solutions of x² - D y² = N, using the custom function to generate base
     * solutions.
     */
    public static Generator<BPoint> general(long D, Function<LPoint, Set<LPoint>> stdSolToBaseSols) {
        return new Generator<BPoint>() {
            @Override
            public void generateUntil(Function<BPoint, Boolean> f) {
                LPoint stdSol = standard(D).get(0);
                Set<LPoint> baseSols = stdSolToBaseSols.apply(stdSol);
                List<BPoint> sols = list();
                for (LPoint sol : baseSols) {
                    if (sol.y > 0)
                        sols.add(BPoint.fromPoint(sol));
                    LPoint conjSol = multiply(sol.reflectX(), stdSol, D);
                    if (conjSol.x < 0)
                        conjSol = conjSol.negate();
                    sols.add(BPoint.fromPoint(conjSol));
                }
                sols.sort((sol1, sol2) -> sol1.x.compareTo(sol2.x));
                while (!sols.isEmpty()) {
                    for (BPoint sol : sols)
                        if (f.apply(sol))
                            return;
                    for (int i = 0; i < sols.size(); i++)
                        sols.set(i, multiply(sols.get(i), stdSol, D));
                }
            }
        };
    }

    /**
     * Return (x, y) where x+y√D = (p1.x+p1.y√D)(p2.x+p2.y√D).
     */
    private static LPoint multiply(LPoint p1, LPoint p2, long D) {
        return new LPoint(p1.x * p2.x + D * p1.y * p2.y, p1.x * p2.y + p1.y * p2.x);
    }

    private static BPoint multiply(BPoint p1, LPoint p2, long D) {
        return new BPoint(
            p1.x.multiply(big(p2.x)).add(big(D).multiply(p1.y).multiply(big(p2.y))),
            p1.x.multiply(big(p2.y)).add(p1.y.multiply(big(p2.x))));
    }

    /**
     * Run the PQa algorithm for (P0 + √D) / Q0. At each iteration, a is the next integer in the
     * continued fraction, G/B is the next convergent, and (P + √D) / Q is the remaining value.
     */
    public static Generator<PQaStep> PQa(long P0, long D, long Q0) {
        if (mod(sq(P0), Q0) != mod(D, Q0))
            die();

        long sqrtD = isqrt(D);
        return new Generator<PQaStep>() {
            List<Long> As = list(0L, 1L), Bs = list(1L, 0L), Gs = list(-P0, Q0);
            long P = P0, Q = Q0;

            @Override
            public void generateUntil(Function<PQaStep, Boolean> f) {
                PQaStep step;
                do {
                    long a = (P + sqrtD) / Q;
                    long A = a * last(As) + penult(As);
                    long B = a * last(Bs) + penult(Bs);
                    long G = a * last(Gs) + penult(Gs);
                    As.add(A);
                    Bs.add(B);
                    Gs.add(G);
                    step = new PQaStep(a, A, B, G, P, Q);
                    P = a * Q - P;
                    Q = (D - sq(P)) / Q;
                } while (!f.apply(step));
            }
        };
    }

    @Data
    public static class PQaStep {
        public final long a;
        public final long A, B, G;
        public final long P, Q;
    }
}
