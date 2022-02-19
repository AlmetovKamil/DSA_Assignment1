import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        taskB();
    }

    public static void taskA() {
        // N - number of input lines
        int N, K;
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        K = in.nextInt();
        // circular bounded queue with capacity K will store K last lines
        ICircularBoundedQueue<String> queue = new ArrayCircularBoundedQueue<>(K);
        in.nextLine();
        // just put every line into the queue
        for (int i = 0; i < N; i++) {
            String operation = in.nextLine();
            queue.offer(operation);
        }
        // print all lines that are remained at the queue
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    public static void taskB() {
        // N - number of input lines
        int N;
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        // set for storing files and directories
        ISet<String> set = new DoubleHashSet<>();
        for (int i = 0; i < N; i++) {
            String operationType = in.next();
            String name = "";
            switch (operationType) {
                // operation NEW
                case "NEW":
                    // name of file or directory
                    name = in.next();
                    // if it's a file, and the set doesn't contain file or directory with the same name,
                    // we put it to the set
                    if (!name.endsWith("/") && !set.contains(name) && !set.contains(name + "/")) {
                        set.add(name);
                    }
                    // if it's directory, and the set doesn't contain file or directory with the same name,
                    // we put it to the set
                    else if (name.endsWith("/") && !set.contains(name) && !set.contains(name.replace("/", ""))) {
                        set.add(name);
                    }
                    // in other cases we cannot perform an operation,
                    // so we print an error
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                //operation REMOVE
                case "REMOVE":
                    // name of file or directory
                    name = in.next();
                    // if the current object is in the set, remove it
                    if (set.contains(name)) {
                        set.remove(name);
                    }
                    // otherwise, we cannot perform an operation
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                // operation LIST
                case "LIST":
                    // print string representation of a set (all elements in a row with space delimiter)
                    System.out.println(set);
                    break;
                // all other situations should be considered as an error
                default:
                    System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    break;
            }
        }

    }

    public static void taskC() {
        //N - number of operations
        //K - capacity of the stack
        int N, K;
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        K = in.nextInt();
        //set is for storing the current state
        DoubleHashSet<String> set = new DoubleHashSet<>();
        //stack is for storing K last states
        IBoundedStack<ISet<String>> stackOfStates = new QueuedBoundedStack<>(K);
        //the first state is when the set is empty
        stackOfStates.push(new DoubleHashSet<>(set));

        for (int i = 0; i < N; i++) {
            String operationType = in.next();
            String name = "";
            switch (operationType) {
                case "NEW":
                    // name of file or directory
                    name = in.next();
                    // if it's a file, and the set doesn't contain file or directory with the same name,
                    // we put it to the set
                    if (!name.endsWith("/") && !set.contains(name) && !set.contains(name + "/")) {
                        set.add(name);
                        //the current set was changed, so put the new state to the stack
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    // if it's directory, and the set doesn't contain file or directory with the same name,
                    // we put it to the set
                    else if (name.endsWith("/") && !set.contains(name) && !set.contains(name.replace("/", ""))) {
                        set.add(name);
                        //the current set was changed, so put the new state to the stack
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    // in other cases we cannot perform an operation,
                    // so we print an error
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                // operation REMOVE
                case "REMOVE":
                    // name of file or directory
                    name = in.next();
                    // if the current object is in the set, remove it
                    if (set.contains(name)) {
                        set.remove(name);
                        //the current set was changed, so put the new state to the stack
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    // otherwise, we cannot perform an operation
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                // operation LIST
                case "LIST":
                    // print string representation of a set (all elements in a row with space delimiter)
                    System.out.println(set);
                    break;
                // operation UNDO
                case "UNDO":
                    // tmp is a string representation of a depth of UNDO
                    // operation "UNDO 1" might look like "UNDO"
                    // that's why getting the input is performed in that way
                    String tmp = in.nextLine().replace(" ", "");
                    // depth - how many operations we need to undo
                    // if it's operation "UNDO", then depth = 1
                    int depth = 1;
                    // otherwise, depth = q, where q is from "UNDO q"
                    if (!tmp.isEmpty()) depth = Integer.parseInt(tmp);
                    // pop last states depth times if there are enough stored states
                    if (depth < stackOfStates.size()) {
                        for (int j = 0; j < depth; j++) {
                            stackOfStates.pop();
                        }
                        // now current set is equal the top of the stack
                        set = new DoubleHashSet<>((DoubleHashSet<String>) stackOfStates.top());
                    }
                    // otherwise, print an error
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + tmp);
                    }
                    break;
                // all other situations should be considered as an error
                default:
                    System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    break;
            }
        }
    }
}
