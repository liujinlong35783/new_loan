package com.tkcx.api.business.acctPrint.model;

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
 * @description：
 * @create： 2021/1/27 11:13
 */
@Getter
@Setter
@TableName(value="ACCT_SETTLE", schema = "QN_DB_ACCT")
public class AcctSettleModel extends Model<AcctSettleModel>  {


    private static final long serialVersionUID = 1L;


    /**
     * 主键
     *
     */
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;
    /**
     * 日期
     *
     */
    @TableField(value="SYS_DATE")
    private Date sysDate;

    /**
     * 更新日期
     */
    @TableField(value="UPDATE_DATE")
    private Date updateDate;

    /**
     * 系统日期
     */
    @TableField(value="SYSTEM_STATUS")
    private String systemStatus;
}
