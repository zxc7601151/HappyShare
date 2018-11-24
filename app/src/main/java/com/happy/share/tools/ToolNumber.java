package com.happy.share.tools;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DecimalFormat;

/**
 * desc: 数字工具类 <br/>
 * time: 2018/8/22 上午11:54 <br/>
 * author: Logan <br/>
 * since V 1.2 <br/>
 */
public interface ToolNumber {

    /**
     * 校验字符串是否都是数字 <br/>
     * 示例：<br/>
     * true: 0  11 1234545679 <br/>
     * false: -2  -1.1  0.0  1.3  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的数字字符串
     * @return true:是数字
     */
    static boolean isDigit(@Nullable String numberValue) {
        if (numberValue == null || numberValue.length() == 0) {
            return false;
        }

        return numberValue.matches("[0-9]+");
    }

    /**
     * 校验字符串是否是整数 ("整数"指的整数类型，非小数点类型。即：包括负数、Long数据类型均返回true) <br/>
     * 示例：<br/>
     * true: -12  0  15 <br/>
     * false: -1.1  0.0  1.3  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的数值字符串
     * @return true:是数值
     */
    static boolean isInteger(@Nullable String numberValue) {
        if (numberValue == null || numberValue.length() == 0) {
            return false;
        }

        return numberValue.matches("-[0-9]+|[0-9]+");
    }

    /**
     * 校验字符串是否是数值 (包含负数与浮点数) <br/>
     * <p>
     * 示例：<br/>
     * true: -12  -12.0  -12.056  12  12.0  12.056 <br/>
     * false: .  1.  1sr  -   12.  -12.  １２３(全角的) <br/>
     *
     * @param numberValue 需要校验的字符串
     * @return true：是数值
     */
    static boolean isNumeric(@Nullable String numberValue) {
        String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";

        if (numberValue == null || !numberValue.matches(regex)) {
            return false;
        }

        return true;
    }

    /**
     * 强转成int基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * 示例：<br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 11、11.13、"11.99" -> 11 <br/>
     * -1、-1.13、"-1.99" -> -1 <br/>
     *
     * @param intValue Integer、Number或String类型的int值
     * @return 强转后的int值
     */
    static int toInt(@Nullable Object intValue) {
        if (intValue instanceof Integer) {
            return (Integer) intValue;
        } else if (intValue instanceof Number) {
            return ((Number) intValue).intValue();
        } else if (intValue instanceof String) {
            try {
                return (int) Double.parseDouble((String) intValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成long基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * 示例：<br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 11、11.13、"11.99" -> 11 <br/>
     * -1、-1.13、"-1.99" -> -1 <br/>
     *
     * @param longValue Long、String类型的Long值
     * @return 强转后的long值
     */
    static long toLong(@Nullable Object longValue) {
        if (longValue instanceof Long) {
            return (Long) longValue;
        } else if (longValue instanceof Number) {
            return ((Number) longValue).longValue();
        } else if (longValue instanceof String) {
            try {
                return (long) Double.parseDouble((String) longValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成short基本类型值，默认0 (譬如：数据类型不匹配、转换异常) <br/>
     * <p>
     * short类型：最小值是 -32768，最大值是32767。超出范围转换后的数值不准确。<br/>
     * 如果不确定数据范围，请使用 {@link #toInt(Object)}、 {@link #toLong(Object)}。<br/>
     * <p>
     * 示例： <br/>
     * null、""、1abc、ab2、１２３４(全角) -> 0 <br/>
     * 55、55.13、"55.99" -> 55 <br/>
     * -5、-5.13、"-5.99" -> -5 <br/>
     *
     * @param shortValue Short、Number、String类型的short值
     * @return 强转后的short值
     */
    static short toShort(@Nullable Object shortValue) {
        if (shortValue instanceof Short) {
            return (Short) shortValue;
        } else if (shortValue instanceof Number) {
            return ((Number) shortValue).shortValue();
        } else if (shortValue instanceof String) {
            try {
                return (short) Double.parseDouble((String) shortValue);
            } catch (NumberFormatException ignored) {
                ignored.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 强转成double基本类型值，默认0.0 (譬如：数据类型不匹配、转换异常) <br/>
     *
     * @param decFormat   浮点数格式化器
     * @param doubleValue 浮点值
     * @return 格式化后的double值
     */
    static double toDouble(@NonNull DecimalFormat decFormat, @Nullable Object doubleValue) {
        return Double.parseDouble(decFormat.format(toDouble(doubleValue)));
    }

    /**
     * 强转成double基本类型值，不截取、不会四舍五入，默认0.0 (譬如：数据类型不匹配、转换异常) <br/>
     * 可能存在(1.0/3 = 0.333333333333333)未截取情况，使用 {@link #toDouble(DecimalFormat, Object)} 可处理。<br/>
     * <p>
     * 示例：<br/>
     * null、""、1.1abc、ab2.2、１２３４(全角) -> 0.0 <br/>
     * 55.66、"55.66" -> 55.66 <br/>
     * -5.4999、"-5.4999" -> -5.4999 <br/>
     *
     * @param doubleValue 浮点值
     * @return 转换后的浮点值
     */
    static double toDouble(@Nullable Object doubleValue) {
        if (doubleValue instanceof Double) {
            return (Double) doubleValue;
        } else if (doubleValue instanceof Number) {
            return ((Number) doubleValue).doubleValue();
        } else if (doubleValue instanceof String) {
            try {
                return Double.parseDouble((String) doubleValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0.0;
    }

    /**
     * 获取 1 到 x 之间随机数 (包括1、X) <br/>
     * x最小数字是1，否则会不准确。<br/>
     *
     * @param x 随机数最大数
     * @return 随机数
     */
    static int getRandom1ToX(@IntRange(from = 1) int x) {
        long randomValue = (long) (Math.random() * x + 1);

        if (randomValue > Integer.MAX_VALUE) {
            randomValue = Integer.MAX_VALUE;
        }

        return (int) randomValue;
    }

}
