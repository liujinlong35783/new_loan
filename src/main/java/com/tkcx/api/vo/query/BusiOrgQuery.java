package com.tkcx.api.vo.query;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 机构轧帐单/机构流水查询
 *
 * @author by cuijh
 * @date 2019/10/8 17:26
 *
 */
@Getter
@Setter
public class BusiOrgQuery {

    /**
     * 机构号
     */
    private String orgCode;

    /**
     * 会计日期, yyyy-MM-dd格式
     */
    private String acctDate;

    public BusiOrgQuery(String queryStr) {
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                this.orgCode = columns[0];
                this.acctDate = columns[1];
            }
        }
    }

    public BusiOrgQuery() {
    }
}
