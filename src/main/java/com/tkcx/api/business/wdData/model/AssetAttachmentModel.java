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
 * @Date 2019-08-27 19:55
 */
@Getter
@Setter
@TableName(value="ASSET_ATTACHMENT",schema="QN_DB_BIZ")
public class AssetAttachmentModel extends Model<AssetAttachmentModel> {

    private static final long serialVersionUID = 1L;

	 /**
	* AssetAttachmentId
	* 
	*/
	@TableId(value = "ASSET_ATTACHMENT_ID",type = IdType.AUTO)
	private Integer assetAttachmentId;
	 /**
	* AssetAttachmentType
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_TYPE")
	private String assetAttachmentType;
	 /**
	* AssetAttachmentTypeText
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_TYPE_TEXT")
	private String assetAttachmentTypeText;
	 /**
	* AssetAttachmentName
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_NAME")
	private String assetAttachmentName;
	 /**
	* AssetAttachmentUrl
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_URL")
	private String assetAttachmentUrl;
	 /**
	* 状态：1：正常；0：作废
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_STATUS")
	private Integer assetAttachmentStatus;
	 /**
	* 创建时间
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_CREATE_AT")
	private Date assetAttachmentCreateAt;
	 /**
	* 更新时间
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_UPDATE_AT")
	private Date assetAttachmentUpdateAt;
	 /**
	* AssetAttachmentContractCode
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_CONTRACT_CODE")
	private String assetAttachmentContractCode;
	 /**
	*  合同签约时间
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_CONTRACT_SIGN_AT")
	private Date assetAttachmentContractSignAt;
	 /**
	* AssetAttachmentAssetItemNo
	* 
	*/
	@TableField(value="ASSET_ATTACHMENT_ASSET_ITEM_NO")
	private String assetAttachmentAssetItemNo;

	@Override
	protected Serializable pkVal() {
                  return this.assetAttachmentId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
