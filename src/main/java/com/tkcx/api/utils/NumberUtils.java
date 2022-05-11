package com.tkcx.api.utils;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2022/2/21 10:18
 */
public class NumberUtils {


    /**
     * 获取整数有多少位
     * @param num 要判断的整数
     * @return 该整数的位数
     */
    public static int getNumDigit(int num) {

        // 设置整数最大位数组参数
        int[] digits = {9, 99, 999, 9999, 99999, 999999, 9999999,
                99999999, 999999999, Integer.MAX_VALUE};
        // 循环判断num的大小
        for (int i = 0; ; i++) {
            // 当num的值小于digits[i]时停止循环，此时i+1便是当前num的位数
            if (num <= digits[i]) {
                return i + 1;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getNumDigit(199));

        System.out.println(String.format("%08d", 199));
    }
}
