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
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Getter
@Setter
@TableName(value="ACCT_BUSI_CODE",schema="QN_DB_ACCT")
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
	/**
	 * 字符串拆分对象
	 * @param lineData 字符串内容
	 */
	public AcctBusiCodeModel(String lineData) {

		if(StringUtils.isNotEmpty(lineData)){
			String[] columns = lineData.split("\\^@");
			if(columns!=null && columns.length == 8) {
				this.identifier = columns[0].trim();
				this.busiCode = columns[1].trim();
				this.busiName = columns[2].trim();
				this.balanceIdentifier = columns[3].trim();
				this.balanceName = columns[4].trim();
				this.itemCtrl = columns[5].trim();
				this.itemName = columns[6].trim();
				this.status = columns[7].trim();
			}
		}
	}

	@Override
	protected Serializable pkVal() {
		return this.codeId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
