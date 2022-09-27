package com.tkcx.api.business.acctPrint.model.Loan;

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
 * 网贷业务机构轧账单
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="BUSI_ORG_BILL",schema="QN_DB_LOAN")
public class BusiOrgBillLoanModel extends Model<BusiOrgBillLoanModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	
	 /**
	* 主键
	* 
	*/
	@TableId(value = "BILL_ID",type = IdType.AUTO)
	private Integer billId;
	 /**
	* 日期
	* 
	*/
	@TableField(value="BUSI_DATE")
	private Date busiDate;
	 /**
	* 贷方发生额
	* 
	*/
	@TableField(value="CREDIT_AMOUNT")
	private String creditAmount;
	 /**
	* 贷方笔数
	* 
	*/
	@TableField(value="CREDIT_NUM")
	private Integer creditNum;
	 /**
	* 借方发生额
	* 
	*/
	@TableField(value="DEBT_AMOUNT")
	private String debtAmount;
	 /**
	* 借方笔数
	* 
	*/
	@TableField(value="DEBT_NUM")
	private Integer debtNum;
	 /**
	* 表内外标识
	* 
	*/
	@TableField(value="FLAG")
	private String flag;
	 /**
	* 科目号 改名科目控制字
	* 
	*/
	@TableField(value="ITEM_CODE")
	private String itemCode;
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
	 * 渠道来源
	 *
	 */
	@TableField(value="CHANNEL")
	private String channel;

	@Override
	protected Serializable pkVal() {
		return this.billId;
	}

	@Override
	public String toString() {

		return "┃" + BusinessUtils.addDefaultVal(20 - itemCode.length()) + itemCode
				+ "┃" + BusinessUtils.addDefaultVal(16) + ("0".equals(flag) ? "表内":"表外")
				+ "┃" + BusinessUtils.addDefaultVal(24 - debtNum.toString().length()) + debtNum
				+ "┃" + BusinessUtils.addDefaultVal(28 - ToolUtil.fenToYuan(debtAmount).length()) + ToolUtil.fenToYuan(debtAmount)
				+ "┃" + BusinessUtils.addDefaultVal(24 - creditNum.toString().length()) + creditNum
				+ "┃" + BusinessUtils.addDefaultVal(28 - ToolUtil.fenToYuan(creditAmount).length()) + ToolUtil.fenToYuan(creditAmount) + "┃";
	}

	@Override
	public String toHtmlString() {
		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(itemCode);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(("0".equals(flag) ? "表内":"表外"));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(debtNum);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(debtAmount));
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(creditNum);
		htmlStr.append("</td>");htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(creditAmount));
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString();
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {}
}
