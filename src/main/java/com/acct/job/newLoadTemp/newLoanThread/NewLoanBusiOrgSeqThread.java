package com.acct.job.newLoadTemp.newLoanThread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.newLoanFileService.NewLoanBusiOrgSeqFileService;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import com.tkcx.api.common.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 网贷业务机构业务流水
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class NewLoanBusiOrgSeqThread extends NewLoanAcctBaseThread {
    private Boolean isRemove;

    private String fileName;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;


    public NewLoanBusiOrgSeqThread(Boolean isRemove, String fileName) {

        this.isRemove = isRemove;
        this.fileName = fileName;
    }
    @Autowired
    private HjFileInfoService hjFileInfoService;

    public NewLoanBusiOrgSeqFileService newLoanBusiOrgSeqFileService = BeanContext.getBean(NewLoanBusiOrgSeqFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);

    @Override
    public void run(){


        log.info("NewLoanBusiOrgSeqThread start..." + new Date());

        Date startDate = new Date();
        try {
            super.run();
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryXWDFileInfo(fileName, NewLoanFileFlagConstant.YWLS);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>新网贷：待读取的【网贷业务机构业务流水文件】：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), NewLoanFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行新网贷：网贷业务机构业务流水文件解析线程",
                            fileName, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    return;
                }
                // 程序每60毫秒执行一次 值可更改
                Thread.sleep(60);
                // 业务逻辑
                newLoanBusiOrgSeqFileService.handleBusiOrgSeqFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("读取新网贷：【网贷业务机构业务流水文件】线程异常：{}", e);
        } finally {
            Date endDate = new Date();
            log.info("新网贷：网贷业务机构业务流水文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
        }
    }

}
