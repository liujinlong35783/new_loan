package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgBillLoanModel;
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
 * @description： 新网贷113717_JGZZD_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanBusiOrgBillConvert {

    /**
     * 解析113717_JGZZD_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<BusiOrgBillLoanModel> makeBusiOrgBillModelList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<BusiOrgBillLoanModel> branchList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            BusiOrgBillLoanModel brchModeal = assembleBusiOrgBillTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
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
    public static BusiOrgBillLoanModel assembleBusiOrgBillTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.JGZZD_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        BusiOrgBillLoanModel busiOrgBillModel = new BusiOrgBillLoanModel();
        busiOrgBillModel.setOrgName(buffers[1].toString());
        busiOrgBillModel.setOrgCode(buffers[2].toString());
        busiOrgBillModel.setBusiDate(DateUtil.parseDate(buffers[3].toString(), "yyyy-MM-dd"));
        busiOrgBillModel.setItemCode(buffers[4].toString());
        busiOrgBillModel.setFlag(buffers[5].toString());
        busiOrgBillModel.setDebtNum(Double.valueOf(buffers[6].toString()).intValue());
        busiOrgBillModel.setDebtAmount(buffers[7].toString());
        busiOrgBillModel.setCreditNum(Double.valueOf(buffers[8].toString()).intValue());
        busiOrgBillModel.setCreditAmount(buffers[9].toString());
//        busiOrgBillModel.setCreateDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd hh:mm:ss"));
        busiOrgBillModel.setCreateDate(new Date());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[11])) {
            busiOrgBillModel.setAcctDate(DateUtil.parseDate(buffers[11].toString(), "yyyy-MM-dd"));
        }
        busiOrgBillModel.setChannel(buffers[12].toString());
        return busiOrgBillModel;
    }
}



