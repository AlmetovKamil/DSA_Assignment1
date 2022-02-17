class DoubleHashSet<T> implements ISet<T>{
    private T[] elements;
    // 0 - empty, 1 - presented, 2 - deleted
    private byte[] states;
    private static final int capacity = 100003;
    private static final int prime = 100019;
    private int size;

    private static <T> int hashcode1(T item) {
        return Math.abs(item.hashCode()) % capacity;
    }

    private static <T> int hashcode2(T item) {
        return (prime - hashcode1(item)) % capacity;
    }

    private static int getKey(int h1, int h2, int j) {
        return (int) (((long)h1 + (long)j*h2) % capacity);
    }

    @SuppressWarnings("unchecked")
    public DoubleHashSet() {
        size = 0;
        elements = (T[])new Object[capacity];
        states = new byte[capacity];
    }

    public DoubleHashSet(DoubleHashSet<T> set) {
        this();
        this.size = set.size;
        this.states = set.states.clone();
        this.elements = set.elements.clone();
    }

    @Override
    public void add(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        int key;
        for (int j = 0; j < capacity; ++j) {
            key = getKey(h1, h2, j);
            if (states[key] == 0) {
                elements[key] = item;
                states[key] = 1;
                size++;
                return;
            }
            else if (states[key] == 1 && elements[key].equals(item)) {
                return;
            }
        }

    }

    @Override
    public void remove(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        int key;
        for (int j = 0; j < capacity; ++j) {
            key = getKey(h1, h2, j);
            if (states[key] == 0) {
                return;
            }
            else if (states[key] == 1 && elements[key].equals(item)) {
                states[key] = 2;
                elements[key] = null;
                size--;
                return;
            }
        }
    }

    @Override
    public boolean contains(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        int key;
        for (int j = 0; j < capacity; ++j) {
            key = getKey(h1, h2, j);
            if (states[key] == 0) {
                return false;
            }
            else if (states[key] == 1 && elements[key].equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int k = 0;
        for (int i = 0; i < capacity; i++) {
            if (states[i] == 1) {
                stringBuilder.append(elements[i]).append(" ");
                k++;
            }
            if (k == size) break;
        }
        return stringBuilder.toString();
    }
}
