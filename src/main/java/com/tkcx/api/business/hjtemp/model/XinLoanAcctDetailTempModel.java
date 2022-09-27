package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Getter
@Setter
@TableName(value="ACCT_DETAIL_TEMP",schema="QN_DB_LOAN")
public class XinLoanAcctDetailTempModel extends Model<XinLoanAcctDetailTempModel> {

    private static final long serialVersionUID = 1L;


	/**
	 * 会计科目标识
	 *
	 */
	@TableId(value = "DETAIL_ID",type = IdType.AUTO)
	private Integer detailId;
	/**
	 * 行别
	 */
	@TableField(value="IDENTIFIER")
	private String identifier;
	 /**
	 * 账号
	 *
	 */
	@TableField(value="ACCOUNT_CODE")
	private String accountCode;

	 /**
	 * 户名
	 *
	 */
	@TableField(value="ACCOUNT_NAME")
	private String accountName;

	 /**
	 * 会计日期
	 *
	 */
	@TableField(value="ACCT_DATE")
	private Date acctDate;

	 /**
	 * 核算机构
	 *
	 */
	@TableField(value="ACCT_ORG")
	private String acctOrg;

	 /**
	 * 会计流水
	 *
	 */
	@TableField(value="ACCT_SEQ")
	private String acctSeq;

	 /**
	 * 记账方式：1-正常2-红字-3-蓝字+
	 *
	 */
	@TableField(value="ACCT_TYPE")
	private String acctType;

	 /**
	 * 钞汇标识
	 *
	 */
	@TableField(value="BANK_NOTE")
	private String bankNote;

	 /**
	 * 渠道会计日期
	 *
	 */
	@TableField(value="CHANNEL_DATE")
	private Date channelDate;

	 /**
	 * 渠道流水号
	 *
	 */
	@TableField(value="CHANNEL_SEQ")
	private String channelSeq;

	 /**
	 * 渠道
	 *
	 */
	@TableField(value="CHANNEL_WAY")
	private String channelWay;

	 /**
	 * CreateDate
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;

	 /**
	 * 清算标识：0-未清算1-已清算
	 *
	 */
	@TableField(value="CRITICIZE_FLAG")
	private String criticizeFlag;

	 /**
	 * 币种
	 *
	 */
	@TableField(value="CURRENCY")
	private String currency;

	 /**
	 * 借贷标识：D-借 C-贷
	 *
	 */
	@TableField(value="DEBT_FLAG")
	private String debtFlag;

	 /**
	 * 科目号
	 *
	 */
	@TableField(value="ITEM_CODE")
	private String itemCode;

	 /**
	 * 科目控制字
	 *
	 */
	@TableField(value="ITEM_CTRL")
	private String itemCtrl;

	 /**
	 * 表内表外标识：0-表内1-表外
	 *
	 */
	@TableField(value="OFF_BALANCE_FLAG")
	private String offBalanceFlag;

	 /**
	 * 序号
	 *
	 */
	@TableField(value="SERIAL_NO")
	private Long serialNo;

	 /**
	 * 服务码
	 *
	 */
	@TableField(value="SERVICE_CODE")
	private String serviceCode;

	 /**
	 * 服务名称
	 *
	 */
	@TableField(value="SERVICE_NAME")
	private String serviceName;

	 /**
	 * 状态：01-未记账02-记账成功03-记账失败05-已冲正
	 *
	 */
	@TableField(value="STATUS")
	private String status;

	 /**
	 * 交易金额
	 *
	 */
	@TableField(value="TRANS_AMOUNT")
	private BigDecimal transAmount;

	 /**
	 * 现转标识
	 *
	 */
	@TableField(value="TRANSFER_FLAG")
	private String transferFlag;


	/**
	 * 渠道来源
	 *
	 */
	@TableField(value="CHANNEL")
	private String channel;

	public XinLoanAcctDetailTempModel() {
	}

	@Override
	protected Serializable pkVal() {
		return this.detailId;
	}

	@Override
	public String toString() {
		return "AcctDetailTempModel{" +
				"detailId=" + detailId +
				", identifier='" + identifier + '\'' +
				", accountCode='" + accountCode + '\'' +
				", accountName='" + accountName + '\'' +
				", acctDate=" + acctDate +
				", acctOrg='" + acctOrg + '\'' +
				", acctSeq='" + acctSeq + '\'' +
				", acctType='" + acctType + '\'' +
				", bankNote='" + bankNote + '\'' +
				", channelDate=" + channelDate +
				", channelSeq='" + channelSeq + '\'' +
				", channelWay='" + channelWay + '\'' +
				", createDate=" + createDate +
				", criticizeFlag='" + criticizeFlag + '\'' +
				", currency='" + currency + '\'' +
				", debtFlag='" + debtFlag + '\'' +
				", itemCode='" + itemCode + '\'' +
				", itemCtrl='" + itemCtrl + '\'' +
				", offBalanceFlag='" + offBalanceFlag + '\'' +
				", serialNo=" + serialNo +
				", serviceCode='" + serviceCode + '\'' +
				", serviceName='" + serviceName + '\'' +
				", status='" + status + '\'' +
				", transAmount=" + transAmount +
				", transferFlag='" + transferFlag + '\'' +
				'}';
	}

}
