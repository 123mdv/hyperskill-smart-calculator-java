import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // realStack is the actual stack, maxStack is used to store the current max
        Deque<Integer> realStack = new ArrayDeque<>();
        Deque<Integer> maxStack = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);
        int numberOfCommands = scanner.nextInt();
        int v = 0;

        for (int i = 0; i <= numberOfCommands; i++) {
            String command = scanner.nextLine();
            if (command.startsWith("push")) {
                v = Integer.parseInt(command.substring(5));
                realStack.offerLast(v);
                if (maxStack.isEmpty())
                    maxStack.offerLast(v);
                else
                    maxStack.offerLast(v > maxStack.peekLast() ? v : maxStack.peekLast());
            }
            else if ("pop".equals(command)) {
                realStack.pollLast();
                maxStack.pollLast();
            }
            else if ("max".equals(command)) { // assuming this is then the max command
                System.out.println(maxStack.peekLast());
            }
        }
    }
}