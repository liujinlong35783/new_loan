package com.tkcx.api.vo.query;


import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 *客户还款信息查询
 *
 * @author cuijh
 */
@Getter
@Setter
public class RefundReceiptQuery {

    /**
     * 户名
     *
     */
    private String refundName;

    /**
     * 贷款账号
     *
     */
    private String loanAccount;

    /**
     * 结算账号
     *
     */
    private String payAccount;

    /**
     * 查询时间段
     */
    private TimeSegment timeSegment;

    /**
     * 业务类型
     */
    private Integer busiType;

    public RefundReceiptQuery(String queryStr) {
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                this.refundName = columns[0];
                this.loanAccount = columns[1];
                this.payAccount = columns[2];
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
    public RefundReceiptQuery() {
    }
}
