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
 * @Date 2019-08-15 17:50
 */
@Getter
@Setter
@TableName(value = "ASSET_INDIVIDUAL", schema = "QN_DB_BIZ")
public class AssetIndividualModel extends Model<AssetIndividualModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* 第二联系人和借款人的亲属关系
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_SECOND_RELATIVE_RELATION")
	private String assetIndividualSecondRelativeRelation;
	 /**
	* 第二联系人联系方式
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_SECOND_RELATIVE_TEL")
	private String assetIndividualSecondRelativeTel;
	 /**
	* AssetIndividualAssetItemNo
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ASSET_ITEM_NO")
	private String assetIndividualAssetItemNo;
	 /**
	* AssetIndividualId
	* 
	*/
	@TableId(value = "ASSET_INDIVIDUAL_ID",type = IdType.AUTO)
	private Integer assetIndividualId;
	 /**
	* 会员id（来自于paydayloan.oauth_user）
	* 
	*/
	@TableField(value="OAUTH_USER_ID")
	private Integer oauthUserId;
	 /**
	* 个人类型：borrow：借款人; repay:还款人
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_TYPE")
	private String assetIndividualType;
	 /**
	* 姓名
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_NAME")
	private String assetIndividualName;
	 /**
	* 身份证号码
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_IDNUM")
	private String assetIndividualIdnum;
	 /**
	* AssetIndividualIdVerifyTo
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ID_VERIFY_TO")
	private Date assetIndividualIdVerifyTo;
	 /**
	* AssetIndividualBirthday
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_BIRTHDAY")
	private Date assetIndividualBirthday;
	 /**
	* 身份证地址
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ID_ADDR")
	private String assetIndividualIdAddr;
	 /**
	* 身份类型(0-未知
	* 身份类型(0-未知,1-在职,2-在读,3-待业)
	*/
	@TableField(value="ASSET_INDIVIDUAL_ID_TYPE")
	private Integer assetIndividualIdType;
	 /**
	* 身份证地址邮编
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ID_POST_CODE")
	private String assetIndividualIdPostCode;
	 /**
	* 性别
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_GENDER")
	private String assetIndividualGender;
	 /**
	* 学历(0-未知
	* 学历(0-未知,1-博士研究生,2-硕士研究生,3-本科,4-大专,5-中专,6-技校,7-高中,8-初中,9-小学,10-文盲或半文盲)
	*/
	@TableField(value="ASSET_INDIVIDUAL_EDUCATION")
	private Integer assetIndividualEducation;
	 /**
	* 收入区间左值
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_INCOME_LFT")
	private Integer assetIndividualIncomeLft;
	 /**
	* 收入区间右值
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_INCOME_RGT")
	private Integer assetIndividualIncomeRgt;
	 /**
	* 信用类型(0-未知
	* 信用类型(0-未知,1-芝麻分,2-万象分,3-考拉分,4-星辰分,5-京东分,6-腾讯分)
	*/
	@TableField(value="ASSET_INDIVIDUAL_CREDIT_TYPE")
	private Integer assetIndividualCreditType;
	 /**
	* 信用分数
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_CREDIT_SCORE")
	private Integer assetIndividualCreditScore;
	 /**
	* 居住地
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_RESIDENCE")
	private String assetIndividualResidence;
	 /**
	* 单位名称
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_CORP_NAME")
	private String assetIndividualCorpName;
	 /**
	* 单位电话
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_CORP_TEL")
	private String assetIndividualCorpTel;
	 /**
	* 单位所属行业(0-未知
	* 单位所属行业(0-未知,1-政府机关/社会团体,2-军事/公检法,3-教育/科研,4-医院,5-公共事业/邮电通信/仓储运输物流,6-建筑业,7-传统制造业,8-互联网/其他高新技术行业,9-金融业,10-商业/贸易,11-餐饮/酒店/旅游,12-媒体/体育/娱乐,13-服务业,14-专业事务所,15-农林牧渔/自由职业/其他)
	*/
	@TableField(value="ASSET_INDIVIDUAL_CORP_TRADE")
	private Integer assetIndividualCorpTrade;
	 /**
	* 住宅电话
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_RESIDENCE_TEL")
	private String assetIndividualResidenceTel;
	 /**
	* 居住状况(0-未知
	* 居住状况(0-未知,1-自置,2-按揭,3-亲属楼宇,4-集体宿舍,5-租房,6-共有住宅,7-其他)
	*/
	@TableField(value="ASSET_INDIVIDUAL_RESIDENCE_STATUS")
	private Integer assetIndividualResidenceStatus;
	 /**
	* 工作地
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_WORKPLACE")
	private String assetIndividualWorkplace;
	 /**
	* 联系电话
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_TEL")
	private String assetIndividualTel;
	 /**
	* 婚姻状况(0-未知
	* 婚姻状况(0-未知,1-未婚,2-已婚,3-丧偶,4-离婚)
	*/
	@TableField(value="ASSET_INDIVIDUAL_MARRIAGE")
	private Integer assetIndividualMarriage;
	 /**
	* 职务(0-未知
	* 职务(0-未知,1-高级管理人员,2-中级管理人员,3-办公类员工,4-技术类员工,5-后勤类员工,6-操作类员工,7-服务类员工,8-销售类员工,9-其他类型员工)
	*/
	@TableField(value="ASSET_INDIVIDUAL_DUTY")
	private Integer assetIndividualDuty;
	 /**
	* 配偶姓名
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_MATE_NAME")
	private String assetIndividualMateName;
	 /**
	* 配偶联系方式
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_MATE_TEL")
	private String assetIndividualMateTel;
	 /**
	* 亲属姓名
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_RELATIVE_NAME")
	private String assetIndividualRelativeName;
	 /**
	* 和借款人的亲属关系
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_RELATIVE_RELATION")
	private String assetIndividualRelativeRelation;
	 /**
	* 亲属联系方式
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_RELATIVE_TEL")
	private String assetIndividualRelativeTel;
	 /**
	* 同事姓名
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_WORKMATE_NAME")
	private String assetIndividualWorkmateName;
	 /**
	* 同事联系方式
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_WORKMATE_TEL")
	private String assetIndividualWorkmateTel;
	 /**
	* 入学时间
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ENROLLMENT_TIME")
	private Date assetIndividualEnrollmentTime;
	 /**
	* 学校电话
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_SCHOOL_PLACE")
	private String assetIndividualSchoolPlace;
	 /**
	* 学校名称
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_SCHOOL_NAME")
	private String assetIndividualSchoolName;
	 /**
	* 个人在财务系统中的名称
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_ACCOUNT_NAME")
	private String assetIndividualAccountName;
	 /**
	* AssetIndividualCreateAt
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_CREATE_AT")
	private Date assetIndividualCreateAt;
	 /**
	* AssetIndividualCreateUserName
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_CREATE_USER_NAME")
	private String assetIndividualCreateUserName;
	 /**
	* AssetIndividualUpdateAt
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_UPDATE_AT")
	private Date assetIndividualUpdateAt;
	 /**
	* AssetIndividualUpdateUserName
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_UPDATE_USER_NAME")
	private String assetIndividualUpdateUserName;
	 /**
	* 第二联系人亲属姓名
	* 
	*/
	@TableField(value="ASSET_INDIVIDUAL_SECOND_RELATIVE_NAME")
	private String assetIndividualSecondRelativeName;

	@Override
	protected Serializable pkVal() {
                  return this.assetIndividualId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
