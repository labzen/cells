package cn.labzen.cells.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static cn.labzen.cells.core.utils.Bytes.*;

public class BytesTest {

  @Test
  void testHex() {
    String original = "410f1b";
    byte[] bytes = hexStringToBytes(original);
    String hex = bytesToHexString(bytes, false);

    Assertions.assertEquals(original, hex);

    String binary = hexStringToBinaryString("03");
    Assertions.assertEquals("00000011", binary);
  }

  @Test
  void testAscii() {
    String original = "ascii0123";
    byte[] bytes = asciiStringToBytes(original);
    String ascii = bytesToAsciiString(bytes);

    Assertions.assertEquals(original, ascii);
  }

  @Test
  void testInt() {
    int original = 12498761;
    byte[] bytes = intToBytes(original);
    int integer = bytesToInt(bytes);

    Assertions.assertEquals(original, integer);
  }

  @Test
  void testBigInt() {
    BigInteger original = new BigInteger("4546928818324112");
    byte[] bytes = bigIntToBytes(original);
    BigInteger integer = bytesToBigInt(bytes);

    Assertions.assertEquals(original, integer);
  }

  @Test
  void testByteArray() {
    long number = Randoms.longNumber(Long.MAX_VALUE);
    byte[] bytes = longToBytes(number);
    long recovered = bytesToLong(bytes);

    Assertions.assertEquals(number, recovered);
  }
}
