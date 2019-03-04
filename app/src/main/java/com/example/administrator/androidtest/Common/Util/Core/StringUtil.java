package com.example.administrator.androidtest.Common.Util.Core;


public class StringUtil {
    /**
     * 字符串是否含有空格
     */
    public static boolean isSpace(final String S) {
        if (S == null)
            return true;
        for (int i = 0, len = S.length(); i < len; i++) {
            if (!Character.isWhitespace(S.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断字符串是否为null
     */
    public static boolean isEmpty(final CharSequence S) {
        return S == null || S.length() == 0;
    }


    /**
     * 判断trim过后的字符串是否为null
     */
    public static boolean isTrimEmpty(final String S) {
        return (S == null || S.trim().length() == 0);
    }

    /**
     * 判断两个字符串是否相等
     */
    public static boolean equals(final CharSequence S1, final CharSequence S2) {
        if (S1 == S2) return true;
        int length = 0;
        if (S1 != null && S2 != null && (length = S1.length()) == S2.length()) {
            if (S1 instanceof String && S2 instanceof String) {
                return S1.equals(S2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (S1.charAt(i) != S2.charAt(i))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 忽略大小写比较两个字符串是否相等
     */
    public static boolean equalsIgnoreCase(final String S1, final String S2) {
        return S1 == null ? S2 == null : S1.equalsIgnoreCase(S2);
    }


    /**
     * 获取字符串长度
     */
    public static int length(final CharSequence S) {
        return S == null ? 0 : S.length();
    }

    /**
     * 字符串为null初始化为空串
     */
    public static String nullAndInit(final String S) {
        return S == null ? "" : S;
    }

}
