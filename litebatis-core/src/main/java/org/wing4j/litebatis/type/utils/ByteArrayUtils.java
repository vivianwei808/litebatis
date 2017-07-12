package org.wing4j.litebatis.type.utils;


public class ByteArrayUtils {

  private ByteArrayUtils() {
    // Prevent Instantiation
  }

  public static byte[] convertToPrimitiveArray(Byte[] objects) {
    final byte[] bytes = new byte[objects.length];
    for (int i = 0; i < objects.length; i++) {
      bytes[i] = objects[i].byteValue();
    }
    return bytes;
  }

  public static Byte[] convertToObjectArray(byte[] bytes) {
    final Byte[] objects = new Byte[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      objects[i] = Byte.valueOf(bytes[i]);
    }
    return objects;
  }
}
