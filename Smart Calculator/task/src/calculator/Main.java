package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, BigInteger> variables = new HashMap<>();
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
                System.out.println(
                        "The program can calculate expressions with multiple additions and subtractions.\n" +
                        "It supports +-*/() operators, including multiple +++ and --- and unary minus operators.\n" +
                        "You can assign values to variables and then use them in your calculations.\n" +
                        "Type /exit to end the program.");
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    public static void printValueOfVariable(String variable, Map<String, BigInteger> variables) {
        System.out.println(variables.get(variable) != null ? variables.get(variable) : "Unknown variable");
    }

    public static void assignValueToVariable(String assignment, Map<String, BigInteger> variables) {
        assignment = assignment.replaceAll(" ", "");
        String[] tokens = assignment.split("=");

        if (!tokens[0].matches("[a-zA-Z]+")) {
            System.out.println("Invalid identifier");
            return;
        }

        if (variables.get(tokens[1]) != null) {
            variables.put(tokens[0], variables.get(tokens[1]));
            return;
        }

        if (tokens[1].matches("[-+]?[0-9]+")) {
            variables.put(tokens[0], new BigInteger(tokens[1]));
            return;
        }

        System.out.println("Invalid assignment");
    }

    public static void printResultOfExpression(String expression, Map<String, BigInteger> variables) {
        try {
            expression = validateAndSimplifyExpression(expression);
            String postfix = convertInfixToPostfix(expression);
            BigInteger result = calculateResultOfPostfix(postfix, variables);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Invalid expression");
        }
    }

    public static String validateAndSimplifyExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
//        System.out.println(expression);

        // if multiple *** or /// then return null
        if (expression.matches(".*[*/]{2,}.*")) {
//            System.out.println("multiple *** or ///");
            return null;
        }

        // add a zero if there is an initial + or -
        if (expression.matches("^[-+].*")) {
            expression = "0" + expression;
        }

        // replace every -- with +
        expression = expression.replaceAll("--", "\\+");

        // for every multiple +++ replace with +
        expression = expression.replaceAll("\\+{2,}", "+");

        // replace every +- with -
        expression = expression.replaceAll("\\+-", "-");

        // remove unary plus and add space before unary minus
        expression = expression.replaceAll("\\*\\+", "*");
        expression = expression.replaceAll("\\*-", "* -");
        expression = expression.replaceAll("/\\+", "/");
        expression = expression.replaceAll("/-", "/ -");
        expression = expression.replaceAll("\\(\\+", "(");
        expression = expression.replaceAll("\\(-", "( -");

//        System.out.println(expression);
        return expression;
    }

    private static String convertInfixToPostfix(String infix) {
        final String OPERATORS_AND_SPACES = "[-*/+ ()]";
        String postfix = "";
        String token = "";
        StringTokenizer tokens = new StringTokenizer(infix, OPERATORS_AND_SPACES, true);
        Deque<String> mathStack = new ArrayDeque<>();

        while (tokens.hasMoreTokens()) {
            token = tokens.nextToken();

//            If the token is a space, then it is followed by a unary minus
            if (token.matches(" ")) {
                token = tokens.nextToken() + tokens.nextToken();
            }

//        Add operands (numbers and variables) to the result (postfix notation) as they arrive.
            if (!token.matches(OPERATORS_AND_SPACES)) {
                postfix += token + " ";
                continue;
            }

//        If the stack is empty or contains a left parenthesis on top, push the incoming operator on the stack.
            if (mathStack.isEmpty() || mathStack.peekFirst().matches("\\(")) {
                mathStack.offerFirst(token);
                continue;
            }

//        If the incoming operator has higher precedence than the top of the stack, push it on the stack.
            if (token.matches("[*/]") && mathStack.peekFirst().matches("[-+]")) {
                mathStack.offerFirst(token);
                continue;
            }

//        If the incoming operator has lower or equal precedence than the top of the operator stack,
//        pop the stack and add operators to the result until you see an operator that has a smaller precedence
//        or a left parenthesis on the top of the stack; then add the incoming operator to the stack.
            if (token.matches("[-+]")) {
                while (!mathStack.isEmpty() && !mathStack.peekFirst().matches("\\(")) {
                    postfix += mathStack.pollFirst() + " ";
                }
                mathStack.offerFirst(token);
                continue;
            }

            if (token.matches("[*/]") && mathStack.peekFirst().matches("[-+]")) {
                while (!mathStack.isEmpty() && !mathStack.peekFirst().matches("[-+(]")) {
                    postfix += mathStack.pollFirst() + " ";
                }
                mathStack.offerFirst(token);
                continue;
            }

//        If the incoming element is a left parenthesis, push it on the stack.
            if (token.matches("\\(")) {
                mathStack.offerFirst(token);
                continue;
            }

//        If the incoming element is a right parenthesis, pop the stack and add operators to the result
//        until you see a left parenthesis. Discard the pair of parentheses.
            if (token.matches("\\)")) {
                while (!mathStack.isEmpty() && !mathStack.peekFirst().matches("\\(")) {
                    postfix += mathStack.pollFirst() + " ";
                }
                mathStack.removeFirst();
                continue;
            }
        }

//      At the end of the expression, pop the stack and add all operators to the result.
//      No parentheses should remain on the stack. Otherwise, the expression has unbalanced brackets. It is a syntax error.
        while (!mathStack.isEmpty()) {
            if (mathStack.peekFirst().matches("[()]")) {
                return null; //unbalanced parenthesis = invalid expression
            }
            postfix += mathStack.pollFirst() + " ";

        }

//        System.out.println(postfix);
        return postfix;
    }

    public static BigInteger calculateResultOfPostfix(String postfix, Map<String, BigInteger> variables) {
        final String OPERATORS = "[-*/+() ]";
        boolean makeVariableNegative = false;
        BigInteger variableValue = BigInteger.ZERO;
        BigInteger result = BigInteger.ZERO;
        Deque<BigInteger> mathStack = new ArrayDeque<>();
        StringTokenizer tokens = new StringTokenizer(postfix, " ", false);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

//        If the incoming element is a number, push it into the stack (the whole number, not a single digit!).
            if (token.matches("[-+]?[0-9]+")) {
                mathStack.offerFirst(new BigInteger(token));
                continue;
            }

//        If the incoming element is a variable with a unary minus, then remember the minus in a temp variable
            if (token.matches("-[a-zA-Z]+")) {
                makeVariableNegative = true;
                token = token.replaceAll("-", "");
            }

//         If you element is a variable, get its value and add the minus if needed
            if (variables.get(token) != null) {
                variableValue = makeVariableNegative ? variables.get(token).negate() : variables.get(token);
                mathStack.offerFirst(variableValue);
                makeVariableNegative = false;
                continue;
            }

//        If the incoming element is an operator, then pop twice to get two numbers and perform the operation;
//        push the result on the stack.
            if (token.matches(OPERATORS)) {
                BigInteger a = mathStack.pop();
                BigInteger b = mathStack.pop();
                BigInteger c = BigInteger.ZERO;
                switch (token) {
                    case "+":
                        c = b.add(a);
                        break;
                    case "-":
                        c = b.subtract(a);
                        break;
                    case "*":
                        c = b.multiply(a);
                        break;
                    case "/":
                        c = b.divide(a);
                    default:
                }
                mathStack.offerFirst(c);
            }
        }

//        When the expression ends, the number on the top of the stack is a final result.
        return mathStack.pollFirst();
    }
}