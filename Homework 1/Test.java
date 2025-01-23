import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        String hexString = "a0";
        String hexString2 = "1df";
        String a = new BigInteger(hexString, 16).toString(2);
        String b = new BigInteger(hexString2, 16).toString(2);
        String c = new BigInteger(a+b, 2).toString(16);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
