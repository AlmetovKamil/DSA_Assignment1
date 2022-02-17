import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int N, K;
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        K = in.nextInt();
        DoubleHashSet<String> set = new DoubleHashSet<>();
        IBoundedStack<ISet<String>> stackOfStates = new QueuedBoundedStack<>(K);
        stackOfStates.push(new DoubleHashSet<>(set));

        for (int i = 0; i < N; i++) {
            String operationType = in.next();
            String name = "";
            switch (operationType) {
                case "NEW":
                    name = in.next();
                    if (!name.endsWith("/") && !set.contains(name) && !set.contains(name + "/")) {
                        set.add(name);
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    else if (name.endsWith("/") && !set.contains(name) && !set.contains(name.replace("/", ""))) {
                        set.add(name);
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                case "REMOVE":
                    name = in.next();
                    if (set.contains(name)) {
                        set.remove(name);
                        stackOfStates.push(new DoubleHashSet<>(set));
                    }
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    }
                    break;
                case "LIST":
                    System.out.println(set);
                    break;
                case "UNDO":
                    //TODO:
                    String tmp = in.nextLine().replace(" ", "");
                    int depth = 1;
                    if (!tmp.isEmpty()) depth = Integer.parseInt(tmp);
                    if (depth < stackOfStates.size()) {
                        for (int j = 0; j < depth; j++) {
                            stackOfStates.pop();
                        }
                        set = new DoubleHashSet<>((DoubleHashSet<String>) stackOfStates.top());
                    }
                    else {
                        System.out.println("ERROR: cannot execute " + operationType + " " + tmp);
                    }
                    break;
                default:
                    System.out.println("ERROR: cannot execute " + operationType + " " + name);
                    break;
            }
        }

    }
}

