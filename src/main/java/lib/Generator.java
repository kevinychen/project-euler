
package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Generator<T> {

    void generateUntil(Function<T, Boolean> f);

    default void generate(Consumer<T> f) {
        generateUntil(obj -> {
            f.accept(obj);
            return false;
        });
    }

    default T get(int index) {
        AtomicInteger counter = new AtomicInteger();
        AtomicReference<T> result = new AtomicReference<>();
        generateUntil(obj -> {
            result.set(obj);
            return counter.getAndIncrement() == index;
        });
        return result.get();
    }

    default List<T> toList(int limit) {
        List<T> list = new ArrayList<>();
        generateUntil(obj -> {
            list.add(obj);
            return list.size() == limit;
        });
        return list;
    }
}
