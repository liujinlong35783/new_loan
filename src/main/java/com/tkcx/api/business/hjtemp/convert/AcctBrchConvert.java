package com.tkcx.api.business.hjtemp.convert;

import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    public static List<AcctBrchTempModel> makeAcctDetailList(String path){

        String lineStr;
        List<AcctBrchTempModel> branchList = Lists.newArrayList();
        while ((lineStr= FileUtil.readLineByLines(path)) != null) {
            //遍历行数据
            if (StringUtils.isEmpty(lineStr)) {
                return null;
            }
            //行数据生成对象
            AcctBrchTempModel branchModel = new AcctBrchTempModel(lineStr);
            branchList.add(branchModel);
        }
        return branchList;
    }

}
