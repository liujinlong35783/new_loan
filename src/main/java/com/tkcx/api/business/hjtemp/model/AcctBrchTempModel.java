package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.utils.BigDecimalUtils;
import common.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
@TableName(value="ACCT_BRCH_TEMP",schema="QN_DB_ACCT")
public class AcctBrchTempModel extends Model<AcctBrchTempModel> {

    private static final long serialVersionUID = 1L;

	/**
	 * 机构总账标识
	 *
	 */
	@TableId(value = "ID",type = IdType.AUTO)
	private Integer id;

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
	 * 创建时间
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;

	 /**
	 * 币种
	 *
	 */
	@TableField(value="CURRENCY")
	private String currency;

	 /**
	 * 借贷标识
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
	 * 科目号控制字
	 *
	 */
	@TableField(value="ITEM_CTRL")
	private String itemCtrl;

	 /**
	 * 科目名称
	 *
	 */
	@TableField(value="ITEM_NAME")
	private String itemName;

	 /**
	 * 表内表外标识：0-表内1-表外
	 *
	 */
	@TableField(value="OFF_BALANCE_FLAG")
	private String offBalanceFlag;

	 /**
	 * 状态：1-正常2-删除
	 *
	 */
	@TableField(value="STATUS")
	private String status;

	 /**
	 * 本日借方发生额
	 *
	 */
	@TableField(value="TODAY_DEBIT_AMOUNT")
	private BigDecimal todayDebitAmount;

	 /**
	 * 本日借方余额
	 *
	 */
	@TableField(value="TODAY_DEBIT_BALANCE")
	private BigDecimal todayDebitBalance;

	 /**
	 * 本日借方笔数
	 *
	 */
	@TableField(value="TODAY_DEBIT_QUANTITIES")
	private Integer todayDebitQuantities;

	 /**
	 * 本日贷方发生额
	 *
	 */
	@TableField(value="TODAY_LOAN_AMOUNT")
	private BigDecimal todayLoanAmount;

	 /**
	 * 本日贷方余额
	 *
	 */
	@TableField(value="TODAY_LOAN_BALANCE")
	private BigDecimal todayLoanBalance;

	 /**
	 * 本日贷方笔数
	 *
	 */
	@TableField(value="TODAY_LOAN_QUANTITIES")
	private Integer todayLoanQuantities;

	 /**
	 * 昨日借方余额
	 *
	 */
	@TableField(value="YEST_DEBIT_BALANCE")
	private BigDecimal yestDebitBalance;

	 /**
	 * 昨日贷方余额
	 *
	 */
	@TableField(value="YEST_LOAN_BALANCE")
	private BigDecimal yestLoanBalance;

	public AcctBrchTempModel() {
	}

	public String getItemCode() {
		return itemCode.trim();
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode.trim();
	}

	public String getItemCtrl() {
		return itemCtrl.trim();
	}

	public void setItemCtrl(String itemCtrl) {
		this.itemCtrl = itemCtrl.trim();
	}

	/**
	 * 字符串拆分对象
	 * @param lineData 字符串内容
	 */
	public AcctBrchTempModel(String lineData) {

		if(StringUtils.isNotEmpty(lineData)){
			String[] columns = lineData.split("\\^@");
			if(columns!=null && columns.length == 16) {
				if (StringUtils.isNotEmpty(columns[0]))
					this.acctDate = DateUtil.parseDate(columns[0].trim(), "yyyyMMdd");
				this.acctOrg = columns[1].trim();
				this.itemCtrl = columns[2].trim();
				this.itemCode = columns[3].trim();
				this.itemName = columns[4].trim();
				this.offBalanceFlag = columns[5].trim();
				this.currency = columns[6].trim();
				this.yestDebitBalance = BigDecimalUtils.valueOf(columns[7].trim());
				this.yestLoanBalance = BigDecimalUtils.valueOf(columns[8].trim());
				if (StringUtils.isNotEmpty(columns[9]))
					this.todayDebitQuantities = Integer.valueOf(columns[9].trim());
				if (StringUtils.isNotEmpty(columns[10]))
					this.todayLoanQuantities = Integer.valueOf(columns[10].trim());
				this.todayDebitAmount = BigDecimalUtils.valueOf(columns[11].trim());
				this.todayLoanAmount = BigDecimalUtils.valueOf(columns[12].trim());
				this.todayDebitBalance = BigDecimalUtils.valueOf(columns[13].trim());
				this.todayLoanBalance = BigDecimalUtils.valueOf(columns[14].trim());
				this.status = columns[15].trim();
				//this.createDate = createDate;
				//this.debtFlag = columns[7];
			}
		}
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
