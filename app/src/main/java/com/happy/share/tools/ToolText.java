package com.happy.share.tools;

import android.support.annotation.Nullable;

/**
 * desc:文本工具类, 该工具作为 {@link android.text.TextUtils}补充 <br/>
 * time: 2018-8-3 <br/>
 * author: 杨斌才 <br/>
 * since:  V 1.1 <br/>
 */
public interface ToolText {

    /**
     * 判断是否为空
     *
     * @param str 传入的字符串
     * @return true:为空
     */
    static boolean isEmptyOrNull(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否 非空
     *
     * @param str 传入的字符串
     * @return true:传入的字符串不为空
     */
    static boolean isNotEmpty(@Nullable String str) {
        return !isEmptyOrNull(str);
    }

    /**
     * 判断字符串中是否含有指定的文案
     *
     * @param str       被检测的字符串
     * @param searchStr 指定的文案
     * @return true: 字符串中是否含有要查找的字符串
     */
    static boolean contains(@Nullable String str, @Nullable String searchStr) {
        return !isEmptyOrNull(str) && searchStr != null && str.contains(searchStr);
    }

    /**
     * 去除字符串左右空格
     *
     * @param str 传入的字符串
     * @return 去除左右空格后的字符串
     */
    @Nullable
    static String trim(@Nullable String str) {
        if (isEmptyOrNull(str)) {
            return str;
        }
        return str.trim();
    }

    /**
     * 获取字符串的长度
     *
     * @param str 字符文本
     * @return str的长度
     */
    static int length(@Nullable String str) {
        return isEmptyOrNull(str) ? 0 : str.length();
    }

    /**
     * 忽略大小写后判断字符串是否相等
     *
     * @param str1 要比较的两字符串的其中一个
     * @param str2 要比较的两字符串的另一个
     * @return true：忽略大小写后，两字符串相等
     */
    static boolean equalsIgnoreCase(@Nullable String str1, @Nullable String str2) {
        if (str1 == null) {
            //判断str2是否也为null，都为null，返回true
            return str2 == null;
        } else {
            return str1.equalsIgnoreCase(str2);
        }
    }

    /**
     * 根据传入的拆分字符，拆分字符串
     *
     * @param textStr  字符串
     * @param splitStr 拆分字符
     * @return 拆分后的字符串数组
     */
    @Nullable
    static String[] split(@Nullable String textStr, @Nullable String splitStr) {
        if (isEmptyOrNull(textStr)) {
            return null;
        }
        if (splitStr == null) {
            return new String[]{textStr};
        }
        return textStr.split(splitStr);
    }

    /**
     * 将字符串首字母转为大写
     *
     * @param str 传入的字符串
     * @return 首字母大写后的字符串，若传入的字符串去掉首末空格后为空，则返回传入的字符串
     */
    @Nullable
    static String upperFirstLetter(@Nullable String str) {
        String trim = trim(str);
        //trim之后为空，返回原始字符串
        if (isEmptyOrNull(trim)) {
            return str;
        }

        if (!Character.isLowerCase(trim.charAt(0))) {
            return trim;
        }

        return String.valueOf((char) (trim.charAt(0) - 32)) + trim.substring(1);
    }

    /**
     * 将字符串首字母转为小写
     *
     * @param str 传入的字符串
     * @return 首字母小写后的字符串，若传入的字符串去掉首末空格后为空，则返回传入的字符串
     */
    @Nullable
    static String lowerFirstLetter(@Nullable String str) {
        String trim = trim(str);
        //trim之后为空，返回原始字符串
        if (isEmptyOrNull(trim)) {
            return str;
        }

        if (!Character.isUpperCase(trim.charAt(0))) {
            return trim;
        }

        return String.valueOf((char) (trim.charAt(0) + 32)) + trim.substring(1);
    }

    /**
     * 统计fullStr中包含searchStr的个数，默认返回0
     *
     * @param fullStr   长字符串
     * @param searchStr 要统计的短字符串
     * @return count  含有的个数
     */
    static int countStr(@Nullable String fullStr, @Nullable String searchStr) {
        if (!contains(fullStr, searchStr) || isEmptyOrNull(searchStr)) {
            return 0;
        }

        int count = 0;
        int index;
        String temp = fullStr;

        while ((index = temp.indexOf(searchStr)) > -1) {
            count++;
            if (temp.length() > index + searchStr.length()) {
                // fullStr最后几位恰好为searchStr时，此时index + searchStr.length() = temp.length
                // ，此时substring不会抛索引越界，但建议过滤
                temp = temp.substring(index + searchStr.length());
            } else {
                break;
            }
        }

        return count;
    }


}
