import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regNum = scanner.nextLine(); // a valid or invalid registration number

        /* write your code here */
        String regNumRegex = "[ABEKMHOPCTYX]\\d{3}[ABEKMHOPCTYX]{2}";
        boolean isRegNumber = regNum.matches(regNumRegex);
        System.out.println(isRegNumber);
    }
}