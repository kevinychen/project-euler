
package data;

import java.util.Map;
import java.util.TreeMap;

import lib.EulerLib;
import one.util.streamex.EntryStream;

public class RadicalSum extends EulerLib implements Comparable<RadicalSum> {

    /**
     * This map represents the sum(c√r) where the sum goes over all entries (r, c). All r are
     * positive square-free integers.
     */
    private final TreeMap<Long, Long> radicalToCoefficients;

    public RadicalSum(Map<Long, Long> radicalToCoefficients) {
        this.radicalToCoefficients = tmap();
        radicalToCoefficients.forEach((r, c) -> {
            if (r > 0) {
                long coeff = EntryStream.of(lprimeFactor(r))
                    .mapKeyValue((p, e) -> pow(p, e / 2))
                    .reduce(1L, (x, y) -> x * y);
                addToMap(this.radicalToCoefficients, r / sq(coeff), c * coeff);
            }
        });
        map(this.radicalToCoefficients).forEach((r, c) -> {
            if (c == 0)
                this.radicalToCoefficients.remove(r);
        });
    }

    public static RadicalSum integer(long n) {
        return new RadicalSum(map(1L, n));
    }

    public static RadicalSum sqrt(long n) {
        return new RadicalSum(map(n, 1L));
    }

    public double doubleValue() {
        return EntryStream.of(radicalToCoefficients)
            .mapKeyValue((r, c) -> c * Math.sqrt(r))
            .reduce(0., Double::sum);
    }

    public RadicalSum add(RadicalSum other) {
        Map<Long, Long> newRadicalToCoefficients = map(radicalToCoefficients);
        other.radicalToCoefficients.forEach((r, c) -> addToMap(newRadicalToCoefficients, r, c));
        return new RadicalSum(newRadicalToCoefficients);
    }

    public RadicalSum negate() {
        Map<Long, Long> newRadicalToCoefficients = map();
        radicalToCoefficients.forEach((r, c) -> newRadicalToCoefficients.put(r, -c));
        return new RadicalSum(newRadicalToCoefficients);
    }

    public RadicalSum subtract(RadicalSum other) {
        return add(other.negate());
    }

    @Override
    public String toString() {
        if (radicalToCoefficients.isEmpty())
            return "0";
        StringBuilder sb = sb();
        radicalToCoefficients.forEach((r, c) -> {
            if (sb.length() == 0)
                sb.append(c);
            else
                sb.append((c > 0 ? " + " : " - ") + Math.abs(c));
            if (r > 1)
                sb.append("√" + r);
        });
        return sb.toString();
    }

    @Override
    public int compareTo(RadicalSum o) {
        return Double.compare(doubleValue(), o.doubleValue());
    }
}
