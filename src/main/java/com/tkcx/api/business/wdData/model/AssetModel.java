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
 * @Date 2019-08-08 18:37
 */
@Getter
@Setter
@TableName(value="ASSET",schema="QN_DB_BIZ")
public class AssetModel extends Model<AssetModel> {

    private static final long serialVersionUID = 1L;

	/**
	 * 资产行号，单表主键
	 *
	 */
	@TableId(value = "ASSET_ID",type = IdType.AUTO)
	private Integer assetId;
	 /**
	* 当前计财状态(normal：正常 overdue: 逾期 writeoff: 核销 payoff: 还款完成)
	* 
	*/
	@TableField(value="ASSET_FINANCE_STATUS")
	private String assetFinanceStatus;
	 /**
	* 是否应计状态(1：应计
	* 是否应计状态(1：应计,0：非应计)
	*/
	@TableField(value="IS_ACCRUED")
	private Integer isAccrued;
	 /**
	* 是否允许提前结清(0：否 1：是）
	* 
	*/
	@TableField(value="IS_CLEAN_ADVANCE")
	private Integer isCleanAdvance;
	 /**
	* 还款后是否重建还款计划(0：否 1：是）
	* 
	*/
	@TableField(value="IS_REBUILD_PLAN")
	private Integer isRebuildPlan;
	 /**
	* 重建还款计划的方式(“same_period_redue_principal”:期数不变，减少每期本金)
	* 
	*/
	@TableField(value="REBUILD_PLAN_TYPE")
	private String rebuildPlanType;
	 /**
	* 资产修改版本号
	* 资产修改版本号,主动修改资产时，才递增版本号
	*/
	@TableField(value="ASSET_VERSION")
	private Integer assetVersion;
	 /**
	* 最后一次利息计提的时间
	* 
	*/
	@TableField(value="LAST_ACCRUAL_DATE")
	private Date lastAccrualDate;
	 /**
	* 按月统计的M几标记，例如(N-1-2-1-2-N)
	* 
	*/
	@TableField(value="MONTH_STATES")
	private String monthStates;
	 /**
	* 归属机构号
	* 
	*/
	@TableField(value="ORGID")
	private String orgid;

