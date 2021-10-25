package com.tkcx.api.business.hjtemp.hjThread;

import com.tkcx.api.business.hjtemp.convert.AcctOrgConvert;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/24 13:35
 */
@Slf4j
@Setter
@Getter
public class AcctOrgReadThread extends Thread {

    private String filePath;

    private Boolean isRemove;

    public AcctOrgReadThread(String filePath, Boolean isRemove) {
        this.filePath = filePath;
        this.isRemove = isRemove;
    }

    @Autowired
    private AcctOrgTempService acctOrgTempService;

    @Override
    public void run() {

        List<AcctOrgTempModel> orgList = AcctOrgConvert.makeAcctDetailList(filePath);
        log.info("t_act_pub_org待更新的数据总数：{}", orgList.size());
        acctOrgTempService.remove(null);
        log.info("AcctOrgTempModel数据清空成功");
        acctOrgTempService.saveBatch(orgList);
        log.info("AcctOrgTempModel保存成功");
    }
}