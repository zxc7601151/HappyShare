package com.happy.share.common.glide.glideimpl.transform;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * desc: Glide 旋转Transform <br/>
 * time: 2017/3/16 14:39 <br/>
 * author: 尾生 <br/>
 * since 搭配V1.0 <br/>
 */
public class RotateTransformation extends BitmapTransformation {

    private static final String ID = CropTransformation.class.getCanonicalName();

    /**
     * 旋转角度
     */
    private float mRotateAngle;

    public RotateTransformation(float rotateAngle) {
        this.mRotateAngle = rotateAngle;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool
            , @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        matrix.postRotate(mRotateAngle);
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth()
                , toTransform.getHeight(), matrix, true);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + mRotateAngle).getBytes(CHARSET));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RotateTransformation &&
                ((RotateTransformation) o).mRotateAngle == mRotateAngle;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + (int) (mRotateAngle * 10);
    }
}