import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // write your code here
        Scanner scanner = new Scanner(System.in);
        String codeWithComments = scanner.nextLine();
        String codeWithoutComments =
                codeWithComments.replaceAll("/\\*.*?\\*/", "");
        codeWithoutComments =
                codeWithoutComments.replaceAll("//.*", "");

        System.out.println(codeWithoutComments);
    }
}