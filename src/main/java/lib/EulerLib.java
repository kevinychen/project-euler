
package lib;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import data.FPoint;
import data.FPolynomial;
import data.LFraction;
import data.LPoint;
import data.LPolynomial;
import data.LTriangle;
import one.util.streamex.EntryStream;

/**
 * Common functions in all areas that can be conveniently called from all tests that subclass
 * {@link EulerLib}.
 */
public class EulerLib {

    /*********************************************************************************
     * PROGRAMMING UTILS.
     *********************************************************************************/

    public static String toString(Object o) {
        if (o instanceof long[])
            return Arrays.toString((long[]) o);
        else if (o instanceof long[][])
            return join(Arrays.asList((long[][]) o), "\n");
        else if (o instanceof int[])
            return Arrays.toString((int[]) o);
        else if (o instanceof int[][])
            return join(Arrays.asList((int[][]) o), "\n");
        else if (o instanceof double[])
            return Arrays.toString((double[]) o);
        else if (o instanceof double[][])
            return join(Arrays.asList((double[][]) o), "\n");
        else if (o instanceof boolean[])
            return Arrays.toString((boolean[]) o);
        else if (o instanceof boolean[][])
            return join(Arrays.asList((boolean[][]) o), "\n");
        else if (o instanceof char[])
            return new String((char[]) o);
        else if (o instanceof char[][])
            return join(Arrays.asList((char[][]) o), "\n");
        else if (o instanceof Object[])
            return Arrays.toString((Object[]) o);
        else if (o instanceof Collection)
            return Collections2.transform((Collection<?>) o, EulerLib::toString).toString();
        return o + "";
    }

    public static void pr(Object... objs) {
        System.out.println(join(Arrays.asList(objs), " "));
    }

    public static IllegalArgumentException die() {
        throw new IllegalArgumentException("death");
    }

    public static <K, V> V memoize(Map<K, V> cache, K key, Function<K, V> f) {
        if (!cache.containsKey(key))
            cache.put(key, f.apply(key));
        return cache.get(key);
    }

    public static String join(List<?> objs) {
        return join(objs, "");
    }

    public static String join(List<?> objs, String delimiter) {
        return Joiner.on(delimiter).join(Lists.transform(objs, EulerLib::toString));
    }

    public static BigInteger big(Object o) {
        return new BigInteger(o + "");
    }

