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
 * @Date 2019-08-08 20:01
 */
@Getter
@Setter
@TableName(value="REPAY_ASSEMBLY_RECORD",schema="QN_DB_BIZ")
public class RepayAssemblyRecordModel extends Model<RepayAssemblyRecordModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* RepayAssemblyId
	* 
	*/
	@TableId(value = "REPAY_ASSEMBLY_ID",type = IdType.AUTO)
	private Integer repayAssemblyId;
	 /**
	* 网贷系统还款流水号(一次还款一个唯一号)
	* 
	*/
	@TableField(value="REPAY_SERIAL_NO")
	private String repaySerialNo;
	 /**
	* 资产编号
	* 
	*/
	@TableField(value="ASSET_NO")
	private String assetNo;
	 /**
	* 资产类型
	* 
	*/
	@TableField(value="ASSET_TYPE")
	private String assetType;
	 /**
	* 用户身份证
	* 
	*/
	@TableField(value="INDIVIDUAL_IDNUM")
	private String individualIdnum;
	 /**
	* 还款卡卡号
	* 
	*/
	@TableField(value="REPAY_CARD")
	private String repayCard;
	 /**
	* 还款卡种类（cardI
	* 还款卡种类（cardI,cardII,wxpay）
	*/
	@TableField(value="REPAY_CARD_CODE")
	private String repayCardCode;
	 /**
	* 还款状态：Repaying-还款中，success-还款完成
	* 还款状态：Repaying-还款中，success-还款完成,failed-还款失败
	*/
	@TableField(value="REPAY_STATUS")
	private String repayStatus;
	 /**
	* 还款结果：Partial-部分还款 Full-当期全部还款
	* 
	*/
	@TableField(value="REPAY_RESULT")
	private String repayResult;
	 /**
	* 主动还款: period_pack-当期打包 overdue_pack-逾期打包 clean-结清
	* 
	*/
	@TableField(value="REPAY_TYPE")
	private String repayType;
	 /**
	* 还款方式：BankCard-银行卡 WechatPay-微信支付 BankCarⅡ-二类账户
	* 
	*/
	@TableField(value="REPAY_WAY")
	private String repayWay;
	 /**
	* 应还款总金额
	* 
	*/
	@TableField(value="REPAY_AMOUNT")
	private Integer repayAmount;
	 /**
	* 本次实际还款金额
	* 
	*/
	@TableField(value="REPAY_REPAID_AMOUNT")
	private Integer repayRepaidAmount;
	 /**
	* 未还金额，部分还款的时候存在
	* 
	*/
	@TableField(value="REPAY_NOT_PAY_AMOUNT")
	private Integer repayNotPayAmount;
	 /**
	* 截至当前总共罚息
	* 
	*/
	@TableField(value="FINED_AMOUNT")
	private Integer finedAmount;
	 /**
	* 本次还款已还罚息
	* 
	*/
	@TableField(value="FINED_REPAID_AMOUNT")
	private Integer finedRepaidAmount;
	 /**
	* 未还罚息
	* 
	*/
	@TableField(value="FINED_NOT_PAY_AMOUNT")
	private Integer finedNotPayAmount;
	 /**
	* 优惠券编号
	* 
	*/
	@TableField(value="COUPON_ITEM_NO")
	private String couponItemNo;
	 /**
	* 实际本次还款能使用的优惠金额（分）
	* 
	*/
	@TableField(value="COUPON_AMOUNT")
	private Integer couponAmount;
	 /**
	* 优惠券票面金额(分)
	* 
	*/
	@TableField(value="COUPON_INIT_AMOUNT")
	private Integer couponInitAmount;
	 /**
	* 银行还款流水号
	* 
	*/
	@TableField(value="REPAY_BANK_SERIAL_NO")
	private String repayBankSerialNo;
	 /**
	* 外围流水号
	* 
	*/
	@TableField(value="REPAY_REQ_BANK_SERIAL_NO")
	private String repayReqBankSerialNo;
	 /**
	* 网贷系统代扣流水号(如果是代扣）
	* 
	*/
	@TableField(value="WITHHOLD_SERIAL_NO")
	private String withholdSerialNo;
	 /**
	* 还款完成时间
	* 
	*/
	@TableField(value="REPAY_FINISH_TIME")
	private Date repayFinishTime;
	 /**
	* 还款备注
	* 
	*/
	@TableField(value="REPAY_REMARK")
	private String repayRemark;
	 /**
	* CreateTime
	* 
	*/
	@TableField(value="CREATE_TIME")
	private Date createTime;
	 /**
	* UpdateTime
	* 
	*/
	@TableField(value="UPDATE_TIME")
	private Date updateTime;
	 /**
	* 资产历史记录id
	* 
	*/
	@TableField(value="ASSET_HISTORY_ID")
	private Integer assetHistoryId;
	 /**
	* 还款核心日期
	* 
	*/
	@TableField(value="CORE_SYS_DATE")
	private Date coreSysDate;

	@Override
	protected Serializable pkVal() {
         return this.repayAssemblyId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
