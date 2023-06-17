
package lib;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class EulerGenerators extends EulerLib {

    public static <T> Generator<T> laggedFibonacci(Processor<Integer, T> processor) {
        return process(new Iterator<Integer>() {
            final List<Integer> S = list(-1);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int k = S.size();
                int next;
                if (k <= 55)
                    next = imod(100003 - 200003 * k + 300007 * cb(k), 1000000);
                else
                    next = (S.get(k - 24) + S.get(k - 55)) % 1000000;
                S.add(next);
                return next;
            }
        }, processor);
    }

    public static <T> Generator<T> blumBlumShub(int skip, Processor<Long, T> processor) {
        return process(Stream.iterate(290797L, S -> sq(S) % 50515093).skip(skip).iterator(), processor);
    }

    public static <T> Generator<T> blumBlumShub2(int skip, Processor<Long, T> processor) {
        return process(Stream.iterate(6563116L, S -> sq(S) % 32745673).skip(skip).iterator(), processor);
    }

    public static <S, T> Generator<T> process(Iterator<S> supplier, Processor<S, T> processor) {
        return f -> {
            while (!f.apply(processor.process(supplier)));
        };
    }

    @FunctionalInterface
    public interface Processor<S, T> {
        T process(Iterator<S> iterator);
    }

    private EulerGenerators() {
    }
}
