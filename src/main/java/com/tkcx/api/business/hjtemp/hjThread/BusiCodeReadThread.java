package com.tkcx.api.business.hjtemp.hjThread;

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


    private String filePath;

    private Boolean isRemove;

    private Date fileDate;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;


    public BusiCodeReadThread(String filePath, Boolean isRemove, Date fileDate) {
        this.filePath = filePath;
        this.isRemove = isRemove;
        this.fileDate = fileDate;
    }

    private BusiCodeFileService busiCodeFileService = BeanContext.getBean(BusiCodeFileService.class);
    public HjCommonService hjCommonService = BeanContext.getBean(HjCommonService.class);


    @Override
    public void run() {

        try {
            super.run();
            //一直循环
            while (pause) {
                // 如果读取未完成，则暂停线程
                HjFileInfoModel hjFileInfoModel = hjCommonService
                        .queryHjFileInfo(fileDate, HjFileFlagConstant.BUSI_CODE_FILE);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>互金：待读取的【会计科目】信息：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",
                        hjFileInfoModel.toString());
                if (StringUtils.equals(hjFileInfoModel.getReadFlag(), HjFileFlagConstant.FINISHED)) {
                    log.info("日期：【{}】,读取标识：【{}】,结束执行互金会计科目文件解析线程",
                            fileDate, hjFileInfoModel.getReadFlag());
                    // 线程停止
                    //interrupt();
                    pause = false;
                }
                //程序每60毫秒(1秒)执行一次 值可更改
                Thread.sleep(60);
                //这里写你的代码 你的代码  你的代码  重要的事情说三遍
                busiCodeFileService.handleBusiCodeFile(filePath, isRemove, hjFileInfoModel);
            }
        } catch (Exception e) {
            log.error("互金会计科目信息线程异常：{}", e);
        }
    }


}
