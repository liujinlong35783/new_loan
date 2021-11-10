package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Getter
@Setter
@TableName(value="ACCT_BRCH_TEMP",schema="QN_DB_ACCT")
public class AcctBrchTempModel extends Model<AcctBrchTempModel> {

    private static final long serialVersionUID = 1L;

	/**
	 * 机构总账标识
	 *
	 */
	@TableId(value = "ID",type = IdType.AUTO)
	private Integer id;

	 /**
	 * 会计日期
	 *
	 */
	@TableField(value="ACCT_DATE")
	private Date acctDate;

	 /**
	 * 核算机构
	 *
	 */
	@TableField(value="ACCT_ORG")
	private String acctOrg;

	 /**
	 * 创建时间
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;

	 /**
	 * 币种
	 *
	 */
	@TableField(value="CURRENCY")
	private String currency;

	 /**
	 * 借贷标识
	 *
	 */
	@TableField(value="DEBT_FLAG")
	private String debtFlag;

	 /**
	 * 科目号
	 *
	 */
	@TableField(value="ITEM_CODE")
	private String itemCode;

	 /**
	 * 科目号控制字
	 *
	 */
	@TableField(value="ITEM_CTRL")
	private String itemCtrl;

	 /**
	 * 科目名称
	 *
	 */
	@TableField(value="ITEM_NAME")
	private String itemName;

	 /**
	 * 表内表外标识：0-表内1-表外
	 *
	 */
	@TableField(value="OFF_BALANCE_FLAG")
	private String offBalanceFlag;

	 /**
	 * 状态：1-正常2-删除
	 *
	 */
	@TableField(value="STATUS")
	private String status;

	 /**
	 * 本日借方发生额
	 *
	 */
	@TableField(value="TODAY_DEBIT_AMOUNT")
	private BigDecimal todayDebitAmount;

	 /**
	 * 本日借方余额
	 *
	 */
	@TableField(value="TODAY_DEBIT_BALANCE")
	private BigDecimal todayDebitBalance;

	 /**
	 * 本日借方笔数
	 *
	 */
	@TableField(value="TODAY_DEBIT_QUANTITIES")
	private Integer todayDebitQuantities;

	 /**
	 * 本日贷方发生额
	 *
	 */
	@TableField(value="TODAY_LOAN_AMOUNT")
	private BigDecimal todayLoanAmount;

	 /**
	 * 本日贷方余额
	 *
	 */
	@TableField(value="TODAY_LOAN_BALANCE")
	private BigDecimal todayLoanBalance;

	 /**
	 * 本日贷方笔数
	 *
	 */
	@TableField(value="TODAY_LOAN_QUANTITIES")
	private Integer todayLoanQuantities;

	 /**
	 * 昨日借方余额
	 *
	 */
	@TableField(value="YEST_DEBIT_BALANCE")
	private BigDecimal yestDebitBalance;

	 /**
	 * 昨日贷方余额
	 *
	 */
	@TableField(value="YEST_LOAN_BALANCE")
	private BigDecimal yestLoanBalance;

	public AcctBrchTempModel() {
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AcctBrchTempModel{" +
				"id=" + id +
				", acctDate=" + acctDate +
				", acctOrg='" + acctOrg + '\'' +
				", createDate=" + createDate +
				", currency='" + currency + '\'' +
				", debtFlag='" + debtFlag + '\'' +
				", itemCode='" + itemCode + '\'' +
				", itemCtrl='" + itemCtrl + '\'' +
				", itemName='" + itemName + '\'' +
				", offBalanceFlag='" + offBalanceFlag + '\'' +
				", status='" + status + '\'' +
				", todayDebitAmount=" + todayDebitAmount +
				", todayDebitBalance=" + todayDebitBalance +
				", todayDebitQuantities=" + todayDebitQuantities +
				", todayLoanAmount=" + todayLoanAmount +
				", todayLoanBalance=" + todayLoanBalance +
				", todayLoanQuantities=" + todayLoanQuantities +
				", yestDebitBalance=" + yestDebitBalance +
				", yestLoanBalance=" + yestLoanBalance +
				'}';
	}
}
