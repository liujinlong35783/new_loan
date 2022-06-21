package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
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
 * @description： 新网贷113717_KJPZ_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanAcctVoucherConvert {

    /**
     * 解析113717_KJPZ_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<AccountVoucherModel> makeAcctVoucherList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<AccountVoucherModel> branchList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            AccountVoucherModel voucherModel= assembleAcctVoucherTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(voucherModel == null){
                continue;
            }
            branchList.add(voucherModel);
        }
        return branchList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static AccountVoucherModel assembleAcctVoucherTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.KJPZ_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        AccountVoucherModel AccountVoucherModel = new AccountVoucherModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            acctBrchTempModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        AccountVoucherModel.setLoanAccount(buffers[1].toString());
        AccountVoucherModel.setDebtNo(buffers[2].toString());
        AccountVoucherModel.setContractNo(buffers[3].toString());
        AccountVoucherModel.setSerialNo(buffers[4].toString());
        AccountVoucherModel.setOrgCode(buffers[5].toString());
        AccountVoucherModel.setBusiType(buffers[6].toString());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[7])) {
            AccountVoucherModel.setBusiDate(DateUtil.parseDate(buffers[7].toString(), "yyyy-mm-dd hh:MM:ss"));
        }
        AccountVoucherModel.setTransferFlag(Integer.valueOf(buffers[8].toString()));
        AccountVoucherModel.setVoucherNo(buffers[9].toString());
        AccountVoucherModel.setOperator(buffers[10].toString());
        AccountVoucherModel.setAccountName(buffers[11].toString());
        AccountVoucherModel.setAccountCode(buffers[12].toString());
        AccountVoucherModel.setItemCtrl(buffers[13].toString());
        AccountVoucherModel.setCurrency(buffers[14].toString());
        AccountVoucherModel.setAbstracts(buffers[15].toString());
        AccountVoucherModel.setAmount(buffers[16].toString());
        AccountVoucherModel.setDebtFlag(Integer.valueOf(buffers[17].toString()));
        AccountVoucherModel.setRemark(buffers[18].toString());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[19])) {
            AccountVoucherModel.setCreateDate(DateUtil.parseDate(buffers[19].toString(), "yyyy-mm-dd hh:MM:ss"));
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[20])) {
            AccountVoucherModel.setAcctDate(DateUtil.parseDate(buffers[20].toString(), "yyyy-MM-dd"));
        }
        AccountVoucherModel.setOrgName(buffers[21].toString());
        AccountVoucherModel.setNewLoanFlag("1");
        return AccountVoucherModel;
    }
}
