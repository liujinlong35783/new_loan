package com.tkcx.api.business.realTime.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cuijh
 * @since 2019/10/24 15:32
 */
@Getter
@Setter
public class LoanReceiptSyncModel extends Model<LoanReceiptSyncModel> {

    /**
     * 贷户住址
     *
     */
    private String borrowerAddr;

    /**
     * 身份证号
     *
     */
    private String borrowerIdnum;

    /**
     * 借款人
     *
     */
    private String borrowerName;

    /**
     * 到期日期
     *
     */
    private Date dueDate;

    /**
     * 借款利率
     *
     */
    private BigDecimal interestRate;

    /**
     * 贷款账号
     *
     */
    private String loanAccount;

    /**
     * 借据金额
     *
     */
    private String loanAmount;

    /**
     * 贷款日期
     *
     */
    private Date loanDate;

    /**
     * 借据种类
     *
     */
    private Integer loanType;

    /**
     * 借款用途
     *
     */
    private String loanUsage;

    /**
     * 还款方式
     *
     */
    private Integer payoffType;

    /**
     * 资产编号
     *
     */
    private String assetNo;

    /**
     * 会计日期
     *
     */
    private Date acctDate;

    /**
     * 机构号
     *
     */
    private String orgCode;

    /**
     * 合同号
     */
    private String contractNo;

}
