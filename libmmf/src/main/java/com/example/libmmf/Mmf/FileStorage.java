package com.example.libmmf.Mmf;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// TODO: 2020-02-27 Conditions for the end of inputStream
public class FileStorage {
    public static boolean writeStream(String path, InputStream inputStream) {
        if (!InternalUtil.createFile(path)) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path));
            byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalUtil.close(fos);
        }
        return false;
    }

    public static boolean writeJsonObj(String path, Object object) {
        if (!InternalUtil.createFile(path)) {
            return false;
        }
        return writeStr(path, InternalUtil.toJson(object));
    }

    public static boolean writeStr(String path, String str) {
        if (!InternalUtil.createFile(path)) {
            return false;
        }
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(new File(path)));
            osw.write(str);
            osw.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalUtil.close(osw);
        }
        return false;
    }

    public static void readStream(String path, StreamCallback<byte[]> streamCallback) {
        if (streamCallback == null) {
            return;
        }
        if (!InternalUtil.createFile(path)) {
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
            byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
                streamCallback.success(buffer, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
            streamCallback.fail();
        } finally {
            InternalUtil.close(fis);
        }
    }


    public static String readStr(String path) {
        if (!InternalUtil.createFile(path)) {
            return null;
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(new File(path)));
            char[] buffer = new char[4096];
            int read = 0;
            StringBuilder builder = new StringBuilder();
            while ((read = isr.read(buffer, 0, buffer.length)) != -1) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalUtil.close(isr);
        }
        return null;
    }

    public static <T> T readJsonObj(String path) {
        if (!InternalUtil.createFile(path)) {
            return null;
        }
        return InternalUtil.toObj(readStr(path), new TypeToken<T>() {});
    }
}
