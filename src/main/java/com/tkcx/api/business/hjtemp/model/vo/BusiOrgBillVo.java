package com.tkcx.api.business.hjtemp.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BusiOrgBillVo {

    /**
     * 核算机构
     *
     */
    private String acctOrg;

    /**
     * 核算机构名称
     *
     */
    private String orgName;
    /**
     * 科目号 改名科目控制字
     *
     */
    private String itemCtrl;
    /**
     * 币种
     *
     */
    private String currency;

    /**
     * 借贷标识：D-借 C-贷
     *
     */
    private String debtFlag;
    /**
     * 表内表外标识：0-表内1-表外
     *
     */
    private String offBalanceFlag;
    /**
     * 记账方式：1-正常2-红字 负数3-蓝字 正数
     *
     */
    private String acctType;
    /**
     * 交易金额
     *
     */
    private BigDecimal transAmount;
    /**
     * 交易笔数
     *
     */
    private Integer transNum;
}
