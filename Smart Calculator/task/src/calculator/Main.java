package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!"/exit".equals(input)) {
            input = scanner.nextLine();

            if (input.startsWith("/")) {
                switch (input) {
                    case "/exit":
                        System.out.println("Bye!");
                        break;
                    case "/help":
                        System.out.println("The program can calculate expressions with multiple additions and subtractions.\n" +
                                "It supports unary and binary minus operators, as well as several operators following each other.");
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }
            else if (!"".equals(input)){
                System.out.println(multiSum(input));
            }
        }
    }

    public static String multiSum(String input) {
        // int answer = value of first item
        String[] inputArray = input.split("\\s+");
        int answer = 0;

        try {
            answer = Integer.parseInt(inputArray[0]);

            if (inputArray.length == 1) {
                return (answer + "");
            }

            for (int i = 1; i < inputArray.length; i += 2) {
                answer = singleSum(answer, inputArray[i], Integer.parseInt(inputArray[i + 1]));
            }

            return (answer + "");
        }
        catch (Exception e) {
            return "Invalid expression";
        }
    }

    public static int singleSum(int a, String operand, int b) {
        if (operand.matches("\\++")) return a + b;
        // if operand is not + then it must be -
        if (operand.length() % 2 == 0) return a + b;
        // uneven number of minuses is minus, so...
        return a - b;
    }
}
