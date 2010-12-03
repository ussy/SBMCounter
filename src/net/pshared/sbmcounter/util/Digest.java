package net.pshared.sbmcounter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {

    private MessageDigest digest;

    public Digest(String algorithm) {
        try {
            this.digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String hexdigest(String value) {
        return hexdigest(value.getBytes());
    }

    public String hexdigest(byte[] values) {
        digest.update(values);
        return byte2hexString(digest.digest());
    }

    String byte2hexString(byte[] values) {
        StringBuilder hex = new StringBuilder();
        for (byte b : values) {
            hex.append(Integer.toHexString((b >> 4) & 0x0f));
            hex.append(Integer.toHexString(b & 0x0f));
        }

        return hex.toString();
    }
}
