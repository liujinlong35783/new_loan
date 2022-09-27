package com.tkcx.api.business.acctPrint.model.Loan;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.utils.BusinessUtils;
import com.tkcx.api.utils.ToolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 贷款明细账
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="LOAN_DETAIL_BILL",schema="QN_DB_LOAN")
public class LoanDetailBillLoanModel extends Model<LoanDetailBillLoanModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键标识
	 *
	 */
	@TableId(value = "DETAIL_ID",type = IdType.AUTO)
	private Integer detailId;
	 /**
	* 全部金额
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
	* 发放日期
	* 
	*/
	@TableField(value="GRANT_DATE")
	private Date grantDate;
	 /**
	* 发放金额
	* 
	*/
	@TableField(value="GTANT_AMOUNT")
	private String gtantAmount;
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
	* 还款日期
	* 
	*/
	@TableField(value="PAYOFF_DATE")
	private Date payoffDate;
	 /**
	* 还息金额
	* 
	*/
	@TableField(value="PAYOFF_INTEREST")
	private String payoffInterest;
	 /**
	* 还本金额
	* 
	*/
	@TableField(value="PAYOFF_PRINCIPAL")
	private String payoffPrincipal;

	 /**
	* 本金余额
	* 
	*/
	@TableField(value="PRINCIPAL_BALANCE")
	private String principalBalance;
	 /**
	* 生效日期
	* 
	*/
	@TableField(value="VALID_DATE")
	private Date validDate;

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
	 * 渠道来源
	 *
	 */
	@TableField(value="CHANNEL")
	private String channel;

	@Override
	protected Serializable pkVal() {
		return this.detailId;
	}

	@Override
	public String toString() {
		String debtFlagStr = "";
		if (debtFlag == 1) {
			debtFlagStr = "借";
		} else if (debtFlag == 2) {
			debtFlagStr = "贷";
		}

		String payoffDateStr = null == payoffDate ? "" :DateUtil.formatDate(payoffDate);

		return "┃" + BusinessUtils.addDefaultVal(8 - orgCode.length()) + orgCode
				+ "┃" + BusinessUtils.addDefaultVal(46 - orgName.length() * 2) + orgName
				+ "┃" + BusinessUtils.addDefaultVal(12 - loanName.length() * 2) + loanName
				+ "┃" + BusinessUtils.addDefaultVal(28 - loanAccount.length()) + loanAccount
				+ "┃" + BusinessUtils.addDefaultVal(28 - debtNo.length()) + debtNo
				+ "┃" + BusinessUtils.addDefaultVal(22 - contractNo.length()) + contractNo
				+ "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(grantDate).length()) + DateUtil.formatDate(grantDate)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(gtantAmount).length()) + ToolUtil.fenToYuan(gtantAmount)
				+ "┃" + BusinessUtils.addDefaultVal(10 - payoffDateStr.length()) + payoffDateStr
				+ "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(validDate).length()) + DateUtil.formatDate(validDate)
				+ "┃" + BusinessUtils.addDefaultVal(10 - debtFlagStr.length() * 2) + debtFlagStr
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffPrincipal).length()) + ToolUtil.fenToYuan(payoffPrincipal)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffInterest).length()) + ToolUtil.fenToYuan(payoffInterest)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(amount).length()) + ToolUtil.fenToYuan(amount)
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(principalBalance).length()) + ToolUtil.fenToYuan(principalBalance)
				+ "┃" + BusinessUtils.addDefaultVal(20 - borrowerIdnum.length()) + borrowerIdnum + "┃";
	}

	/**
	 * @return 转换后的html代码
	 */
	@Override
	public String toHtmlString() {
		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(orgCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(orgName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(loanName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(loanAccount);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(debtNo);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(contractNo);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(grantDate));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(gtantAmount));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(payoffDate == null ? "" : DateUtil.formatDate(payoffDate));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(validDate));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append("1".equals(debtNo) ? "借": "贷");
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffPrincipal));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(payoffInterest));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(amount));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(principalBalance));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(borrowerIdnum);
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString();
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {

	}
}
