package com.tkcx.api.business.hjtemp.convert;

import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.business.hjtemp.utils.HjStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description： 互金t_act_busi_code_map文件处理类
 * @create： 2021/10/23 11:23
 */
@Slf4j
@Component
public class AcctBusiConvert {

    /**
     * 解析t_act_busi_code_map文件
     * @param path
     * @return
     */
    public static List<AcctBusiCodeModel> makeAcctBusiList(String path, int readStartNum, int readEndNum){

        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        log.info("------------------解析的文件行数：{}",lines.size());
        List<AcctBusiCodeModel> busiList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (StringBuffer lineStr : lines) {
            //行数据生成对象
            busiList.add(assembleDetailTemp(lineStr.toString()));
        }
        log.info("待入库互金文件信息：{}条", busiList.size());

        return busiList;
    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static AcctBusiCodeModel assembleDetailTemp(String lineStr) {


        StringBuffer[] buffers = HjStringUtils.convertString2Buffer(lineStr,
                HjFileFlagConstant.BUSI_CODE_LINE_LENGTH);
        AcctBusiCodeModel busCodeModel = new AcctBusiCodeModel();
        busCodeModel.setIdentifier(buffers[0].toString());
        busCodeModel.setBusiCode(buffers[1].toString());
        busCodeModel.setBusiName(buffers[2].toString());
        busCodeModel.setBalanceIdentifier(buffers[3].toString());
        busCodeModel.setBalanceName(buffers[4].toString());
        busCodeModel.setItemCtrl(buffers[5].toString());
        busCodeModel.setItemName(buffers[6].toString());
        busCodeModel.setStatus(buffers[7].toString());
        return busCodeModel;
    }
}
