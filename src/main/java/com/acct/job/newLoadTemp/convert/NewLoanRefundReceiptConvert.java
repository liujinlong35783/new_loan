package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.Loan.RefundReceiptLoanModel;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;
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
 * @description： 新网贷113717_JJHD_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanRefundReceiptConvert {

    /**
     * 解析113717_JJHD_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<RefundReceiptLoanModel> makeRefundReceiptList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<RefundReceiptLoanModel> refundReceiptModelList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            RefundReceiptLoanModel refundReceiptModel = assembleRefundReceiptTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
            if(refundReceiptModel == null){
                continue;
            }
            refundReceiptModelList.add(refundReceiptModel);
        }
        return refundReceiptModelList;

    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static RefundReceiptLoanModel assembleRefundReceiptTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.HKHD_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        RefundReceiptLoanModel refundReceiptModel = new RefundReceiptLoanModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            refundReceiptModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyy-MM-dd"));
//        }
        refundReceiptModel.setRefundName(buffers[1].toString());
        refundReceiptModel.setContractNo(buffers[2].toString());
        refundReceiptModel.setLoanAccount(buffers[3].toString());
        refundReceiptModel.setPayAccount(buffers[4].toString());
        refundReceiptModel.setDebtNo(buffers[5].toString());
        refundReceiptModel.setLoanAmount(buffers[6].toString());
        refundReceiptModel.setPayoffDate(DateUtil.parseDate(buffers[7].toString(), "yyyy-MM-dd hh:mm:ss"));
        refundReceiptModel.setGrantDate(DateUtil.parseDate(buffers[8].toString(), "yyyy-MM-dd hh:mm:ss"));
        refundReceiptModel.setDueDate(DateUtil.parseDate(buffers[9].toString(), "yyy-MM-dd"));
        refundReceiptModel.setInterestRate(BigDecimalUtils.valueOf(buffers[10].toString()));
        refundReceiptModel.setOverdueInterestRate(BigDecimalUtils.valueOf(buffers[11].toString()));
        refundReceiptModel.setRepaidPrincipalAmount(buffers[12].toString());
        refundReceiptModel.setUnsettlePrincipalAmount(buffers[13].toString());
        refundReceiptModel.setRepaidInterestAmount(buffers[14].toString());
        refundReceiptModel.setUnsettleInterestAmount(buffers[15].toString());
        refundReceiptModel.setRepaidSumAmount(buffers[16].toString());
        refundReceiptModel.setUnsettleSumAmount(buffers[17].toString());
//        refundReceiptModel.setCreateDate(DateUtil.parseDate(buffers[18].toString(), "yyyy-MM-dd hh:mm:ss"));
        refundReceiptModel.setCreateDate(new Date());
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[19])) {
            refundReceiptModel.setAcctDate(DateUtil.parseDate(buffers[19].toString(), "yyyy-MM-dd"));
        }
        refundReceiptModel.setBorrowerIdnum(buffers[20].toString());
        refundReceiptModel.setOrgName(buffers[21].toString());
        refundReceiptModel.setOrgCode(buffers[22].toString());
        refundReceiptModel.setChannel(buffers[23].toString());

        return refundReceiptModel;
    }
}
