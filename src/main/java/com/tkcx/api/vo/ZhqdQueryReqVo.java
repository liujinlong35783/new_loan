package com.tkcx.api.vo;

import com.tkcx.api.vo.callback.ServiceRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 接收综合前端请求
 *
 * @author tianyi
 *
 */
@Getter
@Setter
@ToString
public class ZhqdQueryReqVo extends ServiceRequestVo {

    /**
     * 1-客户借款信息2-客户还款信息3-机构扎账单4-机构流水5-贷款科目分户账6-贷款明细账7-贷款利息登记簿8-电子凭证9-贷款形态调整登记簿
     */
    private Integer interfaceIden;//接口标识

    private String queryContent;//查询条件字符拼接串

    private Integer printFlag;//打印标识 是-1，否-0

    private String queryNo;//查询号

    private String orgName;//机构名称

    @Override
    public void withMap(Map<String, Object> map) {
        this.interfaceIden = Integer.valueOf((String) map.get("QryTp"));
        this.queryContent = StringUtils.trim((String) map.get("FileCntnt"));
        this.printFlag = Integer.valueOf((String) map.get("PrtFlg"));
        this.queryNo = StringUtils.trim((String) map.get("BtchNo"));
        if(StringUtils.isNotEmpty(queryContent)){
            String[] columns = queryContent.split("\\^@");
            this.orgName = columns[columns.length-1];
        }
    }
}
