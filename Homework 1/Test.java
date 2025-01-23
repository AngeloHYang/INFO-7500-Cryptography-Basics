import java.math.BigInteger;
import java.util.Random;
import java.util.random.*;

public class Test {
    public static void main(String[] args) {
        // String hexString = "a0";
        // String hexString2 = "1df";
        // String a = new BigInteger(hexString, 16).toString(2);
        // String b = new BigInteger(hexString2, 16).toString(2);
        // String c = new BigInteger(a+b, 2).toString(16);
        // System.out.println(a);
        // System.out.println(b);
        // System.out.println(c);

        Random rand = new Random();
        byte[] byteArray = new byte[5];
        rand.nextBytes(byteArray);

        for (byte b : byteArray) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
