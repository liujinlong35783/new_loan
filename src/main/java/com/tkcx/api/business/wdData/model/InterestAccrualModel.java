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
 * @Date 2019-08-30 16:53
 */
@Getter
@Setter
@TableName(value="INTEREST_ACCRUAL",schema="QN_DB_BIZ")
public class InterestAccrualModel extends Model<InterestAccrualModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 计提表id
	* 
	*/
	@TableId(value = "INTEREST_ACCRUAL_ID",type = IdType.AUTO)
	private Integer interestAccrualId;
	 /**
	* 资产类型
	* 
	*/
	@TableField(value="ASSET_TYPE")
	private String assetType;
	 /**
	* 资产编号
	* 
	*/
	@TableField(value="ASSET_ITEM_NO")
	private String assetItemNo;
	 /**
	* 还款计划主编号
	* 
	*/
	@TableField(value="REPAY_PLAN_MAIN_NO")
	private String repayPlanMainNo;
	 /**
	* 还款计划期次编号
	* 
	*/
	@TableField(value="REPAY_PLAN_NO")
	private String repayPlanNo;
	 /**
	* 计提类型(interest_accr:正常利息计提  extend_interest_accr:宽限期利息计提  penalty_interest_accr:罚息利息计提 penalty_interest_ replenish_accr:罚息补计)
	* 
	*/
	@TableField(value="ACCRUAL_TYPE")
	private String accrualType;
	 /**
	* 创建时核心时间
	* 
	*/
	@TableField(value="CREATE_CORE_SYSTE_DATE")
	private Date createCoreSysteDate;
	 /**
	* 资产是否应计状态
	* 
	*/
	@TableField(value="ASSET_IS_ACCRUED")
	private Integer assetIsAccrued;
	 /**
	* 还款计划期次是否应计状态
	* 
	*/
	@TableField(value="REPAY_PLAN_IS_ACCRUED")
	private Integer repayPlanIsAccrued;
	 /**
	* 资产逾期状态
	* 
	*/
	@TableField(value="ASSET_OVERDUE_STATUS")
	private String assetOverdueStatus;
	 /**
	* 资产逾期天数
	* 
	*/
	@TableField(value="ASSET_OVERDUE_DAYS")
	private Integer assetOverdueDays;
	 /**
	* 还款计划期次逾期天数
	* 
	*/
	@TableField(value="REPAY_PLAN_OVERDUE_DAYS")
	private Integer repayPlanOverdueDays;
	 /**
	* 计算的积数
	* 
	*/
	@TableField(value="CURR_ACCUM")
	private Integer currAccum;
	 /**
	* 计算的日利率
	* 
	*/
	@TableField(value="CURR_INTEREST_RATE")
	private BigDecimal currInterestRate;
	 /**
	* 算前累计计提金额
	* 
	*/
	@TableField(value="ACCRUAL_SUM_BF")
	private Integer accrualSumBf;
	 /**
	* 本次计提金额(分）
	* 
	*/
	@TableField(value="CURR_ACCRUAL_AMOUNT")
	private Integer currAccrualAmount;
	 /**
	* 算后累计计提金额
	* 
	*/
	@TableField(value="ACCRUAL_SUM_AF")
	private Integer accrualSumAf;
	 /**
	* 是否已结息
	* 
	*/
	@TableField(value="IS_INTEREST_SETTLE")
	private Integer isInterestSettle;
	 /**
	* 计提记账流水号
	* 
	*/
	@TableField(value="ACCT_RECORD_NO")
	private String acctRecordNo;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_TIME")
	private Date createTime;
	 /**
	* 变更时的核心日期
	* 
	*/
	@TableField(value="UPDATE_CORE_SYSTE_DATE")
	private Date updateCoreSysteDate;
	 /**
	* 变更时间
	* 
	*/
	@TableField(value="UPDATE_TIME")
	private Date updateTime;

	@Override
	protected Serializable pkVal() {
                  return this.interestAccrualId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
