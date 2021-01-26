package com.tkcx.api.business.wdData.model;

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
 * @Date 2019-08-08 20:09
 */
@Getter
@Setter
@TableName(value="CARDII",schema="QN_DB_BIZ")
public class CardiiModel extends Model<CardiiModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* CardiiId
	* 
	*/
	@TableId(value = "CARDII_ID",type = IdType.AUTO)
	private Integer cardiiId;
	 /**
	* 身份证号
	* 
	*/
	@TableField(value="CARDII_IDNUM")
	private String cardiiIdnum;
	 /**
	* 用户姓名
	* 
	*/
	@TableField(value="CARDII_NAME")
	private String cardiiName;
	 /**
	* 二类卡账号
	* 
	*/
	@TableField(value="CARDII_ACCOUNT")
	private String cardiiAccount;
	 /**
	* CardiiCoreAccountNo
	* 
	*/
	@TableField(value="CARDII_CORE_ACCOUNT_NO")
	private String cardiiCoreAccountNo;
	 /**
	* 绑定的一类卡
	* 
	*/
	@TableField(value="CARDII_BIND_CARD")
	private String cardiiBindCard;
	 /**
	* CardiiMobile
	* 
	*/
	@TableField(value="CARDII_MOBILE")
	private String cardiiMobile;
	 /**
	* 开户行号
	* 
	*/
	@TableField(value="CARDII_NODE_NO")
	private String cardiiNodeNo;
	 /**
	* 冻结金额
	* 
	*/
	@TableField(value="CARDII_FROZEN_AMOUNT")
	private Integer cardiiFrozenAmount;
	 /**
	* 版本号
	* 
	*/
	@TableField(value="CARDII_VERSION")
	private Integer cardiiVersion;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CARDII_CREATE_TIME")
	private Date cardiiCreateTime;
	 /**
	* 更新时间
	* 
	*/
	@TableField(value="CARDII_UPDATE_TIME")
	private Date cardiiUpdateTime;
	 /**
	* 归属机构号
	* 
	*/
	@TableField(value="CARDII_ORGID")
	private String cardiiOrgid;

	@Override
	protected Serializable pkVal() {
                  return this.cardiiId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
