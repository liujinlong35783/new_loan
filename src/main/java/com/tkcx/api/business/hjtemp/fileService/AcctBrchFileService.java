package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.convert.AcctBrchConvert;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctBrchTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 17:08
 */
@Slf4j
@Service
public class AcctBrchFileService {

    @Autowired
    private AcctBrchTempService acctBrchTempService;

    @Autowired
    private HjCommonService hjCommonService;


    /**
     * 处理互金会计科目文件
     *
     * @param isRemove
     * @param queryResult
     */
    public void handleAcctBrchFile(Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleAcctBrchFile(isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleAcctBrchFile(isRemove, queryResult);
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
    private void settleAcctBrchFile(Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<AcctBrchTempModel> branchList = AcctBrchConvert.makeAcctBrchList(filePath, readStartNum, readEndNum);
        /** 已读取完成的数据入库 */
        if(isRemove) {
            acctBrchTempService.remove(null);
            log.info("AcctBrchTempModel数据清空成功");
        }
        acctBrchTempService.saveBatch(branchList);
        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }



}
