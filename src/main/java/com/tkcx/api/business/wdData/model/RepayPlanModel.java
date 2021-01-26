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
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-10 15:23
 */
@Getter
@Setter
@TableName(value="REPAY_PLAN",schema="QN_DB_BIZ")
public class RepayPlanModel extends Model<RepayPlanModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 已还本金
	* 
	*/
	@TableField(value="REPAY_PLAN_REPAID_PRINCIPAL")
	private Integer repayPlanRepaidPrincipal;
	 /**
	* 未还本金
	* 
	*/
	@TableField(value="REPAY_PLAN_NO_REPAY_PRINCIPAL")
	private Integer repayPlanNoRepayPrincipal;
	 /**
	* 实际产生的本金利息
	* 
	*/
	@TableField(value="REPAY_PLAN_ACTUAL_INTEREST")
	private Integer repayPlanActualInterest;
	 /**
	* 理论的足期的本金利息
	* 
	*/
	@TableField(value="REPAY_PLAN_INTEREST")
	private Integer repayPlanInterest;
	 /**
	* 已还本金利息
	* 
	*/
	@TableField(value="REPAY_PLAN_REPAID_INTEREST")
	private Integer repayPlanRepaidInterest;
	 /**
	* 未还本金利息(=实际本金利息-已还本金利息)
	* 
	*/
	@TableField(value="REPAY_PLAN_NO_REPAY_INTEREST")
	private Integer repayPlanNoRepayInterest;
	 /**
	* 已计提未结的本金利息
	* 
	*/
	@TableField(value="REPAY_PLAN_UNSETTLE_INTEREST")
	private Integer repayPlanUnsettleInterest;
	 /**
	* 宽限期利息
	* 
	*/
	@TableField(value="REPAY_PLAN_EXTEND_INTEREST")
	private Integer repayPlanExtendInterest;
	 /**
	* 已还宽限期利息
	* 
	*/
	@TableField(value="REPAY_PLAN_REPAID_EXTEND_INTEREST")
	private Integer repayPlanRepaidExtendInterest;
	 /**
	* 未还宽限日利息
	* 
	*/
	@TableField(value="REPAY_PLAN_NO_REPAY_EXTEND_INTEREST")
	private Integer repayPlanNoRepayExtendInterest;
	 /**
	* 已计提未结息的宽限期利息
	* 
	*/
	@TableField(value="REPAY_PLAN_UNSETTLE_EXTEND_INTEREST")
	private Integer repayPlanUnsettleExtendInterest;
	 /**
	* 罚息
	* 
	*/
	@TableField(value="REPAY_PLAN_PENALTY_INTEREST")
	private Integer repayPlanPenaltyInterest;
	 /**
	* 已还罚息
	* 
	*/
	@TableField(value="REPAY_PLAN_REPAID_PENALTY_INTEREST")
	private Integer repayPlanRepaidPenaltyInterest;
	 /**
	* 未还罚息
	* 
	*/
	@TableField(value="REPAY_PLAN_NO_REPAY_PENALTY_INTEREST")
	private Integer repayPlanNoRepayPenaltyInterest;
	 /**
	* 已计提未结息的罚息
	* 
	*/
	@TableField(value="REPAY_PLAN_UNSETTLE_PENALTY_INTEREST")
	private Integer repayPlanUnsettlePenaltyInterest;
	 /**
	* 还款状态
	* 
	*/
	@TableField(value="REPAY_PLAN_STATUS")
	private String repayPlanStatus;
	 /**
	* 当期的逾期天数
	* 
	*/
	@TableField(value="OVERDUE_DAYS")
	private Integer overdueDays;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="REPAY_PLAN_CREATE_AT")
	private Date repayPlanCreateAt;
	 /**
	* 还款结束时间
	* 
	*/
	@TableField(value="REPAY_PLAN_FINISH_AT")
	private Date repayPlanFinishAt;
	 /**
	* 更新时间
	* 
	*/
	@TableField(value="REPAY_PLAN_UPDATE_AT")
	private Date repayPlanUpdateAt;
	 /**
	* 备注
	* 
	*/
	@TableField(value="REPAY_PLAN_REMARK")
	private Object repayPlanRemark;
	 /**
	* 最后一次还款时间
	* 
	*/
	@TableField(value="REPAY_PLAN_TRADE_AT")
	private Date repayPlanTradeAt;
	 /**
	* 跑批时间
	* 
	*/
	@TableField(value="REPAY_PLAN_CORE_SYS_DATE")
	private Date repayPlanCoreSysDate;
	 /**
	* 累积已使用优惠金额
	* 
	*/
	@TableField(value="REPAY_PLAN_COUPON")
	private Integer repayPlanCoupon;
	 /**
	* 当前演进表的id
	* 
	*/
	@TableField(value="REPAY_PLAN_PROGRESS_ID")
	private Integer repayPlanProgressId;
	 /**
	* 本期次是否有效(1: 有效，0：无效）
	* 
	*/
	@TableField(value="IS_VALID")
	private Integer isValid;
	 /**
	* 计提总和(分）
	* 
	*/
	@TableField(value="TOTAL_INTEREST_ACCRUAL")
	private Integer totalInterestAccrual;
	 /**
	* 计提总税和
	* 
	*/
	@TableField(value="TOTAL_INTEREST_ACCRUAL_TAX")
	private Integer totalInterestAccrualTax;
	 /**
	* 结息总和(分，包含正常利息和罚息和宽限期利息)
	* 
	*/
	@TableField(value="TOTAL_INTEREST_SETTLE")
	private Integer totalInterestSettle;
	 /**
	* 结息利息总税额
	* 
	*/
	@TableField(value="TOTAL_INTEREST_SETTLE_TAX")
	private Integer totalInterestSettleTax;
	 /**
	* 当前累计的利息积数(分*天)
	* 
	*/
	@TableField(value="ACCUM_INTEREST")
	private Integer accumInterest;
	 /**
	* 当前累计的宽限期利息积数(分*天)
	* 
	*/
	@TableField(value="ACCUM_EXTEND_INTEREST")
	private Integer accumExtendInterest;
	 /**
	* 当前累计的罚息积数(分*天)
	* 
	*/
	@TableField(value="ACCUM_PENALTY_INTEREST")
	private Integer accumPenaltyInterest;
	 /**
	* 是否应计（1：应计 0：非应计）
	* 
	*/
	@TableField(value="IS_ACCRUED")
	private Integer isAccrued;
	 /**
	* 表主键
	* 
	*/
	@TableId(value = "REPAY_PLAN_ID",type = IdType.AUTO)
	private Integer repayPlanId;
	 /**
	* 还款计划主编号
	* 
	*/
	@TableField(value="REPAY_PLAN_MAIN_NO")
	private String repayPlanMainNo;
	 /**
	* 还款计划期次主键
	* 
	*/
	@TableField(value="REPAY_PLAN_NO")
	private String repayPlanNo;
	 /**
	* 资产编号
	* 
	*/
	@TableField(value="REPAY_PLAN_ASSET_ITEM_NO")
	private String repayPlanAssetItemNo;
	 /**
	* 期次
	* 
	*/
	@TableField(value="REPAY_PLAN_PERIOD")
	private Integer repayPlanPeriod;
	 /**
	* 本期次的起始时间
	* 
	*/
	@TableField(value="REPAY_PLAN_START_AT")
	private Date repayPlanStartAt;
	 /**
	* 期次到期日
	* 
	*/
	@TableField(value="REPAY_PLAN_DUE_AT")
	private Date repayPlanDueAt;
	 /**
	* 宽限日期(宽限期最后一天的日期)
	* 
	*/
	@TableField(value="REPAY_PLAN_EXTEND_DATE")
	private Date repayPlanExtendDate;
	 /**
	* 理论总金额:本金+理论利息
	* 
	*/
	@TableField(value="REPAY_PLAN_TOTAL_AMOUNT")
	private Integer repayPlanTotalAmount;
	 /**
	* 实际总金额:本金+实际总利息
	* 
	*/
	@TableField(value="REPAY_PLAN_ACTUAL_TOTAL_AMOUNT")
	private Integer repayPlanActualTotalAmount;
	 /**
	* 本金
	* 
	*/
	@TableField(value="REPAY_PLAN_PRINCIPAL")
	private Integer repayPlanPrincipal;

	@Override
	protected Serializable pkVal() {
                  return this.repayPlanId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
