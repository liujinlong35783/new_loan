package com.tkcx.api.business.hjtemp.convert;

import com.tkcx.api.business.hjtemp.model.XinLoanAcctDetailTempModel;
import com.tkcx.api.business.hjtemp.utils.AcctStringUtils;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.utils.BigDecimalUtils;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description： 互金t_act_one_detail文件转换类
 * @create： 2021/10/23 11:23
 */
@Slf4j
@Component
public class XinLoanAcctDetailConvert {


    /**
     * 解析互金t_act_one_detail文件
     * @param path
     * @return
     */
    public static List<XinLoanAcctDetailTempModel> makeXinLoanAcctDetailList(String path, int readStartNum, int readEndNum){

        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<XinLoanAcctDetailTempModel> acctDetailList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            XinLoanAcctDetailTempModel detailModel = assembleDetailTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(detailModel == null){
                continue;
            }
            acctDetailList.add(detailModel);
        }
        return acctDetailList;
    }

    /**
     * 组装会计分录信息
     * @param lineStr
     * @return
     */
    public static XinLoanAcctDetailTempModel assembleDetailTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                HjFileFlagConstant.XIN_LOAN_ACCT_DETAIL_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }
        XinLoanAcctDetailTempModel xinLoanAcctDetailTempModel = new XinLoanAcctDetailTempModel();
        xinLoanAcctDetailTempModel.setIdentifier(buffers[0].toString());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[1].toString())) {
            xinLoanAcctDetailTempModel.setChannelDate(DateUtil.parseDate(buffers[1].toString(), "yyyyMMdd"));
        }
        xinLoanAcctDetailTempModel.setChannelSeq(buffers[2].toString());
        xinLoanAcctDetailTempModel.setChannelWay(buffers[3].toString());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[4])) {
            xinLoanAcctDetailTempModel.setAcctDate(DateUtil.parseDate(buffers[4].toString(), "yyyyMMdd"));
        }
        xinLoanAcctDetailTempModel.setAcctSeq(buffers[5].toString());
        xinLoanAcctDetailTempModel.setServiceCode(buffers[6].toString());
        xinLoanAcctDetailTempModel.setServiceName(buffers[7].toString());
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[8])) {
            xinLoanAcctDetailTempModel.setSerialNo(Long.valueOf(buffers[8].toString()));
        }
        xinLoanAcctDetailTempModel.setAcctOrg(buffers[9].toString());
        xinLoanAcctDetailTempModel.setItemCtrl(buffers[10].toString());
        xinLoanAcctDetailTempModel.setItemCode(buffers[11].toString());
        xinLoanAcctDetailTempModel.setAccountCode(buffers[12].toString());
        xinLoanAcctDetailTempModel.setAccountName(buffers[13].toString());
        xinLoanAcctDetailTempModel.setCurrency(buffers[14].toString());
        xinLoanAcctDetailTempModel.setTransferFlag(buffers[15].toString());
        xinLoanAcctDetailTempModel.setBankNote(buffers[16].toString());
        xinLoanAcctDetailTempModel.setDebtFlag(buffers[17].toString());
        xinLoanAcctDetailTempModel.setAcctType(buffers[18].toString());
        xinLoanAcctDetailTempModel.setTransAmount(BigDecimalUtils.valueOf(buffers[19].toString()));
        xinLoanAcctDetailTempModel.setOffBalanceFlag(buffers[20].toString());
        xinLoanAcctDetailTempModel.setCriticizeFlag(buffers[21].toString());
        xinLoanAcctDetailTempModel.setStatus(buffers[22].toString());
        return xinLoanAcctDetailTempModel;
    }

}
