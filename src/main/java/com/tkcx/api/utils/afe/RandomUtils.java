package com.tkcx.api.utils.afe;

import java.util.UUID;

public class RandomUtils {

    public static String getGUID() {
        //生成16位唯一性的订单号
        //随机生成一位整数
        int random = (int) (Math.random() * 9 + 1);
        String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        return value;
    }

    public static String creatCnsmrSeqNo() {
        //生成16位唯一性的订单号
        //随机生成一位整数
        int random = (int) (Math.random() * 9 + 1);
        String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        value = value.substring(0,8);
        return value;
    }


    public static void main(String[] args) {
        String guid = getGUID();
        System.out.println(guid);
        String eightUuid = creatCnsmrSeqNo();
        System.out.println(eightUuid);
    }
}

