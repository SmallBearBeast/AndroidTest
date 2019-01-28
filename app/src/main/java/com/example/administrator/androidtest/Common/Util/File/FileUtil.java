package com.example.administrator.androidtest.Common.Util.File;

import java.io.File;

/**
 * 文件操作工具类
 */
public class FileUtil {

    /**
     * 文件夹是否包含文件
     */
    public static boolean isDirContainFile(String dirPath, String fileName) {
        if (!checkPath(dirPath))
            return false;
        File dirFile = new File(dirPath);
        return isDirContainFile(dirFile, fileName);
    }

    public static boolean isDirContainFile(File dirFile, String fileName) {
        if (dirFile.getName().equals(fileName))
            return true;
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                boolean isContain = isDirContainFile(files[i], fileName);
                if (isContain)
                    return true;
            }
        }
        return false;
    }
    /***文件夹是否包含文件**/

    /**
     * 创建文件夹
     */
    public static boolean createDir(String dirPath) {
        if (!checkPath(dirPath))
            return false;
        File dirFile = new File(dirPath);
        return createDir(dirFile);
    }

    public static boolean createDir(File dirFile) {
        if (dirFile == null)
            return false;
        if (!dirFile.exists() && !dirFile.mkdirs())
            return false;
        return true;
    }
    /**创建文件夹**/

    /**
     * 创建文件
     */
    public static boolean createFile(String filePath) {
        if (!checkPath(filePath))
            return false;
        return createFile(new File(filePath));
    }

    public static boolean createFile(File file) {
        if (file == null)
            return false;
        return createFile(file.getParent(), file.getName());
    }

    public static boolean createFile(String dirPath, String fileName) {
        if (!checkPath(dirPath) || !checkPath(fileName))
            return false;
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
        if (isFileExist(dstPath) && isFileExist(srcPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return moveD1ChildToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean moveD1ChildToD2(File srcFile, File dstFile) {
        if(srcFile == null || dstFile == null)
            return false;
        File[] files = srcFile.listFiles();
        if(files != null){
            for (int i = 0; i < files.length; i++) {
                boolean result = moveD1ToD2(files[i], dstFile);
                if (!result)
                    return false;
            }
        }
        return true;
    }

    public static boolean moveD1ToD2(String srcPath, String dstPath) {
        if (isFileExist(dstPath) && isFileExist(srcPath)) {
            File dstFile = new File(dstPath);
            File srcFile = new File(srcPath);
            return moveD1ToD2(srcFile, dstFile);
        }
        return false;
    }

    public static boolean moveD1ToD2(File srcFile, File dstFile) {
        if (srcFile == null || dstFile == null)
            return false;
        File newDst = new File(dstFile, srcFile.getName());
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            if (newDst.mkdirs()) {
                for (int i = 0; i < files.length; i++) {
                    boolean result = moveD1ToD2(files[i], newDst);
                    if (!result)
                        return false;
                }
                return srcFile.delete();
            }
        } else if (srcFile.isFile()) {
            try {
                if (srcFile.renameTo(newDst)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**移动文件夹1的文件到文件夹2**/

    /**
     * 检查文件路径
     */
    private static boolean checkPath(String path) {
        if (path == null || path.trim().equals("")) {
            return false;
        }
        return true;
    }
    /**检查文件路径**/

    /**
     * 删除文件||删除文件夹里面的文件
     */
    public static boolean deleteChildFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return deleteChildFile(file);
        }
        return false;
    }

    public static boolean deleteChildFile(File file) {
        if (file == null)
            return false;
        File[] files = file.listFiles();
        if (files == null)
            return false;
        for (int i = 0; i < files.length; i++) {
            boolean success = deleteFile(files[i]);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    public static boolean deleteFile(String filePath) {
        if (!checkPath(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return deleteFile(file);
        }
        return false;
    }


    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
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
    /**删除文件||删除文件夹里面的文件**/

    /**
     * 获取后缀
     */
    public static String getSuffix(String path){
        if(checkPath(path)){
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
        if(isFileExist(path)){
            File file = new File(path);
            return file.getParentFile().getAbsolutePath();
        }
        return null;
    }

    public static String getParent(File file){
        if(isFileExist(file)){
            return file.getParentFile().getAbsolutePath();
        }
        return null;
    }
    /**获取文件目录路径**/

    /**获取文件名字**/
    public static String getName(String path){
        if(isFileExist(path)){
            return new File(path).getName();
        }
        return null;
    }

    public static String getName(File file){
        if(isFileExist(file)){
            return file.getName();
        }
        return null;
    }
    /**获取文件名字**/

}
