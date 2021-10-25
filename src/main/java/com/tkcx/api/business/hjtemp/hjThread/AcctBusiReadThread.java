package com.tkcx.api.business.hjtemp.hjThread;

import com.tkcx.api.business.hjtemp.convert.AcctBusiConvert;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.service.AcctBusiCodeService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/24 13:33
 */
@Slf4j
@Setter
@Getter
public class AcctBusiReadThread extends Thread {

    private String filePath;

    private Boolean isRemove;

    public AcctBusiReadThread(String filePath, Boolean isRemove) {
        this.filePath = filePath;
        this.isRemove = isRemove;
    }

    @Autowired
    private AcctBusiCodeService acctBusiCodeService;

    @Override
    public void run() {

        List<AcctBusiCodeModel> busiList = AcctBusiConvert.makeAcctDetailList(filePath);
        log.info("t_act_brch_day_tot待更新的数据总数：{}", busiList.size());
        if(isRemove){
            acctBusiCodeService.remove(null);
            log.info("AcctBrchTempModel数据清空成功");
        }
        acctBusiCodeService.saveBatch(busiList);
        log.info("AcctBrchTempModel保存成功");
    }
}
