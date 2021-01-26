package com.tkcx.api.business.acctPrint.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-10-31 11:50
 */
@Getter
@Setter
@TableName(value = "VOUCHER_PRINT", schema = "QN_DB_ACCT")
public class VoucherPrintModel extends Model<VoucherPrintModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* PrintId
	* 
	*/
	@TableId(value = "PRINT_ID",type = IdType.AUTO)
	private Integer printId;
	 /**
	* 查询编号
	* 
	*/
	@TableField(value="QUERY_NO")
	private String queryNo;
	 /**
	* 唯一凭证标识
	* 
	*/
	@TableField(value="SERIAL_NO")
	private String serialNo;
	 /**
	* 凭证类型
	* 
	*/
	@TableField(value="BUSI_TYPE")
	private Integer busiType;
	 /**
	* 打印次数
	* 
	*/
	@TableField(value="PRINT_COUNT")
	private Integer printCount;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_DATE")
	private Date createDate;
	 /**
	* 更新时间
	* 
	*/
	@TableField(value="UPDATE_DATE")
	private Date updateDate;
	 /**
	* Spare1
	* 
	*/
	@TableField(value="SPARE1")
	private String spare1;
	 /**
	* Spare2
	* 
	*/
	@TableField(value="SPARE2")
	private String spare2;


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
