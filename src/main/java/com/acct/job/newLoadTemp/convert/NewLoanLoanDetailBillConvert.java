package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.Loan.LoanDetailBillLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project：
 * @author： liujinlong
 * @description： 新网贷113717_MXZ_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanLoanDetailBillConvert {

    /**
     * 解析113717_MXZ_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<LoanDetailBillLoanModel> makeLoanDetailBillList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<LoanDetailBillLoanModel> loanDetailBillModelList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            LoanDetailBillLoanModel loanDetailBillModel = assembleLoanDetailBillTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(loanDetailBillModel == null){
                continue;
            }
            loanDetailBillModelList.add(loanDetailBillModel);
        }
        return loanDetailBillModelList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static LoanDetailBillLoanModel assembleLoanDetailBillTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.MXZ_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        LoanDetailBillLoanModel loanDetailBillModel = new LoanDetailBillLoanModel();

        loanDetailBillModel.setOrgName(buffers[1].toString());
        loanDetailBillModel.setOrgCode(buffers[2].toString());
        loanDetailBillModel.setDebtNo(buffers[3].toString());
        loanDetailBillModel.setLoanAccount(buffers[4].toString());
        loanDetailBillModel.setPayoffDate(DateUtil.parseDate(buffers[5].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanDetailBillModel.setPayoffPrincipal(buffers[6].toString());
        loanDetailBillModel.setPayoffInterest(buffers[7].toString());
        loanDetailBillModel.setValidDate(DateUtil.parseDate(buffers[8].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanDetailBillModel.setAmount(buffers[9].toString());
        loanDetailBillModel.setPrincipalBalance(buffers[10].toString());
        loanDetailBillModel.setGrantDate(DateUtil.parseDate(buffers[11].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanDetailBillModel.setGtantAmount(buffers[12].toString());
        loanDetailBillModel.setDebtFlag(Integer.valueOf(buffers[13].toString()));
//        loanDetailBillModel.setCreateDate(DateUtil.parseDate(buffers[14].toString(), "yyyy-MM-dd hh:mm:ss"));//预留创建时间字段
        loanDetailBillModel.setCreateDate(new Date());//预留创建时间字段
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[15])) {
            loanDetailBillModel.setAcctDate(DateUtil.parseDate(buffers[15].toString(), "yyyy-MM-dd"));
        }
        loanDetailBillModel.setContractNo(buffers[16].toString());
        loanDetailBillModel.setBorrowerIdnum(buffers[17].toString());
        loanDetailBillModel.setLoanName(buffers[18].toString());
        loanDetailBillModel.setChannel(buffers[19].toString());

        return loanDetailBillModel;
    }
}
