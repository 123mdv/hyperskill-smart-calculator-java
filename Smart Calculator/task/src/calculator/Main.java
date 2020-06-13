package calculator;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> variables = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!"/exit".equals(input)) {
            input = scanner.nextLine();

            if (input.startsWith("/")) {
                executeCommand(input);
            } else if (input.matches("[a-zA-Z]+")) {
                printValueOfVariable(input, variables);
            } else if (input.contains("=")) {
                assignValueToVariable(input, variables);
            } else if (!"".equals(input)) {
                printResultOfExpression(input, variables);
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

    public static void printValueOfVariable(String variable, Map<String, Integer> variables) {
        System.out.println(variables.get(variable) != null ? variables.get(variable) : "Unknown variable");
    }

    public static void assignValueToVariable(String variable, Map<String, Integer> variables) {
        String[] tokens = variable.split("\\s+=\\s+");

        if (!tokens[0].matches("[a-zA-Z]+")) {
            System.out.println("Invalid identifier");
            return;
        }

        if (variables.get(tokens[1]) != null) {
            variables.put(tokens[0], variables.get(tokens[1]));
            return;
        }

        if (tokens[1].matches("[-+]?[0-9]+")) {
            variables.put(tokens[0], Integer.parseInt(tokens[1]));
            return;
        }

        System.out.println("Invalid assignment");
    }

    public static void printResultOfExpression(String expression, Map<String, Integer> variables) {
        try {
            expression = validateAndSimplifyExpression(expression);
            String postfix = convertInfixToPostfix(expression);
            int result = calculateResultOfPostfix(postfix, variables);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Invalid expression");
        }
    }

    public static String validateAndSimplifyExpression(String expression) {
        String result = "";
        StringTokenizer tokens = new StringTokenizer(expression, "[+-/*]+");
        String[] array = expression.split("(?<=[+-/*]+|?=[+-/*]+)");
        return "test";
    }

    public static int calculateResultOfPostfix(String postfix, Map<String, Integer> variables) {
        final String OPERATORS = "[-*/+() ]";
        int result = 0;
        Stack<Integer> mathStack = new Stack<>();
        StringTokenizer tokens = new StringTokenizer(postfix, " ", false);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

//        If the incoming element is a number, push it into the stack (the whole number, not a single digit!).
            if (token.matches("[-+]?[0-9]+")) {
                mathStack.push(Integer.parseInt(token));
                continue;
            }

//        If the incoming element is the name of a variable, push its value into the stack.
            if (variables.get(token) != null) {
                mathStack.push(variables.get(token));
                continue;
            }

//        If the incoming element is an operator, then pop twice to get two numbers and perform the operation;
//        push the result on the stack.
            if (token.matches(OPERATORS)) {
                int a = mathStack.pop();
                int b = mathStack.pop();
                int c = 0;
                switch (token) {
                    case "+":
                        c = b + a;
                        break;
                    case "-":
                        c = b - a;
                        break;
                    case "*":
                        c = b * a;
                        break;
                    case "/":
                        c = b / a;
                    default:
                }
                mathStack.push(c);
            }
        }

//        When the expression ends, the number on the top of the stack is a final result.
        return mathStack.pop();
    }

    private static String convertInfixToPostfix(String infix) {
        final String OPERATORS = "[-*/+() ]";
        String postfix = "";
        String token = "";
        StringTokenizer tokens = new StringTokenizer(infix, OPERATORS, true);
        Stack<String> mathStack = new Stack<>();

        while (tokens.hasMoreTokens()) {
            token = tokens.nextToken();

//            Ignore spaces
            if (token.matches("\\s"))
                continue;

//        Add operands (numbers and variables) to the result (postfix notation) as they arrive.
            if (!token.matches(OPERATORS)) {
                postfix += token + " ";
                continue;
            }

//        If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
            if (mathStack.isEmpty() || mathStack.peek().matches("\\(")) {
                mathStack.push(token);
                continue;
            }

//        If the incoming operator has higher precedence than the top of the stack, push it on the stack.
            if (token.matches("[*/]") && mathStack.peek().matches("[-+]")) {
                mathStack.push(token);
                continue;
            }

//        If the incoming operator has lower or equal precedence than the top of the operator stack,
//        pop the stack and add operators to the result until you see an operator that has a smaller precedence
//        or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
            if (token.matches("[-+]")) {
                while (!mathStack.isEmpty() && !mathStack.peek().matches("\\(")) {
                    postfix += mathStack.pop() + " ";
                }
                mathStack.push(token);
                continue;
            }

            if (token.matches("[*/]") && mathStack.peek().matches("[-+]")) {
                while (!mathStack.isEmpty() && !mathStack.peek().matches("[-+(]")) {
                    postfix += mathStack.pop() + " ";
                }
                mathStack.push(token);
                continue;
            }

//        If the incoming element is a left parenthesis, push it on the stack.
            if (token.matches("\\(")) {
                mathStack.push(token);
                continue;
            }

//        If the incoming element is a right parenthesis, pop the stack and add operators to the result
//        until you see a left parenthesis. Discard the pair of parentheses.
            if (token.matches("\\)")) {
                while (!mathStack.isEmpty() && !mathStack.peek().matches("\\(")) {
                    postfix += mathStack.pop() + " ";
                }
                mathStack.pop();
                continue;
            }
        }

//      At the end of the expression, pop the stack and add all operators to the result.
//      No parentheses should remain on the stack. Otherwise, the expression has unbalanced brackets. It is a syntax error.
        while (!mathStack.isEmpty()) {
            if (mathStack.peek().matches("[()]")) {
                return null; //unbalanced parenthesis = invalid expression
            }
            postfix += mathStack.pop() + " ";

        }

        //temp, to be removed!!
        System.out.println(postfix);
        return postfix;
    }
}