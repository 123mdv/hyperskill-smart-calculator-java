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
                case "":
                    continue;
                default:
                    String[] inputArray = input.split(" ");
                    if (inputArray.length == 1) {
                        System.out.println(input);
                        continue;
                    }
                    System.out.println(Integer.parseInt(inputArray[0]) +
                            Integer.parseInt(inputArray[1]));
            }
        }
    }
}
