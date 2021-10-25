package com.tkcx.api.business.hjtemp.hjThread;

import com.tkcx.api.business.hjtemp.convert.AcctBrchConvert;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.service.AcctBrchTempService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/24 13:28
 */
@Slf4j
@Setter
@Getter
public class AcctBrchReadThread extends Thread {

    private String filePath;

    private Boolean isRemove;

    public AcctBrchReadThread(String filePath, Boolean isRemove) {
        this.filePath = filePath;
        this.isRemove = isRemove;
    }

    @Autowired
    private AcctBrchTempService acctBrchTempService;

    @Override
    public void run() {

        List<AcctBrchTempModel> brchList = AcctBrchConvert.makeAcctDetailList(filePath);
        log.info("t_act_brch_day_tot待更新的数据总数：{}", brchList.size());
        if(isRemove){
            acctBrchTempService.remove(null);
            log.info("AcctBrchTempModel数据清空成功");
        }
        acctBrchTempService.saveBatch(brchList);
        log.info("AcctBrchTempModel保存成功");
    }
}
