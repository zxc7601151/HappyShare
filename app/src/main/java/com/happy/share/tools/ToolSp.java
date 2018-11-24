package com.happy.share.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.happy.share.tools.other.Optional2;

import java.util.Map;
import java.util.Set;

/**
 * desc: {@link SharedPreferences} 帮助类 <br/>
 * time: 2018/9/17 下午2:38 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ToolSp {

    /**
     * 当前指定文件SharedPreferences对象
     */
    @NonNull
    private SharedPreferences mSharedPref;
    /**
     * SharedPreferences编辑器
     */
    @NonNull
    private SharedPreferences.Editor mEdit;


    /**
     * @param context  上下文
     * @param fileName SharedPreferences文件名称，譬如：user_config.sp
     * @param mode     存放方式。值有：<br/>
     *                 {@link Context#MODE_PRIVATE}：该文件是私有数据，只能被应用本身访问。<br/>
     *                 {@link Context#MODE_APPEND}：检查文件是否存在，存在就向文件追加内容，否则就创建新文件。<br/>
     */
    @SuppressLint("CommitPrefEdits")
    public ToolSp(@NonNull Context context, @NonNull String fileName, int mode) {
        mSharedPref = context.getSharedPreferences(fileName, mode);
        mEdit = mSharedPref.edit();
    }

    /**
     * 获取指定key字符串值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    @NonNull
    public String getString(@NonNull String key, @NonNull final String defaultValue) {
        return mSharedPref.getString(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    @NonNull
    public ToolSp setString(@NonNull final String key, @NonNull final String value) {
        mEdit.putString(key, value);
        return this;
    }

    /**
     * 获取指定key boolean值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return mSharedPref.getBoolean(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    public ToolSp setBoolean(@NonNull final String key, final boolean value) {
        mEdit.putBoolean(key, value);
        return this;
    }

    /**
     * 获取指定key int值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return mSharedPref.getInt(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    public ToolSp setInt(@NonNull final String key, final int value) {
        mEdit.putInt(key, value);
        return this;
    }

    /**
     * 获取指定key float值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return mSharedPref.getFloat(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    public ToolSp setFloat(@NonNull final String key, final float value) {
        mEdit.putFloat(key, value);
        return this;
    }

    /**
     * 获取指定key long值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return mSharedPref.getLong(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    public ToolSp setLong(@NonNull final String key, final long value) {
        mEdit.putLong(key, value);
        return this;
    }

    /**
     * 获取指定key StringSet值
     *
     * @param key          key
     * @param defaultValue 当未获取到指定key值的时候，返回该值。
     * @return key->value or defaultValue
     */
    @NonNull
    public Set<String> getStringSet(@NonNull final String key, @NonNull final Set<String> defaultValue) {
        return mSharedPref.getStringSet(key, defaultValue);
    }

    /**
     * 新增或更新 key、value 到 SharedPreferences文件 <br/>
     * 注意：调用 {@link #commit()} 或 {@link #apply()} 后才会执行保存操作。
     *
     * @param key   key
     * @param value 保存的值
     * @return 当前ToolSp对象
     */
    public ToolSp setStringSet(@NonNull final String key, final Set<String> value) {
        mEdit.putStringSet(key, value);
        return this;
    }

    /**
     * 是否存在指定的key
     *
     * @param key key
     * @return true:有
     */
    public boolean hasKey(@Nullable String key) {
        return mSharedPref.contains(key);
    }

    /**
     * 保存 (异步)
     */
    public void apply() {
        mEdit.apply();
    }

    /**
     * 保存 (同步) <br/>
     * 建议优先使用 {@link #apply()} 保存
     *
     * @return true:保存成功
     */
    public boolean commit() {
        return mEdit.commit();
    }

    /**
     * 获取所有配置
     *
     * @return 所有配置
     */
    @NonNull
    public Optional2<Map<String, ?>> getAll() {
        try {
            return Optional2.ofNullable(mSharedPref.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional2.empty();
    }

    /**
     * 清除SharedPreferences所有数据
     *
     * @param isExeCommit 是否立即执提交操作。<br/>
     *                    true: 执行{@link SharedPreferences.Editor#commit()}
     *                    false：执行{@link SharedPreferences.Editor#apply()}
     */
    public void clearAll(boolean isExeCommit) {
        mEdit.clear();

        if (isExeCommit) {
            mEdit.commit();
        } else {
            mEdit.apply();
        }
    }

    /**
     * 删除多个配置
     *
     * @param keys 删除的键值对
     */
    public void removeKeys(@Nullable String... keys) {
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                if (!TextUtils.isEmpty(key)) {
                    mEdit.remove(key);
                }
            }

            mEdit.apply();
        }
    }

}
