class ArrayCircularBoundedQueue<T> implements ICircularBoundedQueue<T>{
    private T[] elements;
    private final int capacity;
    private int size;
    private int startId;
    private int endId;

    @SuppressWarnings("unchecked")
    public ArrayCircularBoundedQueue(int capacity) {
        this.capacity = capacity;
        size = 0;
        startId = 0;
        endId = 0;
        elements = (T[]) new Object[capacity];
    }

    @Override
    public void offer(T value) {
        if (isFull()) {
            startId = (startId + 1) % capacity;
        }
        else {
            size++;
        }
        elements[endId] = value;
        endId = (endId + 1) % capacity;
    }

    @Override
    public T poll() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't poll from an empty queue");
        T tmp = elements[startId];
        elements[startId] = null;
        startId = (startId + 1) % capacity;
        size--;
        return tmp;
    }

    @Override
    public T peek() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't peek from an empty queue");
        return elements[startId];
    }

    @Override
    public void flush() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't flush from an empty queue");
        while (!isEmpty()) {
            poll();
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
