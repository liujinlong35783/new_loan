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
 * @Date 2019-08-23 14:49
 */
@Getter
@Setter
@TableName(value = "ACCT_ORG_TEMP" ,schema="QN_DB_LOAN")
public class AcctOrgTempModel extends Model<AcctOrgTempModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 机构主键
	* 
	*/
	@TableId(value="ORG_ID",type = IdType.AUTO)
	private Integer orgId;
	/**
	 * 行别
	 */
	@TableField(value="IDENTIFIER")
	private String identifier;
	 /**
	* 机构代码
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
	 * 状态：1-有效2-无效
	 *
	 */
	@TableField(value="STATUS")
	private String status;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_DATE")
	private Date createDate;

	public AcctOrgTempModel() {
	}


	@Override
	protected Serializable pkVal() {
		return this.orgId;
	}

	@Override
	public String toString() {
		return "AcctOrgTempModel{" +
				"orgId=" + orgId +
				", identifier='" + identifier + '\'' +
				", orgCode='" + orgCode + '\'' +
				", orgName='" + orgName + '\'' +
				", status='" + status + '\'' +
				", createDate=" + createDate +
				'}';
	}


}
