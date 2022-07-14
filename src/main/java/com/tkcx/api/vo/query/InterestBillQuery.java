package com.tkcx.api.vo.query;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 贷款利息登记簿查询
 *
 * @author cuijh
 * @date 2019/10/8 17:38
 */
@Getter
@Setter
public class InterestBillQuery {

    /**
     * 机构号
     */
    private String orgCode;

    /**
     * 户名
     */
    private String loanName;

    /**
     * 身份证号
     */
    private String borrowerIdnum;

    /**
     * 贷款账号
     */
    private String loanAccount;

    /**
     * 结清标识
     */
    private Integer payoffFlag;

    /**
     * 查询时间段
     */
    private TimeSegment timeSegment;

    public InterestBillQuery(String queryStr) {
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                if (StringUtils.isNotEmpty(columns[0])) {
                    this.orgCode = columns[0].replace(",", "','") ;
//                    this.orgCode = "'" + columns[0].replace(",", "','") + "'";
                }
                this.loanName = columns[1];
                this.borrowerIdnum = columns[2];
                this.loanAccount = columns[3];
                this.timeSegment = new TimeSegment(columns[4],columns[5]);
                if(StringUtils.isNotEmpty(columns[6])){
                    this.payoffFlag = Integer.valueOf(columns[6].trim());
                }
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
    public InterestBillQuery() {
    }
}
