package com.tkcx.api.business.acctPrint.model;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.business.acctPrint.html2pdf.BusiHtmlToPdf;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.utils.ToolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 还款回单
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="REFUND_RECEIPT",schema="QN_DB_ACCT")
public class RefundReceiptModel extends Model<RefundReceiptModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 *
	 */
	@TableId(value = "RRECEIPT_ID",type = IdType.AUTO)
	private Integer rreceiptId;
	 /**
	* 合同号
	* 
	*/
	@TableField(value="CONTRACT_NO")
	private String contractNo;
	/**
	 * 贷款账号
	 *
	 */
	@TableField(value="LOAN_ACCOUNT")
	private String loanAccount;
	/**
	 * 借据号
	 *
	 */
	@TableField(value="DEBT_NO")
	private String debtNo;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_DATE")
	private Date createDate;
	 /**
	* 到期日期
	* 
	*/
	@TableField(value="DUE_DATE")
	private Date dueDate;
	 /**
	* 发放日期
	* 
	*/
	@TableField(value="GRANT_DATE")
	private Date grantDate;
	 /**
	* 正常利率
	* 
	*/
	@TableField(value="INTEREST_RATE")
	private BigDecimal interestRate;
	 /**
	* 贷款金额
	* 
	*/
	@TableField(value="LOAN_AMOUNT")
	private String loanAmount;
	 /**
	* 逾期利率
	* 
	*/
	@TableField(value="OVERDUE_INTEREST_RATE")
	private BigDecimal overdueInterestRate;
	 /**
	* 结算账号
	* 
	*/
	@TableField(value="PAY_ACCOUNT")
	private String payAccount;
	 /**
	* 还款日期
	* 
	*/
	@TableField(value="PAYOFF_DATE")
	private Date payoffDate;
	 /**
	* 户名
	* 
	*/
	@TableField(value="REFUND_NAME")
	private String refundName;
	 /**
	* 偿还利息金额
	* 
	*/
	@TableField(value="REPAID_INTEREST_AMOUNT")
	private String repaidInterestAmount;
	 /**
	* 偿还本金金额
	* 
	*/
	@TableField(value="REPAID_PRINCIPAL_AMOUNT")
	private String repaidPrincipalAmount;
	 /**
	* 偿还本息合计金额
	* 
	*/
	@TableField(value="REPAID_SUM_AMOUNT")
	private String repaidSumAmount;
	 /**
	* 结欠利息金额
	* 
	*/
	@TableField(value="UNSETTLE_INTEREST_AMOUNT")
	private String unsettleInterestAmount;
	 /**
	* 结欠本金金额
	* 
	*/
	@TableField(value="UNSETTLE_PRINCIPAL_AMOUNT")
	private String unsettlePrincipalAmount;
	 /**
	* 结欠本息合计金额
	* 
	*/
	@TableField(value="UNSETTLE_SUM_AMOUNT")
	private String unsettleSumAmount;

	/**
	 * 会计日期
	 *
	 */
	@TableField(value="ACCT_DATE")
	private Date acctDate;

	/**
	 * 身份证号
	 *
	 */
	@TableField(value="BORROWER_IDNUM")
	private String borrowerIdnum;

	/**
	 * 机构号
	 *
	 */
	@TableField(value="ORG_CODE")
	private String orgCode;
	/**
	 * 机构名称
	 *
	 */
	@TableField(value="ORG_NAME")
	private String orgName;

	private String printCount;

	@Override
	protected Serializable pkVal() {
		return this.rreceiptId;
	}

	@Override
	public String toString() {
		return (DateUtil.formatDateTime(payoffDate) + "^@" + refundName + "^@"
				+ contractNo + "^@" + loanAccount + "^@" + payAccount + "^@"
				+ debtNo + "^@" + loanAmount + "^@" + DateUtil.formatDateTime(grantDate) + "^@"
				+ DateUtil.formatDateTime(dueDate) + "^@" + interestRate + "^@"
				+ overdueInterestRate + "^@" + repaidPrincipalAmount + "^@"
				+ unsettlePrincipalAmount + "^@" + repaidInterestAmount + "^@"
				+ unsettleInterestAmount + "^@" + unsettleSumAmount + "^@"
				+ repaidSumAmount + "^@" + orgCode + "^@" + orgName + "^@" + "^@" + (printCount == null ? String.valueOf(0) : printCount)
		).replace("null", "");
	}

	/**
	 * @return 转换后的html代码
	 */
	@Override
	public String toHtmlString() {
		return ("   <tr><td>户名:</td><td>"+refundName+"</td><td>合同号:</td><td>"+contractNo+"</td></tr>\n" +
				"    <tr><td>贷款账号:</td><td>"+loanAccount+"</td><td>借据号:</td><td>"+debtNo+"</td></tr>\n" +
				"    <tr><td>还款账号:</td><td>"+payAccount+"</td><td>贷款金额:</td><td>￥"+ToolUtil.fenToYuan(loanAmount) +"</td></tr>\n" +
				"    <tr><td>发放日期:</td><td>"+DateUtil.formatDateTime(grantDate)+"</td><td>到期日期:</td><td>"+DateUtil.formatDateTime(dueDate)+"</td></tr>\n" +
				"    <tr><td>正常利率:</td><td>"+interestRate+"</td><td>逾期利率:</td><td>"+overdueInterestRate+"</td></tr>\n" +
				"    <tr><td>偿还本金:</td><td>￥"+ToolUtil.fenToYuan(repaidPrincipalAmount)+"</td><td>偿还利息:</td><td>￥"+ToolUtil.fenToYuan(repaidPrincipalAmount)+"</td></tr>\n" +
				"    <tr><td>偿还本息合计:</td><td colspan=\"3\">￥"+ToolUtil.fenToYuan(repaidSumAmount)+"  人民币"+ToolUtil.toChinese(ToolUtil.fenToYuan(repaidSumAmount))+"</td></tr>\n" +
				"    <tr><td>结欠本金:</td><td>￥"+ToolUtil.fenToYuan(unsettlePrincipalAmount)+"</td><td>结欠利息:</td><td>￥"+ToolUtil.fenToYuan(unsettleInterestAmount)+"</td></tr>\n" +
				"    <tr><td>结欠本息合计:</td><td colspan=\"3\">￥"+ToolUtil.fenToYuan(unsettleSumAmount)+"  人民币："+ToolUtil.toChinese(ToolUtil.fenToYuan(unsettleSumAmount))+"</td></tr>" +
				"    <tr><td>机构名称:</td><td>"+orgName+"</td><td>机构号:</td><td>"+orgCode+"</td></tr>\n")
				.replace("null", "");
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {
		BusiHtmlToPdf.setValue("payoffDate", DateUtil.formatDateTime(payoffDate), htmlBuffer);
	}
}
