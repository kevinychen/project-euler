
package lib;

/**
 * A MIN heap. All objects are compared by their "val", but are referenced by a unique positive
 * integer "key" for efficiency.
 */
public class Heap {
    private final int[] keys;
    private final int[] indexes;
    private final double[] vals;
    private int size;

    /**
     * Creates a heap.
     *
     * @param limit
     *            the maximum number of elements in the heap at any given time
     * @param maxKey
     *            the maximum key ID of any element
     */
    public Heap(int limit, int maxKey) {
        this.keys = new int[limit];
        this.indexes = new int[maxKey + 1];
        this.vals = new double[maxKey + 1];
    }

    public int size() {
        return size;
    }

    public int minKey() {
        return keys[0];
    }

    /**
     * Adds a new element to the heap. The key must not be already present in the heap.
     */
    public void add(int key, double val) {
        keys[size] = key;
        indexes[key] = size;
        set(key, val);
        size++;
    }

    /**
     * Changes the "val" (the comparison value) of a key already in the heap.
     */
    public void set(int key, double val) {
        vals[key] = val;
        heapify(indexes[key]);
    }

    public int removeMin() {
        int minKey = minKey();
        removeKey(minKey);
        return minKey;
    }

    public boolean removeKey(int key) {
        int index = indexes[key];
        if (index >= size || keys[index] != key)
            return false;
        size--;
        keys[index] = keys[size];
        indexes[keys[index]] = index;
        heapify(index);
        return true;
    }

    private void heapify(int index) {
        int key = keys[index];
        double val = vals[key];
        while (index > 0 && Double.compare(val, vals[keys[(index - 1) / 2]]) < 0) {
            keys[index] = keys[(index - 1) / 2];
            indexes[keys[index]] = index;
            index = (index - 1) / 2;
        }
        keys[index] = key;
        indexes[keys[index]] = index;
        while (true) {
            int newIndex = index;
            if (2 * index + 1 < size
                    && Double.compare(vals[keys[newIndex]], vals[keys[2 * index + 1]]) > 0)
                newIndex = 2 * index + 1;
            if (2 * index + 2 < size
                    && Double.compare(vals[keys[newIndex]], vals[keys[2 * index + 2]]) > 0)
                newIndex = 2 * index + 2;
            if (newIndex == index)
                break;
            keys[index] = keys[newIndex];
            indexes[keys[index]] = index;
            index = newIndex;
            keys[index] = key;
            indexes[keys[index]] = index;
        }
    }
}
