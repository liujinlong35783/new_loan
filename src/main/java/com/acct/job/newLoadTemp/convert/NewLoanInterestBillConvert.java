package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.InterestBillModel;
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
 * @description： 新网贷113717_LXDJB_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanInterestBillConvert {

    /**
     * 解析113717_LXDJB_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<InterestBillModel> makeInterestBillList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<InterestBillModel> interestBillModelList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            InterestBillModel interestBillModel = assembleInterestBillTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(interestBillModel == null){
                continue;
            }
            interestBillModelList.add(interestBillModel);
        }
        return interestBillModelList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static InterestBillModel assembleInterestBillTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.LXDJB_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        InterestBillModel interestBillModel = new InterestBillModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            interestBillModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        interestBillModel.setOrgName(buffers[1].toString());
        interestBillModel.setOrgCode(buffers[2].toString());
        interestBillModel.setLoanAccount(buffers[3].toString());
        interestBillModel.setDebtNo(buffers[4].toString());
        interestBillModel.setPayoffDate(DateUtil.parseDate(buffers[5].toString(), "yyyy-MM-dd hh:mm:ss"));
        interestBillModel.setPayoffFlag(Integer.valueOf(buffers[6].toString()));
        interestBillModel.setShouldPrincipal(buffers[7].toString());
        interestBillModel.setShouldNormalInterest(buffers[8].toString());
        interestBillModel.setShouldOverdueInterest(buffers[9].toString());
        interestBillModel.setShouldSum(buffers[10].toString());
        interestBillModel.setPayoffPrincipal(buffers[11].toString());
        interestBillModel.setPayoffNormalInterest(buffers[12].toString());
        interestBillModel.setPayfoffOverdueInterest(buffers[13].toString());
        interestBillModel.setPayoffSum(buffers[14].toString());
        interestBillModel.setAmount(buffers[15].toString());
        interestBillModel.setDebtFlag(Integer.valueOf(buffers[16].toString()));
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[17])) {
            interestBillModel.setCreateDate(DateUtil.parseDate(buffers[17].toString(), "yyyy-MM-dd hh:mm:ss"));//预留创建时间字段
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[18])) {
            interestBillModel.setAcctDate(DateUtil.parseDate(buffers[18].toString(), "yyyy-MM-dd"));
        }
        interestBillModel.setBorrowerIdnum(buffers[19].toString());
        interestBillModel.setLoanName(buffers[20].toString());
        interestBillModel.setContractNo(buffers[21].toString());
        interestBillModel.setNewLoanFlag("1");
        return interestBillModel;
    }
}
