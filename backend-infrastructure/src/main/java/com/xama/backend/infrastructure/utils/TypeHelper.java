package com.xama.backend.infrastructure.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class TypeHelper {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public TypeHelper() {
    }

    public static String hexStringUUIDToUUIDString(String hexStringUUID) {
        if (hexStringUUID != null) {
            if (checkStringIsUUID(hexStringUUID)) {
                return hexStringUUID;
            } else {
                byte[] bytes = getBytesFromHexString(hexStringUUID);
                return UUID.fromString(hexStringUUID.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")).toString();
            }
        } else {
            return null;
        }
    }

    public static UUID hexStringUUIDToUUID(String hexStringUUID) {
        return hexStringUUID != null && !hexStringUUID.equals("") ? UUID.fromString(hexStringUUID.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")) : null;
    }

    public static byte[] getBytesFromHexString(String hex) {
        byte[] val = new byte[hex.length() / 2];

        for(int i = 0; i < val.length; ++i) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte)j;
        }

        return val;
    }

    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    public static boolean checkStringIsUUID(String value) {
        return value.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for(int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 15];
        }

        return new String(hexChars);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static String uuidStringToUUIDHex(String uuidString) {
        return bytesToHex(asBytes(UUID.fromString(uuidString)));
    }

    public static String uuidToUUIDHex(UUID uuid) {
        return bytesToHex(asBytes(uuid));
    }
}
