package com.tkcx.api.business.hjtemp.utils;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/4/8 16:28
 */
public class HjFileFlagConstant {

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
    public final static Integer READ_END_NUM_INITIAL  = 501;

    /**
     * 文件读取步长
     */
    public final static Integer ONE_TIME_READ_LINE_NUM = 500;

    /**################################ 会计科目配置 ########################################*/

    /**
     * 会计科目文件名
     */
    public final static String ACCT_DETAIL_FILE = "t_act_one_detail";
    /**
     * 会计科目文件每行长度
     */
    public final static int ACCT_DETAIL_LINE_LENGTH = 23;

    /**################################# 机构总账配置 #######################################*/
    /**
     * 机构总账文件名
     */
    public final static String ACT_BRCH_FILE = "t_act_brch_day_tot";
    /**
     * 机构总账每行长度
     */
    public final static int ACT_BRCH_LINE_LENGTH = 16;

    /**#################################### 业务编码配置 ####################################*/

    /**
     * 业务编码文件名
     */
    public final static String BUSI_CODE_FILE = "t_act_busi_code_map";
    /**
     * 业务编码每行长度
     */
    public final static int BUSI_CODE_LINE_LENGTH = 8;

    /**################################### 机构信息配置 #####################################*/

    /**
     * 机构信息文件名
     */
    public final static String ACT_PUB_ORG_FILE = "t_act_pub_org";
    /**
     * 机构信息每行长度
     */
    public final static int PUB_ORG_LINE_LENGTH = 4;
}
