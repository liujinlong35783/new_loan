package com.tkcx.api.constant;

/**
 *  常量
 *
 * @author cuijh
 * @since 2019-08-09
 */
public class EnumConstant {

    /**
     * 交易类型
     */
    public static final Integer BUSI_TYPE_NOMAL = 0;
    /**
     * 借贷标识：1-借，2-贷
     */
    public static final Integer DEBT_FLAG_DEBT = 1;

    public static final Integer DEBT_FLAG_CREDIT = 2;
    /**
     * 账户状态
     */
    public static final Integer ACCOUNT_STATUS = 3;

    /**
     * 结清标识：0-未结清，1-期次结清，2-整笔结清
     */
    public static final Integer PAYOFF_FLAG_NULL = 0;

    public static final Integer PAYOFF_FLAG_PART = 1;

    public static final Integer PAYOFF_FLAG_FULL = 2;

    /**
     * 借据种类-短期
     */
    public static final Integer LOAN_TYPE_SHORT = 0;

    /**
     * 等额本息
     */
    public static final Integer EQ_LOAN_PMT = 1;

    /**
     * 等额本金
     */
    public static final Integer EQ_PRINCIPAL_PMT = 2;
    /**
     * 先息后本
     */
    //我改的
    public static final Integer pay_int_principal_due = 3;

    /**
     * 等本等息
     */
    public static final Integer eq_prin_inte_pmt = 4;

    /**
     * 现转标识：0-现金，1-转账
     */
    public static final Integer TRANSFER_CASH = 0;

    public static final Integer TRANSFER_ELEC = 1;

    /**
     * normal正常、overdue逾期、idle呆滞、payoff还清
     * @param key
     * @return
     */
    public static int PRINCIPALSTATUS(String key){
        int value = 0;
        switch (key){
            case "normal":value = 0;break;
            case "overdue":value = 1;break;
            case "idle":value = 2;break;
            case "payoff":value = 0;break;
        }
        return value;
    }
}
