import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = new int[n][n];

        // fill the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

//        //print matrix
//        for (int[] vector : matrix)
//            System.out.println(Arrays.toString(vector));

        // check symmetry
        boolean isSymmetric = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++)
                if (matrix[i][j] != matrix[j][i]) isSymmetric = false;
        }

        String answer = isSymmetric ? "YES" : "NO";
        System.out.println(answer);
    }
}