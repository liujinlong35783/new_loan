package com.acct.job.newLoadTemp.convert;


import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import com.tkcx.api.utils.BigDecimalUtils;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @project：
 * @author： liujinlong
 * @description： 新网贷113717_HKHD_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanLoanReceiptConvert {

    /**
     * 解析113717_HKHD_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<LoanReceiptModel> makeLoanReceiptList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<LoanReceiptModel> loanReceiptModelList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            LoanReceiptModel loanReceiptModel = assembleLoanReceiptTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(loanReceiptModel == null){
                continue;
            }
            loanReceiptModelList.add(loanReceiptModel);
        }
        return loanReceiptModelList  ;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static LoanReceiptModel assembleLoanReceiptTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.JJHD_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        LoanReceiptModel loanReceiptModel = new LoanReceiptModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            loanReceiptModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        loanReceiptModel.setBorrowerName(buffers[1].toString());
        loanReceiptModel.setBorrowerIdnum(buffers[2].toString());
        loanReceiptModel.setBorrowerAddr(buffers[3].toString());
        loanReceiptModel.setLoanType(Integer.valueOf(buffers[4].toString()));
        loanReceiptModel.setLoanAccount(buffers[5].toString());
        loanReceiptModel.setReceiverAccount(buffers[6].toString());
        loanReceiptModel.setLoanUsage(buffers[7].toString());
        loanReceiptModel.setInterestRate(BigDecimalUtils.valueOf(buffers[8].toString()));
        loanReceiptModel.setPayoffType(Integer.valueOf(buffers[9].toString()));
        loanReceiptModel.setLoanDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanReceiptModel.setDueDate(DateUtil.parseDate(buffers[11].toString(), "yyyy-MM-dd"));
        loanReceiptModel.setContractNo(buffers[12].toString());
        loanReceiptModel.setLoanAmount(buffers[13].toString());
        loanReceiptModel.setCreateDate(DateUtil.parseDate(buffers[14].toString(), "yyyy-MM-dd hh:mm:ss"));
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[15])) {
            loanReceiptModel.setAcctDate(DateUtil.parseDate(buffers[15].toString(), "yyyy-MM-dd"));
        }
        loanReceiptModel.setOrgName(buffers[16].toString());
        loanReceiptModel.setOrgCode(buffers[17].toString());

        return loanReceiptModel;
    }
}
