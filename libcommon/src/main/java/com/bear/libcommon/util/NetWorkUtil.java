package com.bear.libcommon.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import androidx.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import static android.Manifest.permission.*;
import static android.content.Context.WIFI_SERVICE;

public class NetWorkUtil extends AppInitUtil{

    /**
     * 获取NetworkInfo信息
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)
            return null;
        return cm.getActiveNetworkInfo();
    }


    /**
     * 判断网络是否连接
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断手机数据是否开启
     */
    public static boolean getMobileDataEnabled() {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null)
                return false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm.isDataEnabled();
            }
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            Log.e("NetworkUtils", "getMobileDataEnabled: ", e);
        }
        return false;
    }

    /**
     * 开启或者关闭手机数据
     */
    @RequiresPermission(MODIFY_PHONE_STATE)
    public static boolean setMobileDataEnabled(final boolean ENABLED) {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null)
                return false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tm.setDataEnabled(ENABLED);
                return false;
            }
            Method setDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setDataEnabledMethod) {
                setDataEnabledMethod.invoke(tm, ENABLED);
                return true;
            }
        } catch (Exception e) {
            Log.e("NetworkUtils", "setMobileDataEnabled: ", e);
        }
        return false;
    }

    /**
     * 是否是移动数据
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isMobileData() {
        NetworkInfo info = getActiveNetworkInfo();
        return null != info && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 是否是4G网络
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean is4G() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * wifi是否开启
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean getWifiEnabled() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) getContext().getSystemService(WIFI_SERVICE);
        if (manager == null)
            return false;
        return manager.isWifiEnabled();
    }

    /**
     * 开启或者关闭wifi
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    public static void setWifiEnabled(final boolean ENABLED) {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) getContext().getSystemService(WIFI_SERVICE);
        if (manager == null || ENABLED == manager.isWifiEnabled())
            return;
        manager.setWifiEnabled(ENABLED);
    }

    /**
     * wifi是否连接
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取网络运营商的名字
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null)
            return "";
        return tm.getNetworkOperatorName();
    }


    /**
     * 判断是否是以太网
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isEthernet() {
        final ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null)
            return false;
        NetworkInfo.State state = info.getState();
        if (null == state)
            return false;
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    /**
     * 判断具体网络类型
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static int getNetworkType() {
        if (isEthernet()) {
            return NetworkType.NETWORK_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetworkType.NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkType.NETWORK_2G;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkType.NETWORK_3G;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkType.NETWORK_4G;

                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetworkType.NETWORK_3G;
                        }
                }
            }
        }
        return NetworkType.NETWORK_UNKNOWN;
    }

    @RequiresPermission(INTERNET)
    public static String getIPAddress(final boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp() || ni.isLoopback())
                    continue;
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement());
                }
            }
            for (InetAddress add : adds) {
                if (!add.isLoopbackAddress()) {
                    String hostAddress = add.getHostAddress();
                    boolean isIPv4 = hostAddress.indexOf(':') < 0;
                    if (useIPv4) {
                        if (isIPv4) return hostAddress;
                    } else {
                        if (!isIPv4) {
                            int index = hostAddress.indexOf('%');
                            return index < 0
                                    ? hostAddress.toUpperCase()
                                    : hostAddress.substring(0, index).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp() || ni.isLoopback())
                    continue;
                List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                for (int i = 0, size = ias.size(); i < size; i++) {
                    InterfaceAddress ia = ias.get(i);
                    InetAddress broadcast = ia.getBroadcast();
                    if (broadcast != null) {
                        return broadcast.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresPermission(INTERNET)
    public static String getDomainAddress(final String domain) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getIpAddressByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().ipAddress);
    }

    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getGatewayByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().gateway);
    }

    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getNetMaskByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().netmask);
    }

    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return "";
        return Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress);
    }


    public interface NetworkType {
        byte NETWORK_ETHERNET = 1;
        byte NETWORK_WIFI = NETWORK_ETHERNET + 1;
        byte NETWORK_4G = NETWORK_WIFI + 1;
        byte NETWORK_3G = NETWORK_4G + 1;
        byte NETWORK_2G = NETWORK_3G + 1;
        byte NETWORK_UNKNOWN = NETWORK_2G + 1;
        byte NETWORK_NO = NETWORK_UNKNOWN + 1;
    }
}
