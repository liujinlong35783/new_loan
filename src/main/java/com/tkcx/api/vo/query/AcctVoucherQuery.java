package com.tkcx.api.vo.query;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 记账凭证查询
 *
 * @author cuijh
 * @date 2019/10/8 17:40
 */
@Getter
@Setter
public class AcctVoucherQuery {

    /**
     * 机构号
     */
    private String orgCode;

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 查询时间段
     */
    private TimeSegment timeSegment;

    /**
     * 户名
     */
    private String loanName;

    /**
     * 身份证号
     */
    private String borrowerIdnum;

    /**
     * 业务类型
     */
    private Integer busiType;

    public AcctVoucherQuery(String queryStr){
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                this.orgCode = columns[0];
                this.serialNo = columns[1];
                this.timeSegment = new TimeSegment(columns[2], columns[3]);
/*                this.loanName = columns[2];
                this.borrowerIdnum = columns[3];
                this.timeSegment = new TimeSegment(columns[4], columns[5]);*/
            }
        }
    }

    public AcctVoucherQuery() {
    }
}
