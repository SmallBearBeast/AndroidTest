package com.example.administrator.androidtest.other;

import android.util.Base64;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test {
    public static void main(String[] args) {
        AAA aaa = new AAA();
        try {
            Field nameField = aaa.getClass().getDeclaredField("DEBUG");
            nameField.setAccessible(true);
            Field modifiers = nameField.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
            nameField.set(aaa, true);
            aaa.print();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Test test = new Test();
        test.jwtParse("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl9pZCI6MTYwMzk5NjMwNjUwNiwidHlwZSI6InNpZ25pbiIsInVpZCI6MTMxNjk0NTk1LCJjaWQiOjM2MDY5Mzc3LCJyY19leHRfaWQiOi0xLCJpbmZvIjp7ImJpZCI6IjEyMTAifSwiaWF0IjoxNjAzOTk2MzA2LCJpc3MiOiJhd3MxMWcwN3VkczA1LmRldi5nbGlwLm5ldCIsInN1YiI6ImdsaXAifQ.HisosBPa_Pu8Fk85Zq7xdzG6GmANBry9Hh6yX3bjfNDtK9DsyUkYgqXG4L1v75plgVsjSIMpRmEOG51mCKQ8kA");
    }

    public static class AAA {
        private static final boolean DEBUG = (null == null ? false : true);

        private boolean field_1 = false;

        public void print() {
            if (field_1) {
                System.out.println("print field_1 successfully");
            }
            if (DEBUG) {
                System.out.println("print DEBUG successfully");
            }
        }
    }

    public void jwtParse(String jwtToken) {
        String[] splitTokens = jwtToken.split("\\.");
        if (splitTokens.length == 3) {
            String base64Header = splitTokens[0];
            String base64Body = splitTokens[1];
            String decodeHeader = Base64.encodeToString(base64Header.getBytes(), Base64.DEFAULT);
            String bodyHeader = new String(Base64.decode(base64Body, Base64.DEFAULT));
            System.out.println("decodeHeader = " + decodeHeader + ", bodyHeader = " + bodyHeader);
        }
    }
}
