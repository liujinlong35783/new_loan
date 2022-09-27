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
import com.tkcx.api.utils.ToolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 借款借据回单
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="LOAN_RECEIPT",schema="QN_DB_ACCT")
public class LoanReceiptModel extends Model<LoanReceiptModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 唯一序列
	 *
	 */
	@TableId(value = "LRECEIPT_ID",type = IdType.AUTO)
	private Integer lreceiptId;
	 /**
	* 贷户住址
	* 
	*/
	@TableField(value="BORROWER_ADDR")
	private String borrowerAddr;
	 /**
	* 身份证号
	* 
	*/
	@TableField(value="BORROWER_IDNUM")
	private String borrowerIdnum;
	 /**
	* 借款人
	* 
	*/
	@TableField(value="BORROWER_NAME")
	private String borrowerName;
	 /**
	* 合同号
	* 
	*/
	@TableField(value="CONTRACT_NO")
	private String contractNo;
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
	* 借款利率
	* 
	*/
	@TableField(value="INTEREST_RATE")
	private BigDecimal interestRate;
	 /**
	* 贷款账号
	* 
	*/
	@TableField(value="LOAN_ACCOUNT")
	private String loanAccount;
	 /**
	* 借据金额
	* 
	*/
	@TableField(value="LOAN_AMOUNT")
	private String loanAmount;
	 /**
	* 贷款日期
	* 
	*/
	@TableField(value="LOAN_DATE")
	private Date loanDate;
	 /**
	* 借据种类
	* 
	*/
	@TableField(value="LOAN_TYPE")
	private Integer loanType;
	 /**
	* 借款用途
	* 
	*/
	@TableField(value="LOAN_USAGE")
	private String loanUsage;
	 /**
	* 还款方式
	* 
	*/
	@TableField(value="PAYOFF_TYPE")
	private Integer payoffType;
	 /**
	* 存款账号
	* 
	*/
	@TableField(value="RECEIVER_ACCOUNT")
	private String receiverAccount;

	/**
	 * 会计日期
	 *
	 */
	@TableField(value="ACCT_DATE")
	private Date acctDate;

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
	 * 新网贷标志
	 *
	 */
	@TableField(value="NEWLOAN_FLAG")
	private String newLoanFlag;

	private String printCount;

	@Override
	protected Serializable pkVal() {
		return this.lreceiptId;
	}

	@Override
	public String toString() {
		String payoffTypeStr = "";
		if (payoffType == EnumConstant.EQ_LOAN_PMT) {
			payoffTypeStr = "等额本息" ;
		} else if (payoffType == EnumConstant.EQ_PRINCIPAL_PMT) {
			payoffTypeStr = "等额本金" ;
		}

		return (borrowerName + "^@" + borrowerIdnum + "^@" + borrowerAddr + "^@"
				+ loanType + "^@" + loanAccount + "^@"
				+ receiverAccount + "^@" + loanUsage + "^@" + interestRate + "^@"
				+ payoffTypeStr + "^@" + DateUtil.formatDateTime(loanDate) + "^@"
				+ DateUtil.formatDateTime(dueDate) + "^@" + contractNo + "^@"
				+ loanAmount + "^@" + orgCode + "^@" + orgName + "^@" + "^@" + (printCount == null ? String.valueOf(0) : printCount))
				.replace("null", "");
	}

	/**
	 * @return 转换后的html代码
	 */
	@Override
	public String toHtmlString() {
		return "<tr><td>借款人:</td><td>"+borrowerName+"</td><td>身份证号:</td><td>"+borrowerIdnum+"</td></tr>\n" +
				"    <tr><td>贷户住址:</td><td>"+borrowerAddr+"</td><td>合同号:</td><td>"+contractNo+"</td></tr>\n" +
				"    <tr><td>存款账号:</td><td>"+receiverAccount+"</td><td>贷款账号:</td><td>"+loanAccount+"</td></tr>\n" +
				"    <tr><td>借款利率:</td><td>"+interestRate+"</td><td>还款方式:</td><td>"+(payoffType == Integer.valueOf(1) ? "等额本息": "等额本金") +"</td></tr>\n" +
				"    <tr><td>贷款用途:</td><td>"+loanUsage+"</td><td>借据种类:</td><td>"+(loanType == Integer.valueOf(0)  ? "短期" : "")+"</td></tr>\n" +
				"    <tr><td>贷款日期:</td><td>"+DateUtil.formatDateTime(loanDate)+"</td><td>到期日期:</td><td>"+DateUtil.formatDateTime(dueDate)+"</td></tr>\n" +
				"    <tr><td>借款金额:</td><td colspan=\"3\">￥"+ToolUtil.fenToYuan(loanAmount)+"  人民币："+ToolUtil.toChinese(ToolUtil.fenToYuan(loanAmount))+"</td></tr>" +
				"    <tr><td>机构名称:</td><td>"+orgName+"</td><td>机构号:</td><td>"+orgCode+"</td></tr>\n"
						.replace("null", "");
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {
		BusiHtmlToPdf.setValue("loanDate", DateUtil.formatDateTime(loanDate), htmlBuffer);
	}
}
