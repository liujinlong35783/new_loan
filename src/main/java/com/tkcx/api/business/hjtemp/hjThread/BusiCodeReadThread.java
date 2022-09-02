package com.tkcx.api.business.hjtemp.hjThread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.fileService.BusiCodeFileService;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
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
 * @author： zhaodan
 * @description： 业务编码处理线程
 * @create： 2021/10/24 13:33
 */
@Slf4j
@Setter
@Getter
public class BusiCodeReadThread extends Thread {



    private Boolean isRemove;

    private Date fileDate;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;


    public BusiCodeReadThread( Boolean isRemove, Date fileDate) {

        this.isRemove = isRemove;
        this.fileDate = fileDate;
    }

    private BusiCodeFileService busiCodeFileService = BeanContext.getBean(BusiCodeFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);


    @Override
    public void run() {

        Date startDate = new Date();
        try {
            super.run();
            // 删除前一天的业务编码表信息
            busiCodeFileService.delBusiCodeData(fileDate);
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryHjFileInfo(fileDate, HjFileFlagConstant.BUSI_CODE_FILE);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>互金：待读取的【业务编码】：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), HjFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行互金业务编码文件解析线程",
                            fileDate, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    return;
                }
                //程序每60毫秒执行一次 值可更改
                Thread.sleep(60);
                // 业务逻辑
                busiCodeFileService.handleBusiCodeFile(isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("读取互金【业务编码】信息线程异常：{}", e);
        } finally {
            Date endDate = new Date();
            log.info("互金业务编码文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
        }
    }


}