    public static BigDecimal bigd(Object o) {
        return new BigDecimal(o + "");
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    @SafeVarargs
    public static <T> List<T> list(T... objs) {
        return new ArrayList<>(Arrays.asList(objs));
    }

    public static <T> List<T> list(Collection<T> list) {
        return new ArrayList<>(list);
    }

    public static <T> LinkedList<T> llist() {
        return new LinkedList<>();
    }

    public static <T> LinkedList<T> llist(Collection<T> list) {
        return new LinkedList<>(list);
    }

    @SafeVarargs
    public static <T> LinkedList<T> llist(T... objs) {
        return new LinkedList<>(Arrays.asList(objs));
    }

    public static <T> Set<T> set() {
        return new HashSet<>();
    }

    @SafeVarargs
    public static <T> Set<T> set(T... objs) {
        return new HashSet<>(Arrays.asList(objs));
    }

    public static <T> Set<T> set(Collection<T> set) {
        return new HashSet<>(set);
    }

    public static <T extends Comparable<T>> TreeSet<T> tset() {
        return new TreeSet<>();
    }

    public static <T extends Comparable<T>> TreeSet<T> tset(T... objs) {
        return new TreeSet<>(Arrays.asList(objs));
    }

    public static <T extends Comparable<T>> TreeSet<T> tset(Collection<T> set) {
        return new TreeSet<>(set);
    }

    public static <T> Multiset<T> mset() {
        return HashMultiset.create();
    }

    public static <T> Multiset<T> mset(Iterable<T> set) {
        return HashMultiset.create(set);
    }

    public static <T extends Comparable<T>> TreeMultiset<T> tmset() {
        return TreeMultiset.create();
    }

    public static <T extends Comparable<T>> TreeMultiset<T> tmset(Iterable<T> set) {
        return TreeMultiset.create(set);
    }

    public static <T, U> Map<T, U> map() {
        return new HashMap<>();
    }

    public static <T, U> Map<T, U> map(Map<T, U> map) {
        return new HashMap<>(map);
    }

    @SuppressWarnings("unchecked")
    public static <T, U> Map<T, U> map(Object... objs) {
        Map<T, U> map = map();
        for (int i = 0; i < objs.length; i += 2)
            map.put((T) objs[i], (U) objs[i + 1]);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T, U> LinkedHashMap<T, U> lmap(Object... objs) {
        LinkedHashMap<T, U> map = new LinkedHashMap<>();
        for (int i = 0; i < objs.length; i += 2)
            map.put((T) objs[i], (U) objs[i + 1]);
        return map;
    }

    public static <T extends Comparable<T>, U> TreeMap<T, U> tmap() {
        return new TreeMap<>();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>, U> TreeMap<T, U> tmap(Object... objs) {
        TreeMap<T, U> map = tmap();
        for (int i = 0; i < objs.length; i += 2)
            map.put((T) objs[i], (U) objs[i + 1]);
        return map;
    }

    public static <T, U> ListMultimap<T, U> mmap() {
        return ArrayListMultimap.create();
    }

    public static <T, U> BiMap<T, U> bmap() {
        return HashBiMap.create();
    }

    public static StringBuilder sb() {
        return new StringBuilder();
    }

    public static StringBuilder sb(Object o) {
        return new StringBuilder(o + "");
    }

    /*********************************************************************************
     * BASIC ARITHMETIC.
     *********************************************************************************/

    public static int imod(long n, int mod) {
        int res = (int) (n % mod);
        if (res < 0)
            res += mod;
        return res;
    }

    public static long mod(long n, long mod) {
        long res = n % mod;
        if (res < 0)
            res += mod;
        return res;
    }

    public static int isq(int n) {
        return n * n;
    }

    public static long sq(long n) {
        return n * n;
    }

    public static long sq(long n, long mod) {
        return sq(mod(n, mod)) % mod;
    }

    public static double fsq(double n) {
        return n * n;
    }

    public static boolean isSq(long n) {
        return sq(isqrt(n)) == n;
    }

    public static boolean isSq(long n, long pmod) {
        return pow(n, (pmod - 1) / 2, pmod) <= 1;
    }

    public static long cb(long n) {
        return n * n * n;
    }

    public static long cb(long n, long mod) {
        return n * n % mod * n % mod;
    }

    public static double fcb(double n) {
        return n * n * n;
    }

    public static int itr(int n) {
        return n * (n + 1) / 2;
    }

    public static long tr(long n) {
        return n * (n + 1) / 2;
    }

    public static long tr(long n, long mod) {
        if (n % 2 == 0)
            return (n / 2) % mod * ((n + 1) % mod) % mod;
        else
            return ((n + 1) / 2 % mod) * (n % mod) % mod;
    }

    public static long figurate(long n, long numSides) {
        return n * ((numSides - 2) * n - numSides + 4) / 2;
    }

    public static int ipow(int a, long b) {
        if (b == 0)
            return 1;
        int res = ipow(a, b / 2);
        return isq(res) * (b % 2 == 0 ? 1 : a);
    }

    public static long pow(long a, long b) {
        if (b == 0)
            return 1;
        long res = pow(a, b / 2);
        return sq(res) * (b % 2 == 0 ? 1 : a);
    }

    public static long pow(long a, long b, long mod) {
        if (b == 0)
            return 1;
        else if (b == 1)
            return mod(a, mod);
        else if (b == 2)
            return sq(a, mod);
        a %= mod;
        long res = 1;
        for (long bit = Long.highestOneBit(b); bit > 0; bit /= 2) {
            res = res * res % mod;
            if ((b & bit) > 0)
                res = res * a % mod;
        }
        if (res < 0)
            res += mod;
        return res;
    }

    public static int isqrt(double r) {
        return (int) Math.sqrt(r);
    }

    public static boolean isProbablePrime(Object n) {
        return big(n).isProbablePrime(32);
    }

    public static long[] pows(long base, int limit) {
        return pows(base, limit, Long.MAX_VALUE);
    }

    public static long[] pows(long base, int limit, long mod) {
        long[] pows = new long[limit + 1];
        pows[0] = 1;
        for (int i = 1; i <= limit; i++)
            pows[i] = pows[i - 1] * base % mod;
        return pows;
    }

    public static double[] fpows(double base, int limit) {
        double[] pows = new double[limit + 1];
        pows[0] = 1;
        for (int i = 1; i <= limit; i++)
            pows[i] = pows[i - 1] * base;
        return pows;
    }

    public static long[] nthPows(int limit, long exp, long mod) {
        preff(limit);
        long[] pows = new long[limit + 1];
        for (int i = 1; i <= limit; i++)
            if (ff[i] == i)
                pows[i] = pow(i, exp, mod);
            else
                pows[i] = pows[ff[i]] * pows[i / ff[i]] % mod;
        return pows;
    }

    public static long ceil(long a, long b) {
        return (a + b - 1) / b;
    }

    public static int parity(long n) {
        return n % 2 == 0 ? 1 : -1;
    }

    public static long roundUp(long n, long k) {
        return ceil(n, k) * k;
    }

    public static long roundDown(long n, long k) {
        return n / k * k;
    }

    public static int iroundDown(int n, int k) {
        return n / k * k;
    }

    public static boolean feq(double a, double b) {
        return Math.abs(a - b) < 1e-13;
    }

    public static int iceilPow(int n, int base) {
        return ipow(base, (int) Math.ceil(Math.log(n) / Math.log(base)));
    }

    public static int ilog2(double n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    /**
     * Given log(X) and log(Y), returns log(X+Y). Useful for adding very large numbers.
     */
    public static double logSum(double logX, double logY) {
        if (logX < logY)
            return logSum(logY, logX);
        return logX + Math.log(1 + Math.exp(logY - logX));
    }

    /**
     * Fast (but unchecked) version of {@link Math#hypot}.
     */
    public static double hypot(double a, double b) {
        return Math.sqrt(a * a + b * b);
    }

    /*********************************************************************************
     * AD HOC FUNCTIONS.
     *********************************************************************************/

    public static List<Long> fibonaccis() {
        List<Long> fibonaccis = list(0L, 1L);
        for (int i = 0; i < 90; i++)
            fibonaccis.add(penult(fibonaccis) + last(fibonaccis));
        return fibonaccis;
    }

    public static List<Long> fibonaccis(int limit, long mod) {
        List<Long> fibonaccis = list(0L, 1L);
        for (int i = 0; i < limit; i++)
            fibonaccis.add((penult(fibonaccis) + last(fibonaccis)) % mod);
        return fibonaccis;
    }

    public static long fibonacci(int n) {
        return fibonaccis().get(n);
    }

    public static long fibonacci(long n, long mod) {
        long a = 0, b = 1;
        for (long bit = Long.highestOneBit(n) >> 1; bit > 0; bit >>= 1) {
            long new_a = b * b + a * a;
            long new_b = b * b + 2 * a * b;
            if ((n & bit) > 0) {
                new_b += new_a;
                new_a = new_b - new_a;
            }
            a = new_a % mod;
            b = new_b % mod;
        }
        return b;
    }

    public static boolean isPalindrome(Object o) {
        String s = o + "";
        return (sb(s).reverse() + "").equals(s);
    }

    public static Generator<LTriangle> pythagoreanTriples(long maxPerim) {
        return pythagoreanTriples((m, n, k) -> k * 2 * m * (m + n) <= maxPerim);
    }

    public static Generator<LTriangle> pythagoreanTriples(PythagoreanTriplePredicate predicate) {
        return f -> {
            for (long n = 1; predicate.verify(n, n, 1); n++)
                for (long m = n + 1; predicate.verify(m, n, 1); m += 2)
                    if (gcd(m, n) == 1)
                        for (long k = 1; predicate.verify(m, n, k); k++) {
                            long a = k * (m * m - n * n);
                            long b = k * (2 * m * n);
                            long c = k * (m * m + n * n);
                            if (f.apply(new LTriangle(a, b, c)))
                                return;
                        }
        };
    }

    public interface PythagoreanTriplePredicate {

        boolean verify(long m, long n, long k);
    }

    public static int wordValue(String s) {
        int wordValue = 0;
        for (char c : s.toCharArray())
            wordValue += c - 'A' + 1;
        return wordValue;
    }

    public static List<Integer> digits(long n) {
        List<Integer> digits = list();
        while (n > 0) {
            digits.add((int) (n % 10));
            n /= 10;
        }
        return digits;
    }

    public static List<Integer> digits(Object n) {
        List<Integer> digits = list();
        for (char c : (n + "").toCharArray())
            digits.add(c - '0');
        return digits;
    }

    public static int sumDigits(Object n) {
        int sumDigits = 0;
        for (int digit : digits(n))
            sumDigits += digit;
        return sumDigits;
    }

    public static <T> Generator<List<T>> permutations(List<T> objs) {
        return new Generator<List<T>>() {
            @Override
            public void generateUntil(Function<List<T>, Boolean> f) {
                List<T> permutation = list();
                List<T> remaining = list(objs);
                helper(permutation, remaining, f);
            }

            boolean helper(List<T> permutation, List<T> remaining, Function<List<T>, Boolean> f) {
                if (remaining.isEmpty())
                    return f.apply(permutation);
                for (int i = 0; i < remaining.size(); i++) {
                    T temp = remaining.remove(i);
                    permutation.add(temp);
                    if (helper(permutation, remaining, f))
                        return true;
                    remaining.add(i, temp);
                    removeLast(permutation);
                }
                return false;
            }
        };
    }

    public static <T extends Comparable<T>> boolean isPermutation(List<T> list1, List<T> list2) {
        List<T> list1Copy = list(list1);
        List<T> list2Copy = list(list2);
        Collections.sort(list1Copy);
        Collections.sort(list2Copy);
        return list1Copy.equals(list2Copy);
    }

    public static <T> Generator<List<T>> combinations(List<T> objs, int size) {
        return new Generator<List<T>>() {
            @Override
            public void generateUntil(Function<List<T>, Boolean> f) {
                List<T> combination = list();
                List<T> remaining = list(objs);
                Collections.reverse(remaining);
                helper(combination, remaining, f);
            }

            boolean helper(List<T> combination, List<T> remaining, Function<List<T>, Boolean> f) {
                if (combination.size() == size)
                    return f.apply(combination);
                LinkedList<T> temps = llist();
                while (combination.size() + remaining.size() >= size) {
                    T temp = removeLast(remaining);
                    combination.add(temp);
                    temps.addFirst(temp);
                    if (helper(combination, remaining, f))
                        return true;
                    removeLast(combination);
                }
                remaining.addAll(temps);
                return false;
            }
        };
    }

    public static boolean isPandigital(String s, int start, int end) {
        if (s.length() != end - start + 1)
            return false;
        boolean[] used = new boolean[10];
        for (char c : s.toCharArray())
            used[c - '0'] = true;
        for (int i = start; i <= end; i++)
            if (!used[i])
                return false;
        return true;
    }

    public static Generator<Long> numPartitions() {
        return numPartitions(Long.MAX_VALUE);
    }

    /**
     * Return the number of partitions of all natural numbers n starting from 0. A partition of n is
     * a set of one or more positive integers that sum to n.
     * <p>
     * Computing P_n takes time O(n^1.5). https://en.wikipedia.org/wiki/Pentagonal_number_theorem
     */
    public static Generator<Long> numPartitions(long mod) {
        return new Generator<Long>() {
            final List<Long> allNumPartitions = list();

            @Override
            public void generateUntil(Function<Long, Boolean> f) {
                for (int i = 0; true; i++) {
                    long numPartitions = numPartitions(i);
                    if (f.apply(numPartitions))
                        return;
                    allNumPartitions.add(numPartitions);
                }
            }

            long numPartitions(int n) {
                if (n == 0)
                    return 1;
                long numPartitions = 0;
                for (int k = 1; true; k++) {
                    int diff1 = k * (3 * k + 1) / 2;
                    if (diff1 <= n)
                        numPartitions -= parity(k) * allNumPartitions.get(n - diff1);
                    int diff2 = k * (3 * k - 1) / 2;
                    if (diff2 <= n)
                        numPartitions -= parity(k) * allNumPartitions.get(n - diff2);
                    else
                        return numPartitions % mod;
                }
            }
        };
    }

    public static <T> Generator<List<T>> cartesianProduct(List<T> axis, int numAxes) {
        return cartesianProduct(Collections.nCopies(numAxes, axis));
    }

    public static <T> Generator<List<T>> cartesianProduct(List<List<T>> axes) {
        return new Generator<List<T>>() {
            @Override
            public void generateUntil(Function<List<T>, Boolean> f) {
                List<T> cartesianProduct = list();
                helper(cartesianProduct, 0, f);
            }

            boolean helper(List<T> cartesianProduct, int index, Function<List<T>, Boolean> f) {
                if (index == axes.size())
                    return f.apply(cartesianProduct);
                for (T obj : axes.get(index)) {
                    cartesianProduct.add(obj);
                    if (helper(cartesianProduct, index + 1, f))
                        return true;
                    removeLast(cartesianProduct);
                }
                return false;
            }
        };
    }

    public static <T> void addToMap(Map<T, Long> map, T key, long dval) {
        addToMap(map, key, dval, Long.MAX_VALUE);
    }

    public static <T> void addToMap(Map<T, Long> map, T key, long dval, long mod) {
        map.compute(key, (_key, val) -> val == null ? dval : (val + dval) % mod);
    }

    public static <T> Map<T, Integer> indexMap(Iterable<T> objs) {
        int index = 0;
        Map<T, Integer> indexMap = map();
        for (T obj : objs)
            indexMap.put(obj, index++);
        return indexMap;
    }

    public static FPoint ternarySearch(double min, double max, Function<Double, Double> f) {
        if (max - min < 1e-12)
            return new FPoint(min, f.apply(min));
        double mid1 = (2 * min + max) / 3, mid2 = (min + 2 * max) / 3;
        return f.apply(mid1) > f.apply(mid2) ? ternarySearch(min, mid2, f) : ternarySearch(mid1, max, f);
    }

    /*********************************************************************************
     * BASIC NUMBER THEORY.
     *********************************************************************************/

    /**
     * ff[n] is a positive integer > 1 that divides n.
     */
    public static int[] ff;

    public static void preff(int limit) {
        if (ff != null && ff.length > limit)
            return;
        ff = new int[limit + 1];
        for (int i = 1; i <= limit; i++)
            ff[i] = i;
        for (int i = 2; i <= limit; i += 2)
            ff[i] = 2;
        for (int i = 3; sq(i) <= limit; i++)
            if (ff[i] == i)
                for (int j = isq(i); j <= limit; j += 2 * i)
                    ff[j] = i;
    }

    public static boolean isPrime(int n) {
        return n > 1 && ff[n] == n;
    }

    public static List<Integer> primes(int limit) {
        return primes(2, limit);
    }

    public static List<Integer> primes(int start, int limit) {
        preff(limit);
        List<Integer> primes = list();
        for (int i = start; i <= limit; i++)
            if (isPrime(i))
                primes.add(i);
        return primes;
    }

    public static List<Integer> primesMod(int limit, int k, int mod) {
        List<Integer> primesMod = list();
        for (int p : primes(limit))
            if (p % mod == k)
                primesMod.add(p);
        return primesMod;
    }

    public static int getPrimesLimit(int numPrimes) {
        return (int) (numPrimes * (Math.log(numPrimes) + Math.log(Math.log(numPrimes + 1)))) + 2;
    }

    public static List<Integer> getPrimes(int numPrimes) {
        return primes(getPrimesLimit(numPrimes)).subList(0, numPrimes);
    }

    /**
     * Precomputes all values of the multiplicative function (in the number theory sense) F,
     * given the values of F(p^e) = f(p,e).
     */
    public static long[] multiplicativeFunction(int limit, BiFunction<Integer, Integer, Long> f) {
        preff(limit);
        long[] values = new long[limit + 1];
        values[1] = 1;
        for (int i = 2; i <= limit; i++) {
            int n = i, p = ff[i], e = 0;
            while (n % p == 0) {
                n /= p;
                e++;
            }
            values[i] = values[n] * f.apply(p, e);
        }
        return values;
    }

    public static long[] numDivisors(int limit) {
        return sumDivisorPowers(limit, 0);
    }

    public static long[] sumDivisors;

    public static void preSumDivisors(int limit) {
        sumDivisors = sumDivisorPowers(limit, 1);
    }

    public static int sumProperDivisors(int n) {
        return (int) sumDivisors[n] - n;
    }

    public static long[] sumDivisorPowers(int limit, int exp) {
        preff(limit);
        long[] sumDivisorPowers = new long[limit + 1];
        sumDivisorPowers[1] = 1;
        for (int i = 2; i <= limit; i++) {
            int n = i;
            long mult = 1;
            while (n % ff[i] == 0) {
                n /= ff[i];
                mult = mult * pow(ff[i], exp) + 1;
            }
            sumDivisorPowers[i] = sumDivisorPowers[n] * mult;
        }
        return sumDivisorPowers;
    }

    public static Map<Integer, Integer> primeFactor(int n) {
        Map<Integer, Integer> factors = map();
        while (n > 1) {
            int factor = ff[n];
            int e = 0;
            while (n % factor == 0) {
                n /= factor;
                e++;
            }
            factors.put(factor, e);
        }
        return factors;
    }

    public static List<Integer> mostPrimeFactors(long n) {
        List<Integer> factors = new ArrayList<>();
        for (int factor = 2; 1L * factor * factor <= n; factor++) {
            int e = 0;
            while (n % factor == 0) {
                n /= factor;
                e++;
            }
            if (e > 0) {
                factors.add(factor);
                if (ff != null && n < ff.length) {
                    primeFactor((int) n).forEach((p, f) -> factors.add(p));
                    return factors;
                }
            }
        }
        return factors;
    }

    public static Map<Long, Integer> lprimeFactor(long n) {
        Map<Long, Integer> factors = new HashMap<>();
        for (long p : mostPrimeFactors(n)) {
            int e = 0;
            while (n % p == 0) {
                n /= p;
                e++;
            }
            factors.put(p, e);
        }
        if (n > 1)
            factors.put(n, 1);
        return factors;
    }

    public static List<Integer> allDivisors(int n) {
        List<Integer> divisors = list();
        divisors.add(1);
        while (n > 1) {
            int d = ff[n], size = divisors.size(), p = 1;
            while (n % d == 0) {
                n /= d;
                p *= d;
                for (int i = 0; i < size; i++)
                    divisors.add(divisors.get(i) * p);
            }
        }
        return divisors;
    }

    /**
     * Return all divisors of n, given the set of all but at most one unique prime factors.
     */
    public static List<Long> allDivisors(long n, Collection<Integer> mostPrimeFactors) {
        List<Long> divisors = list(1L);
        for (int p : mostPrimeFactors) {
            int size = divisors.size();
            for (long prod = p; n % p == 0; prod *= p) {
                for (int i = 0; i < size; i++)
                    divisors.add(divisors.get(i) * prod);
                n /= p;
            }
        }
        if (n > 1) {
            int size = divisors.size();
            for (int i = 0; i < size; i++)
                divisors.add(divisors.get(i) * n);
        }
        return divisors;
    }

    public static int[] phi;

    public static void prePhi(int limit) {
        if (phi != null && phi.length > limit)
            return;
        phi = new int[limit + 1];
        for (int i = 1; i <= limit; i++)
            phi[i] = i;
        for (int i = 2; i <= limit; i++)
            if (phi[i] == i)
                for (int j = i; j <= limit; j += i)
                    phi[j] = phi[j] / i * (i - 1);
    }

    public static long lphi(long n) {
        return EntryStream.of(lprimeFactor(n))
            .mapKeyValue((p, e) -> (p - 1) * pow(p, e - 1))
            .reduce(1L, (x, y) -> x * y);
    }

    /**
     * Return the number of square-free integers from 1 to limit (inclusive).
     */
    public static long numSquareFree(long limit) {
        preMobius(isqrt(limit));
        long numSquareFree = 0;
        for (int i = 1; sq(i) <= limit; i++)
            numSquareFree += limit / sq(i) * mobius[i];
        return numSquareFree;
    }

    /**
     * Returns an array where omegas[n] is the number of distinct prime divisors of n.
     */
    public static int[] omegas(int limit) {
        preff(limit);
        int[] omegas = new int[limit + 1];
        for (int n = 2; n <= limit; n++) {
            int nCopy = n;
            int d = ff[n];
            while (nCopy % d == 0)
                nCopy /= d;
            omegas[n] = omegas[nCopy] + 1;
        }
        return omegas;
    }

    /**
     * Returns an array where rads[n] is the radical of n, the product of the distinct prime factors
     * of n.
     */
    public static long[] rads(int limit) {
        return multiplicativeFunction(limit, (p, e) -> (long) p);
    }

    public static int[] mobius;

    public static void preMobius(int limit) {
        if (mobius != null && mobius.length > limit)
            return;
        mobius = new int[limit + 1];
        Arrays.fill(mobius, 1);
        for (int i = 2; sq(i) <= limit; i++)
            if (mobius[i] == 1) {
                for (int j = i; j <= limit; j += i)
                    mobius[j] *= -i;
                for (int j = isq(i); j <= limit; j += sq(i))
                    mobius[j] = 0;
            }
        for (int i = 2; i <= limit; i++)
            if (mobius[i] == i)
                mobius[i] = 1;
            else if (mobius[i] == -i)
                mobius[i] = -1;
            else if (mobius[i] < 0)
                mobius[i] = 1;
            else if (mobius[i] > 0)
                mobius[i] = -1;
    }

    public static int igcd(int a, int b) {
        return b == 0 ? a : igcd(b, a % b);
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static long gcd(long... nums) {
        long gcd = gcd(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++)
            gcd = gcd(nums[i], gcd);
        return gcd;
    }

    public static int[][] gcds(int limit) {
        int[][] gcds = new int[limit + 1][limit + 1];
        for (int g = 1; g <= limit; g++)
            for (int i = 0; i <= limit; i += g)
                for (int j = 0; j <= limit; j += g)
                    gcds[i][j] = g;
        gcds[0][0] = 0;
        return gcds;
    }

    public static int ilcm(int a, int b) {
        return a / igcd(a, b) * b;
    }

    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static long factorial(int n) {
        long factorial = 1;
        for (int i = 1; i <= n; i++)
            factorial *= i;
        return factorial;
    }

    public static long factorial(int n, long M) {
        long factorial = 1;
        if (n < 10_000_000)
            for (int i = 1; i <= n; i++) {
                factorial *= i;
                factorial %= M;
            }
        else {
            // https://fredrikj.net/blog/2012/03/factorials-mod-n-and-wilsons-theorem/
            int l = isqrt(n);
            List<LPolynomial> factors = list();
            for (int i = 1; i <= l; i++) {
                factors.add(new LPolynomial(i, 1));
                while (factors.size() >= 2 && (penult(factors).degree() == last(factors).degree() || i == l))
                    factors.add(removeLast(factors).multiply(removeLast(factors), M));
            }
            List<Long> ns = list();
            for (long i = 0; i < n / l; i++)
                ns.add(i * l);
            for (long result : factors.get(0).evaluate(ns, M))
                factorial = factorial * result % M;
            for (long i = roundDown(n, l) + 1; i <= n; i++)
                factorial = factorial * i % M;
        }
        return factorial;
    }

    public static double ffactorial(int n) {
        double factorial = 1;
        for (int i = 1; i <= n; i++)
            factorial *= i;
        return factorial;
    }

    public static long[] factorials(int n, long M) {
        long[] factorials = new long[n + 1];
        factorials[0] = 1;
        for (int i = 1; i <= n; i++)
            factorials[i] = factorials[i - 1] * i % M;
        return factorials;
    }

    public static double[] logFactorials(int n) {
        double[] logFactorials = new double[n + 1];
        for (int i = 1; i <= n; i++)
            if (i < 10000)
                logFactorials[i] = logFactorials[i - 1] + Math.log(i);
            else
                logFactorials[i] = (i + .5) * Math.log(i + 1) - (i + 1) + Math.log(2 * Math.PI) / 2 + 1. / (12 * (i + 1));
        return logFactorials;
    }

    /**
     * For a prime p, returns the maximum e such that p^e | n!.
     */
    public static long numFactorsInFactorial(long n, long p) {
        long numFactors = 0;
        while (n > 0)
            numFactors += n /= p;
        return numFactors;
    }

    public static BigInteger bfactorial(int n) {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= n; i++)
            factorial = factorial.multiply(big(i));
        return factorial;
    }

    public static long nCr(long a, int b) {
        if (b < 0 || a < b)
            return 0;
        long nCr = 1;
        for (int i = 0; i < b; i++) {
            nCr *= a - i;
            nCr /= i + 1;
        }
        return nCr;
    }

    public static long nCr(long a, long b, long pmod) {
        if (b < 0 || a < b)
            return 0;
        long nCr = 1;
        for (long n = a, r = b; r > 0; n /= pmod, r /= pmod)
            for (int i = 0; i < r % pmod; i++) {
                nCr *= (n - i) % pmod * modInv(i + 1, pmod) % pmod;
                nCr %= pmod;
            }
        return nCr;
    }

    public static double fnCr(double a, int b) {
        if (b < 0 || a < b)
            return 0;
        double nCr = 1;
        for (int i = 0; i < b; i++) {
            nCr *= a - i;
            nCr /= i + 1;
        }
        return nCr;
    }

    public static long gnCr(int... bs) {
        int a = 0;
        for (int b : bs)
            a += b;
        if (a <= 20) {
            long nCr = factorial(a);
            for (int b : bs)
                nCr /= factorial(b);
            return nCr;
        }
        BigInteger nCr = bfactorial(a);
        for (int b : bs)
            nCr = nCr.divide(bfactorial(b));
        return nCr.longValue();
    }

    public static long[][] nCrs(int limit, long M) {
        long[][] nCrs = new long[limit + 1][];
        for (int i = 0; i <= limit; i++) {
            nCrs[i] = new long[i + 1];
            nCrs[i][0] = nCrs[i][i] = 1;
            for (int j = 1; j < i; j++)
                nCrs[i][j] = (nCrs[i - 1][j - 1] + nCrs[i - 1][j]) % M;
        }
        return nCrs;
    }

    public static long nPr(long a, int b) {
        long nPr = 1;
        for (int i = 0; i < b; i++)
            nPr *= a - i;
        return nPr;
    }

    public static BigInteger isqrt(BigInteger b) {
        BigInteger sqrt = b.shiftRight(1);
        while (true) {
            BigInteger div = b.divide(sqrt);
            if (sqrt.compareTo(div) <= 0)
                return sqrt;
            sqrt = div.add(sqrt).shiftRight(1);
        }
    }

    /**
     * Compute sqrt(n) (mod p) for prime p using the Tonelli Shank's algorithm.
     * (https://en.wikipedia.org/wiki/Tonelli%E2%80%93Shanks_algorithm)
     */
    public static long sqrt(long n, long p) {
        if (p % 2 == 0)
            return n % p;
        else if (p % 4 == 3)
            return pow(n, (p + 1) / 4, p);
        else if (n % p == 0)
            return 0;

        long Q = p - 1, S = 0;
        while (Q % 2 == 0) {
            Q /= 2;
            S++;
        }
        int z = 2;
        while (isSq(z, p))
            z++;
        long R = pow(n, (Q + 1) / 2, p), c = pow(z, Q, p), t = pow(n, Q, p), M = S;
        while (t != 1) {
            int i = 0;
            for (long t2i = t; t2i != 1; t2i = sq(t2i, p))
                i++;
            long b = c;
            for (int k = 0; k < M - i - 1; k++)
                b = sq(b, p);
            R = R * b % p;
            c = sq(b, p);
            t = t * sq(b, p) % p;
            M = i;
        }
        return R;
    }

    public static BigDecimal sqrt(BigInteger b, int precision) {
        BigInteger scale = big(10).pow(precision);
        return bigd(isqrt(b.multiply(scale.pow(2)))).divide(bigd(scale));
    }

    public static long nthrt(long b, int n) {
        long estimate = (long) Math.pow(b, 1. / n);
        if (Math.pow(estimate + 1, n) < Long.MAX_VALUE && pow(estimate + 1, n) <= b)
            estimate++;
        return estimate;
    }

    public static BigInteger nthrt(BigInteger b, int n) {
        BigInteger low = BigInteger.ZERO, high = b;
        while (low.add(BigInteger.ONE).compareTo(high) < 0) {
            BigInteger mid = low.add(high).shiftRight(1);
            if (mid.pow(n).compareTo(b) <= 0)
                low = mid;
            else
                high = mid;
        }
        return low;
    }

    /*
     * Find (x, y) such that ax+by=(a,b)
     */
    public static LPoint linComb(long a, long b) {
        if (b == 0)
            return new LPoint(a > 0 ? 1 : -1, 0);
        LPoint prev = linComb(b, a % b);
        return new LPoint(prev.y, prev.x - a / b * prev.y);
    }

    /**
     * Find (x, y) such that ax+by=c
     */
    public static LPoint linComb(long a, long b, long c) {
        LPoint linComb = linComb(a, b);
        return linComb.multiply(c / (a * linComb.x + b * linComb.y));
    }

    public static int imodInv(int n, int mod) {
        return imod(linComb(n, mod).x, mod);
    }

    public static long modInv(long n, long mod) {
        return mod(linComb(n, mod).x, mod);
    }

    public static long[] modInvs(int limit, long pmod) {
        long[] inverses = new long[limit + 1];
        inverses[1] = 1;
        for (int i = 2; i <= limit; i++)
            inverses[i] = pmod - (pmod / i) * inverses[(int) (pmod % i)] % pmod;
        return inverses;
    }

    /**
     * Find x such that x≡a_i (mod m_i) for all i. The m_i must be pairwise coprime.
     */
    public static long crt(List<? extends Number> as, List<? extends Number> ms) {
        long M = 1;
        for (Number m : ms)
            M *= m.longValue();
        long crt = 0;
        for (int i = 0; i < as.size(); i++) {
            long m = ms.get(i).longValue();
            crt += as.get(i).longValue() * modInv(M / m, m) % m * (M / m);
        }
        return mod(crt, M);
    }

    /**
     * Find x such that x≡f(m) (mod m) for all m. All m must be pairwise coprime.
     */
    public static <T extends Number> long crt(Function<T, Long> f, List<T> ms) {
        List<Long> as = list();
        for (T m : ms)
            as.add(f.apply(m));
        return crt(as, ms);
    }

    public static BigInteger bcrt(List<BigInteger> as, List<BigInteger> ms) {
        BigInteger M = big(1);
        for (BigInteger m : ms)
            M = M.multiply(m);
        BigInteger crt = big(0);
        for (int i = 0; i < as.size(); i++) {
            BigInteger m = ms.get(i);
            crt = crt.add(as.get(i).multiply(M.divide(m)).multiply(M.divide(m).modInverse(m)));
        }
        return crt.mod(M);
    }

    /**
     * Given a function that computes the result of a computation modulo n, returns the exact
     * result.
     */
    public static BigInteger computeExact(Function<Long, Long> f) {
        BigInteger result = BigInteger.ZERO;
        List<BigInteger> as = list();
        List<BigInteger> ms = list();
        Random random = new Random(0);
        while (true) {
            BigInteger m = BigInteger.probablePrime(31, random);
            as.add(big(mod(f.apply(m.longValue()), m.longValue())));
            ms.add(m);
            BigInteger newResult = bcrt(as, ms);
            if (result.equals(newResult))
                return result;
            result = newResult;
        }
    }

    public static boolean isGenerator(long n, long pmod) {
        return isGenerator(n, pmod, lprimeFactor(pmod - 1).keySet());
    }

    public static int generator(long pmod) {
        Set<Long> primeFactors = lprimeFactor(pmod - 1).keySet();
        for (int k = 2; true; k++)
            if (isGenerator(k, pmod, primeFactors))
                return k;
    }

    private static boolean isGenerator(long n, long pmod, Set<Long> primeFactors) {
        for (long p : primeFactors)
            if (pow(n, (pmod - 1) / p, pmod) == 1)
                return false;
        return true;
    }

    /**
     * Find the minimum positive e such that n^e ≡ 1, or MAX_VALUE if none exists.
     */
    public static long order(long n, long mod) {
        long lphi = lphi(mod);
        long min = Long.MAX_VALUE;
        for (long divisor : allDivisors(lphi, mostPrimeFactors(lphi)))
            if (divisor < min && pow(n, divisor, mod) == 1 % mod)
                min = divisor;
        return min;
    }

    /**
     * Returns a polynomial C(n) = C[0] + C[1] n + C[2] n² + ... C[e+1] n^{e+1} such that C(n) =
     * sum_{k=1}^n k^e.
     */
    public static LFraction[] sumPowerCoefficients(int e) {
        LFraction[] coefficients = new LFraction[e + 2];
        coefficients[e + 1] = LFraction.reduced(1, e + 1);
        for (int i = e; i >= 1; i--) {
            coefficients[i] = LFraction.integer(0);
            for (int j = i + 1; j <= e + 1; j++)
                coefficients[i] = coefficients[i].subtract(LFraction.integer(parity(j - i) * nCr(j, i - 1)).multiply(coefficients[j]));
            coefficients[i] = coefficients[i].divide(LFraction.integer(i));
        }
        coefficients[0] = LFraction.integer(0);
        return coefficients;
    }

    /**
     * Returns sum_{k=1}^limit k^exp.
     */
    public static long sumPowers(long limit, int exp) {
        return sumPowers(limit, exp, Long.MAX_VALUE);
    }

    public static long sumPowers(long limit, int exp, long mod) {
        if (exp == 0)
            return limit % mod;
        else if (exp == 1)
            return tr(limit, mod);
        else if (exp == 2) {
            if (limit % 6 == 0)
                return mod(limit / 6, mod) * mod(limit + 1, mod) % mod * mod(2 * limit + 1, mod) % mod;
            else if (limit % 6 == 1)
                return mod(limit, mod) * mod((limit + 1) / 2, mod) % mod * mod((2 * limit + 1) / 3, mod) % mod;
            else if (limit % 6 == 2)
                return mod(limit / 2, mod) * mod((limit + 1) / 3, mod) % mod * mod(2 * limit + 1, mod) % mod;
            else if (limit % 6 == 3)
                return mod(limit / 3, mod) * mod((limit + 1) / 2, mod) % mod * mod(2 * limit + 1, mod) % mod;
            else if (limit % 6 == 4)
                return mod(limit / 2, mod) * mod(limit + 1, mod) % mod * mod((2 * limit + 1) / 3, mod) % mod;
            else if (limit % 6 == 5)
                return mod(limit, mod) * mod((limit + 1) / 6, mod) % mod * mod(2 * limit + 1, mod) % mod;
            else
                throw die();
        } else if (exp == 3)
            return sq(tr(limit, mod), mod);

        long[] pows = nthPows(exp + 2, exp, mod);
        long[] factorials = factorials(exp + 1, mod);
        long sumPows = 0;
        long res = 0;
        for (int j = 1; j <= exp + 2; j++) {
            sumPows += pows[j];
            sumPows %= mod;
            if (j == limit)
                return sumPows;
            res += sumPows
                    * parity(exp + j)
                    * modInv((limit - j) % mod * factorials[j - 1] % mod * factorials[exp + 2 - j] % mod, mod);
            res %= mod;
        }
        for (int j = 1; j <= exp + 2; j++) {
            res *= (limit - j) % mod;
            res %= mod;
        }
        return res;
    }

    /**
     * A[n] = π(n), the number of primes ≤ n.
     */
    public static int[] primeCounts(int limit) {
        preff(limit);
        int[] primeCounts = new int[limit + 1];
        for (int i = 2; i <= limit; i++)
            primeCounts[i] = primeCounts[i - 1] + (ff[i] == i ? 1 : 0);
        return primeCounts;
    }

    public static boolean[] isSquareFree;

    public static boolean isSquareFree(long n) {
        if (isSquareFree != null)
            return isSquareFree[(int) n];
        for (int d = 2; sq(d) <= n; d++)
            if (isPrime(d) && n % sq(d) == 0)
                return false;
        return true;
    }

    public static void preSquareFrees(int n) {
        if (isSquareFree != null && isSquareFree.length > n)
            return;
        isSquareFree = new boolean[n + 1];
        for (int i = 1; i <= n; i++)
            isSquareFree[i] = true;
        for (int p : primes(isqrt(n)))
            for (int i = isq(p); i <= n; i += sq(p))
                isSquareFree[i] = false;
    }

    /**
     * An approximation of the nth Harmonic number 1 + 1/2 + 1/3 + ... + 1/n.
     */
    public static double harmonic(double n) {
        return 0.57721566490153286 + Math.log(n) + 1 / (2 * n) - 1 / (12 * fsq(n)) + 1 / (120 * Math.pow(n, 4));
    }

    /**
     * @return number of positive integers k <= limit relatively prime to n.
     */
    public static long numRelativelyPrime(int n, long limit) {
        if (n == 1)
            return limit;
        int d = ff[n];
        while (n % d == 0)
            n /= d;
        return numRelativelyPrime(n, limit) - numRelativelyPrime(n, limit / d);
    }

    /*********************************************************************************
     * BASIC DATA STRUCTURES.
     *********************************************************************************/

    public static List<Integer> range(int end) {
        return range(0, end);
    }

    public static List<Integer> range(int start, int end) {
        List<Integer> nums = list();
        for (int i = start; i < end; i++)
            nums.add(i);
        return nums;
    }

    public static List<Integer> rangeC(int end) {
        return range(0, end + 1);
    }

    public static List<Integer> rangeC(int start, int end) {
        return range(start, end + 1);
    }

    public static <T> T last(List<T> list) {
        return last(list, 1);
    }

    public static <T> T penult(List<T> list) {
        return last(list, 2);
    }

    public static <T> T last(List<T> list, int index) {
        return list.get(list.size() - index);
    }

    public static <T> T removeLast(List<T> list) {
        return list.remove(list.size() - 1);
    }

    /*********************************************************************************
     * ALGEBRA.
     *********************************************************************************/

    public static long det(long[][] M) {
        int n = M.length;
        if (n == 1)
            return M[0][0];
        else if (n == 2)
            return M[0][0] * M[1][1] - M[0][1] * M[1][0];
        else
            throw die();
    }

    public static double[] linearSystem(double[][] A, double[] B) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int j = i + 1; j < n; j++)
                if (A[j][i] > A[maxRow][i])
                    maxRow = j;
            for (int j = 0; j < n; j++) {
                double temp = A[i][j];
                A[i][j] = A[maxRow][j];
                A[maxRow][j] = temp;
            }
            double temp = B[i];
            B[i] = B[maxRow];
            B[maxRow] = temp;
            for (int j = i + 1; j < n; j++) {
                double ratio = A[j][i] / A[i][i];
                for (int k = i; k < n; k++)
                    A[j][k] -= ratio * A[i][k];
                B[j] -= ratio * B[i];
            }
        }
        double[] res = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            res[i] = B[i];
            for (int j = i + 1; j < n; j++)
                res[i] -= A[i][j] * res[j];
            res[i] /= A[i][i];
        }
        return res;
    }

