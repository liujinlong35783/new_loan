package com.acct.job.newLoadTemp.newLoanThread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.newLoanFileService.NewLoanBusiOrgBillFileService;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import com.tkcx.api.common.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 网贷业务机构轧账单
 *
 * @author cuijh
 * @since 2019-08-06
 */
@Slf4j
public class NewLoanBusiOrgBillThread extends NewLoanAcctBaseThread {

    private Boolean isRemove;

    private String fileName;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;
    public NewLoanBusiOrgBillThread(Boolean isRemove, String fileName) {

        this.isRemove = isRemove;
        this.fileName = fileName;
    }
    @Autowired
    private HjFileInfoService hjFileInfoService;

    public NewLoanBusiOrgBillFileService newLoanBusiOrgBillFileService = BeanContext.getBean(NewLoanBusiOrgBillFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);
    @Override
    public void run(){
        log.info("NewLoanBusiOrgBillThread start..." + new Date());
        Date startDate = new Date();
        try {
            super.run();
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryXWDFileInfo(fileName, NewLoanFileFlagConstant.JGZZD);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>新网贷：待读取的【网贷业务机构轧账单文件】：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), NewLoanFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行新网贷网贷业务机构轧账单文件解析线程",
                            fileName, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    return;
                }
                // 程序每60毫秒执行一次 值可更改
                Thread.sleep(60);
                // 业务逻辑
                newLoanBusiOrgBillFileService.handleBusiOrgBillFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("读取新网贷【网贷业务机构轧账单文件】线程异常：{}", e);
        } finally {
            Date endDate = new Date();
            log.info("新网贷：网贷业务机构轧账单文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
        }
    }
}
