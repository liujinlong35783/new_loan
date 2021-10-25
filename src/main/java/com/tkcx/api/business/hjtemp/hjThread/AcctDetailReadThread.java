package com.tkcx.api.business.hjtemp.hjThread;

import com.tkcx.api.business.hjtemp.convert.AcctDetailConvert;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/24 13:15
 */
@Slf4j
@Setter
@Getter
public class AcctDetailReadThread extends Thread {

    private String filePath;

    private Boolean isRemove;

    public AcctDetailReadThread(String filePath, Boolean isRemove) {
        this.filePath = filePath;
        this.isRemove = isRemove;
    }

    @Autowired
    private AcctDetailTempService acctDetailTempService;

    @Override
    public void run() {

        List<AcctDetailTempModel> detailList = AcctDetailConvert.makeAcctDetailList(filePath);
        log.info("t_act_one_detail待更新的数据总数：{}", detailList.size());
        if(isRemove) {
            acctDetailTempService.remove(null);
            log.info("AcctDetailTempModel数据清空成功");
        }
        acctDetailTempService.saveBatch(detailList);
        log.info("AcctDetailTempModel保存成功");
    }
}
