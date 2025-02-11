/*

Implement a program to find an x such that H (x ◦ id) ∈ Y where
H = SHA-256
id = 0xED00AF5F774E4135E7746419FEB65DE8AE17D6950C95CEC3891070FBB5B03C78
Y is the set of all 256 bit values that have some byte with the value 0x2F.
Assume SHA-256 is puzzle-friendly. Your answer for x must be in hexadecimal. 
Here’s a useful link for understanding binary encoding, decimal encoding, and hex encoding: https://www.rapidtables.com/convert/number/hex-to-decimal.html
You must use a systems language like Java, Rust, or Golang.
If you use Rust, use the following Rust crates:
https://crates.io/crates/hex
https://crates.io/crates/sha2 
https://crates.io/crates/rand
Caution:  
The notation “x ◦ id” means the byte array x concatenated with the byte array id. For example, 11110000 ◦ 10101010 is the byte array 1111000010101010.
The following two code segments are not equivalent:
INCORRECT
CORRECT
let id_hex = “1D253A2F";

if id_hex.contains("1D") {
   return;
}
let id_hex = “1D253A2F";

let decoded = hex::decode(id_hex).expect("Decoding failed");
let u = u8::from(29); //29 in decimal is 0x1d in hex
if decoded.contains(&u) {
   return;
}




The second code segment above is the correct way to check whether 0x1D is a byte in 0x1D253A2F. Remember that hex format is only a way to represent a byte sequence in a human readable format. You should never perform operations directly on hex-string representations. Instead, you should first convert hex-strings into byte arrays, then perform operations on the byte arrays directly, and then convert the final byte array into a hex format when giving your answer. Performing operations directly on the hex strings is incorrect.

*/

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class FindX {
   public static String byteArrayToString(byte[] byteArray) {
      StringBuilder hexString = new StringBuilder();
      for (byte b : byteArray) {
         String hex = Integer.toHexString(0xff & b);
         if (hex.length() == 1) {
            hexString.append('0');
         }
         hexString.append(hex);
      }
      return hexString.toString();
   }

   public static byte[] hexStringToByteArray(String hexString) throws IllegalArgumentException {
      
      if (hexString.length() % 2 != 0) {
         hexString = "0" + hexString;
      }
      int len = hexString.length();

      byte[] byteArray = new byte[len / 2];
   
      // Check if the string is a valid hex string
      for (int i = 0; i < len; i++) {
         char c = hexString.charAt(i);
         if (!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'))) {
            throw new IllegalArgumentException("Invalid hex string");
         }
      }

      for (int i = 0; i < len; i += 2) {
         // System.out.println("The value of i is: " + i);
         String byteString = hexString.substring(i, i + 2);
         // System.out.println("The byte string is: " + byteString);
         byteArray[i / 2] = (byte) Integer.parseInt(byteString, 16);
         // System.out.println();
      }

      return byteArray;
   }

   public static String hexString_To_SHA256String(String hexString) {
      return byteArrayToString(byteArray_To_SHA256ByteArray((hexStringToByteArray(hexString))));
   }

   public static byte[] byteArray_To_SHA256ByteArray(byte[] byteArray) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");

         byte[] hash = digest.digest(byteArray);

         return hash;
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
      }
   }

   public static byte[] concatenateHexString_toByteArray(String x, String id) {
      byte[] xArray = hexStringToByteArray(x);
      byte[] idArray = hexStringToByteArray(id);

      return concatenateByteArray_toArray(xArray, idArray);
   }

   public static byte[] concatenateByteArray_toArray(byte[] x, byte[] id) {
      byte[] concatenatedArray = new byte[x.length + id.length];

      System.arraycopy(x, 0, concatenatedArray, 0, x.length);
      System.arraycopy(id, 0, concatenatedArray, x.length, id.length);

      return concatenatedArray;
   }

   public static boolean checkIfContains2F(byte[] byteArray) {
      for (byte b : byteArray) {
         if (b == 0x2F) {
            return true;
         }
      }
      return false;
   }

   public static void main(String[] args) {
      String id = "ED00AF5F774E4135E7746419FEB65DE8AE17D6950C95CEC3891070FBB5B03C78";
      byte[] idArray = hexStringToByteArray(id);

      // Get X
      Random rand = new Random();
      
      int counter = 0;

      while (true) {
         // Determine x length
         int xLength = rand.nextInt(64);
         if (xLength > 0) {
            byte[] xArray = new byte[xLength];
            rand.nextBytes(xArray);
            counter++;

            byte[] concatenatedArray = concatenateByteArray_toArray(xArray, idArray);
            byte[] SHA256Array = byteArray_To_SHA256ByteArray(concatenatedArray);


            if (checkIfContains2F(SHA256Array)) {
               System.out.println("X: " + byteArrayToString(xArray));
               System.out.println("SHA-256: " + byteArrayToString(SHA256Array));
               System.out.println("Counter: " + counter);
               break;
            }
         }  
      }
   }
}