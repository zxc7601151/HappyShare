package com.happy.share.common.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.happy.share.ApplicationBase;
import com.happy.share.tools.ToolSp;
import com.happy.share.tools.ToolText;

import io.reactivex.schedulers.Schedulers;


/**
 * desc: 配置文件 <br/>
 * time: 2017/10/27 14:44 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public class UserConfig {

    //====================================== 常量 ======================================//

    /**
     * key：用户名称
     */
    private static final String SP_NAME_USER = "user_config.preferences";
    /**
     * key：用户名称
     */
    private static final String KEY_USER_NAME = "key_user_name";
    /**
     * key：用户ID
     */
    private static final String KEY_USER_ID = "key_user_id";
    /**
     * key：用户token
     */
    private static final String KEY_USER_TOKEN = "key_user_token";
    /**
     * key: 用户是否编辑过个人信息 1 未编辑 2 已编辑
     */
    private static final String KEY_USER_INFO_EDIT = "key_user_info_edit";
    /**
     * 用户登录后状态保存，true 已登录，false未登录
     */
    private static final String KEY_USER_LOGIN_STATE = "key_user_login_state";
    /**
     * 用户登录后保存头像
     */
    private static final String KEY_USER_HEAD_PIC = "key_user_head_pic";

    //====================================== 变量 ======================================//
    /**
     * 用户ID
     */
    private String mUserId;
    /**
     * 用户名
     */
    private String mUserName;
    /**
     * 用户token
     */
    private String mUserToken;
    /**
     * 单例对象
     */
    private static UserConfig mInstance;
    /**
     * SharedPreferences工具类
     */
    private static ToolSp mSpConfig;

    private UserConfig() {
        loadLocalConfig();
    }

    /**
     * 获取单例实例
     *
     * @return ServerConfig
     */
    @NonNull
    public static synchronized UserConfig getInstance() {
        if (mInstance == null || mSpConfig == null) {
            mSpConfig = new ToolSp(ApplicationBase.getContext(), SP_NAME_USER, Context.MODE_PRIVATE);
            mInstance = new UserConfig();
        }

        return mInstance;
    }

    /**
     * 是否登陆
     *
     * @return true:登陆状态
     */
    public boolean isLogin() {
        return ToolText.isNotEmpty(getUserId()) && ToolText.isNotEmpty(getUserName());
    }

    /**
     * userId 不能为0
     *
     * @param value 不能传0
     */
    public void setUserId(String value) {
        mUserId = value;
        mSpConfig.setString(KEY_USER_ID, (value == null ? "" : value));
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserName(String value) {
        mUserName = value;
        mSpConfig.setString(KEY_USER_NAME, (value == null ? "" : value));
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserToken(String value) {
        mUserToken = value;
        mSpConfig.setString(KEY_USER_TOKEN, (value == null ? "" : value));
    }

    public String getUserToken() {
        return mUserToken;
    }

    /**
     * 将本地配置加载到内存
     */
    private void loadLocalConfig() {
        mUserId = mSpConfig.getString(KEY_USER_ID, "");
        mUserName = mSpConfig.getString(KEY_USER_NAME, "");
        mUserToken = mSpConfig.getString(KEY_USER_TOKEN, "");
    }

    /**
     * 是否立即保存
     *
     * @param isExeRightNow true：立即保存
     */
    public void save2Sp(boolean isExeRightNow) {
        if (isExeRightNow) {
            save2Sp();
        }
    }

    /**
     * 保存信息到SharedPreferences
     */
    public void save2Sp() {
        Schedulers.io().scheduleDirect(() -> mSpConfig.apply());
    }

    /**
     * 删除过期的key
     *
     * @param keys 删除key列表
     */
    public void removeKeys(String... keys) {
        mSpConfig.removeKeys(keys);
    }

    /**
     * 保存用户登录状态 isLogin
     *
     * @param isLogin 是否登录
     */
    public void setUserLoginState(boolean isLogin) {
        mSpConfig.setBoolean(KEY_USER_LOGIN_STATE, isLogin);
        save2Sp();
    }

    public boolean getUserLoginState() {
        return mSpConfig.getBoolean(KEY_USER_LOGIN_STATE, false);
    }

    public void setUserHeadPic(String headPic) {
        mSpConfig.setString(KEY_USER_HEAD_PIC, headPic);
        save2Sp();
    }

    /**
     * @return 获取用户登录后头像
     */
    public String getUserHeadPic() {
        return mSpConfig.getString(KEY_USER_HEAD_PIC, "");
    }

    public boolean getBooleanValue(String key) {
        return getBooleanValue(key, false);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return mSpConfig.getBoolean(key, defaultValue);
    }

    public void setLongValue(String key, long value) {
        mSpConfig.setLong(key, value);
        save2Sp();
    }

    public long getLongValue(String key) {
        return mSpConfig.getLong(key, 0);
    }

    public void setStringValue(String key, String value) {
        mSpConfig.setString(key, value);
        save2Sp();
    }
    public String getStringValue(String key) {
        return getStringValue(key, null);
    }

    public String getStringValue(String key, String defaultValue) {
        return mSpConfig.getString(key, defaultValue);
    }
}