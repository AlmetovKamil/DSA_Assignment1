/**
 * Class Circular Bounded Queue that implements ICircularBoundedQueue and based on the array.
 * It was tested by just creating an object of the class and applying all methods in the concrete example.
 *
 * @param <T> the type of the elements in the queue
 * @author Kamil Almetov BS21-05
 * @see ICircularBoundedQueue
 */
class ArrayCircularBoundedQueue<T> implements ICircularBoundedQueue<T> {
    /**
     * Array that store the elements of the queue
     */
    private final T[] elements;
    /**
     * Maximum size of the queue
     */
    private final int capacity;
    /**
     * The number of elements presented in the queue
     */
    private int size;
    /**
     * The index in the array of the first element of the queue
     */
    private int startId;
    /**
     * The index in the array of the last element of the queue
     */
    private int endId;

    /**
     * The constructor with 1 parameter.
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @param capacity maximum number of elements
     */
    @SuppressWarnings("unchecked")
    public ArrayCircularBoundedQueue(int capacity) {
        this.capacity = capacity;
        //At the beginning the queue is empty
        size = 0;
        //At the beginning startId = 0 and endId = 0
        startId = 0;
        endId = 0;
        //initialize the array
        elements = (T[]) new Object[capacity];
    }

    /**
     * If the queue is full we can't increase the size,
     * so we shift the startId because we need to add one more element
     * at the end of the queue (the value at startId index is kind of erased).
     * Also, we always increase by module of capacity to avoid IndexOutOfBounds.
     * If the queue isn't full, increase the size.
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see ICircularBoundedQueue#offer(T)
     */
    @Override
    public void offer(T value) {
        if (isFull()) {
            startId = (startId + 1) % capacity;
        } else {
            size++;
        }
        //Assign the new value to the last element of the queue
        elements[endId] = value;
        endId = (endId + 1) % capacity;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @return the removed element
     * @throws NullPointerException if the queue is empty we can't poll the element
     * @see ICircularBoundedQueue#poll()
     */
    @Override
    public T poll() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't poll from an empty queue");
        //the value of the removed element
        T tmp = elements[startId];
        // delete the first element of the queue
        elements[startId] = null;
        // shift the beginning of the queue
        startId = (startId + 1) % capacity;
        // decrease the size
        size--;
        // return the removed element
        return tmp;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @return the element at the beginning of the queue
     * @throws NullPointerException if the queue is empty
     * @see ICircularBoundedQueue#peek()
     */
    @Override
    public T peek() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't peek from an empty queue");
        return elements[startId];
    }

    /**
     * Apply the poll() method while the queue won't become empty
     * <br>
     * <b>Time complexity (worst case) - O(n) (n - size of the queue)</b>
     *
     * @throws NullPointerException if the queue is empty
     * @see ICircularBoundedQueue#flush()
     */
    @Override
    public void flush() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't flush from an empty queue");
        while (!isEmpty()) {
            poll();
        }
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see ICircularBoundedQueue#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see ICircularBoundedQueue#isFull()
     */
    @Override
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see ICircularBoundedQueue#size()
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see ICircularBoundedQueue#capacity()
     */
    @Override
    public int capacity() {
        return capacity;
    }
}
