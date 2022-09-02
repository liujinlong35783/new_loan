package com.acct.job.newLoadTemp.convert;

import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
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
 * @description： 新网贷113717_YWLS_YYYYMMDD文件处理类
 * @create： 2022/5/16 14:39
 */
@Slf4j
@Component
public class NewLoanBusiOrgSeqConvert {

    /**
     * 解析113717_YWLS_YYYYMMDD文件
     * @param path
     * @return
     */
    public static List<BusiOrgSeqModel> makeBusiOrgSeqList(String path, int readStartNum, int readEndNum){


        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<BusiOrgSeqModel> branchList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (int lineNum = 0; lineNum <lines.size(); lineNum++) {
            //行数据生成对象
            BusiOrgSeqModel brchModeal = assembleBusiOrgSeqTemp(lines.get(lineNum).toString(), readStartNum, lineNum);
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
    public static BusiOrgSeqModel assembleBusiOrgSeqTemp(String lineStr,int readStartNum, int lineNum) {


        StringBuffer[] buffers = AcctStringUtils.convertString2Buffer(lineStr,
                NewLoanFileFlagConstant.YWLS_LINE_LENGTH, readStartNum, lineNum);
        if(buffers == null){
            return null;
        }

        BusiOrgSeqModel busiOrgSeqModel = new BusiOrgSeqModel();
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[0])) {
//            busiOrgSeqModel.setAcctDate(DateUtil.parseDate(buffers[0].toString(), "yyyyMMdd"));
//        }
        busiOrgSeqModel.setOrgName(buffers[1].toString());
        busiOrgSeqModel.setOrgCode(buffers[2].toString());
        busiOrgSeqModel.setBusiDate(DateUtil.parseDate(buffers[3].toString(), "yyyy-MM-dd hh:mm:ss"));
        busiOrgSeqModel.setDebtNo(buffers[4].toString());
        busiOrgSeqModel.setItemCtrl(buffers[5].toString());
        busiOrgSeqModel.setAbstracts(buffers[6].toString());
        busiOrgSeqModel.setAmount(buffers[7].toString());
        busiOrgSeqModel.setDebtFlag(Integer.valueOf(buffers[8].toString()));
        busiOrgSeqModel.setBusiType(Integer.valueOf(buffers[9].toString()));
        busiOrgSeqModel.setCreateDate(DateUtil.parseDate(buffers[10].toString(), "yyyy-MM-dd hh:mm:ss"));
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(buffers[11])) {
            busiOrgSeqModel.setAcctDate(DateUtil.parseDate(buffers[11].toString(), "yyyy-MM-dd"));
        }
        busiOrgSeqModel.setBizTrackNo(buffers[12].toString());
        busiOrgSeqModel.setTransSeqNo(buffers[13].toString());
        busiOrgSeqModel.setLoanAccount(buffers[14].toString());
        busiOrgSeqModel.setOperator(buffers[15].toString());

        return busiOrgSeqModel;
    }
}
