package com.happy.share.common.consts;

/**
 * desc: 公共常量类 <br/>
 * time: 2018/11/24 10:16 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public interface CommonConst {
    //=============================== 页面 request code

    /**
     * 裁剪图片
     */
    int REQUEST_CODE_FOR_RESULT_CROP_IMAGE = 3005;
    /**
     * 拍照
     */
    int REQUEST_CODE_FOR_RESULT_CAMERA = 3006;
    /**
     * 相册
     */
    int REQUEST_CODE_FOR_RESULT_PHOTO = 3007;

    // =============================== 文件保存二级目录
    /**
     * 图片目录
     */
    String DIR_IMAGE = "image";
    /**
     * 缓存目录
     */
    String DIR_CACHE = "cache";
    /**
     * 唯一码目录
     */
    String DIR_UNIQUE = "unique";
    /**
     * 资源包目录
     */
    String DIR_RESOURCE = "resource";
    /**
     * 临时存储目录
     */
    String DIR_TEMP = "temp";

    // =============================== 权限
    /**
     * 读取手机状态
     */
    int PERMISSION_REQUEST_READ_PHONE_STATE = 0;
    /**
     * 相机权限
     */
    int PERMISSION_REQUEST_CAMERA = 1;
    /**
     * 存储权限
     */
    int PERMISSION_REQUEST_STORAGE = 2;
    /**
     * 头像裁剪返回intent
     */
    int PERMISSION_IMAGE_CROP = 3;

    // =============================== 其它
    /**
     * 图片
     */
    String JPG = ".jpg";
    /**
     * UTF-8编码
     */
    String CHARSET_UTF_8 = "UTF-8";
    /**
     * 是
     */
    int YES = 1;
    /**
     * 否
     */
    int NO = 0;
}
