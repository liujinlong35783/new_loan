package com.tkcx.api.business.hjtemp.hjThread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.fileService.XinLoanAcctDetailFileService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.common.BeanContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @project：
 * @author： liujinlong
 * @description： 互金会计科目信息线程
 * @create： 2022/5/26 13:15
 */
@Slf4j
@Setter
@Getter
public class XinLoanAcctDetailReadThread extends Thread {

    private Boolean isRemove;

    private Date fileDate;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;


    public XinLoanAcctDetailReadThread(Boolean isRemove, Date fileDate) {

        this.isRemove = isRemove;
        this.fileDate = fileDate;
    }

    public XinLoanAcctDetailFileService xinLoanAcctDetailFileService = BeanContext.getBean(XinLoanAcctDetailFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);

    @Override
    public void run() {

        Date startDate = new Date();
        try {
            super.run();
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryHjFileInfo(fileDate, HjFileFlagConstant.XIN_LOAN_ACCT_DETAIL_FILE);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>互金：待读取的【会计科目】：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
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
                xinLoanAcctDetailFileService.handleAcctDetailFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("读取互金【会计科目】线程异常：{}", e);
        } finally {
            Date endDate = new Date();
            log.info("互金会计科目文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
        }

    }



}
