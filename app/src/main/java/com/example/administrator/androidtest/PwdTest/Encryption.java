package com.example.administrator.androidtest.PwdTest;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * -d
 * -e
 * -o
 */
public class Encryption {
    private static final String DIVIDER = "---------------------------------------------------------";
    private static final String DIR = "";

    public static void main(String[] args) {
        System.out.println("The user's current working directory: " + System.getProperty("user.dir"));
        args = new String[] {
                "-d", "Imo"
        };
        Set<Character> argsSet = checkArgs(args);
        if (argsSet == null) {
            return;
        }
        String decodeFileName = args[args.length - 1] + "Decode.txt";
        String encodeFileName = args[args.length - 1] + "Encode.txt";
        System.out.println("Please input secret key");
        String key = new String(System.console().readPassword());
        if (argsSet.contains('e')) {
            String decodeText = readDecodeText(decodeFileName);
            if (decodeText == null || decodeText.length() < 1) {
                System.out.println("The DecodeText is empty");
                return;
            }
            String encodeText = encode(decodeText, key);
            writeEncodeText(encodeText, encodeFileName);
            if (deleteDecodeText(decodeFileName)) {
                System.out.println("DecodeText.txt is deleted successfully");
            } else {
                System.out.println("DecodeText.txt is deleted unsuccessfully");
            }
        } else if (argsSet.contains('d')) {
            String encodeText = readEncodeText(encodeFileName);
            if (encodeText == null || encodeText.length() < 1) {
                System.out.println("The EncodeText is empty");
                return;
            }
            String decodeText = decode(encodeText, key);
            if (decodeText == null) {
                System.out.println("The DecodeText is empty, secret key is not match");
                return;
            }
            System.out.println(DIVIDER);
            System.out.println(decodeText);
            System.out.println(DIVIDER);
            if (argsSet.contains('o')) {
                writeDecodeText(decodeText, decodeFileName);
                System.out.println("DecodeText is created, remember to re-encrypt after modification");
            }
        }
    }

    private static Set<Character> checkArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter parameters, -e, -d or -o");
            return null;
        }
        Set<Character> set = new HashSet<>();
        Set<Character> validSet = new HashSet<>();
        validSet.add('e');
        validSet.add('d');
        validSet.add('o');
        for (int i = 0; i < args.length - 1; i++) {
            if (!args[i].startsWith("-")) {
                System.out.println("The parameter format is invalid, the prefix needs - character");
                return null;
            }
            String parameter = args[i].substring(1);
            for (int j = 0; j < parameter.length(); j++) {
                if (!validSet.contains(parameter.charAt(j))) {
                    System.out.println("The parameter format is invalid, does not include " + args[i] + " parameter");
                    return null;
                }
                set.add(parameter.charAt(j));
            }
        }
        if (set.contains('e') && set.contains('o')) {
            System.out.println("The parameter cannot contain both -e and -o");
            return null;
        }
        if (set.contains('d') && set.contains('e')) {
            System.out.println("The parameter cannot contain both -d and -e");
            return null;
        }
        return set;
    }

    private static boolean deleteDecodeText(String decodeFileName) {
        File file = new File(DIR + decodeFileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private static void writeDecodeText(String content, String decodeFileName) {
        File file = new File(DIR + decodeFileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("DecodeText.txt create successfully");
                } else {
                    System.out.println("DecodeText.txt create failure");
                }
            } catch (Exception e) {
                System.out.println("DecodeText.txt create failure");
                e.printStackTrace();
            }
        }
        writeFile(file, content);
    }

    private static void writeEncodeText(String content, String encodeFileName) {
        File file = new File(DIR + encodeFileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("EncodeText.txt create successfully");
                } else {
                    System.out.println("EncodeText.txt create failure");
                }
            } catch (Exception e) {
                System.out.println("EncodeText.txt create failure");
                e.printStackTrace();
            }
        }
        writeFile(file, content);
    }

    private static void writeFile(File file, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
    }

    private static String readDecodeText(String decodeFileName) {
        File file = new File(DIR + decodeFileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("DecodeText.txt create successfully");
                } else {
                    System.out.println("DecodeText.txt create failure");
                }
            } catch (Exception e) {
                System.out.println("EncodeText.txt create failure");
                e.printStackTrace();
            }
            return null;
        }
        return readFile(file);
    }

    private static String readEncodeText(String encodeFileName) {
        File file = new File(DIR + encodeFileName);
        if (!file.exists()) {
            System.out.println("EncodeText.txt does not exist");
            return null;
        }
        return readFile(file);
    }

    private static String readFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
                builder.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
        return null;
    }

    private static String encode(String content, String key) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            keyGen.init(128, random);
            SecretKey secretKey = keyGen.generateKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteResult = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < byteResult.length; i++) {
                String hex = Integer.toHexString(byteResult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                builder.append(hex.toUpperCase());
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decode(String content, String key) {
        byte[] byteResult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteResult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            keyGen.init(128, random);
            SecretKey secretKey = keyGen.generateKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(byteResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
