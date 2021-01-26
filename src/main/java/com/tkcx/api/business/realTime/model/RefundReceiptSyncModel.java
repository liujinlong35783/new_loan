package com.tkcx.api.business.realTime.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cuijh
 * @since 2019/10/24 14:47
 */
@Getter
@Setter
public class RefundReceiptSyncModel extends Model<RefundReceiptSyncModel> {

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
     * 到期日期
     *
     */
    private Date dueDate;
    /**
     * 发放日期
     *
     */
    private Date grantDate;
    /**
     * 正常利率
     *
     */
    private BigDecimal interestRate;
    /**
     * 贷款金额
     *
     */
    private String loanAmount;
    /**
     * 逾期利率
     *
     */
    private BigDecimal overdueInterestRate;
    /**
     * 结算账号
     *
     */
    private String payAccount;
    /**
     * 还款日期
     *
     */
    private Date payoffDate;
    /**
     * 户名
     *
     */
    private String refundName;

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
     * 机构号
     *
     */
    private String orgCode;

    /**
     * 还款标识
     */
    private Integer assemblyId;

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 还款类型
     */
    private String repayType;


}
