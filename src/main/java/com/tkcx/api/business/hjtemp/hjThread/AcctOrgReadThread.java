package com.tkcx.api.business.hjtemp.hjThread;

import com.tkcx.api.business.hjtemp.fileService.AcctOrgFileService;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.common.BeanContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

    private Boolean isRemove;

    private Date fileDate;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;


    public  AcctOrgReadThread(Boolean isRemove, Date fileDate) {

        this.isRemove = isRemove;
        this.fileDate = fileDate;
    }

    private AcctOrgFileService acctOrgFileService= BeanContext.getBean(AcctOrgFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);

    @Autowired
    private AcctOrgTempService acctOrgTempService;

    @Override
    public void run() {

        try {
            super.run();
            // 一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryHjFileInfo(fileDate, HjFileFlagConstant.ACT_PUB_ORG_FILE);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>互金：待读取的【会计科目】信息：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), HjFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行互金会计科目文件解析线程",
                            fileDate, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    return;
                }
                // 程序每60毫秒执行一次 值可更改
                Thread.sleep(60);
                // 业务逻辑
                acctOrgFileService.handleAcctOrgFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("互金会计科目信息线程异常：{}", e);
        }
    }
}