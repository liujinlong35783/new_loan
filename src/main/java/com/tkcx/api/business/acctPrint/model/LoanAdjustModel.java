package com.tkcx.api.business.acctPrint.model;

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
 * 贷款形态调整明细清单、贷款调整登记簿
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="LOAN_ADJUST",schema="QN_DB_LOAN")
public class LoanAdjustModel extends Model<LoanAdjustModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键标识
	 *
	 */
	@TableId(value = "ADJUST_ID",type = IdType.AUTO)
	private Integer adjustId;
	 /**
	* 贷款形态转换日期
	* 
	*/
	@TableField(value="ADJUST_DATE")
	private Date adjustDate;
	 /**
	* 贷款余额
	* 
	*/
	@TableField(value="BALANCE_AMOUNT")
	private String balanceAmount;
	/**
	 * 合同号
	 *
	 */
	@TableField(value="CONTRACT_NO")
	private String contractNo;
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
	* 贷款户名
	* 
	*/
	@TableField(value="LOAN_NAME")
	private String loanName;
	 /**
	* 转化后科目代号
	* 
	*/
	@TableField(value="NEW_ITEM_CODE")
	private String newItemCode;
	 /**
	* 转化后科目名称
	* 
	*/
	@TableField(value="NEW_ITEM_NAME")
	private String newItemName;
	 /**
	* 原科目代号
	* 
	*/
	@TableField(value="ORIGINAL_ITEM_CODE")
	private String originalItemCode;
	 /**
	* 原科目名称
	* 
	*/
	@TableField(value="ORIGINAL_ITEM_NAME")
	private String originalItemName;

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
	 * 渠道来源
	 *
	 */
	@TableField(value="CHANNEL")
	private String channel;

	@Override
	protected Serializable pkVal() {
		return this.adjustId;
	}

	@Override
	public String toString() {

		return "┃" + BusinessUtils.addDefaultVal(8 - orgCode.length()) + orgCode
				+ "┃" + BusinessUtils.addDefaultVal(46 - orgName.length() * 2) + orgName
				+ "┃" + BusinessUtils.addDefaultVal(16 - DateUtil.formatDate(adjustDate).length()) + DateUtil.formatDate(adjustDate)
				+ "┃" + BusinessUtils.addDefaultVal(12 - loanName.length() * 2) + loanName
				+ "┃" + BusinessUtils.addDefaultVal(28 - loanAccount.length()) + loanAccount
				+ "┃" + BusinessUtils.addDefaultVal(28 - debtNo.length()) + debtNo
				+ "┃" + BusinessUtils.addDefaultVal(22 - contractNo.length()) + contractNo
				+ "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(grantDate).length()) + DateUtil.formatDate(grantDate)
				+ "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(dueDate).length()) + DateUtil.formatDate(dueDate)
				+ "┃" + BusinessUtils.addDefaultVal(24 - originalItemName.length() * 2) + originalItemName
				+ "┃" + BusinessUtils.addDefaultVal(12 - originalItemCode.length()) + originalItemCode
				+ "┃" + BusinessUtils.addDefaultVal(24 - newItemName.length() * 2) + newItemName
				+ "┃" + BusinessUtils.addDefaultVal(16 - newItemCode.length()) + newItemCode
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(balanceAmount).length()) + ToolUtil.fenToYuan(balanceAmount) + "┃";
	}

	@Override
	public String toHtmlString() {
		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(orgCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(orgName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(adjustDate));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(loanName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(loanAccount);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(debtNo);
		htmlStr.append("</td>");;htmlStr.append("<td>");
		htmlStr.append(contractNo);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(grantDate));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(dueDate));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(originalItemName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(originalItemCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(newItemName);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(newItemCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(balanceAmount));
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString();
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {

	}
}
