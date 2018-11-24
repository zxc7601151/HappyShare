package com.happy.share.tools;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.happy.share.ApplicationBase;
import com.happy.share.common.consts.CommonConst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * desc: 文件操作类 <br/>
 * time: 2015-3-7 上午11:05:15 <br/>
 * author: rooky <br/>
 * since V 3.2.0 <br/>
 */
public class ToolSdCardFile {

    /**
     * 外部文件是否可写
     *
     * @return true: 外设存储设备可写；<br/>
     */
    public static boolean isExternalStorageWriteable() {
        // Environment.MEDIA_MOUNTED_READ_ONLY.equals()//只读
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * desc：读取文件内容
     *
     * @param file 文件
     */
    public static String readFileContent(File file) {
        StringBuffer sb = new StringBuffer("");

        if (file.exists()) {
            FileReader fr = null;
            BufferedReader br = null;

            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String lineTemp;

                while ((lineTemp = br.readLine()) != null) {
                    sb.append(lineTemp);
                    lineTemp = br.readLine();
                }
            } catch (IOException e) {
                Log.e("ToolSdCardFile","readFileContent() = " + e.toString());
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        Log.e("ToolSdCardFile","readFileContent() = " + e.toString());
                    }
                }

                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        Log.e("ToolSdCardFile","readFileContent() = " + e.toString());
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * 向文件中写内容
     *
     * @param file     文件
     * @param msg      写入信息
     * @param isAppend true:追加内容；false:覆盖内容
     */
    public static void writeFileContent(File file, String msg, boolean isAppend) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file, isAppend);
            bw = new BufferedWriter(fw);
            bw.write(msg);
            bw.close();
            fw.close();
        } catch (IOException e) {
            Log.e("ToolSdCardFile","writeFileContent() = " + e.toString());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                Log.e("ToolSdCardFile","writeFileContent() = " + e.toString());
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *                    {@link CommonConst#DIR_IMAGE}, {@link CommonConst#DIR_CACHE},{@link CommonConst#DIR_RESOURCE}
     *                    {@link CommonConst#DIR_TEMP},{@link CommonConst#DIR_UNIQUE}}
     * @return File
     */
    @NonNull
    public static File createRootFile(@NonNull String rootFileStr) {
        Context ctx = ApplicationBase.getInstance();

        //创建文件夹
        File fileDir = ctx.getExternalFilesDir(rootFileStr);
        if (fileDir == null) {
            fileDir = new File(ctx.getFilesDir(), rootFileStr);
        }
        return fileDir;
    }

    /**
     * 创建文件  (内部创建文件)
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *                    {@link CommonConst#DIR_IMAGE}, {@link CommonConst#DIR_CACHE},{@link CommonConst#DIR_RESOURCE}
     *                    {@link CommonConst#DIR_TEMP},{@link CommonConst#DIR_UNIQUE}}
     * @param fileName    文件名称
     * @return File
     */
    @Nullable
    public static File createFile(@NonNull String rootFileStr, @NonNull String fileName) {
        File file = new File(createRootFile(rootFileStr), fileName);

        //创建文件
        boolean isCreateSuccess = file.exists();
        if (!isCreateSuccess) {
            try {
                isCreateSuccess = file.createNewFile();
            } catch (IOException e) {
                Log.e("ToolSdCardFile","createFile()"+ e.toString());
            }
        }

        return isCreateSuccess ? file : null;
    }

    /**
     * 创建文件夹  (内部创建文件)
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *                    {@link CommonConst#DIR_IMAGE}, {@link CommonConst#DIR_CACHE},{@link CommonConst#DIR_RESOURCE}
     *                    {@link CommonConst#DIR_TEMP},{@link CommonConst#DIR_UNIQUE}}
     * @param fileDirName 文件夹名称
     * @return File
     */
    @Nullable
    public static File createFileDir(@NonNull String rootFileStr, @NonNull String fileDirName) {
        File file = new File(createRootFile(rootFileStr), fileDirName);

        //创建文件夹
        boolean isCreateSuccess = file.exists();
        if (!isCreateSuccess) {
            isCreateSuccess = file.mkdirs();
        }

        return isCreateSuccess ? file : null;
    }

    /**
     * 根据传入的二级根目录，获取其绝对路径
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *                    {@link CommonConst#DIR_IMAGE}, {@link CommonConst#DIR_CACHE},{@link CommonConst#DIR_RESOURCE}
     *                    {@link CommonConst#DIR_TEMP},{@link CommonConst#DIR_UNIQUE}}
     * @return 绝对路径
     */
    @NonNull
    public static String getRootFileAbsolutePath(@NonNull String rootFileStr) {
        return createRootFile(rootFileStr).getAbsolutePath();
    }

    /**
     * 根据传入的参数，获取绝对路径
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *
     * @param fileName    文件名称
     * @return 绝对路径
     */
    @NonNull
    public static String getFileAbsolutePath(@NonNull String rootFileStr, @NonNull String fileName) {
        return getRootFileAbsolutePath(rootFileStr) + "/" + fileName;
    }

    /**
     * 存储图片
     *
     * @param bitmap      图片
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *
     * @param fileName    文件名称
     */
    @Nullable
    public static File saveBitmap(Bitmap bitmap, String rootFileStr, String fileName) {
        return saveBitmap(bitmap, 70, rootFileStr, fileName);
    }

    /**
     * 存储图片
     *
     * @param bitmap      图片
     * @param quality     图片质量
     * @param rootFileStr 二级根目录名称，值为：<br/>
     * @param fileName    文件名称
     */
    @Nullable
    public static File saveBitmap(Bitmap bitmap, int quality, String rootFileStr, String fileName) {
        if (bitmap == null){
            return null;
        }
        FileOutputStream fos = null;
        File fileImg = null;

        try {
            fileImg = createFile(rootFileStr, fileName);
            if (fileImg == null) {
                return null;
            }

            fos = new FileOutputStream(fileImg);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)) {
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            Log.e("ToolSdCardFile","saveBitmap()"+ e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e("ToolSdCardFile","saveBitmap()"+ e.toString());
                }
            }
        }

        return fileImg;
    }



    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     */
    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 判断该文件是否是一个图片。
     */
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    /**
     * 清理cache下的图片缓存  <br/>
     */
    public synchronized static void clearCache() {
        clearFile(CommonConst.DIR_CACHE, null);
    }

    /**
     * 清楚resource下的H5资源文件包
     */
    public synchronized static void clearResource() {
        clearFile(CommonConst.DIR_RESOURCE, null);
    }

    /**
     * 删除文件或文件夹
     *
     * @param rootFileStr 二级根目录名称，值为：<br/>
     *                    {@link CommonConst#DIR_IMAGE}, {@link CommonConst#DIR_CACHE},{@link CommonConst#DIR_RESOURCE}
     *                    {@link CommonConst#DIR_TEMP},{@link CommonConst#DIR_UNIQUE}}
     * @param fileName    要删除的文件名，为空则全部删除
     */
    public synchronized static void clearFile(String rootFileStr, String fileName) {
        File rootFile = ToolSdCardFile.createRootFile(rootFileStr);

        File file = null;
        if (ToolText.isNotEmpty(fileName)) {
            file = new File(rootFile, fileName);
        }
        if (rootFile.exists()) {
            if (file != null) {
                deleteFile(file);
            } else {
                if (rootFile.isDirectory()) {
                    File[] fileList = rootFile.listFiles();
                    if (fileList != null) {
                        for (File targetFile : fileList) {
                            deleteFile(targetFile);
                        }
                    }
                } else {
                    deleteFile(rootFile);
                }
            }
        }
    }

    /**
     * 递归删除文件或文件夹
     *
     * @param file 待删除的文件或文件夹
     */
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();

            if (fileList != null) {
                for (File targetFile : fileList) {
                    deleteFile(targetFile);
                }
            }
        }
        file.delete();
    }

    /**
     * 计算文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public static long calculateFileSize(File file) {
        if (!file.exists()) {
            return 0;
        }

        long size = 0;
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return 0;
        }

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + calculateFileSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * 获取文件大小，M为单位,保留一位小数
     *
     * @return 文件大小
     */
    @Nullable
    public static String getFileSize(long size) {
        String fileSizeStr;
        double formatSize = (double) size / 1048576;
        String formatSizeStr = String.valueOf(formatSize);

        int i = formatSizeStr.indexOf(".");
        if (i < 0 || i + 2 > formatSizeStr.length()) {
            return null;
        }

        formatSizeStr = formatSizeStr.substring(0, i + 2);
        fileSizeStr = formatSizeStr + "M";
        return fileSizeStr;
    }
}
