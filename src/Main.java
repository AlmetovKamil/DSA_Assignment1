public class Main {
    public static void main(String[] args) {
        IBoundedStack<Integer> stack = new QueuedBoundedStack<>(5);
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            System.out.println(stack.top());
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(stack.pop());
        }
    }
}
