package com.tkcx.api.business.hjtemp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description： 互金文件信息表
 * @create： 2021/2/7 16:08
 */
@Getter
@Setter
@TableName(value="HJ_FILE_INFO",schema="QN_DB_ACCT")
public class HjFileInfoModel extends Model<HjFileInfoModel> {

    /**
     * 文件id
     */
    @TableId(value = "FILE_ID",type = IdType.AUTO)
    private int fileId;

    /**
     * 文件类型
     */
    @TableField(value = "FILE_TYPE")
    private String fileType;

    /**
     * 文件名称
     */
    @TableField(value = "FILE_NAME")
    private String fileName;

    /**
     * 文件传输代码
     */
    @TableField(value = "FILE_TRANS_CODE")
    private String fileTransCode;

    /**
     * 文件路径
     */
    @TableField(value = "FILE_PATH")
    private String filePath;
    /**
     * 文件日期
     */
    @TableField(value = "FILE_DATE")
    private Date  fileDate;

    /**
     * 删除标识
     */
    @TableField(value = "DELETE_FLAG")
    private String deleteFlag;

    @TableField(value="CREATE_DATE")
    private Date createDate;

}
