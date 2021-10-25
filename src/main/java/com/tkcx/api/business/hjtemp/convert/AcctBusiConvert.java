package com.tkcx.api.business.hjtemp.convert;

import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    public static List<AcctBusiCodeModel> makeAcctDetailList(String path){

        String lineStr;
        List<AcctBusiCodeModel> busiList = Lists.newArrayList();
        while ((lineStr= FileUtil.readLineByLines(path)) != null) {
            //遍历行数据
            if (StringUtils.isEmpty(lineStr)) {
                return null;
            }
            //行数据生成对象
            AcctBusiCodeModel busiCodeModel = new AcctBusiCodeModel(lineStr);
            busiList.add(busiCodeModel);
        }
        return busiList;
    }

}
