package com.tkcx.api.vo.query;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 客户借款信息查询
 *
 * @author cuijh
 */
@Getter
@Setter
public class LoanReceiptQuery {

    /**
     * 借款人
     */
    private String borrowerName;

    /**
     * 身份证号
     */
    private String borrowerIdnum;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 查询时间段
     */
    private TimeSegment timeSegment;

    /**
     * 业务类型
     */
    private Integer busiType;

    public LoanReceiptQuery(String queryStr) {
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                this.borrowerName = columns[0];
                this.borrowerIdnum = columns[1];
                this.contractNo = columns[2];
                this.timeSegment = new TimeSegment(columns[3], columns[4]);
            }
        }
    }

    /**
     * @param acctDate
     * @return 0-存量查询，1-实时查询
     */
    public Integer getQueryType(String acctDate) {
        String start = DateUtil.format(this.timeSegment.getStartDate(), "yyyy-MM-dd");
        String end = DateUtil.format(this.timeSegment.getEndDate(), "yyyy-MM-dd");
        if (start.equals(start) && end.equals(acctDate)) {
            return 1;
        }
        return 0;
    }
    public LoanReceiptQuery() {
    }
}
