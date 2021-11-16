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

    private static final long serialVersionUID = 6037098408365993942L;

    /**
     * 文件id
     */
    @TableId(value = "FILE_ID",type = IdType.AUTO)
    private Integer fileId;

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

    /**
     * 创建日期
     */
    @TableField(value="CREATE_DATE")
    private Date createDate;

    /**
     * 读取标识
     */
    @TableField(value = "READ_FLAG")
    private String readFlag;

    /**
     * 文件下载路径
     */
    @TableField(value = "FILE_DOWNLOAD_PATH")
    private String fileDownloadPath;

    /**
     * 下一次读取的开始行数
     */
    @TableField(value="NEXT_READ_START_NUM")
    private Integer nextReadStartNum;

    /**
     * 下一次读取的结束行数
     */
    @TableField(value="NEXT_READ_END_NUM")
    private Integer nextReadEndNum;


    @TableField(value = "FILE_LINE_TOTAL_NUM")
    private Integer fileLineTotalNum;

    @Override
    public String toString() {
        return "HjFileInfoModel{" +
                "fileId=" + fileId +
                ", fileType='" + fileType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileTransCode='" + fileTransCode + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileDate=" + fileDate +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", createDate=" + createDate +
                ", readFlag='" + readFlag + '\'' +
                ", fileDownloadPath='" + fileDownloadPath + '\'' +
                ", nextReadStartNum=" + nextReadStartNum +
                ", nextReadEndNum=" + nextReadEndNum +
                ", fileLineTotalNum=" + fileLineTotalNum +
                '}';
    }


}
