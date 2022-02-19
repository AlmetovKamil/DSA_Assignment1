/**
 * The implementation of IBoundedStack based on 2 circular bounded queues.
 * It was tested by just creating an object of the class and applying all methods in the concrete example.
 *
 * @param <T> the type of elements in the stack
 * @author Kamil Almetov BS21-05
 * @see IBoundedStack
 */
class QueuedBoundedStack<T> implements IBoundedStack<T> {
    //In queue1 we store all elements in the correct order.
    //queue2 is needed to keep the order correct when we do push operation.
    private final ICircularBoundedQueue<T> queue1;
    private final ICircularBoundedQueue<T> queue2;

    /**
     * The constructor with 1 parameter. Initializes the queues with given capacity.
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @param capacity maximum size of the stack
     */
    public QueuedBoundedStack(int capacity) {
        queue1 = new ArrayCircularBoundedQueue<>(capacity);
        queue2 = new ArrayCircularBoundedQueue<>(capacity);
    }

    /**
     * <b>Time complexity (worst case) - O(n) (n - size of the stack)</b>
     *
     * @see IBoundedStack#push(T)
     */
    @Override
    public void push(T value) {
        //Transfer all elements from queue1 to queue2
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
        //These actions allow us to maintain the correct order of elements in the queue1
        //Add the value to the queue1
        queue1.offer(value);
        //Take back all elements from queue2 to queue1
        while (!queue1.isFull() && !queue2.isEmpty()) {
            queue1.offer(queue2.poll());
        }
        //And something stays in the queue2 we flush it because the size of stack is bounded by capacity.
        if (!queue2.isEmpty())
            queue2.flush();
    }

    /**
     * Since all elements are stored at queue1 in the correct order, this operation is equivalent to queue1.poll();
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @throws NullPointerException when the stack is empty
     * @see IBoundedStack#pop()
     * @see ICircularBoundedQueue#poll()
     * @see ArrayCircularBoundedQueue#poll()
     */
    @Override
    public T pop() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't pop from an empty stack");
        return queue1.poll();
    }

    /**
     * Since all elements are stored at queue1 in the correct order, this operation is equivalent to queue1.peek();
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @throws NullPointerException when the stack is empty
     * @see IBoundedStack#top()
     * @see ICircularBoundedQueue#peek()
     * @see ArrayCircularBoundedQueue#peek()
     */
    @Override
    public T top() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't get top element from an empty stack");
        return queue1.peek();
    }

    /**
     * Delete all elements of the stack. That means that queue1 have to be flushed because all element stored in it.
     * <br>
     * <b>Time complexity (worst case) - O(n) (n - size of the stack)</b>
     *
     * @throws NullPointerException when the stack is empty
     * @see IBoundedStack#flush()
     * @see ICircularBoundedQueue#flush()
     * @see ArrayCircularBoundedQueue#flush()
     */
    @Override
    public void flush() throws NullPointerException {
        if (isEmpty()) throw new NullPointerException("Can't flush an empty stack");
        if (!queue1.isEmpty())
            queue1.flush();
    }

    /**
     * Since all elements are stored at queue1, this operation is equivalent to queue1.isEmpty()
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see IBoundedStack#isEmpty()
     * @see ICircularBoundedQueue#isEmpty()
     * @see ArrayCircularBoundedQueue#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return queue1.isEmpty();
    }

    /**
     * Since all elements are stored at queue1, this operation is equivalent to queue1.isFull()
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see IBoundedStack#isFull()
     * @see ICircularBoundedQueue#isFull()
     * @see ArrayCircularBoundedQueue#isFull()
     */
    @Override
    public boolean isFull() {
        return queue1.isFull();
    }

    /**
     * Since all elements are stored at queue1, this operation is equivalent to queue1.size()
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see IBoundedStack#size()
     * @see ICircularBoundedQueue#size()
     * @see ArrayCircularBoundedQueue#size()
     */
    @Override
    public int size() {
        return queue1.size();
    }

    /**
     * Since all elements are stored at queue1, this operation is equivalent to queue1.capacity()
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     *
     * @see IBoundedStack#capacity()
     * @see ICircularBoundedQueue#capacity()
     * @see ArrayCircularBoundedQueue#capacity()
     */
    @Override
    public int capacity() {
        return queue1.capacity();
    }
}
