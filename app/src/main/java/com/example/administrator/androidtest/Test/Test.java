package com.example.administrator.androidtest.Test;

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
}
