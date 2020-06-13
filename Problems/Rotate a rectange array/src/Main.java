import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] numbers = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            numbers[i] = sc.nextInt();
        }

        int[][] matrix = new int[m][n];
        int index = 0;
        for (int j = n - 1; j >= 0; j--) {
            for (int k = 0; k < m; k++) {
                matrix[k][j] = numbers[index];
                index++;
            }
        }

        // print
        for (int l = 0; l < m; l++) {
            for (int p = 0; p < n; p++) {
                System.out.print(matrix[l][p] + " ");
            }
            System.out.println();
        }

    }
}