	 /**
	* 项目编号，有一系列规则，用来标识某个项目，可以从项目编号里看到很多信息
	* 
	*/
	@TableField(value="ASSET_ITEM_NO")
	private String assetItemNo;
	 /**
	* 资产类型
	* 
	*/
	@TableField(value="ASSET_TYPE")
	private String assetType;
	 /**
	* 计息方式：等额本息-eq_loan_pmt，等额本金-eq_principal_pmt
	* 
	*/
	@TableField(value="ASSET_INTEREST_TYPE")
	private String assetInterestType;
	 /**
	* 还款周期类型
	* 
	*/
	@TableField(value="ASSET_PERIOD_TYPE")
	private String assetPeriodType;
	 /**
	* 还款总期数
	* 
	*/
	@TableField(value="ASSET_PERIOD_COUNT")
	private Integer assetPeriodCount;
	 /**
	* 实际放款期数
	* 
	*/
	@TableField(value="ASSET_REAL_PERIOD_COUNT")
	private Integer assetRealPeriodCount;
	 /**
	* 资产类别名称：14天，30天，3个月，6个月
	* 
	*/
	@TableField(value="ASSET_PRODUCT_CATEGORY")
	private String assetProductCategory;
	 /**
	* 业务订单号
	* 
	*/
	@TableField(value="ASSET_BIZ_ORDER")
	private String assetBizOrder;
	 /**
	* 资金方订单号，即银行的资产号
	* 
	*/
	@TableField(value="ASSET_CAPITAL_ORDER")
	private String assetCapitalOrder;
	 /**
	* 贷款账户
	* 
	*/
	@TableField(value="ASSET_LOAN_ACCOUNT")
	private String assetLoanAccount;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="ASSET_CREATE_AT")
	private Date assetCreateAt;
	 /**
	* 订单申请时间
	* 
	*/
	@TableField(value="ASSET_APPLY_AT")
	private Date assetApplyAt;
	 /**
	* 合同生效日
	* 
	*/
	@TableField(value="ASSET_EFFECT_AT")
	private Date assetEffectAt;
	 /**
	* 实际放款日
	* 
	*/
	@TableField(value="ASSET_ACTUAL_GRANT_AT")
	private Date assetActualGrantAt;
	 /**
	* 资产到期日
	* 
	*/
	@TableField(value="ASSET_DUE_AT")
	private Date assetDueAt;
	 /**
	* 偿清时间
	* 
	*/
	@TableField(value="ASSET_PAYOFF_AT")
	private Date assetPayoffAt;
	 /**
	* AssetUpdateAt
	* 
	*/
	@TableField(value="ASSET_UPDATE_AT")
	private Date assetUpdateAt;
	 /**
	* AssetFromSystem
	* 
	*/
	@TableField(value="ASSET_FROM_SYSTEM")
	private String assetFromSystem;
	 /**
	* 放款渠道
	* 
	*/
	@TableField(value="ASSET_LOAN_CHANNEL")
	private String assetLoanChannel;
	 /**
	* 资产状态
	* 资产状态,grant-放款中，repay-还款中，payoff-已还清，invalid-无效
	*/
	@TableField(value="ASSET_STATUS")
	private String assetStatus;
	 /**
	* 本息总额（所有本金+理论足期总利息）
	* 
	*/
	@TableField(value="ASSET_TOTAL_AMOUNT")
	private BigDecimal assetTotalAmount;
	 /**
	* 实际总金额（总本金+实际产生的利息之和）
	* 
	*/
	@TableField(value="ASSET_ACTUAL_TOTAL_AMOUNT")
	private BigDecimal assetActualTotalAmount;
	 /**
	* 剩余未偿还总金额(含本，息，宽限期利息，罚息）
	* 
	*/
	@TableField(value="ASSET_BALANCE_AMOUNT")
	private BigDecimal assetBalanceAmount;
	 /**
	* 已偿还总金额
	* 
	*/
	@TableField(value="ASSET_REPAID_AMOUNT")
	private BigDecimal assetRepaidAmount;
	 /**
	* 合同本金
	* 
	*/
	@TableField(value="ASSET_PRINCIPAL_AMOUNT")
	private BigDecimal assetPrincipalAmount;
	 /**
	* 实际的总应还本金（还款计划总本金之和）
	* 
	*/
	@TableField(value="ASSET_REAL_PRINCIPAL_AMOUNT")
	private BigDecimal assetRealPrincipalAmount;
	 /**
	* 实际放款本金
	* 
	*/
	@TableField(value="ASSET_GRANTED_PRINCIPAL_AMOUNT")
	private BigDecimal assetGrantedPrincipalAmount;
	 /**
	* 已偿还本金
	* 
	*/
	@TableField(value="ASSET_REPAID_PRINCIPAL_AMOUNT")
	private BigDecimal assetRepaidPrincipalAmount;
	 /**
	* 实际产生的本金利息
	* 
	*/
	@TableField(value="ASSET_ACTUAL_INTEREST")
	private BigDecimal assetActualInterest;
	 /**
	* 理论借满全部期次的本金利息
	* 
	*/
	@TableField(value="ASSET_INTEREST_AMOUNT")
	private BigDecimal assetInterestAmount;
	 /**
	* 已偿还本金利息（不含罚息）
	* 
	*/
	@TableField(value="ASSET_REPAID_INTEREST_AMOUNT")
	private BigDecimal assetRepaidInterestAmount;
	 /**
	* 已计提未结息的本金利息
	* 
	*/
	@TableField(value="ASSET_UNSETTLE_INTEREST_AMOUNT")
	private BigDecimal assetUnsettleInterestAmount;
	 /**
	* 宽限期利息
	* 
	*/
	@TableField(value="ASSET_EXTEND_INTEREST")
	private BigDecimal assetExtendInterest;
	 /**
	* 已还宽限期利息
	* 
	*/
	@TableField(value="ASSET_REPAID_EXTEND_INTEREST")
	private BigDecimal assetRepaidExtendInterest;
	 /**
	* 已计提未结息的宽限期利息
	* 
	*/
	@TableField(value="ASSET_UNSETTLE_EXTEND_INTEREST")
	private BigDecimal assetUnsettleExtendInterest;
	 /**
	* 罚息
	* 
	*/
	@TableField(value="ASSET_PENALTY_INTEREST")
	private BigDecimal assetPenaltyInterest;
	 /**
	* 已还罚息（表内表外）
	* 
	*/
	@TableField(value="ASSET_REPAID_PENALTY_INTEREST")
	private BigDecimal assetRepaidPenaltyInterest;
	 /**
	* 已计提未结息的罚息
	* 
	*/
	@TableField(value="ASSET_UNSETTLE_PENALTY_INTEREST")
	private BigDecimal assetUnsettlePenaltyInterest;
	 /**
	* 正常利率(0.18表示年化18%)
	* 
	*/
	@TableField(value="ASSET_INTEREST_RATE")
	private BigDecimal assetInterestRate;
	 /**
	* 正常利息日利率(0.0005表示万五)
	* 
	*/
	@TableField(value="ASSET_INTEREST_DAY_RATE")
	private BigDecimal assetInterestDayRate;
	 /**
	* 罚息利率(年化利率，%)
	* 
	*/
	@TableField(value="ASSET_PENALTY_INTEREST_RATE")
	private BigDecimal assetPenaltyInterestRate;
	 /**
	* 罚息日利率
	* 
	*/
	@TableField(value="ASSET_PENALTY_INTEREST_DAY_RATE")
	private BigDecimal assetPenaltyInterestDayRate;
	 /**
	* 一年按几天计算
	* 
	*/
	@TableField(value="INTEREST_YEAR_TYPE")
	private String interestYearType;
	 /**
	* 贷款用途
	* 
	*/
	@TableField(value="ASSET_LOAN_USAGE")
	private String assetLoanUsage;
	 /**
	* 宽限类型
	* 宽限类型,natural_month-自然月月底，day-天数,如果没值，则不宽限
	*/
	@TableField(value="ASSET_EXTEND_TYPE")
	private String assetExtendType;
	 /**
	* 宽限天数
	* 宽限天数,只有asset_extend_type为day时生效
	*/
	@TableField(value="ASSET_EXTEND_DAY")
	private Integer assetExtendDay;
	 /**
	* 逾期状态(normal：正常 overdue: 逾期)
	* 
	*/
	@TableField(value="ASSET_OVERDUE_STATUS")
	private String assetOverdueStatus;
	 /**
	* 逾期天数
	* 
	*/
	@TableField(value="ASSET_OVERDUE_DAYS")
	private Integer assetOverdueDays;
	 /**
	* 借贷人身份证号
	* 
	*/
	@TableField(value="ASSET_BORROWER_IDNUM")
	private String assetBorrowerIdnum;
	 /**
	* 还款人身份证号
	* 
	*/
	@TableField(value="ASSET_REPAYER_IDNUM")
	private String assetRepayerIdnum;
	 /**
	* 借据号
	* 
	*/
	@TableField(value="ASSET_DEBT_NO")
	private String assetDebtNo;
	 /**
	* 累积已使用优惠金额
	* 
	*/
	@TableField(value="ASSET_COUPON")
	private Integer assetCoupon;
	 /**
	* 呆滞呆账天数，与产品对应
	* 
	*/
	@TableField(value="ASSET_PRODUCT_BAD_DEBT_DAYS")
	private Integer assetProductBadDebtDays;
	 /**
	* 计提方式(none:无需计提处理
	* 计提方式(none:无需计提处理,day_end:日末计提,due_date_end：到期日日末)
	*/
	@TableField(value="INTEREST_ACCRUAL_TYPE")
	private String interestAccrualType;
	 /**
	* 结息方式(none: 无需结息处理
	* 结息方式(none: 无需结息处理,day_begin:日初结息，due_date_nextday：到期日第二天)
	*/
	@TableField(value="INTEREST_SETTLE_TYPE")
	private String interestSettleType;

	@Override
	protected Serializable pkVal() {
		return this.assetId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
