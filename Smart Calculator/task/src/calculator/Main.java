package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> variables = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!"/exit".equals(input)) {
            input = scanner.nextLine();

            if (input.startsWith("/")) {
                executeCommand(input);
            } else if (!"".equals(input)) {
                executeStatement(input, variables);
            }
        }
    }

    public static void executeCommand(String command) {
        switch (command) {
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

    public static void executeStatement(String statement, Map<String, Integer> variables) {
        // check if it's an assignment first
        final String variable = "[a-zA-Z]+";
        String[] statementArray = statement.split("\\s*=\\s*");
        switch (statementArray.length) {
            case 1:                // statement is either a variable or an expression
                if (statementArray[0].matches(variable)) {
                    System.out.println(variables.get(statementArray[0]) != null ?
                            variables.get(statementArray[0]) : "Unknown variable");
                    return;
                }

                try {
                    statementArray = statement.split("\\s+");
                    // turn all variables into values
                    for (int i = 0; i < statementArray.length; i += 2) {
                        if (statementArray[i].matches(variable)) {
                            statementArray[i] = "" + variables.get(statementArray[i]);
                        }
                    }
                    // negate all numbers that follow an uneven number of minuses
                    for (int i = 1; i < statementArray.length; i += 2) {
                        if (statementArray[i].matches("-+")
                                && statementArray[i].length() % 2 == 1) {
                            int a = Integer.parseInt(statementArray[i + 1]);
                            statementArray[i + 1] = "" + -a;
                        }
                    }
                    // add up numbers and print result
                    int answer = 0;
                    for (int i = 0; i < statementArray.length; i += 2) {
                        answer += Integer.parseInt(statementArray[i]);
                    }
                    System.out.println(answer);
                } catch (Exception e) {
                    System.out.println("Invalid expression");
                }
                return;
            case 2:                // statement is an assignment
                // check identifier format
                if (!statementArray[0].matches(variable)) {
                    System.out.println("Invalid identifier");
                    return;
                }
                // turn assignment into integer, if it is a variable, and then assign it to the identifier
                try {
                    if (statementArray[1].matches(variable)) {
                        statementArray[1] = "" + variables.get(statementArray[1]);
                    }
                    // assign integer value to variable
                    variables.put(statementArray[0], Integer.parseInt(statementArray[1]));
//                    System.out.println("Variable assigned");
                } catch (Exception e) {
                    System.out.println("Invalid assignment");
                    return;
                }
                return;
            default:                // two or more '=' signs is always wrong
                System.out.println("Invalid assignment");
        }
    }
}