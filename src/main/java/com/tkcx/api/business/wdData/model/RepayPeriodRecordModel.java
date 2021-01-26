package com.tkcx.api.business.wdData.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-08 20:12
 */
@Getter
@Setter
@TableName(value="REPAY_PERIOD_RECORD",schema="QN_DB_BIZ")
public class RepayPeriodRecordModel extends Model<RepayPeriodRecordModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 主键
	* 
	*/
	@TableId(value = "REPAY_RECORD_ID",type = IdType.AUTO)
	private Integer repayRecordId;
	 /**
	* 还款总成表id(repay_assembly_record)
	* 
	*/
	@TableField(value="ASSEMBLY_ID")
	private Integer assemblyId;
	 /**
	* 资产编号
	* 
	*/
	@TableField(value="ASSET_NO")
	private String assetNo;
	 /**
	* 还款期数
	* 
	*/
	@TableField(value="REPAY_PERIOD")
	private Integer repayPeriod;
	 /**
	* 还款计划id
	* 
	*/
	@TableField(value="REPAY_PLAN_ID")
	private Integer repayPlanId;
	 /**
	* 还款计划期次编号
	* 
	*/
	@TableField(value="REPAY_PLAN_NO")
	private String repayPlanNo;
	 /**
	* 用户名身份证
	* 
	*/
	@TableField(value="INDIVIDUAL_IDNUM")
	private String individualIdnum;
	 /**
	* 还款结果：Partial-部分还款 Full-当期全部还款
	* 
	*/
	@TableField(value="REPAY_RESULT")
	private String repayResult;
	 /**
	* 本次还款金额小计（本金+利息+罚息+宽限期利息）
	* 
	*/
	@TableField(value="CURR_REPAY_AMOUNT")
	private BigDecimal currRepayAmount;
	 /**
	* 期次的本金(分)
	* 
	*/
	@TableField(value="PRINCIPAL_AMOUNT")
	private BigDecimal principalAmount;
	 /**
	* 历史已还本金
	* 
	*/
	@TableField(value="PRINCIPAL_REPAY_HISTORY")
	private BigDecimal principalRepayHistory;
	 /**
	* 此次归还本金(分)
	* 
	*/
	@TableField(value="PRINCIPAL_REPAY_AMOUNT")
	private BigDecimal principalRepayAmount;
	 /**
	* 该期总的表内利息(不含罚息，但包含已税未税利息，单位分) 
	* 
	*/
	@TableField(value="INTEREST_AMOUNT")
	private BigDecimal interestAmount;
	 /**
	* 历史已还表内利息(分)
	* 
	*/
	@TableField(value="INTEREST_REPAY_HISTORY")
	private BigDecimal interestRepayHistory;
	 /**
	* 此次归还表内本金利息(不含罚息，但含未税的利息，单位分)
	* 
	*/
	@TableField(value="INTEREST_REPAY_AMOUNT")
	private BigDecimal interestRepayAmount;
	 /**
	* 此次归还的表外本金利息（不含罚息，分）
	* 
	*/
	@TableField(value="OFFBL_INTEREST_REPAY_AMOUNT")
	private BigDecimal offblInterestRepayAmount;
	 /**
	* 该期的宽限期利息
	* 
	*/
	@TableField(value="EXTEND_INTEREST")
	private BigDecimal extendInterest;
	 /**
	* 该期历史归还的宽限期利息
	* 
	*/
	@TableField(value="EXTEND_INTEREST_REPAY_HISTORY")
	private BigDecimal extendInterestRepayHistory;
	 /**
	* 本次归还的宽限期利息
	* 
	*/
	@TableField(value="EXTEND_INTEREST_REPAY_AMOUNT")
	private BigDecimal extendInterestRepayAmount;
	 /**
	* 该期表内罚息（分）
	* 
	*/
	@TableField(value="FINED_AMOUNT")
	private BigDecimal finedAmount;
	 /**
	* 历史已还表内罚息（分)
	* 
	*/
	@TableField(value="FINED_REPAY_HISTORY")
	private BigDecimal finedRepayHistory;
	 /**
	* 此次归还表内逾期利息（罚息，分)
	* 
	*/
	@TableField(value="FINED_REPAY_AMOUNT")
	private BigDecimal finedRepayAmount;
	 /**
	* 此次归还的表外逾期利息（罚息，分）
	* 
	*/
	@TableField(value="OFFBL_FINED_REPAY_AMOUNT")
	private BigDecimal offblFinedRepayAmount;
	 /**
	* 表内未缴税利息（分）
	* 
	*/
	@TableField(value="ONBL_UNTAX_INTEREST")
	private BigDecimal onblUntaxInterest;
	 /**
	* 本次归还的表内未税利息（分）
	* 
	*/
	@TableField(value="ONBL_UNTAX_INTEREST_REPAY_AMOUNT")
	private BigDecimal onblUntaxInterestRepayAmount;
	 /**
	* 此次归还的表内利息（利息+宽限期利息+罚息）包含的税额（分）
	* 
	*/
	@TableField(value="TAX_REPAY_AMOUNT")
	private BigDecimal taxRepayAmount;
	 /**
	* 此次归还的表外利息（利息+罚息）包含的税额（分）
	* 
	*/
	@TableField(value="OFFBL_TAX_REPAY_AMOUNT")
	private BigDecimal offblTaxRepayAmount;
	 /**
	* 本期次历史减免的利息之和
	* 
	*/
	@TableField(value="COUPON_HISTORY")
	private BigDecimal couponHistory;
	 /**
	* 本次本期减免的总利息（=减免的应计利息+减免的非应计利息）
	* 
	*/
	@TableField(value="COUPON_AMOUNT")
	private BigDecimal couponAmount;
	 /**
	* 本次本期减免的应计利息
	* 
	*/
	@TableField(value="COUPON_AMOUNT_ONBALANCE")
	private BigDecimal couponAmountOnbalance;
	 /**
	* 本次本期减免的非应计利息
	* 
	*/
	@TableField(value="COUPON_AMOUNT_OFFBANLANCE")
	private BigDecimal couponAmountOffbanlance;
	 /**
	* 此次归还的表内未税利息的优惠金额（分）
	* 
	*/
	@TableField(value="COUPON_AMOUNT_ONBL_UNTAX")
	private BigDecimal couponAmountOnblUntax;
	 /**
	* 还款前，期次演进表id
	* 
	*/
	@TableField(value="REPAY_PLAN_PROGRESS_ID_BF")
	private Integer repayPlanProgressIdBf;
	 /**
	* 还款后，期次演进表id
	* 
	*/
	@TableField(value="REPAY_PLAN_PROGRESS_ID_AF")
	private Integer repayPlanProgressIdAf;
	 /**
	* 还款前期次的逾期天数
	* 
	*/
	@TableField(value="OVERDUE_DAYS_BF")
	private Integer overdueDaysBf;
	 /**
	* 还款后期次的逾期天数
	* 
	*/
	@TableField(value="OVERDUE_DAYS_AF")
	private Integer overdueDaysAf;
	 /**
	* 还款前期次的计财状态
	* 
	*/
	@TableField(value="FINANCE_STATUS_BF")
	private String financeStatusBf;
	 /**
	* 还款后期次的计财状态
	* 
	*/
	@TableField(value="FINANCE_STATUS_AF")
	private String financeStatusAf;
	 /**
	* 还款前期次的应计状态
	* 
	*/
	@TableField(value="IS_ACCRUED_BF")
	private Integer isAccruedBf;
	 /**
	* 还款后期次的应计状态
	* 
	*/
	@TableField(value="IS_ACCRUED_AF")
	private Integer isAccruedAf;
	 /**
	* 还款备注
	* 
	*/
	@TableField(value="REPAY_REMARK")
	private String repayRemark;
	 /**
	* 记账流水号
	* 
	*/
	@TableField(value="ACCT_RECORD_NO")
	private String acctRecordNo;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="REPAY_CREATE_DATE")
	private Date repayCreateDate;
	 /**
	* 更新时间
	* 
	*/
	@TableField(value="REPAY_UPDATE_DATE")
	private Date repayUpdateDate;
	 /**
	* 还款核心日期
	* 
	*/
	@TableField(value="CORE_SYS_DATE")
	private Date coreSysDate;

	@Override
	protected Serializable pkVal() {
                  return this.repayRecordId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
