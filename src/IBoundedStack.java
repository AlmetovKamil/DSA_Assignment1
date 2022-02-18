/**
 * An interface for Bounded Stack
 *
 * @param <T> the type of elements in the stack
 * @author Kamil Almetov BS21-05
 */
//TODO
interface IBoundedStack<T> {
    /**
     * Push a value onto the stack. Remove the oldest element when the stack is full.
     */
    void push(T value);

    /**
     * Remove an element from the top of the stack
     *
     * @return removed element
     */
    T pop();

    /**
     * Look at the element at the top of the stack (without removing it)
     *
     * @return the top element
     */
    T top();

    /**
     * Remove all elements from the stack
     */
    void flush();

    /**
     * Is the stack empty?
     *
     * @return true if the stack is empty
     */
    boolean isEmpty();

    /**
     * Is the stack full?
     *
     * @return true if the stack is full
     */
    boolean isFull();

    /**
     * @return the number of elements in the stack
     */
    int size();

    /**
     * @return maximum size of the stack
     */
    int capacity();
}
