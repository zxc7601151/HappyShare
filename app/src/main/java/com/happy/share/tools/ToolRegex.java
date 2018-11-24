package com.happy.share.tools;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Pattern;

/**
 * desc: 正则工具类 <br/>
 * time: 2018/8/29 <br/>
 * author 杨斌才 <br/>
 * since V 1.2 <br/>
 */
public interface ToolRegex {

    /**
     * 正则表达式：邮箱
     */
    String REGEX_EMAIL = "^([a-zA-Z0-9_\\-.|]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";


    /**
     * 验证email是否符合邮箱规则
     *
     * @param email  待验证的邮箱
     * @return       true：符合邮箱规则
     */
    static boolean isEmail(@Nullable String email) {
        return ToolText.isNotEmpty(email) && Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 验证字符串是否满足正则表达式
     *
     * @param input  待验证的字符串
     * @param regex  任意非空正则表达式
     * @return       true：传入的字符串满足正则规则
     */
    static boolean isMatches(@Nullable String input, @NonNull String regex) {
        return ToolText.isNotEmpty(input) && Pattern.matches(regex, input);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    @NonNull
    static String getReplaceAll(@Nullable final String input, @NonNull final String regex
            , @NonNull final String replacement) {
        if (input == null) {
            return "";
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
