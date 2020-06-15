import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Deque<Integer> numbers = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);
        int element = 0;
        int numberOfElems = scanner.nextInt();

        for (int i = 0; i < numberOfElems; i++) {
           element = scanner.nextInt();
           if (element % 2 == 0) {
               numbers.offerFirst(element);
           }
           else {
               numbers.offerLast(element);
           }
        }

        while (!numbers.isEmpty()) {
            System.out.println(numbers.poll());
        }
    }
}