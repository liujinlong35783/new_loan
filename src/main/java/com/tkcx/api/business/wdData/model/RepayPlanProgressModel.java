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
 * @Date 2019-08-10 15:22
 */
@Getter
@Setter
@TableName(value="REPAY_PLAN_PROGRESS",schema="QN_DB_BIZ")
public class RepayPlanProgressModel extends Model<RepayPlanProgressModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 演进表id
	* 
	*/
	@TableId(value = "REPAY_PLAN_PROGRESS_ID",type = IdType.AUTO)
	private Integer repayPlanProgressId;
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
	* 演进原因(init:初始化状态 unpay: 未还款导致状态变化  repay: 还款后状态变化)
	* 
	*/
	@TableField(value="PROGRESS_REASON")
	private String progressReason;
	 /**
	* 前置记录的id
	* 
	*/
	@TableField(value="REPAY_PLAN_PROGRESS_ID_BF")
	private Integer repayPlanProgressIdBf;
	 /**
	* 前置记录的计财状态
	* 
	*/
	@TableField(value="FINANCE_STATUS_BF")
	private String financeStatusBf;
	 /**
	* 前置记录的应计状态
	* 
	*/
	@TableField(value="IS_ACCRUED_BF")
	private Integer isAccruedBf;
	 /**
	* 前置记录的逾期天数
	* 
	*/
	@TableField(value="OVERDUE_DAYS_BF")
	private Integer overdueDaysBf;
	 /**
	* 当前计财状态
	* 
	*/
	@TableField(value="FINANCE_STATUS_CURR")
	private String financeStatusCurr;
	 /**
	* 当前是否应计状态
	* 
	*/
	@TableField(value="IS_ACCRUED_CURR")
	private Integer isAccruedCurr;
	 /**
	* 本金(单位分，历史累计值)
	* 
	*/
	@TableField(value="PRINCIPAL")
	private Integer principal;
	 /**
	* 已还本金(单位分，历史累计值)
	* 
	*/
	@TableField(value="REPAID_PRINCIPAL")
	private Integer repaidPrincipal;
	 /**
	* 未还本金(单位分，历史累计值)
	* 
	*/
	@TableField(value="NO_REPAY_PRINCIPAL")
	private Integer noRepayPrincipal;
	 /**
	* 实际产生的表内利息(单位分，历史累计值)
	* 
	*/
	@TableField(value="INTEREST")
	private Integer interest;
	 /**
	* 已还表内利息(单位分，历史累计值)
	* 
	*/
	@TableField(value="REPAID_INTEREST")
	private Integer repaidInterest;
	 /**
	* 表内未缴税利息
	* 
	*/
	@TableField(value="ONBL_UNTAX_INTEREST")
	private Integer onblUntaxInterest;
	 /**
	* 已还表内未缴税利息
	* 
	*/
	@TableField(value="REPAID_ONBL_UNTAX_INTEREST")
	private Integer repaidOnblUntaxInterest;
	 /**
	* 宽限期利息
	* 
	*/
	@TableField(value="EXTEND_INTEREST")
	private Integer extendInterest;
	 /**
	* 已还的宽限期利息
	* 
	*/
	@TableField(value="REPAID_EXTEND_INTEREST")
	private Integer repaidExtendInterest;
	 /**
	* 表外利息(单位分，历史累计值)
	* 
	*/
	@TableField(value="OFFBALANCE_INTEREST")
	private Integer offbalanceInterest;
	 /**
	* 已还表外利息（分）
	* 
	*/
	@TableField(value="REPAID_OFFBALANCE_INTEREST")
	private Integer repaidOffbalanceInterest;
	 /**
	* 表内罚息（分）
	* 
	*/
	@TableField(value="PENALTY_INTEREST")
	private Integer penaltyInterest;
	 /**
	* 已还表内罚息（分）
	* 
	*/
	@TableField(value="REPAID_PENALTY_INTEREST")
	private Integer repaidPenaltyInterest;
	 /**
	* 表外罚息（分）
	* 
	*/
	@TableField(value="OFFBALANCE_PENALTY_INTEREST")
	private Integer offbalancePenaltyInterest;
	 /**
	* 已还表外罚息（分）
	* 
	*/
	@TableField(value="REPAID_OFFBALANCE_PENALTY_INTEREST")
	private Integer repaidOffbalancePenaltyInterest;
	 /**
	* 总共已还金额(历史累计值)
	* 
	*/
	@TableField(value="TOTAL_REPAY")
	private Integer totalRepay;
	 /**
	* 当前还款状态(finish:已还清  nofinish：未还清)
	* 
	*/
	@TableField(value="REPAY_STATUS_CURR")
	private String repayStatusCurr;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_TIME")
	private Date createTime;
	 /**
	* 最后变更的核心时间
	* 
	*/
	@TableField(value="UPDATE_CORE_SYSTE_DATE")
	private Date updateCoreSysteDate;
	 /**
	* 修改时间
	* 
	*/
	@TableField(value="UPDATE_TIME")
	private Date updateTime;
	 /**
	* AcctRecordNo
	* 
	*/
	@TableField(value="ACCT_RECORD_NO")
	private String acctRecordNo;

	@Override
	protected Serializable pkVal() {
                  return this.repayPlanProgressId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
