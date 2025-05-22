package com.example.administrator.androidtest.other.fileMedia.Provider;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MediaConstant {
    public static final int NONE = -1;
    public static final int VIDEO = 1;
    public static final int IMAGE = 2;
    public static final int AUDIO = 3;
    public static final int TEXT = 4;
    public static final int APPLICATION = 5;


    public static Set<String> VIDEO_MIME_SET = new HashSet<>();
    public static Set<String> IMAGE_MIME_SET = new HashSet<>();
    public static Set<String> AUDIO_MIME_SET = new HashSet<>();

    public static Set<String> VIDEO_SUFFIX_SET = new HashSet<>();
    public static Set<String> IMAGE_SUFFIX_SET = new HashSet<>();
    public static Set<String> AUDIO_SUFFIX_SET = new HashSet<>();

    static {
        Collections.addAll(VIDEO_MIME_SET, "video/3gpp", "video/x-msvideo", "video/quicktime", "video/mpeg", "video/mp4");
        Collections.addAll(IMAGE_MIME_SET, "image/bmp", "image/gif", "image/jpeg", "image/png", "image/svg+xml", "image/webp");
        Collections.addAll(AUDIO_MIME_SET, "audio/mpeg", "audio/wav");

        Collections.addAll(VIDEO_SUFFIX_SET, "3gp", "mp4", "avi", "mov");
        Collections.addAll(IMAGE_SUFFIX_SET, "bmp", "gif", "jpeg", "jpg", "png", "webp", "svg");
        Collections.addAll(AUDIO_SUFFIX_SET, "mp3", "wav");
    }


    public static Map<String, String> SUFFIX_MIME_MAP = new HashMap<>();

    static {
        SUFFIX_MIME_MAP.put("3gp", "video/3gpp");
        SUFFIX_MIME_MAP.put("avi", "video/x-msvideo");
        SUFFIX_MIME_MAP.put("mov", "video/quicktime");
        SUFFIX_MIME_MAP.put("mp4", "video/mp4");
        SUFFIX_MIME_MAP.put("mpeg", "video/mpeg");
        SUFFIX_MIME_MAP.put("mpg", "video/mpeg");
        SUFFIX_MIME_MAP.put("bmp", "image/bmp");
        SUFFIX_MIME_MAP.put("gif", "image/gif");
        SUFFIX_MIME_MAP.put("jpeg", "image/jpeg");
        SUFFIX_MIME_MAP.put("jpg", "image/jpeg");
        SUFFIX_MIME_MAP.put("png", "image/png");
        SUFFIX_MIME_MAP.put("webp", "image/webp");
        SUFFIX_MIME_MAP.put("svg", "image/svg+xml");
        SUFFIX_MIME_MAP.put("mp3", "video/mpeg");
        SUFFIX_MIME_MAP.put("rmvb", "audio/3gpp");
        SUFFIX_MIME_MAP.put("wav", "audio/wav");
    }

    public static String mime(String path) {
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        return SUFFIX_MIME_MAP.get(suffix);
    }

    public static String mime(File file) {
        return mime(file.getAbsolutePath());
    }

    public static int type(File file) {
        return type(file.getAbsolutePath());
    }

    public static int type(String path) {
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        int result = NONE;
        if (VIDEO_SUFFIX_SET.contains(suffix)) {
            result = VIDEO;
        } else if (IMAGE_SUFFIX_SET.contains(suffix)) {
            result = IMAGE;
        } else if (AUDIO_SUFFIX_SET.contains(suffix)) {
            result = AUDIO;
        }
        return result;
    }
}
