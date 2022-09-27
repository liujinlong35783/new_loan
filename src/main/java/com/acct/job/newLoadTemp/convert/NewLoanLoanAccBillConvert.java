package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanTempModel;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import com.tkcx.api.utils.BigDecimalUtils;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project：
 * @author： liujinlong
 * @description： 新网贷113717_FHZ_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanLoanAccBillConvert {

    /**
     * 解析113717_FHZ_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<LoanAccBillLoanTempModel> makeLoanAccBillList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<LoanAccBillLoanTempModel> loanAccBillModelList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
          LoanAccBillLoanTempModel loanAccBillLoanTempModel = assembleLoanAccBillLoanTemp(lines.get(lineNum).toString(), readStartNum, lineNum);

            if(loanAccBillLoanTempModel == null){
                continue;
            }
            loanAccBillModelList.add(loanAccBillLoanTempModel);
        }
        return loanAccBillModelList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static LoanAccBillLoanModel assembleLoanAccBillTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.FHZ_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        LoanAccBillLoanModel loanAccBillModel = new LoanAccBillLoanModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            loanAccBillModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        loanAccBillModel.setOrgName(buffers[1].toString());
        loanAccBillModel.setOrgCode(buffers[2].toString());
        loanAccBillModel.setItem(buffers[3].toString());
        loanAccBillModel.setLoanName(buffers[4].toString());
        loanAccBillModel.setDebtNo(buffers[5].toString());
        loanAccBillModel.setGrantDate(DateUtil.parseDate(buffers[6].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAccBillModel.setAccountStatus(Integer.valueOf(buffers[7].toString()));
        loanAccBillModel.setGrantAccount(buffers[8].toString());
        loanAccBillModel.setPayoffAccount(buffers[9].toString());
        loanAccBillModel.setDueDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd"));
        loanAccBillModel.setActualRate(BigDecimalUtils.valueOf(buffers[11].toString()));
        loanAccBillModel.setGtantAmount(buffers[12].toString());
        loanAccBillModel.setBalanceAmount(buffers[13].toString());
        loanAccBillModel.setPrincipalStatus(Integer.valueOf(buffers[14].toString()));
        loanAccBillModel.setPayoffDate(DateUtil.parseDate(buffers[15].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAccBillModel.setPayoffAmount(buffers[16].toString());
        loanAccBillModel.setDebtFlag(Integer.valueOf(buffers[17].toString()));
        loanAccBillModel.setCreateDate(DateUtil.parseDate(buffers[18].toString(), "yyyy-MM-dd hh:mm:ss"));//预留创建时间字段
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[19])) {
            loanAccBillModel.setAcctDate(DateUtil.parseDate(buffers[19].toString(), "yyyy-MM-dd"));
        }
        loanAccBillModel.setBorrowerIdnum(buffers[20].toString());
        loanAccBillModel.setLoanAccount(buffers[21].toString());
        loanAccBillModel.setChannel(buffers[22].toString());

        return loanAccBillModel;
    }


    /**
     * 贷款分户账组装数据入临时表
     * @param lineStr
     * @return
     */
    public static LoanAccBillLoanTempModel assembleLoanAccBillLoanTemp(String lineStr, int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.FHZ_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        LoanAccBillLoanTempModel loanAccBillTempModel = new LoanAccBillLoanTempModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            loanAccBillModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        loanAccBillTempModel.setOrgName(buffers[1].toString());
        loanAccBillTempModel.setOrgCode(buffers[2].toString());
        loanAccBillTempModel.setItem(buffers[3].toString());
        loanAccBillTempModel.setLoanName(buffers[4].toString());
        loanAccBillTempModel.setDebtNo(buffers[5].toString());
        loanAccBillTempModel.setGrantDate(DateUtil.parseDate(buffers[6].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAccBillTempModel.setAccountStatus(Integer.valueOf(buffers[7].toString()));
        loanAccBillTempModel.setGrantAccount(buffers[8].toString());
        loanAccBillTempModel.setPayoffAccount(buffers[9].toString());
        loanAccBillTempModel.setDueDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd"));
        loanAccBillTempModel.setActualRate(BigDecimalUtils.valueOf(buffers[11].toString()));
        loanAccBillTempModel.setGtantAmount(buffers[12].toString());
        loanAccBillTempModel.setBalanceAmount(buffers[13].toString());
        loanAccBillTempModel.setPrincipalStatus(Integer.valueOf(buffers[14].toString()));
        loanAccBillTempModel.setPayoffDate(DateUtil.parseDate(buffers[15].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAccBillTempModel.setPayoffAmount(buffers[16].toString());
        loanAccBillTempModel.setDebtFlag(Integer.valueOf(buffers[17].toString()));
//        loanAccBillTempModel.setCreateDate(DateUtil.parseDate(buffers[18].toString(), "yyyy-MM-dd hh:mm:ss"));//预留创建时间字段
        loanAccBillTempModel.setCreateDate(new Date());//预留创建时间字段
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[19])) {
            loanAccBillTempModel.setAcctDate(DateUtil.parseDate(buffers[19].toString(), "yyyy-MM-dd"));
        }
        loanAccBillTempModel.setBorrowerIdnum(buffers[20].toString());
//        loanAccBillTempModel.setLoanAccount(buffers[21].toString());
        loanAccBillTempModel.setChannel(buffers[21].toString());
        //新希望改
        loanAccBillTempModel.setLoanAccount(buffers[22].toString());

        return loanAccBillTempModel;
    }
}
