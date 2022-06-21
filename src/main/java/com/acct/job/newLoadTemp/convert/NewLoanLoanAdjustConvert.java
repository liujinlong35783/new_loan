package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @project：
 * @author： liujinlong
 * @description： 新网贷113717_XTTZ_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanLoanAdjustConvert {

    /**
     * 解析113717_XTTZ_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<LoanAdjustModel> makeLoanAdjustList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<LoanAdjustModel> branchList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            LoanAdjustModel brchModeal = assembleLoanAdjustTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(brchModeal == null){
                continue;
            }
            branchList.add(brchModeal);
        }
        return branchList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static LoanAdjustModel assembleLoanAdjustTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.XTTZ_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        LoanAdjustModel loanAdjustModel = new LoanAdjustModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            loanAdjustModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        loanAdjustModel.setLoanName(buffers[1].toString());
        loanAdjustModel.setDebtNo(buffers[2].toString());
        loanAdjustModel.setGrantDate(DateUtil.parseDate(buffers[3].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAdjustModel.setDueDate(DateUtil.parseDate(buffers[4].toString(), "yyyy-MM-dd"));
        loanAdjustModel.setOriginalItemName(buffers[5].toString());
        loanAdjustModel.setOriginalItemCode(buffers[6].toString());
        loanAdjustModel.setNewItemName(buffers[7].toString());
        loanAdjustModel.setNewItemCode(buffers[8].toString());
        loanAdjustModel.setBalanceAmount(buffers[9].toString());
        loanAdjustModel.setAdjustDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd hh:mm:ss"));
        loanAdjustModel.setNewItemCode(buffers[11].toString());//预留科目控制字
        loanAdjustModel.setNewItemCode(buffers[12].toString());//预料创建时间
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[13])) {
            loanAdjustModel.setAcctDate(DateUtil.parseDate(buffers[13].toString(), "yyyy-MM-dd"));
        }
        loanAdjustModel.setNewLoanFlag("1");
        return loanAdjustModel;
    }
}
