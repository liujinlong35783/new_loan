package com.tkcx.api.business.acctPrint.model;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.StrUtil;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.hutool.core.date.DateUtil.format;

/**
 *  会计凭证(记账凭证/交易凭证)
 *
 * @author tianyi
 * @Date 2019-08-06 20:51
 */
@Getter
@Setter
@TableName(value="ACCOUNT_VOUCHER",schema="QN_DB_ACCT")
public class AccountVoucherModel  extends Model<AccountVoucherModel> implements IAcctPrintCommon {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键标识
	 *
	 */
	@TableId(value = "VOUCHER_ID",type = IdType.AUTO)
	private Integer voucherId;
	 /**
	* 摘要
	*
	*/
	@TableField(value="ABSTRACT")
	private String abstracts;
	 /**
	* 账号
	*
	*/
	@TableField(value="ACCOUNT_CODE")
	private String accountCode;
	 /**
	* 会计分录
	*
	*/
	@TableField(value="ACCOUNT_ENTRY")
	private String accountEntry;
	 /**
	* 户名
	*
	*/
	@TableField(value="ACCOUNT_NAME")
	private String accountName;
	 /**
	* 发生额
	*
	*/
	@TableField(value="AMOUNT")
	private String amount;
	 /**
	* 交易时间
	*
	*/
	@TableField(value="BUSI_DATE")
	private Date busiDate;
	 /**
	* 交易类型
	*
	*/
	@TableField(value="BUSI_TYPE")
	private String busiType;
	 /**
	* 合同号
	*
	*/
	@TableField(value="CONTRACT_NO")
	private String contractNo;
	 /**
	* 币种
	*
	*/
	@TableField(value="CURRENCY")
	private String currency;
	 /**
	* 借贷标志
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
	* 科目控制字
	*
	*/
	@TableField(value="ITEM_CTRL")
	private String itemCtrl;
	 /**
	* 贷款账号
	*
	*/
	@TableField(value="LOAN_ACCOUNT")
	private String loanAccount;
	 /**
	* 操作员
	*
	*/
	@TableField(value="OPERATOR")
	private String operator;
	 /**
	* 机构号
	*
	*/
	@TableField(value="ORG_CODE")
	private String orgCode;
	 /**
	* 备注
	*
	*/
	@TableField(value="REMARK")
	private String remark;
	 /**
	* 流水号
	*
	*/
	@TableField(value="SERIAL_NO")
	private String serialNo;
	 /**
	* 现转标志
	*
	*/
	@TableField(value="TRANSFER_FLAG")
	private Integer transferFlag;

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
	* 传票号
	*
	*/
	@TableField(value="VOUCHER_NO")
	private String voucherNo;

	/**
	 * 机构名称
	 *
	 */
	@TableField(value="ORG_NAME")
	private String orgName;

	private String printCount;


	@Override
	protected Serializable pkVal() {
		return this.voucherId;
	}


	@Override
	public String toString() {
		String txtResult = busiType + "^@" + format(busiDate, "yyyy年MM月dd日HH时mm分ss秒") + "^@"
				+ transferFlag + "^@"
				+ voucherNo + "^@" + serialNo + "^@" + operator + "^@"
				+ accountCode + "^@" + accountName + "^@" + itemCtrl+ "^@"
				+ currency + "^@" + ToolUtil.fenToYuan(amount) + "^@" + debtFlag + "^@" + remark + "^@"
				+ abstracts + "^@" + orgCode + "^@" + orgName + "^@" + "^@" + (printCount == null ? String.valueOf(0) : printCount);
		return txtResult.replace("null", "");
	}

	@Override
	public String toHtmlString() {
		return
				"<tr>\n" +
						"    <td>" + accountCode + " " + accountName + "</td>\n" +
						"    <td>" + itemCtrl + "</td>\n" +
						"    <td>" + currency + "</td>\n" +
						"    <td>" + ToolUtil.fenToYuan(amount) + "</td>\n" +
						"    <td>" + (debtFlag == 1 ? "借" : "贷") + "</td>\n" +
						"    <td>" + (null == remark ? "" : remark) + "</td>\n" +
						"    <td>" + abstracts + "</td>\n" +
				"</tr>\n";
	}

	@Override
	public void customizedHtmlTitle(StringBuffer htmlBuffer) {
		BusiHtmlToPdf.setValue("busiDate", format(busiDate, "yyyy年mm月dd日HH时mm分ss秒"), htmlBuffer);
		BusiHtmlToPdf.setValue("voucherNo", voucherNo, htmlBuffer);
		BusiHtmlToPdf.setValue("busiType", busiType, htmlBuffer);
		BusiHtmlToPdf.setValue("transferFlag", (transferFlag == EnumConstant.TRANSFER_ELEC ? "转账" : "现金"), htmlBuffer);
		BusiHtmlToPdf.setValue("serialNo", serialNo, htmlBuffer);
		BusiHtmlToPdf.setValue("operator", operator, htmlBuffer);
	}
}
