import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        // create the matrix and read k
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int k = sc.nextInt();
        System.out.println();

        // loop through rows
        // count # consecutive zeros as you go through the row
        for (int l = 0; l < m; l++) {
            int consecZeros = 0;
            for (int p = 0; p < n; p++) {
                consecZeros = (matrix[l][p] == 0) ? consecZeros + 1 : 0;
                // if # = k, then print row number and stop program
                if (consecZeros == k) {
                    System.out.println(l + 1);
                    return;
                }
            }
        }

        // if none found, print zero
        System.out.println(0);

    }
}