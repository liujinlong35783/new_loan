package com.tkcx.api.business.realTime.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author cuijh
 * @since 2019/10/24 17:10
 */
@Getter
@Setter
public class LoanDetailBillSyncModel extends Model<LoanDetailBillSyncModel> {

    /**
     * 全部金额
     *
     */
    private String amount;

    /**
     * 借贷标识
     *
     */
    private Integer debtFlag;

    /**
     * 贷款账号
     *
     */
    private String loanAccount;

    /**
     * 借据号
     *
     */
    private String debtNo;

    /**
     * 发放日期
     *
     */
    private Date grantDate;

    /**
     * 发放金额
     *
     */
    private String gtantAmount;

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
     * 还息金额
     *
     */
    private String payoffInterest;

    /**
     * 还本金额
     *
     */
    private String payoffPrincipal;

    /**
     * 本金余额
     *
     */
    private String principalBalance;
    /**
     * 生效日期
     *
     */
    private Date validDate;

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
     * 资产编号
     */
    private String assetNo;

    /**
     * 还款标识
     */
    private Integer assemblyId;

}
