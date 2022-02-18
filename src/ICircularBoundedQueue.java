/**
 * An interface for Circular Bounded Queue
 *
 * @param <T> the type of elements in the Queue
 * @author Kamil Almetov BS21-05
 */
interface ICircularBoundedQueue<T> {

    /**
     * Insert a value to the rear of the queue.
     */
    void offer(T value);

    /**
     * Overwrite the oldest elements
     * when the queue is full.
     * Remove an element from the front of the queue
     *
     * @return the removed element
     */
    T poll();

    /**
     * Look at the element at the front of the queue (without removing it)
     *
     * @return the element at the front of the queue
     */
    T peek(); //

    //

    /**
     * Remove all elements from the queue
     */
    void flush();

    /**
     * Is the queue empty?
     *
     * @return true if the queue is empty
     */
    boolean isEmpty();

    /**
     * Is the queue full?
     *
     * @return true if the queue is full
     */
    boolean isFull();

    /**
     * @return the number of the elements in the queue
     */
    int size();

    /**
     * @return the maximum size of the queue
     */
    int capacity();
}
