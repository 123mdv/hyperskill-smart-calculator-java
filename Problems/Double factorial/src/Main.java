import java.math.BigInteger;
import java.util.Scanner;

class DoubleFactorial {
    public static BigInteger calcDoubleFactorial(int n) {
        // type your java code here
        BigInteger result = BigInteger.ONE;
        for (int i = n; i > 0; i -= 2) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}