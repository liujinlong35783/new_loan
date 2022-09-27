package com.tkcx.api.business.hjtemp.utils;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/4/8 16:28
 */
public class NewLoanFileFlagConstant {

    /**############################### 互金文件公共配置 #########################################*/

    public final static String DELETED = "DELETED";

    public final static String NOT_DELETED= "NOT_DELETED";

    public final static String FINISHED = "FINISHED";

    public final static String NOT_FINISH = "NOT_FINISH";

    /**
     * 读取互金文件起始行初始值
     */
    public final static Integer READ_START_NUM_INITIAL = 1;

    /**
     * 读取互金文件结束行初始值
     */
    public final static Integer READ_END_NUM_INITIAL  = 5001;

    /**
     * 文件读取步长
     */
    public final static Integer ONE_TIME_READ_LINE_NUM = 5000;

    /**################################ 网贷借款借据回单配置 ########################################*/

    /**
     * 网贷借款借据回单文件名
     */
    public final static String JJHD = "JJHD";
    /**
     * 网贷借款借据回单文件每行长度
     */
    public final static int JJHD_LINE_LENGTH = 19;


    /**################################ 网贷还款回单配置 ########################################*/

    /**
     * 网贷还款回单文件名
     */
    public final static String HKHD = "HKHD";
    /**
     * 网贷还款回单文件每行长度
     */
    public final static int HKHD_LINE_LENGTH = 24;

    /**################################ 网贷业务机构轧账单配置 ########################################*/

    /**
     * 网贷业务机构轧账单文件名
     */
    public final static String JGZZD = "JGZZD";
    /**
     * 网贷业务机构轧账单文件每行长度
     */
    public final static int JGZZD_LINE_LENGTH = 12;


    /**################################ 网贷业务机构业务流水配置 ########################################*/

    /**
     * 网贷业务机构业务流水文件名
     */
    public final static String YWLS = "YWLS";
    /**
     * 网贷业务机构业务流水文件每行长度
     */
    public final static int YWLS_LINE_LENGTH = 17;

    /**################################ 贷款分户账配置 ########################################*/

    /**
     * 贷款分户账文件名
     */
    public final static String FHZ = "FHZ";
    /**
     * 贷款分户账文件每行长度
     */
//    public final static int FHZ_LINE_LENGTH = 22;
    //新希望改
    public final static int FHZ_LINE_LENGTH = 23;

    /**################################ 贷款明细账配置 ########################################*/

    /**
     * 贷款明细账文件名
     */
    public final static String MXZ = "MXZ";
    /**
     * 贷款明细账文件每行长度
     */
    public final static int MXZ_LINE_LENGTH = 20;

    /**################################ 贷款利息登记簿配置 ########################################*/

    /**
     * 贷款利息登记簿文件名
     */
    public final static String LXDJB = "LXDJB";
    /**
     * 贷款利息登记簿文件每行长度
     */
//    public final static int LXDJB_LINE_LENGTH = 23;
    //新希望改
    public final static int LXDJB_LINE_LENGTH = 25;

    /**################################ 会计凭证配置 ########################################*/

    /**
     * 会计凭证文件名
     */
    public final static String KJPZ = "KJPZ";
    /**
     * 会计凭证文件每行长度
     */
//    public final static int KJPZ_LINE_LENGTH = 23;
    //新希望改
    public final static int KJPZ_LINE_LENGTH = 24;

    /**################################ 贷款形态调整明细清单配置 ########################################*/

    /**
     * 会计凭证文件名
     */
    public final static String XTTZ = "XTTZ";
    /**
     * 会计凭证文件每行长度
     */
//    public final static int XTTZ_LINE_LENGTH = 14;
    //新希望改
    public final static int XTTZ_LINE_LENGTH = 18;

}
