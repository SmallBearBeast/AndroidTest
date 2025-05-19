package com.example.libcommon.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class OsUtil {
	public static int CPU_COUNT = 0;
	public static int CPU_FREQ = 800000;

	private static float sDensityFactor = -1.0f;

	public static final int ORIENTATION_HYSTERESIS = 5;


	public static float densityFactor(Context context) {
		if (sDensityFactor < 0) {
			WindowManager wMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			wMgr.getDefaultDisplay().getMetrics(metrics);
			sDensityFactor = metrics.density;
		}
		return sDensityFactor;
	}

	public static float roundRadiusFactor(Context context, int photoSize) {
/*		float densityDpi = OsUtil.densityFactor(context);
		float roundRadius = 0.0f;
		if (photoSize <= densityDpi * 45.0f) {
			roundRadius = 2.0f * densityDpi;
		} else if (photoSize <= densityDpi * 60.0f) {
			roundRadius = 4.0f * densityDpi;
		} else if (photoSize > densityDpi * 60.0f){
			roundRadius = 4.0f * densityDpi;
		}
		return roundRadius;
		*/
		return photoSize * 10.0f / 96; // 按照logo的圆角比例，亦即96x96的图片，圆角半径为10
	}

	public static float cropImageScaleSize(Context context, int photoSize) {
		float densityDpi = OsUtil.densityFactor(context);
		float size = 1.0f;
		if (densityDpi >= 3) {
			size = 1.0f;
		} else if (densityDpi >= 1.5 && densityDpi < 3) {
			size = 2.0f;
		} else {
			size = 4.0f;
		}
		return size;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static int getDisplayRotation(Activity activity) {
		int rotation;
		if (Build.VERSION.SDK_INT > 7) {
			rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		} else {
			rotation = activity.getWindowManager().getDefaultDisplay().getOrientation();
		}

		switch (rotation) {
			case Surface.ROTATION_0:
				return 0;
			case Surface.ROTATION_90:
				return 90;
			case Surface.ROTATION_180:
				return 180;
			case Surface.ROTATION_270:
				return 270;
		}
		return 0;
	}

	public static int getScreenWidth(Context context) {
		WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = mgr.getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = mgr.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 获取导航栏高度
	 * @param context
	 * @return
	 */
	public static int getNavigationHeight(Context context){
		return getScreenHeightCompat(context) - getScreenHeight(context);
	}

	public static int getScreenHeightCompat(Context context) {
		WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final Display display = mgr.getDefaultDisplay();
		final Point screenResolution = new Point();
		display.getSize(screenResolution);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			final Point screenRealResolution = new Point();
			display.getRealSize(screenRealResolution);
//			screenResolution.x = Math.max(screenRealResolution.x, screenResolution.x);
			screenResolution.y = Math.max(screenRealResolution.y, screenResolution.y);
		}
		return screenResolution.y;
/*		if (screenResolution.x < screenResolution.y*//*screen portrait*//*) {
			return screenResolution.y;
		}
		return screenResolution.x;*/
	}

	@SuppressLint("NewApi")
	public static int getJpegRotation(int cameraId, int orientation) {
		// See android.hardware.Camera.Parameters.setRotation for
		// documentation.
		if (Build.VERSION.SDK_INT < 9) {
			return orientation;
		}
		int rotation = 0;
		if (orientation != OrientationEventListener.ORIENTATION_UNKNOWN) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(cameraId, info);

			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				rotation = (info.orientation - orientation + 360) % 360;
			} else { // back-facing camera
				rotation = (info.orientation + orientation) % 360;
			}
		}
		return rotation;
	}

	public static boolean isCameraOK() {
		boolean isCameraOk = false;
		Camera c = null;
		try {
			c = Camera.open();
			isCameraOk = true;
		} catch (RuntimeException e) {
		} finally {
			if (c != null) {
				c.release();
			}
		}
		return isCameraOk;
	}

	public static void initCpuInfo() {
		CPU_FREQ = getMaxCpuFreq();
		if (isSingleCpu()) {
			CPU_COUNT = 1;
		} else if (isQuadCpu()) {
			CPU_COUNT = 4;
		} else {
			CPU_COUNT = 2;
		}
	}

	/*
	 * public static boolean isMobileNO(String mobiles) { Pattern p =
	 * Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); Matcher
	 * m = p.matcher(mobiles); return m.matches(); }
	 */

	public static int roundOrientation(int orientation, int orientationHistory) {
		boolean changeOrientation = false;
		if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
			changeOrientation = true;
		} else {
			int dist = Math.abs(orientation - orientationHistory);
			dist = Math.min(dist, 360 - dist);
			changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
		}
		if (changeOrientation) {
			return ((orientation + 45) / 90 * 90) % 360;
		}
		return orientationHistory;
	}

	public static int versionName2Code(String versionName) {
		if (versionName.endsWith("-SNAPSHOT")) {
			versionName = versionName.substring(0, 5);
		}
		String[] sp = versionName.split("\\.");
		int s1 = Integer.valueOf(sp[0].trim());
		int s2 = Integer.valueOf(sp[1].trim());
		int s3 = Integer.valueOf(sp[2].trim());
		int versionCode = /* (Integer.valueOf(sp[0])) << 24 + */(s1 << 24) + (s2 << 16) + (s3 << 8);
		return versionCode;
	}

	private static int getMaxCpuFreq() {
		String result = "";
		BufferedReader cpuReader = null;
		try {
			cpuReader = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"));
			result = cpuReader.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (cpuReader != null) {
				try {
					cpuReader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if (pattern.matcher(result).matches()) {
			if (result.length() > 32) {
				return 0xFFFFFFFF;
			}
			try {
				return Integer.parseInt(result);
			} catch (NumberFormatException e) {
				return -1;
			}
		} else {
			return -1;
		}
	}

	private static boolean isQuadCpu() {
		try {
			// check whether cpu1 exists.
			return (new File("/sys/devices/system/cpu/cpu3").exists());
		} catch (Exception e) {
			// Default to return true
			return false;
		}
	}

	private static boolean isSingleCpu() {
		try {
			// check whether cpu1 exists.
			return !(new File("/sys/devices/system/cpu/cpu1").exists());
		} catch (Exception e) {
			// Default to return true
			return true;
		}
	}

	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	public static boolean isSimExsit(Context context) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return (manager.getSimState() == TelephonyManager.SIM_STATE_READY);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static boolean copyToClipBoard(Context context, String text) {
		try {
			int apiLevel = Build.VERSION.SDK_INT;
			Object service = context.getSystemService(Context.CLIPBOARD_SERVICE);
			if (apiLevel < 11) {
				android.text.ClipboardManager cb = (android.text.ClipboardManager) service;
				cb.setText(text);
			} else {
				android.content.ClipboardManager cb = (android.content.ClipboardManager) service;
				cb.setText(text);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getClipboardsContent(Context context) {
		String string;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			string = getClipboardsContentV11(context);
		} else {
			string = getClipboardsContentV9(context);
		}
		return string;
	}

	public static String splitPhone(String phone) {
		if (TextUtils.isEmpty(phone)) {
			return "";
		}
		Pattern pattern = Pattern.compile("[^0-9+*#]");
		String[] strings = pattern.split(phone);
		if (strings != null && strings.length > 0) {
			return strings[strings.length - 1];
		}
		return phone;
	}

	@SuppressLint("NewApi")
	private static String getClipboardsContentV11(Context context) {
		android.content.ClipboardManager cm = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm != null) {
			ClipData cd = cm.getPrimaryClip();
			if (cd != null && cd.getItemCount() > 0) {
				if (cd.getItemAt(0) != null && cd.getItemAt(0).getText() != null) {
					return cd.getItemAt(0).getText().toString();
				}
			}
		}
		return null;
	}

	private static String getClipboardsContentV9(Context context) {
		android.text.ClipboardManager cm = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm != null && cm.getText() != null) {
			return cm.getText().toString();
		}
		return null;
	}

	public static int getStatusBarHeight(Activity activity) {
		if (activity == null) {
			return 0;
		}
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
				statusHeight = activity.getResources().getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	public static boolean isOverMarshmallow() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}

    public static String getFileName(String raw) {
        int suffixIndex = raw.lastIndexOf(".");
        if (suffixIndex > 0) {
            return raw.substring(0, suffixIndex);
        }
        return raw;
    }


//	/**
//	 * 解决BottomSheetDialog闪动问题，同时解决Dialog标题栏黑色的问题,使用这个方法就不需要再对刘海屏手机的Dialog进行其他flag的设置
//	 * 因为显示高度已经去除刘海屏高度栏区域，不需要再费劲去适配刘海屏问题了
//	 * @param window
//	 *//*
//	public static void fixDialogStatusBarDark(Window window) {
//		if (window == null) {
//			return;
//		}
//		int screenHeight = getScreenHeight(AppUtils.getContext());
//		int statusBarHeight = getStatusBarHeight(AppUtils.getCurrentActivity());
//		int dialogHeight = screenHeight - statusBarHeight;
//		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
//	}*/

	public static boolean isAbove19(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	public static boolean isAbove21(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}

	public static boolean isAbove23(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}

	public static boolean isAbove24(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
	}

	public static boolean isAbove26(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
	}
}
