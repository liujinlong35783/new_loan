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
@TableName(value="ASSET_GRANT_RECORD",schema="QN_DB_BIZ")
public class AssetGrantRecordModel extends Model<AssetGrantRecordModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* CoreSysDate
	* 
	*/
	@TableField(value="CORE_SYS_DATE")
	private Date coreSysDate;
	 /**
	* 返回交易流水号
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_RSP_TX")
	private String assetGrantRecordRspTx;
	 /**
	* 实际交易时间
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_TX_TIME")
	private Date assetGrantRecordTxTime;
	 /**
	* 放款金额，即本金，单位：分
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_AMOUNT")
	private Integer assetGrantRecordAmount;
	 /**
	* AssetGrantRecordStartDate
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_START_DATE")
	private Date assetGrantRecordStartDate;
	 /**
	* AssetGrantRecordEndDate
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_END_DATE")
	private Date assetGrantRecordEndDate;
	 /**
	* AssetGrantRecordLoadAccount
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_LOAD_ACCOUNT")
	private String assetGrantRecordLoadAccount;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_CREATE_AT")
	private Date assetGrantRecordCreateAt;
	 /**
	* 借据号，还款计划使用
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_DEBT_NO")
	private String assetGrantRecordDebtNo;
	 /**
	* 放款状态
	* 放款状态,grant-放款中，succeed-放款成功，fail-放款失败
	*/
	@TableField(value="ASSET_GRANT_RECORD_STATUS")
	private String assetGrantRecordStatus;
	 /**
	* 主键
	* 
	*/
	@TableId(value = "ASSET_GRANT_RECORD_ID",type = IdType.AUTO)
	private Integer assetGrantRecordId;
	 /**
	* 资产编号
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_ASSET_ITEM_NO")
	private String assetGrantRecordAssetItemNo;
	 /**
	* AssetGrantRecordCapitalOrder
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_CAPITAL_ORDER")
	private String assetGrantRecordCapitalOrder;
	 /**
	* 请求交易流水号
	* 
	*/
	@TableField(value="ASSET_GRANT_RECORD_REQ_TX")
	private String assetGrantRecordReqTx;

	@Override
	protected Serializable pkVal() {
                  return this.assetGrantRecordId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
