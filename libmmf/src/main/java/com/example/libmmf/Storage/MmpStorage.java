package com.example.libmmf.Storage;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MmpStorage {
    private static final int BUFFER_SIZE = 4096;
    public static void main(String[] args) {
//        if (!InternalUtil.createFile("/Users/wuyisong/mmp/text_mmp.txt")) {
//            return;
//        }
//        File file = new File("/Users/wuyisong/mmp/text_mmp.txt");
//        try {
////            FileStorage.writeStream("/Users/wuyisong/mmp/text_mmp_copy.txt", new FileInputStream(file));
//            writeStream("/Users/wuyisong/mmp/text_mmp_copy.txt", new FileInputStream(file));
////            if (file.delete()) {
////                System.out.println("delete success");
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static boolean writeStream(String path, InputStream inputStream) {
        if (!InternalUtil.createFile(path)) {
            return false;
        }
        RandomAccessFile raf = null;
        FileChannel fc = null;
        MappedByteBuffer mbb = null;
        File file = new File(path);
        try {
            raf = new RandomAccessFile(file, "rw");
            fc = raf.getChannel();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0;
            int hasRead = 0;
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                mbb = fc.map(FileChannel.MapMode.READ_WRITE, hasRead, hasRead + read);
                mbb.put(buffer, 0, read);
                hasRead = hasRead + read;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (file.delete()) {

            }
        } finally {
            InternalUtil.close(fc, raf);
            unmap(mbb);
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
        RandomAccessFile raf = null;
        FileChannel fc = null;
        MappedByteBuffer mbb = null;
        try {
            raf = new RandomAccessFile(path, "rw");
            fc = raf.getChannel();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0;
            int hasRead = 0;
            int size = (int) fc.size();
            while (hasRead < size) {
                read = hasRead + buffer.length < size ? buffer.length : size - hasRead;
                mbb = fc.map(FileChannel.MapMode.READ_WRITE, hasRead, hasRead + read);
                mbb.get(buffer, 0, read);
                streamCallback.success(buffer, read);
                hasRead = hasRead + read;
            }
        } catch (Exception e) {
            e.printStackTrace();
            streamCallback.fail();
        } finally {
            unmap(mbb);
            InternalUtil.close(fc, raf);
        }
    }

    private static void unmap(MappedByteBuffer mbb) {
        if (mbb == null) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("sun.nio.ch.FileChannelImpl");
            Method m = clazz.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(null, mbb);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
