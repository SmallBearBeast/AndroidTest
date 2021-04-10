package com.example.libbase.Util;

import java.io.*;

/**
 * 文件操作工具类
 */
public class FileUtil {

    /**
     * 文件夹是否包含文件 包含返回true，不包含返回false
     */
    public static boolean isDirContainFile(String dirPath, String fileName) {
        if(!isFileExist(dirPath) && checkPath(fileName)){
            return false;
        }
        File dirFile = new File(dirPath);
        return isDirContainFile(dirFile, fileName);
    }

    public static boolean isDirContainFile(File dirFile, String fileName) {
        if(!isFileExist(dirFile) && checkPath(fileName)){
            return false;
        }
        if (dirFile.getName().equals(fileName)) {
            return true;
        }
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                boolean isContain = isDirContainFile(files[i], fileName);
                if (isContain) {
                    return true;
                }
            }
        }
        return false;
    }
    /***文件夹是否包含文件**/

    /**
     * 创建文件夹 成功返回true，失败返回false
     */
    public static boolean createDir(String dirPath) {
        if (!checkPath(dirPath)) {
            return false;
        }
        File dirFile = new File(dirPath);
        return createDir(dirFile);
    }

    public static boolean createDir(File dirFile) {
        if (dirFile == null) {
            return false;
        }
        return dirFile.exists() || dirFile.mkdirs();
    }
    /**创建文件夹**/

    /**
     * 创建文件 成功返回true，失败返回false
     */
    public static boolean createFile(String filePath) {
        if (!checkPath(filePath)) {
            return false;
        }
        return createFile(new File(filePath));
    }

    public static boolean createFile(File file) {
        if (file == null) {
            return false;
        }
        return createFile(file.getParent(), file.getName());
    }

    public static boolean createFile(String dirPath, String fileName) {
        if (!checkPath(dirPath) || !checkPath(fileName)) {
            return false;
        }
        if (createDir(dirPath)) {
            File file = new File(dirPath, fileName);
            if (!file.exists()) {
                try {
                    return file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /**创建文件**/

    /**
     * 文件是否存在
     */
    public static boolean isFileExist(File file) {
        return file != null && file.exists();
    }

    public static boolean isFileExist(String path) {
        return isFileExist(new File(path));
    }
    /**文件是否存在**/


    /**
     * 移动文件夹1的文件到文件夹2
     */
    public static boolean moveD1ChildToD2(String srcPath, String dstPath) {
        if (isFileExist(srcPath) && checkPath(dstPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return moveD1ChildToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean moveD1ChildToD2(File srcFile, File dstFile) {
        if (isFileExist(srcFile) && checkPath(dstFile)) {
            File[] files = srcFile.listFiles();
            if(files != null){
                for (File file : files) {
                    boolean result = moveD1ToD2(file, dstFile);
                    if (!result) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean moveD1ToD2(String srcPath, String dstPath) {
        if (isFileExist(srcPath) && checkPath(dstPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return moveD1ToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean moveD1ToD2(File srcFile, File dstFile) {
        if (isFileExist(srcFile) && checkPath(dstFile)) {
            File newDst = new File(dstFile, srcFile.getName());
            if (srcFile.isDirectory()) {
                File[] files = srcFile.listFiles();
                if (newDst.mkdirs()) {
                    for (File file : files) {
                        boolean result = moveD1ToD2(file, newDst);
                        if (!result) {
                            return false;
                        }
                    }
                    return srcFile.delete();
                }
            } else if (srcFile.isFile()) {
                try {
                    return srcFile.renameTo(newDst);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    /**移动文件夹1的文件到文件夹2**/

    /**
     * 复制文件夹1的文件到文件夹2
     */
    public static boolean copyD1ChildToD2(String srcPath, String dstPath) {
        if (isFileExist(srcPath) && checkPath(dstPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return copyD1ChildToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean copyD1ChildToD2(File srcFile, File dstFile) {
        if (isFileExist(srcFile) && checkPath(dstFile)) {
            File[] files = srcFile.listFiles();
            if(files != null){
                for (File file : files) {
                    boolean result = copyD1ToD2(file, dstFile);
                    if (!result) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean copyD1ToD2(String srcPath, String dstPath) {
        if (isFileExist(srcPath) && checkPath(dstPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return copyD1ToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean copyD1ToD2(File srcFile, File dstFile) {
        if (isFileExist(srcFile) && checkPath(dstFile)) {
            File newDst = new File(dstFile, srcFile.getName());
            if (srcFile.isDirectory()) {
                File[] files = srcFile.listFiles();
                if (newDst.mkdirs()) {
                    for (File file : files) {
                        boolean result = copyD1ToD2(file, newDst);
                        if (!result) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (srcFile.isFile()) {
                return copy(srcFile, newDst);
            }
        }
        return false;
    }

    public static boolean copy(File srcFile, File dstFile){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            byte[] buffer = new byte[1024];
            long fileLen = srcFile.length();
            int hasRead = 0;
            int read = 0;

            fos = new FileOutputStream(dstFile);
            while (read != -1 && hasRead < fileLen){
                read = fis.read(buffer, hasRead, 1024);
                hasRead = hasRead + read;
                if(read == -1 && hasRead < fileLen){
                    return false;
                }
                if(read > 0) {
                    fos.write(buffer, 0, read);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**复制文件夹1的文件到文件夹2**/

    /**
     * 检查文件路径
     */
    private static boolean checkPath(String path) {
        if (path == null || path.trim().equals("")) {
            return false;
        }
        return true;
    }

    private static boolean checkPath(File file) {
        return file != null;
    }
    /**检查文件路径**/

    /**
     * 删除文件||删除文件夹里面的文件
     */
    public static boolean deleteChildFile(String filePath) {
        if(isFileExist(filePath)){
            File file = new File(filePath);
            return deleteChildFile(file);
        }
        return false;
    }

    public static boolean deleteChildFile(File file) {
        if(isFileExist(file)){
            File[] files = file.listFiles();
            if (files == null) {
                return false;
            }
            for (int i = 0; i < files.length; i++) {
                boolean success = deleteFile(files[i]);
                if (!success) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean deleteFile(String filePath) {
        if(isFileExist(filePath)){
            File file = new File(filePath);
            if (file.exists()) {
                return deleteFile(file);
            }
        }
        return false;
    }


    public static boolean deleteFile(File file) {
        if (isFileExist(file)) {
            if (file.isDirectory()) {
                File[] child = file.listFiles();
                if (child != null) {
                    for (int i = 0; i < child.length; i++) {
                        boolean success = deleteFile(child[i]);
                        if (!success) {
                            return false;
                        }
                    }
                }
            }
            return file.delete();
        }
        return false;
    }
    /**删除文件||删除文件夹里面的文件**/

    /**
     * 获取后缀
     */
    public static String getSuffix(String path){
        if(!checkPath(path)){
            return null;
        }
        int indexOfPoint = path.lastIndexOf(".");
        if(indexOfPoint != -1){
            return path.substring(indexOfPoint + 1);
        }
        return null;
    }
    /**获取后缀**/


    /**获取文件目录路径**/
    public static String getParent(String path){
        if(checkPath(path)){
            return getParent(new File(path));
        }
        return null;
    }

    public static String getParent(File file){
        if(checkPath(file)){
            return file.getParentFile().getAbsolutePath();
        }
        return null;
    }
    /**获取文件目录路径**/

    /**获取文件名字**/
    public static String getName(String path){
        if(checkPath(path)){
            return getName(new File(path));
        }
        return null;
    }

    public static String getName(File file){
        if(checkPath(file)){
            return file.getName();
        }
        return null;
    }
    /**获取文件名字**/

    /**
     * 重新命名文件
     */
    public static boolean rename(String srcPath, String dstPath){
        if(isFileExist(srcPath) && checkPath(dstPath)){
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);
            return srcFile.renameTo(dstFile);
        }
        return false;
    }
    /**重新命名文件**/


    /**
     * 获取文件大小 不存在返回0
     */
    public static long getFileSize(String path){
        if(isFileExist(path)){
            return getFileSize(new File(path));
        }
        return 0;
    }

    public static long getFileSize(File file){
        if(isFileExist(file)){
            if(file.isFile()){
                return file.length();
            }else if(file.isDirectory()){
                long fileSize = 0;
                File[] files = file.listFiles();
                for (int i = 0, len = files.length; i < len; i++) {
                    fileSize = fileSize + getFileSize(files[i]);
                }
                return fileSize;
            }
        }
        return 0;
    }
    /**获取文件大小**/
}
