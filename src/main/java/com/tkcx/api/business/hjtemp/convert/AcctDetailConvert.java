package com.tkcx.api.business.hjtemp.convert;

import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    public static List<AcctDetailTempModel> makeAcctDetailList(String path){

        String lineStr;
        List<AcctDetailTempModel> acctDetailList = Lists.newArrayList();
        while ((lineStr=FileUtil.readLineByLines(path)) != null) {
            //遍历行数据
            if (StringUtils.isEmpty(lineStr)) {
                return null;
            }
            //行数据生成对象
            AcctDetailTempModel acctDetailTempModel = new AcctDetailTempModel(lineStr);
            acctDetailList.add(acctDetailTempModel);
        }
        return acctDetailList;
    }

}
