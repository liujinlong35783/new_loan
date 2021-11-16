package com.tkcx.api.business.hjtemp.convert;

import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.HjStringUtils;
import com.tkcx.api.utils.BigDecimalUtils;
import common.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class AcctDetailConvert {


    /**
     * 解析互金t_act_one_detail文件
     * @param path
     * @return
     */
    public static List<AcctDetailTempModel> makeAcctDetailList(String path, int readStartNum, int readEndNum){

        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<AcctDetailTempModel> acctDetailList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (StringBuffer lineStr : lines) {
            //行数据生成对象
            AcctDetailTempModel acctDetailTempModel = assembleDetailTemp(lineStr.toString());
            acctDetailList.add(acctDetailTempModel);
        }
        return acctDetailList;
    }

    /**
     * 组装会计分录信息
     * @param lineStr
     * @return
     */
    public static AcctDetailTempModel assembleDetailTemp(String lineStr) {


        StringBuffer[] buffers = HjStringUtils.convertString2Buffer(lineStr,
                HjFileFlagConstant.ACCT_DETAIL_LINE_LENGTH);

        AcctDetailTempModel acctDetailTempModel = new AcctDetailTempModel();
        acctDetailTempModel.setIdentifier(buffers[0].toString());
        if (StringUtils.isNotEmpty(buffers[1].toString())) {
            acctDetailTempModel.setChannelDate(DateUtil.parseDate(buffers[1].toString(), "yyyyMMdd"));
        }
        acctDetailTempModel.setChannelSeq(buffers[2].toString());
        acctDetailTempModel.setChannelWay(buffers[3].toString());
        if (StringUtils.isNotEmpty(buffers[4])) {
            acctDetailTempModel.setAcctDate(DateUtil.parseDate(buffers[4].toString(), "yyyyMMdd"));
        }
        acctDetailTempModel.setAcctSeq(buffers[5].toString());
        acctDetailTempModel.setServiceCode(buffers[6].toString());
        acctDetailTempModel.setServiceName(buffers[7].toString());
        if(StringUtils.isNotEmpty(buffers[8])) {
            acctDetailTempModel.setSerialNo(Long.valueOf(buffers[8].toString()));
        }
        acctDetailTempModel.setAcctOrg(buffers[9].toString());
        acctDetailTempModel.setItemCtrl(buffers[10].toString());
        acctDetailTempModel.setItemCode(buffers[11].toString());
        acctDetailTempModel.setAccountCode(buffers[12].toString());
        acctDetailTempModel.setAccountName(buffers[13].toString());
        acctDetailTempModel.setCurrency(buffers[14].toString());
        acctDetailTempModel.setTransferFlag(buffers[15].toString());
        acctDetailTempModel.setBankNote(buffers[16].toString());
        acctDetailTempModel.setDebtFlag(buffers[17].toString());
        acctDetailTempModel.setAcctType(buffers[18].toString());
        acctDetailTempModel.setTransAmount(BigDecimalUtils.valueOf(buffers[19].toString()));
        acctDetailTempModel.setOffBalanceFlag(buffers[20].toString());
        acctDetailTempModel.setCriticizeFlag(buffers[21].toString());
        acctDetailTempModel.setStatus(buffers[22].toString());
        return acctDetailTempModel;
    }

}
