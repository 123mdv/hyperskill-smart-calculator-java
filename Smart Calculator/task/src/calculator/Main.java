package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!"/exit".equals(input)) {
            input = scanner.nextLine();
            switch(input) {
                case "/exit":
                    System.out.println("Bye!");
                    break;
                case "/help":
                    System.out.println("The program calculates the sum of numbers");
                    continue;
                case "":
                    continue;
                default:
                    String[] inputArray = input.split(" ");
                    if (inputArray.length == 1) {
                        System.out.println(input);
                        continue;
                    }
                    int sum = 0;
                    for (String numberString : inputArray) {
                        sum += Integer.parseInt(numberString);
                    }
                    System.out.println(sum);
            }
        }
    }
}
