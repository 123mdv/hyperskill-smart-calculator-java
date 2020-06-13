import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // split into 2 options: with or without fractional part
        String intPart = "[+-]?(0|[1-9]\\d*)";
        String fracPart = "[.,](0|\\d*[1-9])";
        String regex = "^" + intPart + "(" + fracPart + ")?$";
//        String regex = "(-0" + fracPart + "|" + intPart + "|" + intPart + fracPart + ")";
//        String regex = "^[+-]?(0|[1-9]\\d*)([.,](0|\\d*[1-9]))?$";
            String number = scanner.nextLine();
            System.out.println(number.matches(regex) ? "YES" : "NO");
    }
}