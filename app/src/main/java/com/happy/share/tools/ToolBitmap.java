package com.happy.share.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * desc: 图片工具类 <br/>
 * PS:此工具类需要两次测试，若验证方法是否能正常运行，请运行单元测试内容'ToolBitmapTest'<br/>
 * 若验证图片展示是否正常，请运行App项目中'TestToolBitmapActivity'<br/>
 * time: 2018/7/19 下午2:45 <br/>
 * author: 匡衡 <br/>
 * since V 1.0 <br/>
 */
public interface ToolBitmap {

    /**
     * 清除回收图片
     *
     * @param bitmaps 图片集合
     */
    static void recycleBitmaps(@Nullable Bitmap... bitmaps) {
        if (bitmaps != null) {
            for (Bitmap bitmap : bitmaps) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**
     * 根据角度，旋转图片 <br/>
     *
     * @param degrees 旋转角度
     * @param bitmap  原始图片
     * @return 旋转后的图片。如果传入的原图为空，则返回空
     */
    @Nullable
    static Bitmap rotatingBitmap(@FloatRange(from = 0.0f, to = 360.0f) float degrees, @Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        //旋转角度为0或者负数时，不进行旋转
        if (degrees <= 0) {
            return bitmap;
        }

        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
                    , matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        if (returnBm == null) {
            return bitmap;
        }

        return returnBm;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param filePath 图片绝对路径
     * @return degree旋转的角度, 默认返回'0'
     */
    static int readPictureDegree(@Nullable String filePath) {
        int degree = 0;
        if (TextUtils.isEmpty(filePath)) {
            return degree;
        }

        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION
                    , ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param bitmap Bitmap
     * @return 获取bitmap的config, 为空则返回ARGB.8888
     */
    @NonNull
    static Bitmap.Config getBitmapConfig(@Nullable Bitmap bitmap) {
        return (bitmap != null && bitmap.getConfig() != null) ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    /**
     * 图片灰处理 <br/>
     * 以原始图片为模板重新绘制图片，绘制中加入灰色蒙板<br/>
     *
     * @param originalBitmap 原始图片
     * @param greyBitmap     被绘制上灰色的图片
     * @return 灰处理后的图片
     */
    @Nullable
    static Bitmap changBitmapToGrey(@Nullable Bitmap originalBitmap, @Nullable Bitmap greyBitmap) {
        if (originalBitmap == null) {
            return null;
        }
        //创建承载灰色蒙板的图片实例
        Bitmap changeGreyBitmap = greyBitmap;
        if (changeGreyBitmap == null) {
            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            changeGreyBitmap = Bitmap.createBitmap(width, height, getBitmapConfig(originalBitmap));
        }

        Canvas canvas = new Canvas(changeGreyBitmap);
        Paint paint = new Paint();
        //创建颜色变换矩阵
        ColorMatrix colorMatrix = new ColorMatrix();
        //设置灰度影响范围
        colorMatrix.setSaturation(0);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        //设置画笔的颜色过滤矩阵
        paint.setColorFilter(colorMatrixFilter);
        //使用处理后的画笔绘制图像
        canvas.drawBitmap(originalBitmap, 0, 0, paint);
        return changeGreyBitmap;
    }

    /**
     * 根据Config 获取压缩后的图片 <br/>
     * PS:同时回收原始图片，即recycler掉
     *
     * @param bitmap  原图
     * @param config  {@link Bitmap.Config}
     * @param quality 压缩值，范围在0-100数值
     * @return 压缩后的图片，图片格式为JPEG
     */
    @Nullable
    static Bitmap getCompressBitmapByConfigAndQuality(@Nullable Bitmap bitmap, @NonNull Bitmap.Config config
            , @IntRange(from = 0, to = 100) int quality) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = config;
        Bitmap outBm = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length
                , options);
        recycleBitmaps(bitmap);
        return outBm;
    }

    /**
     * 获取压缩后的图片 <br/>
     * 由于创建图片时Options的inSampleSize参数为int类型，压缩后无法真正达到目标所希望的图片大小，只能是接近目标宽高值
     *
     * @param filePath 图片文件地址
     * @param config   {@link Bitmap.Config}
     * @param destW    压缩后的目标宽度
     * @param destH    压缩后的目标高度
     * @param quality  压缩值，范围在0-100数值
     * @return 压缩后的图片
     */
    @Nullable
    static Bitmap getCompressBitmap(@Nullable String filePath, @NonNull Bitmap.Config config, int destW
            , int destH, @IntRange(from = 0, to = 100) int quality) {
        if (TextUtils.isEmpty(filePath) || destH <= 0 || destW <= 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        // 压缩宽高比例
        float blW = (float) options.outWidth / destW;
        float blH = (float) options.outHeight / destH;
        options.inSampleSize = 1;
        if (blW > 1 || blH > 1) {
            float bl = (blW > blH ? blW : blH);
            options.inSampleSize = (int) (bl + 0.9f);// 尽量不失真
        }

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        //需要再次压缩图片质量时
        if (quality != 100) {
            bitmap = getCompressBitmapByConfigAndQuality(bitmap, config, quality);
        }
        //处理拍照角度旋转的问题
        int degree = readPictureDegree(filePath);
        return degree == 0 ? bitmap : rotatingBitmap(degree, bitmap);
    }

}
