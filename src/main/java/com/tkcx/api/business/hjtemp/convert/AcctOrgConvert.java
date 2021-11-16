package com.tkcx.api.business.hjtemp.convert;

import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
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
 * @description： 互金t_act_pub_org文件处理类
 * @create： 2021/10/23 11:23
 */
@Slf4j
@Component
public class AcctOrgConvert {

    /**
     * 解析t_act_busi_code_map文件
     * @param path
     * @return
     */
    public static List<AcctOrgTempModel> makeAcctBusiList(String path,int readStartNum, int readEndNum){

        List<StringBuffer> lines = FileUtil.readFileNLine(path, readStartNum, readEndNum);
        List<AcctOrgTempModel> acctOrgList = new ArrayList(HjFileFlagConstant.ONE_TIME_READ_LINE_NUM);
        for (StringBuffer lineStr : lines) {
            //行数据生成对象
            acctOrgList.add(assembleOrgTemp(lineStr.toString()));
        }
        return acctOrgList;
    }

    /**
     * 组装业务编码信息
     * @param lineStr
     * @return
     */
    public static AcctOrgTempModel assembleOrgTemp(String lineStr) {


        StringBuffer[] buffers = HjStringUtils.convertString2Buffer(lineStr,
                HjFileFlagConstant.PUB_ORG_LINE_LENGTH);

        AcctOrgTempModel orgModel = new AcctOrgTempModel();
        orgModel.setIdentifier(buffers[0].toString());
        orgModel.setOrgCode(buffers[1].toString());
        orgModel.setOrgName(buffers[2].toString());
        orgModel.setStatus(buffers[3].toString());
        return orgModel;
    }
}
