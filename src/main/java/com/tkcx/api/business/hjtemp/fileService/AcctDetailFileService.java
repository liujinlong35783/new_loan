package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.convert.AcctDetailConvert;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description： 处理互金会计科目信息，每次处理200行数据，然后入库，再接着读取接下来的200行，直到整个文件解析完成
 * @create： 2021/11/1 15:24
 */
@Slf4j
@Service
public class AcctDetailFileService {

    /**
     * 会计科目服务类
     */
    @Autowired
    private AcctDetailTempService acctDetailTempService;

    /**
     * 互金文件公共service
     */
    @Autowired
    public HjCommonService hjCommonService;


    /**
     * 处理互金会计科目文件
     *
     * @param isRemove
     * @param queryResult
     */
    public void handleAcctDetailFile(Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleAcctDetailFile(isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleAcctDetailFile(isRemove, queryResult);
        } finally {
            //读取完200行后，对JVM的内存进行回收
            System.gc();
        }
    }

    /**
     * 处理互金文件具体逻辑
     *
     * @param isRemove
     * @param queryResult
     */
    private void settleAcctDetailFile(Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<AcctDetailTempModel> detailList = AcctDetailConvert.makeAcctDetailList(filePath, readStartNum, readEndNum);
        /** 已读取完成的数据入库 */
        if(isRemove) {
            acctDetailTempService.remove(null);
            log.info("AcctDetailTempModel数据清空成功");
        }
        acctDetailTempService.saveBatch(detailList);

        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }



}
