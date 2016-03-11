package ru.ifmo.ctddev.biculya.walk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gaudima on 2/10/16.
 */
public class Md5Hasher {
    public String hashFile(Path filePath) throws IOException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No MD5 algorithm.");
            return "";
        }
        try (InputStream is = Files.newInputStream(filePath);
             DigestInputStream dis = new DigestInputStream(is, digest)) {
            while (dis.available() > 0) {
                dis.read();
            }
        }
        StringBuilder ret = new StringBuilder();
        byte[] d = digest.digest();
        for (int i = 0; i < d.length; i++) {
            ret.append(String.format("%02X", d[i]));
        }
        digest.reset();
        return ret.toString();
    }
}
