package com.tkcx.api.constant;

/**
 * 记账的场景定义
 * 
 * @author
 *
 */
public class AcctRecordScene {

	/**
	 * 放款成功
	 */
	public static final String GRANT_SUCCESS = "grant_success";

	/**
	 * 正常状态还款
	 */
	public static final String REPAY_NORMAL = "repay_normal";
	
	/**
	 * 正常状态还款利息减免
	 */
	public static final String REPAY_NORMAL_REDUE = "repay_normal_redue";

	/**
	 * 逾期状态还款
	 */
	public static final String REPAY_OVERDUE = "repay_overdue";
	
	/**
	 * 呆滞状态还款
	 */
	public static final String REPAY_IDLE = "repay_idle";
	
	/**
	 * 逾期状态还款利息减免
	 */
	public static final String REPAY_OVERDUE_REDUE = "repay_overdue_redue";

	/**
	 * 非应计状态下还款
	 */
	public static final String REPAY_OFFBALANCE = "repay_offbalance";
	
	/**
	 * 非应计状态下还款利息减免
	 */
	public static final String REPAY_OFFBALANCE_REDUE = "repay_offbalance_redue";

	/**
	 * 核销状态下还款
	 */
	public static final String REPAY_WRITEOFF = "repay_writeoff";
	
	/**
	 * 核销状态下还款利息减免
	 */
	public static final String REPAY_WRITEOFF_REDUE = "repay_writeoff_redue";

	/**
	 * 正常利息计提
	 */
	public static final String ACCRUAL_NORMAL = "accrual_normal";

	/**
	 * 逾期利息计提
	 */
	public static final String ACCRUAL_OVERDUE = "accrual_overdue";


	/**
	 * 非应计利息计提
	 */
	public static final String ACCRUAL_OFFBALANCE = "accrual_offbalance";

	/**
	 * 正常状态利息结息
	 */
	public static final String SETTLE_NORMAL = "settle_normal";
	
	/**
	 * 呆滞状态利息结息
	 */
	public static final String SETTLE_IDLE = "settle_idle";
	
	/**
	 * 呆账状态利息结息
	 */
	public static final String SETTLE_BAD = "settle_bad";

	/**
	 * 逾期（应计）状态利息结息
	 */
	public static final String SETTLE_OVERDUE = "settle_overdue";

	/**
	 * 正常转逾期
	 */
	public static final String NORMAL_TO_OVERDUE = "normal_to_overdue";
	
	/**
	 * 逾期转呆滞
	 */
	public static final String OVERDUE_TO_IDLE = "overdue_to_idle";
	
	/**
	 * 呆滞转正常或逾期
	 */
	public static final String IDLE_TO_NORMAL_OR_OVERDUE = "idle_to_normal_or_overdue";

	/**
	 * 应计转非应计
	 */
	public static final String BALANCE_TO_OFFBALANCE = "banlance_to_offbalance";

	/**
	 * 非应计转应计
	 */
	public static final String OFFBALANCE_TO_BALANCE = "offbalance_to_balance";

	/**
	 * 核销
	 */
	public static final String WRITE_OFF = "write_off";
}
