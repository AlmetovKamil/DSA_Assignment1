class QueuedBoundedStack<T> implements IBoundedStack<T>{
    private ICircularBoundedQueue<T> queue1;
    private ICircularBoundedQueue<T> queue2;

    public QueuedBoundedStack(int capacity) {
        queue1 = new ArrayCircularBoundedQueue<>(capacity);
        queue2 = new ArrayCircularBoundedQueue<>(capacity);
    }

    @Override
    public void push(T value) {
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
        queue1.offer(value);
        while (!queue1.isFull() && !queue2.isEmpty()) {
            queue1.offer(queue2.poll());
        }
        if (!queue2.isEmpty())
            queue2.flush();
    }

    @Override
    public T pop() {
        return queue1.poll();
    }

    @Override
    public T top() {
        return queue1.peek();
    }

    @Override
    public void flush() {
        if (!queue1.isEmpty())
            queue1.flush();
        if (!queue2.isEmpty())
            queue2.flush();
    }

    @Override
    public boolean isEmpty() {
        return queue1.isEmpty();
    }

    @Override
    public boolean isFull() {
        return queue1.isFull();
    }

    @Override
    public int size() {
        return queue1.size();
    }

    @Override
    public int capacity() {
        return queue1.capacity();
    }
}
