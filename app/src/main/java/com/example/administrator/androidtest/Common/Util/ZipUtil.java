package com.example.administrator.androidtest.Common.Util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by wusijun on 2018/5/8.
 * 文件压缩的帮助类
 */

public class ZipUtil {
    private static final String TAG = "CompressUtils";

    /**
     * 压缩文件
     *
     * @param entryName 入口名字
     * @param srcFile   源文件路径
     * @param desFile   目标文件路径
     * @return
     */
    public static boolean compress(@NonNull String entryName, @NonNull String srcFile, @NonNull String desFile) {
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(desFile));
            zos = new ZipOutputStream(bos);

            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);

            bis = new BufferedInputStream(new FileInputStream(srcFile));

            byte[] b = new byte[2048];
            int bytesRead;

            while ((bytesRead = bis.read(b)) != -1) {
                zos.write(b, 0, bytesRead);
            }

            zos.closeEntry();
            zos.flush();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    /**
     * 压缩文件
     *
     * @param srcPathName 源文件路径
     * @param zipFile     压缩后文件路径
     * @param suffix      文件后缀
     * @param fileFilter  文件过滤器
     * @param fileMatch   文件名的正则串
     */
    public static void compress(@NonNull String srcPathName, @NonNull String zipFile, @NonNull String suffix,
                                FileFilter fileFilter, String fileMatch) {
        File file = new File(srcPathName);
        if (!file.exists())
            return;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir, suffix, fileFilter, fileMatch);
            out.close();
        } catch (Exception e) {
            Log.e(TAG, "IOEx:", e);
        }
    }

    /**
     * 压缩文件或文件夹
     *
     * @param file       文件
     * @param out        压缩后的流
     * @param basedir    路径
     * @param suffix     文件名后缀
     * @param fileFilter 文件过滤器
     * @param fileMatch  文件名的正则匹配
     */
    public static void compress(@NonNull File file, @NonNull ZipOutputStream out,
                                @NonNull String basedir, @NonNull String suffix,
                                FileFilter fileFilter, String fileMatch) {
        if (file.isDirectory()) {
            Log.d(TAG, "compress：" + basedir + file.getName());
            compressDirectory(file, out, basedir, suffix, fileFilter, fileMatch);
        } else {
            Log.d(TAG, "compress：" + basedir + file.getName());
            compressFile(file, out, basedir);
        }
    }

    /**
     * 压缩文件夹
     *
     * @param dir        文件夹
     * @param out        压缩的流
     * @param basedir    文件路径
     * @param suffix     文件后缀
     * @param fileFilter 文件过滤器
     * @param fileMatch  文件名的正则匹配
     */
    public static void compressDirectory(@NonNull File dir, @NonNull ZipOutputStream out, @NonNull String basedir,
                                         @NonNull final String suffix, final FileFilter fileFilter, final String fileMatch) {
        if (!dir.exists())
            return;

        final Pattern pattern;
        if (TextUtils.isEmpty(fileMatch)) {
            pattern = null;
        } else {
            pattern = Pattern.compile(fileMatch);
        }
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(suffix) && (fileFilter == null || fileFilter.accept(file)) &&
                        (pattern == null || pattern.matcher(s).matches());
            }
        });
        for (int i = 0; i < files.length; i++) {
            compress(files[i], out, basedir + dir.getName() + "/", suffix, fileFilter, fileMatch);
        }
    }

    /**
     * 压缩文件
     *
     * @param file    文件
     * @param out     压缩后的文件输出流
     * @param basedir 压缩文件路径
     */
    public static void compressFile(@NonNull File file, @NonNull ZipOutputStream out, @NonNull String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[1024];
            while ((count = bis.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            Log.e(TAG, "comPressFileEx", e);
        }
    }

    /**
     * 解压zip文件
     * @param file zip文件
     * @param destFolder 目标文件夹
     * @return
     */
    public static boolean unzip(File file, File destFolder) {
        if (file == null || !file.exists() || destFolder == null) {
            Log.e(TAG, "unzip fail file=" + file + "; destFolder=" + destFolder);
            return false;
        }

//        FileUtil.createDirByDeleteOldDir(destFolder);

        boolean result = false;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file.getAbsolutePath());
            Enumeration enumeration = zipFile.entries();
            InputStream in;
            if (enumeration != null) {
                while (enumeration.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) enumeration.nextElement();
                    if (entry.getName().contains("../")) {
                        throw new Exception("Unsecurity zipfile!");
                    }
                    if (entry.isDirectory()) {
                        File dir = new File(destFolder, entry.getName());
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                    } else {
                        in = zipFile.getInputStream(entry);
                        if (in == null) {
                            continue;
                        }
                        readZipSubFile(in, entry, destFolder);
                    }
                }
            }
            result = true;
        } catch (Exception ex) {
            Log.e(TAG, "unzip fail with exception", ex);
            result = false;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private static void readZipSubFile(InputStream inputStream, ZipEntry entry, File baseDir) {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            if (entry.getName().contains("../")) {
                throw new Exception("Unsecurity zipfile!");
            }
            File file = new File(baseDir, entry.getName());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fileOutputStream = new FileOutputStream(file);
            outputStream = new BufferedOutputStream(fileOutputStream);
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtil.close(outputStream);
            IOUtil.close(fileOutputStream);
            IOUtil.close(inputStream);
        }
    }
}
