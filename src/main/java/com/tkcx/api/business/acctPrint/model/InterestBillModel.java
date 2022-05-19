package com.tkcx.api.business.acctPrint.model;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.business.acctPrint.html2pdf.BusiHtmlToPdf;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.BusinessUtils;
import com.tkcx.api.utils.ToolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *  贷款利息登记簿
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="INTEREST_BILL",schema="QN_DB_ACCT")
public class InterestBillModel extends Model<InterestBillModel>  implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键标识
	 *
	 */
	@TableId(value = "INTEREST_ID",type = IdType.AUTO)
	private Integer interestId;
	 /**
	* 各项汇总
	* 
	*/
	@TableField(value="AMOUNT")
	private String amount;
	 /**
	* 借贷标识
	* 
	*/
	@TableField(value="DEBT_FLAG")
	private Integer debtFlag;
	 /**
	* 借据号
	* 
	*/
	@TableField(value="DEBT_NO")
	private String debtNo;
	 /**
	* 贷款账号
	* 
	*/
	@TableField(value="LOAN_ACCOUNT")
	private String loanAccount;
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
	 /**
	* 已还逾期利息
	* 
	*/
	@TableField(value="PAYFOFF_OVERDUE_INTEREST")
	private String payfoffOverdueInterest;
	 /**
	* 还款日期
	* 
	*/
	@TableField(value="PAYOFF_DATE")
	private Date payoffDate;
	 /**
	* 结清标识
	* 
	*/
	@TableField(value="PAYOFF_FLAG")
	private Integer payoffFlag;
	 /**
	* 已还正常利息
	* 
	*/
	@TableField(value="PAYOFF_NORMAL_INTEREST")
	private String payoffNormalInterest;
	 /**
	* 已还本金
	* 
	*/
	@TableField(value="PAYOFF_PRINCIPAL")
	private String payoffPrincipal;
	 /**
	* 已还费用
	* 
	*/
	@TableField(value="PAYOFF_SUM")
	private String payoffSum;
	/**
	 * 已还其它费用
	 *
	 */
	@TableField(value="PAYOFF_OTHER_SUM")
	private String payoffOtherSum;
	 /**
	* 应还正常利息
	* 
	*/
	@TableField(value="SHOULD_NORMAL_INTEREST")
	private String shouldNormalInterest;
	 /**
	* 应还逾期利息
	* 
	*/
	@TableField(value="SHOULD_OVERDUE_INTEREST")
	private String shouldOverdueInterest;
	 /**
	* 应还本金
	* 
	*/
	@TableField(value="SHOULD_PRINCIPAL")
	private String shouldPrincipal;
	 /**
	* 应还费用
	* 
	*/
	@TableField(value="SHOULD_SUM")
	private String shouldSum;
	/**
	 * 应还其它费用
	 *
	 */
	@TableField(value="SHOULD_OTHER_SUM")
	private String shouldOtherSum;

	/**
	 * 创建日期（新增）
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;
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
	 * 户名
	 *
	 */
	@TableField(value="LOAN_NAME")
	private String loanName;

	/**
	 * 合同号
	 *
	 */
	@TableField(value="CONTRACT_NO")
	private String contractNo;

	@Override
	protected Serializable pkVal() {
		return this.interestId;
	}

	@Override
	public String toString() {
		String payoffFlagStr = "";
		if (payoffFlag == EnumConstant.PAYOFF_FLAG_NULL) {
			payoffFlagStr = "未结清";
		} else if (payoffFlag == EnumConstant.PAYOFF_FLAG_PART) {
			payoffFlagStr = "期次结清";
		} else if (payoffFlag == EnumConstant.PAYOFF_FLAG_FULL) {
			payoffFlagStr = "整笔结清";
		}

		String payoffDateStr = null == payoffDate ? "" :DateUtil.formatDate(payoffDate);
		String acctDateStr = null != payoffDate ? "" : DateUtil.formatDate(acctDate);

		return "┃" + BusinessUtils.addDefaultVal(8 - orgCode.length()) + orgCode
				+ "┃" + BusinessUtils.addDefaultVal(46 - orgName.length() * 2) + orgName
				+ "┃" + BusinessUtils.addDefaultVal(28 - loanAccount.length()) + loanAccount
				+ "┃" + BusinessUtils.addDefaultVal(12 - loanName.length() * 2) + loanName
				+ "┃" + BusinessUtils.addDefaultVal(10 - payoffDateStr.length()) + payoffDateStr
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(shouldPrincipal).length()) + ToolUtil.fenToYuan(shouldPrincipal)
				+ "┃" + BusinessUtils.addDefaultVal(12 - ToolUtil.fenToYuan(shouldNormalInterest).length()) + ToolUtil.fenToYuan(shouldNormalInterest)
				+ "┃" + BusinessUtils.addDefaultVal(12 - ToolUtil.fenToYuan(shouldOverdueInterest).length()) + ToolUtil.fenToYuan(shouldOverdueInterest)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(shouldOtherSum).length()) + ToolUtil.fenToYuan(shouldOtherSum)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(shouldSum).length()) + ToolUtil.fenToYuan(shouldSum)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffPrincipal).length()) + ToolUtil.fenToYuan(payoffPrincipal)
				+ "┃" + BusinessUtils.addDefaultVal(12 - ToolUtil.fenToYuan(payoffNormalInterest).length()) + ToolUtil.fenToYuan(payoffNormalInterest)
				+ "┃" + BusinessUtils.addDefaultVal(12 - ToolUtil.fenToYuan(payfoffOverdueInterest).length()) + ToolUtil.fenToYuan(payfoffOverdueInterest)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffOtherSum).length()) + ToolUtil.fenToYuan(payoffOtherSum)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffSum).length()) + ToolUtil.fenToYuan(payoffSum)
				+ "┃" + BusinessUtils.addDefaultVal(8 - payoffFlagStr.length() * 2) + payoffFlagStr
				+ "┃" + BusinessUtils.addDefaultVal(20 - borrowerIdnum.length()) + borrowerIdnum
				+ "┃" + BusinessUtils.addDefaultVal(10 - acctDateStr.length()) + acctDateStr + "┃";
	}

	@Override
	public String toHtmlString() {
		String payoffFlagStr = "";
		if (payoffFlag == EnumConstant.PAYOFF_FLAG_NULL) {
			payoffFlagStr = "未结清";
		} else if (payoffFlag == EnumConstant.PAYOFF_FLAG_PART) {
			payoffFlagStr = "期次结清";
		} else if (payoffFlag == EnumConstant.PAYOFF_FLAG_FULL) {
			payoffFlagStr = "整笔结清";
		}

		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(orgCode);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(orgName);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(loanAccount);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(loanName);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(null == payoffDate ? "" :DateUtil.formatDate(payoffDate));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(shouldPrincipal));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(shouldNormalInterest));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(shouldOverdueInterest));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(shouldOtherSum));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(shouldSum));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffPrincipal));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffNormalInterest));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payfoffOverdueInterest));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffOtherSum));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffSum));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(payoffFlagStr);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(borrowerIdnum);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(null != payoffDate ? "" : DateUtil.formatDate(acctDate));
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString();
	}

	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {
		BusiHtmlToPdf.setValue("contractNo", contractNo, htmlBuffer);
		BusiHtmlToPdf.setValue("debtNo", debtNo, htmlBuffer);
	}
}
