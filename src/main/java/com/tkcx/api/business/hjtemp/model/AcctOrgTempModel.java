package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @Date 2019-08-23 14:49
 */
@Getter
@Setter
@TableName("ACCT_ORG_TEMP")
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

	/**
	 * 字符串拆分对象
	 * @param lineData 字符串内容
	 */
	public AcctOrgTempModel(String lineData) {
		if(StringUtils.isNotEmpty(lineData)){
			String[] columns = lineData.split("\\^@");
			if(columns!=null && columns.length == 4) {
				this.identifier = columns[0].trim();
				this.orgCode = columns[1].trim();
				this.orgName = columns[2].trim();
				this.status = columns[3].trim();
			}
		}
	}

	@Override
	protected Serializable pkVal() {
		return this.orgId;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
