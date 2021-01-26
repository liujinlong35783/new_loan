package com.tkcx.api.business.acctPrint.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author cuijh
 * @Date 2019-10-12 15:20
 */
@Getter
@Setter
@TableName(value = "ACCT_LOG", schema = "QN_DB_ACCT")
public class AcctLogModel extends Model<AcctLogModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* LogId
	* 
	*/
	@TableId(value = "LOG_ID",type = IdType.AUTO)
	private Integer logId;
	 /**
	* 日志类型：0-互金日志
	* 
	*/
	@TableField(value="LOG_TYPE")
	private Integer logType;
	 /**
	* 内容
	* 
	*/
	@TableField(value="CONTENT")
	private String content;
	 /**
	* 会计日期
	* 
	*/
	@TableField(value="ACCT_DATE")
	private Date acctDate;
	 /**
	* 流水号
	* 
	*/
	@TableField(value="SERIAL_NO")
	private String serialNo;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_DATE")
	private Date createDate;


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
