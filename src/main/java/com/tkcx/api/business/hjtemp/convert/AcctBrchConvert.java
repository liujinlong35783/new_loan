package com.tkcx.api.business.hjtemp.convert;

import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
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
 * @description： 互金t_act_brch_day_tot文件处理类
 * @create： 2021/10/23 11:23
 */
@Slf4j
@Component
public class AcctBrchConvert {

    /**
     * 解析t_act_brch_day_tot文件
     * @param path
     * @return
     */
    public static List<AcctBrchTempModel> makeAcctBrchList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        log.info("------------------解析的文件行数：{}",lines.size());
        List<AcctBrchTempModel> branchList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (StringBuffer lineStr : lines) {
            //行数据生成对象
            branchList.add(assembleBrchTemp(lineStr.toString()));
        }
        log.info("待入库互金t_act_brch_day_tot文件信息：{}条", branchList.size());
        return branchList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static AcctBrchTempModel assembleBrchTemp(String lineStr) {


        StringBuffer[] buffers = HjStringUtils.convertString2Buffer(lineStr,
                HjFileFlagConstant.ACT_BRCH_LINE_LENGTH);

        AcctBrchTempModel acctBrchTempModel = new AcctBrchTempModel();
        if (StringUtils.isNotEmpty(buffers[0])) {
            acctBrchTempModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
        }
        acctBrchTempModel.setAcctOrg(buffers[1].toString());
        acctBrchTempModel.setItemCtrl(buffers[2].toString());
        acctBrchTempModel.setItemCode(buffers[3].toString());
        acctBrchTempModel.setItemName(buffers[4].toString());
        acctBrchTempModel.setOffBalanceFlag(buffers[5].toString());
        acctBrchTempModel.setCurrency(buffers[6].toString());
        acctBrchTempModel.setYestDebitBalance(BigDecimalUtils.valueOf(buffers[7].toString()));
        acctBrchTempModel.setYestLoanBalance(BigDecimalUtils.valueOf(buffers[8].toString()));
        if (StringUtils.isNotEmpty(buffers[9])){
            acctBrchTempModel.setTodayDebitQuantities(Integer.valueOf(buffers[9].toString()));
        }
        if (StringUtils.isNotEmpty(buffers[10])){
            acctBrchTempModel.setTodayLoanQuantities(Integer.valueOf(buffers[10].toString()));
        }
        acctBrchTempModel.setTodayDebitAmount(BigDecimalUtils.valueOf(buffers[11].toString()));
        acctBrchTempModel.setTodayLoanAmount(BigDecimalUtils.valueOf(buffers[12].toString()));
        acctBrchTempModel.setTodayDebitBalance(BigDecimalUtils.valueOf(buffers[13].toString()));
        acctBrchTempModel.setTodayLoanBalance(BigDecimalUtils.valueOf(buffers[14].toString()));
        acctBrchTempModel.setStatus(buffers[15].toString());
        return acctBrchTempModel;
    }
}
