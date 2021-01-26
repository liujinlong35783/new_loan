package com.tkcx.api.exception;

/**
 * 错误码
 * 
 * @author zhouzhichao
 *
 */
public final class ErrorCode {
    private ErrorCode() {
    }

    /**
     * 返回内容为空
     */
    public static final int RESPONSE_EMPTY = 800001;
    /**
     * 无法实例化对象
     */
    public static final int CANNOT_NEW_INSTANCE = 800002;
    /**
     * 存储PIK失败
     */
    public static final int FAIL_STORE_PIK = 80003;
    /**
     * 存储MIK失败
     */
    public static final int FAIL_STROE_MIK = 80004;
    /**
     * 生成pin失败
     */
    public static final int FAIL_ENCRYPT_PIN = 80005;
    /**
     * 生成mac失败
     */
    public static final int FAIL_GENERATE_MAC = 80006;
    /**
     * 验证mac失败
     */
    public static final int FAIL_VERIFY_MAC = 80007;
    /**
     * 获取PIK失败
     */
    public static final int FAIL_GET_PIK = 80008;
    /**
     * 获取MAK失败
     */
    public static final int FAIL_GET_MAK = 80009;
    /**
     * 请求不能为空
     */
    public static final int REQUEST_CANNOT_BE_EMPTY = 80010;
    /**
     * 二类户不存在
     */
    public static final int CARDII_NOT_EXIST = 80011;
    /**
     * 二类户已经存在
     */
    public static final int CARDII_EXIST = 80012;
    /**
     * 余额不足
     */
    public static final int CARDII_BALANCE_LACK = 80013;
    /**
     * 未找到对应的交易
     */
    public static final int TRANSACTION_NOT_FOUND = 80013;

    /**
     * 联网核查失败,身份证与姓名不符
     */
    public static final int INSPECT_ERROR_NAME = 80014;
    /**
     * 联网核查失败,身份证不存在
     */
    public static final int INSPECT_ERROR_ID_NOT_EXIST = 80015;
    /**
     * 联网核查失败,其他错误
     */
    public static final int INSPECT_ERROR_OTHER = 80016;
    /**
     * 联网核查失败,参数错误
     */
    public static final int INSPECT_ERROR_PARAMETER_ERROR = 80017;
    /**
     * 联网核查失败,人行核查系统故障
     */
    public static final int INSPECT_ERROR_SYSTEM_ERROR = 80018;
    /**
     * 请求失败
     */
    public static final int REQUEST_ERROR = 999999;
}
