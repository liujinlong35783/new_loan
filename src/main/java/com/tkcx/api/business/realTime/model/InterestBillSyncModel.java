package com.tkcx.api.business.realTime.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author cuijh
 * @since 2019/10/25 14:15
 */
@Setter
@Getter
public class InterestBillSyncModel extends Model<InterestBillSyncModel> {


    /**
     * 借贷标识
     *
     */
    private Integer debtFlag;

    /**
     * 借据号
     *
     */
    private String debtNo;

    /**
     * 贷款账号
     *
     */
    private String loanAccount;

    /**
     * 机构号
     *
     */
    private String orgCode;

    /**
     * 还款日期
     *
     */
    private Date payoffDate;

    /**
     * 结清标识
     *
     */
    private Integer payoffFlag;

    /**
     * 会计日期
     *
     */
    private Date acctDate;

    /**
     * 身份证号
     *
     */
    private String borrowerIdnum;

    /**
     * 户名
     *
     */
    private String loanName;

    /**
     * 结息类型
     */
    private String settleType;

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 还款计划编号
     */
    private String repayPlanNo;

    /**
     * 还款标识
     */
    private Integer assemblyId;

}
