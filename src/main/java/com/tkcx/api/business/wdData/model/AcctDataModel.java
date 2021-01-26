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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author tianyi
 * @Date 2019-08-08 20:07
 */
@Getter
@Setter
@TableName(value="ACCT_DATA",schema="QN_DB_BIZ")
public class AcctDataModel extends Model<AcctDataModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 更新时间
	* 
	*/
	@TableField(value="UPDATE_AT")
	private Date updateAt;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="CREATE_AT")
	private Date createAt;
	 /**
	* 账务数据
	* 
	*/
	@TableField(value="MESSAGE")
	private String message;
	 /**
	* 归属机构号
	* 
	*/
	@TableField(value="ORGID")
	private String orgid;
	 /**
	* Id
	* 
	*/
	@TableId(value = "ID",type = IdType.AUTO)
	private Integer id;
	 /**
	* 发送状态：0-成功，1-失败，2-利息计提类，3-重发
	* 
	*/
	@TableField(value="RET_STATUS")
	private String retStatus;
	 /**
	* BizTrackNo
	* 
	*/
	@TableField(value="BIZ_TRACK_NO")
	private String bizTrackNo;
	 /**
	* TransSeqNo
	* 
	*/
	@TableField(value="TRANS_SEQ_NO")
	private String transSeqNo;
	 /**
	* AcgDt
	* 
	*/
	@TableField(value="ACG_DT")
	private String acgDt;
	 /**
	* AssetItemNo
	* 
	*/
	@TableField(value="ASSET_ITEM_NO")
	private String assetItemNo;
	 /**
	* RepayPlanNo
	* 
	*/
	@TableField(value="REPAY_PLAN_NO")
	private String repayPlanNo;
	 /**
	* Scene
	* 
	*/
	@TableField(value="SCENE")
	private String scene;
	 /**
	* ProductCode
	* 
	*/
	@TableField(value="PRODUCT_CODE")
	private String productCode;
	 /**
	* NormalPrincipal
	* 
	*/
	@TableField(value="NORMAL_PRINCIPAL")
	private BigDecimal normalPrincipal;
	 /**
	* OverduePrincipal
	* 
	*/
	@TableField(value="OVERDUE_PRINCIPAL")
	private BigDecimal overduePrincipal;
	 /**
	* IdlePrincipal
	* 
	*/
	@TableField(value="IDLE_PRINCIPAL")
	private BigDecimal idlePrincipal;
	 /**
	* BadPrincipal
	* 
	*/
	@TableField(value="BAD_PRINCIPAL")
	private BigDecimal badPrincipal;
	 /**
	* WriteoffPrincipal
	* 
	*/
	@TableField(value="WRITEOFF_PRINCIPAL")
	private BigDecimal writeoffPrincipal;

	@Override
	protected Serializable pkVal() {
                  return this.id;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
