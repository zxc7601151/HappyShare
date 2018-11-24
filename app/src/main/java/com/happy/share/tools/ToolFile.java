package com.happy.share.tools;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * desc: 纯文件操作相关工具  <br/>
 * time: 2017/9/13 10:59 <br/>
 * author: 义仍 <br/>
 * since V 6.9 <br/>
 */
public class ToolFile {
	/**
	 * 获取文件大小单位为B
	 */
	public static final int SIZETYPE_B = 1;
	/**
	 * 获取文件大小单位为KB
	 */
	public static final int SIZETYPE_KB = 2;
	/**
	 * 获取文件大小单位为MB
	 */
	public static final int SIZETYPE_MB = 3;
	/**
	 * 获取文件大小单位为GB
	 */
	public static final int SIZETYPE_GB = 4;

	/**
	 * 判断文件是否存在
	 *
	 * @param filePath 文件路径
	 * @return true 存在, false 不存在
	 */
	public static boolean isFileExists(String filePath) {
		return isFileExists(new File(filePath));
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param file 文件
	 * @return true 存在, false 不存在
	 */
	public static boolean isFileExists(File file) {
		return file != null && file.exists();
	}

	/**
	 * 重命名文件
	 *
	 * @param filePath 文件路径
	 * @param newName  新名称
	 * @return true 重命名成功, false 重命名失败
	 */
	public static boolean rename(String filePath, String newName) {
		return rename(new File(filePath), newName);
	}

	/**
	 * 重命名文件
	 *
	 * @param file    文件
	 * @param newName 新名称
	 * @return true 重命名成功, false 重命名失败
	 */
	public static boolean rename(File file, String newName) {
		if (file == null || !file.exists()) {// 文件为空或不存在返回false
			return false;
		}
		if (newName == null || "".equals(newName)) {// 新的文件名为空返回false
			return false;
		}
		if (newName.equals(file.getName())) {// 如果文件名没有改变返回true
			return true;
		}
		File newFile = new File(file.getParent() + File.separator + newName);
		return newFile.exists() && file.renameTo(newFile);// 如果重命名的文件已存在返回false
	}

	/**
	 * 判断是否是目录
	 *
	 * @param dirPath 目录路径
	 * @return true 是, false 否
	 */
	public static boolean isDir(String dirPath) {
		return isDir(new File(dirPath));
	}

	/**
	 * 判断是否是目录
	 *
	 * @param file 文件
	 * @return true 是, false 否
	 */
	public static boolean isDir(File file) {
		return file != null && file.exists() && file.isDirectory();
	}

	/**
	 * 判断是否是文件
	 *
	 * @param filePath 文件路径
	 * @return true 是, false 否
	 */
	public static boolean isFile(String filePath) {
		return isFile(new File(filePath));
	}

	/**
	 * 判断是否是文件
	 *
	 * @param file 文件
	 * @return true 是, false 否
	 */
	public static boolean isFile(File file) {
		return file != null && file.exists() && file.isFile();
	}

	/**
	 * 判断目录是否存在，不存在则返回是否创建成功
	 *
	 * @param dirPath 目录路径
	 * @return true 存在或创建成功, false 不存在或创建失败
	 */
	public static boolean createOrExistsDir(String dirPath) {
		return createOrExistsDir(new File(dirPath));
	}

	/**
	 * 判断目录是否存在，不存在则返回是否创建成功
	 *
	 * @param file 目录文件
	 * @return true 存在或创建成功, false 不存在或创建失败
	 */
	public static boolean createOrExistsDir(File file) {
		// 如果存在，是目录则返回true，不存在则返回是否创建成功
		return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
	}

	/**
	 * 判断文件是否存在，不存在则判断是否创建成功
	 *
	 * @param filePath 文件路径
	 * @return true 存在或创建成功, false 不存在或创建失败
	 */
	public static boolean createOrExistsFile(String filePath) {
		return createOrExistsFile(new File(filePath));
	}

	/**
	 * 判断文件是否存在，不存在则判断是否创建成功
	 *
	 * @param file 文件
	 * @return true 存在或创建成功, false 不存在或创建失败
	 */
	public static boolean createOrExistsFile(File file) {
		if (file == null) {
			return false;
		}
		if (file.exists()) {// 如果存在，是文件则返回true，是目录则返回false
			return file.isFile();
		}
		if (!createOrExistsDir(file.getParentFile())) {
			return false;
		}
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断文件是否存在，存在则删除原有的再创建
	 *
	 * @param filePath 文件路径
	 * @return true 创建成功, false 创建失败
	 */
	public static boolean createFileByDeleteOld(String filePath) {
		return createFileByDeleteOld(new File(filePath));
	}

	/**
	 * 判断文件是否存在，存在则删除原有的再创建
	 *
	 * @param file 文件
	 * @return true 创建成功, false 创建失败
	 */
	public static boolean createFileByDeleteOld(File file) {
		if (file == null) {
			return false;
		}
		if (file.exists() && !file.delete()) {// 文件存在并且删除失败返回false
			return false;
		}
		if (!createOrExistsDir(file.getParentFile())) {// 创建目录失败返回false
			return false;
		}
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除目录
	 *
	 * @param dirPath 目录路径
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteDir(String dirPath) {
		return deleteDir(new File(dirPath));
	}

	/**
	 * 删除目录
	 *
	 * @param dir 目录
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteDir(File dir) {
		if (dir == null || !dir.exists()) {// 目录不存在返回true
			return true;
		}
		if (!dir.isDirectory()) {// 不是目录返回false
			return false;
		}
		// 现在文件存在且是文件夹
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isFile()) {
					if (!file.delete()) {
						return false;
					}
				} else if (file.isDirectory()) {
					if (!deleteDir(file)) {
						return false;
					}
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 删除文件
	 *
	 * @param filePath 文件路径
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFile(String filePath) {
		return deleteFile(new File(filePath));
	}

	/**
	 * 删除文件
	 *
	 * @param file 文件
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFile(File file) {
		return file != null && (!file.exists() || file.isFile() && file.delete());
	}

	/**
	 * 删除目录下所有东西
	 *
	 * @param dirPath 目录路径
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteAllInDir(String dirPath) {
		return deleteAllInDir(new File(dirPath));
	}

	/**
	 * 删除目录下所有东西(目录和文件)
	 *
	 * @param dir 目录
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteAllInDir(File dir) {
		return deleteFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File file) {
				return true;
			}
		});
	}

	/**
	 * 删除目录下所有文件(不包括目录)
	 *
	 * @param dirPath 目录路径
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFilesInDir(String dirPath) {
		return deleteFilesInDir(new File(dirPath));
	}

	/**
	 * 删除目录下所有文件(不包括目录)
	 *
	 * @param dir 目录
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFilesInDir(File dir) {
		return deleteFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
	}

	/**
	 * 删除目录下所有过滤的文件
	 *
	 * @param dirPath 目录路径
	 * @param filter  过滤器
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFilesInDirWithFilter(String dirPath, FileFilter filter) {
		return deleteFilesInDirWithFilter(new File(dirPath), filter);
	}

	/**
	 * 删除目录下所有过滤的文件
	 *
	 * @param dir    目录
	 * @param filter 过滤器
	 * @return true 删除成功, false 删除失败
	 */
	public static boolean deleteFilesInDirWithFilter(File dir, FileFilter filter) {
		if (dir == null || !dir.exists()) {// 目录不存在返回true
			return true;
		}
		if (!dir.isDirectory()) {// 不是目录返回false
			return false;
		}
		// 现在文件存在且是文件夹
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (filter.accept(file)) {
					if (file.isFile()) {
						if (!file.delete()) {
							return false;
						}
					} else if (file.isDirectory()) {
						if (!deleteDir(file)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获取目录下所有文件
	 * (不递归进子目录)
	 *
	 * @param dirPath 目录路径
	 * @return 文件list
	 */
	public static List<File> listFilesInDir(String dirPath) {
		return listFilesInDir(dirPath, false);
	}

	/**
	 * 获取目录下所有文件
	 * (不递归进子目录)
	 *
	 * @param dir 目录
	 * @return 文件list
	 */
	public static List<File> listFilesInDir(File dir) {
		return listFilesInDir(dir, false);
	}

	/**
	 * 获取目录下所有文件
	 *
	 * @param dirPath     目录路径
	 * @param isRecursive 是否递归进子目录
	 * @return 文件list
	 */
	public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
		return listFilesInDir(new File(dirPath), isRecursive);
	}

	/**
	 * 获取目录下所有文件(包括目录和文件)
	 *
	 * @param dir         目录
	 * @param isRecursive 是否递归进子目录
	 * @return 文件list
	 */
	public static List<File> listFilesInDir(File dir, boolean isRecursive) {
		return listFilesInDirWithFilter(dir, new FileFilter() {
			@Override
			public boolean accept(File file) {
				return true;
			}
		}, isRecursive);
	}

	/**
	 * 获取目录下所有过滤的文件
	 * (不递归进子目录)
	 *
	 * @param dirPath 目录路径
	 * @param filter  过滤器
	 * @return 文件list
	 */
	public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter) {
		return listFilesInDirWithFilter(new File(dirPath), filter, false);
	}

	/**
	 * 获取目录下所有过滤的文件
	 * (不递归进子目录)
	 *
	 * @param dir    目录
	 * @param filter 过滤器
	 * @return 文件list
	 */
	public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter) {
		return listFilesInDirWithFilter(dir, filter, false);
	}

	/**
	 * 获取目录下所有过滤的文件
	 *
	 * @param dirPath     目录路径
	 * @param filter      过滤器
	 * @param isRecursive 是否递归进子目录
	 * @return 文件list
	 */
	public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter, boolean isRecursive) {
		return listFilesInDirWithFilter(new File(dirPath), filter, isRecursive);
	}

	/**
	 * 获取目录下所有过滤的文件
	 *
	 * @param dir         目录
	 * @param filter      过滤器
	 * @param isRecursive 是否递归进子目录
	 * @return 文件list
	 */
	public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter, boolean isRecursive) {
		if (!isDir(dir)) {
			return null;
		}

		List<File> list = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (filter.accept(file)) {
					list.add(file);
				}
				if (isRecursive && file.isDirectory()) {
					List<File> fileList = listFilesInDirWithFilter(file, filter, true);
					if (fileList != null && fileList.size() > 0) {
						list.addAll(fileList);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取文件最后修改的毫秒时间戳
	 *
	 * @param filePath 文件路径
	 * @return 文件最后修改的毫秒时间戳
	 */

	public static long getFileLastModified(String filePath) {
		return getFileLastModified(new File(filePath));
	}

	/**
	 * 获取文件最后修改的毫秒时间戳
	 *
	 * @param file 文件
	 * @return 文件最后修改的毫秒时间戳
	 */
	public static long getFileLastModified(File file) {
		if (file == null) {
			return -1;
		}

		return file.lastModified();
	}

	/**
	 * 获取目录大小
	 *
	 * @param dirPath 目录路径
	 * @return 目录大小
	 */
	public static long getDirSize(String dirPath) {
		return getDirSize(new File(dirPath));
	}

	/**
	 * 获取目录长度
	 *
	 * @param dir 目录
	 * @return 目录长度
	 */
	public static long getDirSize(File dir) {
		long len = 0;
		if (!isDir(dir)) {
			return len;
		}
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					len += getDirSize(file);
				} else {
					len += file.length();
				}
			}
		}
		return len;
	}

	/**
	 * 获取文件大小
	 *
	 * @param filePath 文件路径
	 * @return 文件大小
	 */
	public static long getFileSize(String filePath) {
		return getFileSize(new File(filePath));
	}

	/**
	 * 获取文件长度
	 *
	 * @param file 文件
	 * @return 文件长度
	 */
	public static long getFileSize(final File file) {
		if (!isFile(file)) {
			return 0;
		}
		return file.length();
	}


	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {
		Locale.setDefault(Locale.US);
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 转换文件大小,指定转换的类型
	 *
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	public static double formetFileSize(long fileS, int sizeType) {
		Locale.setDefault(Locale.US);
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
			case SIZETYPE_B:
				fileSizeLong = Double.valueOf(df.format((double) fileS));
				break;
			case SIZETYPE_KB:
				fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
				break;
			case SIZETYPE_MB:
				fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
				break;
			case SIZETYPE_GB:
				fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
				break;
			default:
				break;
		}
		return fileSizeLong;
	}

	/**
	 * 从assets目录中复制指定的文件中
	 */
	public static void copyFileFromAssets(Context context, String filePath, String targetPath
			, String targetFileName) {
		if (null == context || ToolText.isEmptyOrNull(filePath) || ToolText.isEmptyOrNull(targetPath) ||
				ToolText.isEmptyOrNull(targetFileName)) {
			return;
		}

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getAssets().open(filePath);
			File targetFile = new File(targetPath + File.separator + targetFileName);
			if (targetFile.exists()) {
				return;
			} else {
				targetFile.createNewFile();
			}
			fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			int byteCount=0;
			while((byteCount=is.read(buffer))!=-1) {
				fos.write(buffer, 0, byteCount);
			}
		} catch (IOException ignored) {

        }finally {
            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException ignored) {
            }
		}
	}

    /**
     * 获取下载视频保存的文件夹路径
     */
    public static String getSaveDownloadVideoPath(Context context) {
        String savePath;

        // 1 首先获取视频文件的保存目录
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            savePath = saveDir.getPath();
        } else {
            savePath = context.getFilesDir().getPath() + File.separator + "download" + File.separator + "videos";
        }
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        return savePath;
    }

    /**
     * 获取链接中的文件名称
     */
    public static String getFileName(String fileUrl) {
        int beginIndex = fileUrl.lastIndexOf('/') + 1;
        if (beginIndex >= fileUrl.length()) {
            return null;
        } else {
            return fileUrl.substring(beginIndex);
        }
    }
}
