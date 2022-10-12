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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 贷款分户账
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Getter
@Setter
@TableName(value="LOAN_ACC_BILL",schema="QN_DB_LOAN")
public class LoanAccBillModel extends Model<LoanAccBillModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	
	 /**
	* 主键标识
	* 
	*/
	@TableId(value = "ACC_BILL_ID",type = IdType.AUTO)
	private Integer accBillId;
	 /**
	* 账户状态
	* 
	*/
	@TableField(value="ACCOUNT_STATUS")
	private Integer accountStatus;
	 /**
	* 执行利率
	* 
	*/
	@TableField(value="ACTUAL_RATE")
	private BigDecimal actualRate;
	 /**
	* 余额
	* 
	*/
	@TableField(value="BALANCE_AMOUNT")
	private String balanceAmount;
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
	* 到期日
	* 
	*/
	@TableField(value="DUE_DATE")
	private Date dueDate;
	 /**
	* 放款账号
	* 
	*/
	@TableField(value="GRANT_ACCOUNT")
	private String grantAccount;
	 /**
	* 放款日
	* 
	*/
	@TableField(value="GRANT_DATE")
	private Date grantDate;
	 /**
	* 放款金额
	* 
	*/
	@TableField(value="GTANT_AMOUNT")
	private String gtantAmount;
	 /**
	* 科目
	* 
	*/
	@TableField(value="ITEM")
	private String item;
	 /**
	* 户名
	* 
	*/
	@TableField(value="LOAN_NAME")
	private String loanName;
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
	* 还款账号
	* 
	*/
	@TableField(value="PAYOFF_ACCOUNT")
	private String payoffAccount;
	 /**
	* 还款金额
	* 
	*/
	@TableField(value="PAYOFF_AMOUNT")
	private String payoffAmount;
	 /**
	* 还款日
	* 
	*/
	@TableField(value="PAYOFF_DATE")
	private Date payoffDate;
	 /**
	* 本金状态
	* 
	*/
	@TableField(value="PRINCIPAL_STATUS")
	private Integer principalStatus;
	/**
	 * 身份证号
	 *
	 */
	@TableField(value="BORROWER_IDNUM")
	private String borrowerIdnum;
	/**
	 * 会计日期
	 *
	 */
	@TableField(value="ACCT_DATE")
	private Date acctDate;

	/**
	 * 创建日期（新增 ）
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;

	/**
	 * 渠道来源
	 *
	 */
	@TableField(value="CHANNEL")
	private String channel;

	@Override
	protected Serializable pkVal() {
		return this.accBillId;
	}

	@Override
	public String toString() {
	    String principalStatusStr = "";
	    if (principalStatus == 0) {
            principalStatusStr = "正常";
        } else if (principalStatus == 1) {
            principalStatusStr = "逾期";
        } else if (principalStatus == 2) {
            principalStatusStr = "呆滞";
        } else if (principalStatus == 3) {
            principalStatusStr = "还清";
        }

        String debtFlagStr = "";
        if (debtFlag == 1) {
            debtFlagStr = "借";
        } else if (debtFlag == 2) {
            debtFlagStr = "贷";
        }

        String accStatus = accountStatus == 3 ? "正常" : "已销户";
        String payoffDateStr = nullToString(payoffDate, DateUtil.formatDate(payoffDate));
        String payoffAccStr = nullToString(payoffAccount, payoffAccount);

        return "┃" + BusinessUtils.addDefaultVal(8 - orgCode.length()) + orgCode
                + "┃" + BusinessUtils.addDefaultVal(46 - orgName.length() * 2) + orgName
                + "┃" + BusinessUtils.addDefaultVal(12 - loanName.length() * 2) + loanName
                + "┃" + BusinessUtils.addDefaultVal(26 - loanAccount.length()) + loanAccount
                + "┃" + BusinessUtils.addDefaultVal(10 - item.length()) + item
                + "┃" + BusinessUtils.addDefaultVal(8 - principalStatusStr.length() * 2) + principalStatusStr
                + "┃" + BusinessUtils.addDefaultVal(8 - accStatus.length() * 2) + accStatus
                + "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(grantDate).length()) + DateUtil.formatDate(grantDate)
                + "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(gtantAmount).length()) + ToolUtil.fenToYuan(gtantAmount)
                + "┃" + BusinessUtils.addDefaultVal(20 - grantAccount.length()) + grantAccount
                + "┃" + BusinessUtils.addDefaultVal(8 - actualRate.toString().length()) + actualRate
                + "┃" + BusinessUtils.addDefaultVal(10 - DateUtil.formatDate(dueDate).length()) + DateUtil.formatDate(dueDate)
                + "┃" + BusinessUtils.addDefaultVal(10 - payoffDateStr.length()) + payoffDateStr
                + "┃" + BusinessUtils.addDefaultVal(20 - payoffAccStr.length()) + payoffAccStr
                + "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(payoffAmount).length()) + ToolUtil.fenToYuan(payoffAmount)
                + "┃" + BusinessUtils.addDefaultVal(8 - debtFlagStr.length() * 2) + debtFlagStr
                + "┃" + BusinessUtils.addDefaultVal(10 - ToolUtil.fenToYuan(balanceAmount).length()) + ToolUtil.fenToYuan(balanceAmount)
                + "┃" + BusinessUtils.addDefaultVal(20 - borrowerIdnum.length()) + borrowerIdnum + "┃";
	}

	public String nullToString(Object obj, String val) {
	    return obj == null ? "" : val;
    }

	@Override
	public String toHtmlString() {
		StringBuffer htmlStr = new StringBuffer("<tr>");
		htmlStr.append("<td>");
		htmlStr.append(orgCode);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(orgName);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(loanName);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(loanAccount);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(item);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(principalStatus);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(accountStatus);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDateTime(grantDate));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(gtantAmount));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(grantAccount);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(actualRate);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(DateUtil.formatDate(dueDate));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(payoffDate ==null ? "" : DateUtil.formatDateTime(payoffDate));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(payoffAccount ==null ? "" : payoffAccount);
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(payoffAmount ==null ? "" : ToolUtil.fenToYuan(payoffAmount));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append("1".equals(debtNo) ? "借": "贷");
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(ToolUtil.fenToYuan(balanceAmount));
		htmlStr.append("</td>");
		htmlStr.append("<td>");
		htmlStr.append(borrowerIdnum);
		htmlStr.append("</td>");
		htmlStr.append("</tr>");
		return htmlStr.toString().replace("null","");
	}
	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {

	}
}
