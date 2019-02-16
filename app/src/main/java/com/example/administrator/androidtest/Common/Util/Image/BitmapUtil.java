package com.example.administrator.androidtest.Common.Util.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.example.administrator.androidtest.Common.Util.Other.IOUtil;

import java.io.FileOutputStream;

public class BitmapUtil {
    public static Bitmap getBitmap(String path, int maxWidth, int maxHeight){
        BitmapFactory.Options op = OptionsUtil.optionsByWH(path, maxWidth, maxHeight);
        return BitmapFactory.decodeFile(path, op);
    }

    public static Bitmap getBitmap(String path, int inSampleSize){
        BitmapFactory.Options op = OptionsUtil.optionsByRate(path, inSampleSize);
        return BitmapFactory.decodeFile(path, op);
    }

    public static void save(String path, int maxWidth, int maxHeight, String savePath){
        save(getBitmap(path, maxWidth, maxHeight), savePath);
    }

    public static void save(String path, int inSampleSize, String savePath){
        save(getBitmap(path, inSampleSize), savePath);
    }

    public static void save(Bitmap bitmap, String savePath){
        if(FileUtil.createFile(savePath)){
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(savePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                bitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(fos);
            }
        }
    }

    public static BitmapOp rotate(BitmapOp bop, int degree){
        bop.mMatrix.postRotate(degree);
        return bop;
    }

    public static BitmapOp rotate(BitmapOp bop, int degree, float px, float py){
        bop.mMatrix.postRotate(degree, px, py);
        return bop;
    }

    public static BitmapOp translate(BitmapOp bop, float dx, float dy){
        bop.mMatrix.postTranslate(dx, dy);
        return bop;
    }

    public static BitmapOp scale(BitmapOp bop, float sx, float sy){
        bop.mMatrix.postScale(sx, sy);
        return bop;
    }

    public static class BitmapOp{
        private Matrix mMatrix;
        private Bitmap mSrcBm;

        public BitmapOp(Bitmap srcBm){
            mSrcBm = srcBm;
            mMatrix = new Matrix();
        }

        public Bitmap bitmap(){
            return Bitmap.createBitmap(mSrcBm, 0, 0, mSrcBm.getWidth(), mSrcBm.getHeight(), mMatrix, true);
        }
    }
}
