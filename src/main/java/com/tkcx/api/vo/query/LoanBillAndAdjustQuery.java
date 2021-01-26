package com.tkcx.api.vo.query;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 贷款分户账/明细账/贷款形态调整查询
 *
 * @author cuijh
 * @date 2019/10/8 17:34
 */
@Getter
@Setter
public class LoanBillAndAdjustQuery {

    /**
     * 机构号
     */
    private String orgCode;

    /**
     * 户名
     */
    private String loanName;

    /**
     * 贷款账号
     */
    private String loanAccount;

    /**
     * 身份证号
     */
    private String borrowerIdnum;

    /**
     * 本金状态
     */
    private Integer principalStatus;

    /**
     * 科目控制字
     */
    private String item;

    /**
     * 查询时间段
     */
    private TimeSegment timeSegment;

    /**
     * 5贷款分户账/6明细账/9贷款形态调整查询
     * @param queryStr
     * @param busiCode
     */
    public LoanBillAndAdjustQuery(String queryStr, Integer busiCode) {
        if(StringUtils.isNotEmpty(queryStr)) {
            String[] columns = queryStr.split("\\^@");
            if(columns!=null) {
                if(busiCode == 9){
                    this.loanAccount = columns[2];
                    this.item = columns[3];
                    this.timeSegment = new TimeSegment(columns[4], columns[5]);
                }else if(busiCode == 6){
                    this.borrowerIdnum = columns[2];
                    this.loanAccount = columns[3];
                    this.timeSegment = new TimeSegment(columns[4], columns[5]);
                }else if(busiCode==5){
                    this.borrowerIdnum = columns[2];
                    this.loanAccount = columns[3];
                    this.timeSegment = new TimeSegment(columns[4], columns[5]);
                    if(StringUtils.isNotEmpty(columns[6])){
                        this.principalStatus = Integer.valueOf(columns[6]);
                    }
                    this.item = columns[7];
                }

                if (StringUtils.isNotEmpty(columns[0])) {
                    this.orgCode = "'" + columns[0].replace(",", "','") + "'";
                }
                this.loanName = columns[1];
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
    public LoanBillAndAdjustQuery() {
    }
}
