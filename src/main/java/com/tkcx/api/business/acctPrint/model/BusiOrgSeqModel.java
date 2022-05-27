package com.tkcx.api.business.acctPrint.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.BusinessUtils;
import com.tkcx.api.utils.ToolUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 网贷业务机构业务流水
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="BUSI_ORG_SEQ",schema="QN_DB_ACCT")
public class BusiOrgSeqModel extends Model<BusiOrgSeqModel>  implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键标识
	 *
	 */
	@TableId(value = "BUSI_ID",type = IdType.AUTO)
	private Integer busiId;
	 /**
	* 摘要
	* 
	*/
	@TableField(value="ABSTRACT")
	private String abstracts;
	 /**
	* 发生额
	* 
	*/
	@TableField(value="AMOUNT")
	private String amount;
	 /**
	* 日期
	* 
	*/
	@TableField(value="BUSI_DATE")
	private Date busiDate;
	 /**
	* 交易类型（正常、冲正）
	* 
	*/
	@TableField(value="BUSI_TYPE")
	private Integer busiType;
	 /**
	* 借贷标志
	* 
	*/
	@TableField(value="DEBT_FLAG")
	private Integer debtFlag;
	 /**
	* （借据号）
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
	* 业务流水号
	* 
	*/
	@TableField(value="BIZ_TRACK_NO")
	private String bizTrackNo;
	 /**
	* 科目控制字
	* 
	*/
	@TableField(value="ITEM_CTRL")
	private String itemCtrl;
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
	* 渠道流水号
	* 
	*/
	@TableField(value="TRANS_SEQ_NO")
	private String transSeqNo;

	/**
	 * 创建时间
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
	 * 操作员
	 *
	 */
	@TableField(value="OPERATOR")
	private String operator;

	/**
	 * 新网贷标志
	 *
	 */
	@TableField(value="NEWLOAN_FLAG")
	private String newLoanFlag;

	@Override
	protected Serializable pkVal() {
		return this.busiId;
	}

	@Override
	public String toString() {
		return "┃" + BusinessUtils.addDefaultVal(28 - transSeqNo.length()) + transSeqNo
				+ "┃" + BusinessUtils.addDefaultVal(28 - debtNo.length()) + debtNo
				+ "┃" + BusinessUtils.addDefaultVal(28 - loanAccount.length()) + loanAccount
				+ "┃" + BusinessUtils.addDefaultVal(28 - abstracts.length() * 2) + abstracts
				+ "┃" + BusinessUtils.addDefaultVal(8) + (debtFlag == EnumConstant.DEBT_FLAG_DEBT ? "借" : "贷")
				+ "┃" + BusinessUtils.addDefaultVal(10 - itemCtrl.length()) + itemCtrl
				+ "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(amount).length()) + ToolUtil.fenToYuan(amount)
				+ "┃" + BusinessUtils.addDefaultVal(4) + (busiType == 0 ? "正常" : "冲正")
				+ "┃" + BusinessUtils.addDefaultVal(10 - orgCode.length()) + orgCode
				+ "┃" + BusinessUtils.addDefaultVal(6 - operator.length()) + operator + "┃";
	}

	@Override
	public String toHtmlString() {
		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(transSeqNo);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(debtNo);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(loanAccount);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(abstracts);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(debtFlag == EnumConstant.DEBT_FLAG_DEBT ? "借" : "贷");
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(itemCtrl);
		htmlStr.append("</td>");;htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(amount));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(busiType == 0 ? "正常" : "冲正");
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(orgCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(operator);
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString();
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {

	}
}
