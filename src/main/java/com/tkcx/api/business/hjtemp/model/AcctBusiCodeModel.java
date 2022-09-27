package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Getter
@Setter
@TableName(value="ACCT_BUSI_CODE",schema="QN_DB_LOAN")
public class AcctBusiCodeModel extends Model<AcctBusiCodeModel> {

    private static final long serialVersionUID = 1L;

	/**
	 * 编码标识
	 *
	 */
	@TableId(value = "CODE_ID",type = IdType.AUTO)
	private Integer codeId;
	/**
	 * 行别
	 */
	@TableField(value="IDENTIFIER")
	private String identifier;
	 /**
	 * 余额标识
	 *
	 */
	@TableField(value="BALANCE_IDENTIFIER")
	private String balanceIdentifier;

	 /**
	 * 余额标识名称
	 *
	 */
	@TableField(value="BALANCE_NAME")
	private String balanceName;

	 /**
	 * 业务代码
	 *
	 */
	@TableField(value="BUSI_CODE")
	private String busiCode;

	 /**
	 * 业务名称
	 *
	 */
	@TableField(value="BUSI_NAME")
	private String busiName;

	 /**
	 * 创建时间
	 *
	 */
	@TableField(value="CREATE_DATE")
	private Date createDate;

	 /**
	 * 科目控制字
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
	 * 状态：1-有效2-无效
	 *
	 */
	@TableField(value="STATUS")
	private String status;

	public AcctBusiCodeModel() {
	}

	@Override
	protected Serializable pkVal() {
		return this.codeId;
	}

	@Override
	public String toString() {
		return "AcctBusiCodeModel{" +
				"codeId=" + codeId +
				", identifier='" + identifier + '\'' +
				", balanceIdentifier='" + balanceIdentifier + '\'' +
				", balanceName='" + balanceName + '\'' +
				", busiCode='" + busiCode + '\'' +
				", busiName='" + busiName + '\'' +
				", createDate=" + createDate +
				", itemCtrl='" + itemCtrl + '\'' +
				", itemName='" + itemName + '\'' +
				", status='" + status + '\'' +
				'}';
	}


}