    /**
     * Given a tridiagonal system of equations of the form:
     *
     * <pre>
     * b_0 x_0 + c_0 x_1 = d_0
     * a_1 x_0 + b_1 x_1 + c_1 x_2 = d_1
     * a_2 x_1 + b_2 x_2 + c_2 x_3 = d_2
     * ...
     * a_{n-2} x_{n-3} + b_{n-2} x_{n-2} + c_{n-2} x_{n-1} = d_{n-2}
     * a_{n-1} x_{n-2} + b_{n-1} x_n-1} = d_{n-1},
     * </pre>
     *
     * solve for the x_i using Thomas's algorithm
     * (https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm).
     * <p>
     * Note that a_0 and c_{n-1} are ignored. All b_i must be nonzero.
     */
    public static double[] tridiagonalSystem(double[] a, double[] b, double[] c, double[] d) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            double w = a[i] / b[i - 1];
            b[i] -= w * c[i - 1];
            d[i] -= w * d[i - 1];
        }
        double[] x = new double[n];
        x[n - 1] = d[n - 1] / b[n - 1];
        for (int i = n - 2; i >= 0; i--)
            x[i] = (d[i] - c[i] * x[i + 1]) / b[i];
        return x;
    }

    /**
     * Returns a polynomial OP(n) of degree at most len(sequence)-1 such that OP(startIndex + i) =
     * sequence[i].
     */
    public static FPolynomial op(double[] sequence, int startIndex) {
        int k = sequence.length;
        double[][] pows = new double[k][k];
        for (int i : range(k))
            for (int e : range(k))
                pows[i][e] = pow(startIndex + i, e);
        return new FPolynomial(linearSystem(pows, sequence));
    }

    public static long[][] mult(long[][] A, long[][] B, long mod) {
        long[][] res = new long[A.length][B[0].length];
        if (A.length * fsq(mod) < Long.MAX_VALUE) {
            for (int i = 0; i < A.length; i++)
                for (int k = 0; k < B.length; k++)
                    for (int j = 0; j < B[k].length; j++)
                        res[i][j] += A[i][k] * B[k][j];
            for (int i = 0; i < A.length; i++)
                for (int j = 0; j < B[0].length; j++)
                    res[i][j] %= mod;
        } else {
            for (int i = 0; i < A.length; i++)
                for (int k = 0; k < B.length; k++)
                    for (int j = 0; j < B[k].length; j++) {
                        res[i][j] += A[i][k] * B[k][j];
                        res[i][j] %= mod;
                    }
        }
        return res;
    }

    public static long[][] pow(long[][] A, long b, long mod) {
        return pow(A, big(b), mod);
    }

    public static long[][] pow(long[][] A, BigInteger b, long mod) {
        long[][] res = new long[A.length][A.length];
        for (int i = 0; i < A.length; i++)
            res[i][i] = 1;
        for (char c : b.toString(2).toCharArray()) {
            res = mult(res, res, mod);
            if (c == '1')
                res = mult(res, A, mod);
        }
        return res;
    }

    public static long[] pow2x2(long[] A, long b, long mod) {
        long a0 = A[0], a1 = A[1], a2 = A[2], a3 = A[3];
        long[] X = {1, 0, 0, 1};
        while (b > 0) {
            if (b % 2 == 1) {
                long x0 = X[0], x1 = X[1], x2 = X[2], x3 = X[3];
                X[0] = mod(a0 * x0 + a1 * x2, mod);
                X[1] = mod(a0 * x1 + a1 * x3, mod);
                X[2] = mod(a2 * x0 + a3 * x2, mod);
                X[3] = mod(a2 * x1 + a3 * x3, mod);
            }
            long aa0 = a0, aa1 = a1, aa2 = a2, aa3 = a3;
            a0 = (aa0 * aa0 + aa1 * aa2) % mod;
            a1 = (aa0 * aa1 + aa1 * aa3) % mod;
            a2 = (aa2 * aa0 + aa3 * aa2) % mod;
            a3 = (aa2 * aa1 + aa3 * aa3) % mod;
            b /= 2;
        }
        return X;
    }

    /**
     * Given an array A representing the coefficients c_i of a formal series X = Σ_i c_i x^i,
     * returns X^b where multiplication is defined by (x^i)(x^j) = x^(i⊕j) (the bitwise XOR).
     */
    public static long[] nimPow(long[] A, long b, long mod) {
        A = Arrays.copyOf(A, iceilPow(A.length, 2));
        hadamard(A, mod);
        for (int i = 0; i < A.length; i++)
            A[i] = pow(A[i], b, mod);
        hadamard(A, mod);
        for (int i = 0; i < A.length; i++)
            A[i] = A[i] * modInv(A.length, mod) % mod;
        return A;
    }

    private static void hadamard(long[] A, long mod) {
        for (int u = 1; u < A.length; u *= 2)
            for (int x = 0; x < A.length; x++, x += x & u) {
                A[x] += A[x + u];
                A[x + u] = A[x] - 2 * A[x + u];
                if (A[x] >= mod)
                    A[x] -= mod;
                if (A[x + u] < 0)
                    A[x + u] += mod;
            }
    }

    /*********************************************************************************
     * GEOMETRY.
     *********************************************************************************/

    /** Returns twice the area of the polygon with the given set of vertices. */
    public static long shoestring(LPoint... points) {
        long twiceArea = 0;
        for (int i = 0; i < points.length; i++)
            twiceArea += points[i].cross(points[(i + 1) % points.length]);
        return Math.abs(twiceArea);
    }

    /**
     * Returns a positive value if you veer left when moving from p1 to p2 to p3,
     * a negative value if you veer right, and 0 otherwise.
     */
    public static float turn(LPoint p1, LPoint p2, LPoint p3) {
        return Math.signum(p2.subtract(p1).cross(p3.subtract(p2)));
    }

    /**
     * Use Simpson's Rule to efficiently integrate the given function within the provided bounds.
     */
    public static double integrate(Function<Double, Double> f, double low, double high, int numParts) {
        double integral = f.apply(low) + f.apply(high);
        for (int i = 1; i < numParts; i += 2)
            integral += 4 * f.apply(low + (high - low) / numParts * i);
        for (int i = 2; i < numParts; i += 2)
            integral += 2 * f.apply(low + (high - low) / numParts * i);
        return integral * (high - low) / (3 * numParts);
    }
}
