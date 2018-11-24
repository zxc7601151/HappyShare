package com.happy.share.tools;

import android.support.annotation.IntRange;

import java.math.BigDecimal;

/**
 * desc: 数学工具类 <br/>
 * time: 2018/8/7 下午4:01 <br/>
 * author: 匡衡 <br/>
 * since V 1.2
 */
public interface ToolMath {

    /**
     * 加法
     *
     * @param d1 数字
     * @param d2 数字
     * @return 数字
     */
    static double doubleAdd(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 减法
     *
     * @param d1 数字
     * @param d2 数字
     * @return 数字
     */
    static double doubleSubtract(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 乘法运算
     *
     * @param d1 数字
     * @param d2 数字
     * @return 数字
     */
    static double doubleMul(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 除法运算 (四舍五入)，同时根据参数保留小数点位数
     *
     * @param d1    数字
     * @param d2    数字
     * @param scale 小数点位数
     * @return 计算结果
     */
    static double doubleDiv(double d1, double d2, @IntRange(from = 0) int scale) {
        if (d2 == 0) {
            return 0d;
        }
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 截取小数点位数，同时四舍五入
     *
     * @param d     数值
     * @param scale 小数点位数
     *              保留小数点个数
     * @return 四舍五入的结果
     */
    static double doubleRound(double d, @IntRange(from = 0) int scale) {
        BigDecimal bd = BigDecimal.valueOf(d);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 精确的比较方法: >
     *
     * @param d1 数字
     * @param d2 数字
     * @return true表示 d1 > d2
     */
    static boolean greatThan(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.compareTo(bd2) > 0;
    }

    /**
     * 精确的比较方法: =
     *
     * @param d1 数字
     * @param d2 数字
     * @return true表示 d1 == d2
     */
    static boolean equalsThan(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.compareTo(bd2) == 0;
    }

    /**
     * 精确的比较方法: <
     *
     * @param d1 数字
     * @param d2 数字
     * @return true表示 d1 < d2
     */
    static boolean lessThan(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.compareTo(bd2) < 0;
    }

    /**
     * 精确的比较方法: >=
     *
     * @param d1 数字
     * @param d2 数字
     * @return true表示 d1 >= d2
     */
    static boolean greatEquals(double d1, double d2) {
        return equalsThan(d1, d2) || greatThan(d1, d2);
    }

    /**
     * 精确的比较方法: <=
     *
     * @param d1 数字
     * @param d2 数字
     * @return true表示 d1 <= d2
     */
    static boolean lessEquals(double d1, double d2) {
        return lessThan(d1, d2) || equalsThan(d1, d2);
    }
}
