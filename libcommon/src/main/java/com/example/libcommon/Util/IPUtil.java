package com.example.libcommon.Util;

/**
 * IP类型转换工具类
 */
public class IPUtil {
    /**
     * 十进制ip和点分十进制字符串ip转换
     */
    public static String ipIntToString(int ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip >>> 24 & 0xff);
        sb.append(".");
        sb.append(ip >>> 16 & 0xff);
        sb.append(".");
        sb.append(ip >>> 8 & 0xff);
        sb.append(".");
        sb.append(ip & 0xff);
        return sb.toString();
    }

    public static int ipStringToInt(String ip) {
        if(!checkIp(ip)){
            return -1;
        }
        int result = 0;
        String[] splits = ip.split("\\.");
        int temp = 1;
        for (int i = 1; i <= splits.length; i++) {
            result = result + temp * Integer.parseInt(splits[splits.length - i]);
            temp = temp * 256;
        }
        return result;
    }
    /**十进制ip和点分十进制字符串ip转换**/

    /**
     * 检查IPv4地址是否合法
     */
    public static boolean checkIp(String ip){
        if(ip == null || ip.length() == 0)
            return false;
        String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
        return ip.matches(regex);
    }
    /**检查IPv4地址是否合法**/

    /**
     * 十进制整型ip和为字节数组转换
     */
    public static byte[] ipIntToArray(int ip) {
        byte[] ipArray = new byte[4];
        ipArray[3] = (byte) (ip & 0xff);
        ipArray[2] = (byte) (ip >>> 8 & 0xff);
        ipArray[1] = (byte) (ip >>> 16 & 0xff);
        ipArray[0] = (byte) (ip >>> 24 & 0xff);
        return  ipArray;
    }

    public static int ipArrayToInt(byte[] ip){
        if(ip == null || ip.length != 4)
            return -1;
        int result = (ip[3] & 0xff) + (ip[2] & 0xff) * 256 + (ip[1] & 0xff) * 256 * 256 + (ip[0] & 0xff) * 256 * 256 * 256;
        return result;
    }
    /**十进制整型ip和为字节数组转换**/


    /**
     * 十进制字符串ip和为字节数组转换
     */
    public static byte[] ipStringToArray(String ip) {
        if(!checkIp(ip))
            return new byte[4];
        byte[] ipArray = new byte[4];
        String[] split = ip.split("\\.");
        if (split.length == 4) {
            ipArray[0] = (byte) Short.parseShort(split[0]);
            ipArray[1] = (byte) Short.parseShort(split[1]);
            ipArray[2] = (byte) Short.parseShort(split[2]);
            ipArray[3] = (byte) Short.parseShort(split[3]);
        }
        return ipArray;
    }

    public static String ipArrayToString(byte[] ip) {
        if(ip == null || ip.length != 4)
            return null;
        return (ip[0] & 0xff) + "." + (ip[1] & 0xff) + "." + (ip[2] & 0xff) + "." + (ip[3] & 0xff);
    }
    /**十进制字符串ip和为字节数组转换**/
}
