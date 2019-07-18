package com.example.libbase.Util;

import android.support.annotation.StringDef;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字格式转换工具类
 */
public class DecimalUtil {
    /**
     * 格式化数字四舍五入保留小数，float转double存在精度丢失所以需要BigDecimal转
     */
    public static String format(@DecimalType String format, float value){
        BigDecimal decimal = new BigDecimal(String.valueOf(value));
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(decimal.doubleValue());
    }

    public static String format(@DecimalType String format, double value){
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    public static String format(@DecimalType String format, long value){
        DecimalFormat df = new DecimalFormat(format);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }
    /**格式化数字四舍五入保留小数**/

    public static final String _X = ".0";
    public static final String _XX = ".00";
    public static final String _XXX = ".000";
    public static final String _Y = ".#";
    public static final String _YY = ".##";
    public static final String _YYY = ".###";

    @StringDef({_X, _XX, _XXX, _Y, _YY, _YYY})
    @interface DecimalType{}


    /**
     * 格式化文件大小long类型转字符串
     */
    public static String fileSizeFormat(long size, @DecimalType String format){
        BigDecimal fileSize = new BigDecimal(size);
        BigDecimal param = new BigDecimal(1024);
        int count = 0;
        while(fileSize.compareTo(param) > 0 && count < 5){
            fileSize = fileSize.divide(param);
            count++;
        }
        String result = format(format, fileSize.doubleValue());
        switch (count) {
            case 0:
                result += "B";
                break;
            case 1:
                result += "KB";
                break;
            case 2:
                result += "MB";
                break;
            case 3:
                result += "GB";
                break;
            case 4:
                result += "TB";
                break;
            case 5:
                result += "PB";
                break;
        }
        return result;
    }
    /**格式化文件大小long类型转字符串**/

    /**
     * 音视频时长格式化
     */
    public static String videoDurFormat(long ms){
        StringBuilder builder = new StringBuilder();
        int temp = (int) (ms / 1000);

        int hour = temp / 3600;
        if(hour >= 10) {
            builder.append(hour).append(":");
        }else {
            builder.append(0).append(hour).append(":");
        }
        int min = (temp % 3600) / 60;
        if(min >= 10) {
            builder.append(min).append(":");
        }else {
            builder.append(0).append(min).append(":");
        }
        int sec = temp % 60;
        if(sec >= 10) {
            builder.append(sec).append(":");
        }else {
            builder.append(0).append(sec).append(":");
        }
        return builder.toString();
    }
}
