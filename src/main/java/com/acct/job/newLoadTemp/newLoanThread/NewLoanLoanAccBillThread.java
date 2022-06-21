package com.acct.job.newLoadTemp.newLoanThread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.newLoanFileService.NewLoanLoanAccBillFileService;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.NewLoanFileFlagConstant;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.business.wdData.model.CardiiModel;
import com.tkcx.api.common.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 贷款分户账
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class NewLoanLoanAccBillThread extends NewLoanAcctBaseThread {

    private Map<String, LoanAccBillModel> modelMap = new HashMap<>();
    private LoanAccBillModel loanAccBillModel;
    private LoanAccBillModel normalModel, overdueModel, idleModel;
    private AssetModel asset;
    private CardiiModel cardii;

    private Boolean isRemove;

    private String fileName;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;

    public NewLoanLoanAccBillThread(Boolean isRemove, String fileName) {

        this.isRemove = isRemove;
        this.fileName = fileName;
    }
    @Autowired
    private HjFileInfoService hjFileInfoService;

    public NewLoanLoanAccBillFileService newLoanLoanAccBillFileService = BeanContext.getBean(NewLoanLoanAccBillFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);

    @Override
    public void run() {
        log.info("NewLoanLoanAccBillThread start...");
        Date startDate = new Date();
        try {
            super.run();
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryXWDFileInfo(fileName, NewLoanFileFlagConstant.FHZ);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>新网贷：待读取的【贷款分户账】：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), NewLoanFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行新网贷：贷款分户账文件解析线程",
                            fileName, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    return;
                }
                // 程序每60毫秒执行一次 值可更改
                Thread.sleep(60);
                // 业务逻辑
                newLoanLoanAccBillFileService.handleLoanAccBillFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("读取新网贷：【贷款分户账】线程异常：{}", e);
        } finally {
            Date endDate = new Date();
            log.info("新网贷：贷款分户账文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
        }
    }
}
