package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.convert.AcctOrgConvert;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 19:09
 */
@Slf4j
@Service
public class AcctOrgFileService {


    /**
     * 会计科目服务类
     */
    @Autowired
    private AcctOrgTempService acctOrgTempService;

    /**
     * 互金文件公共service
     */
    @Autowired
    public HjCommonService hjCommonService;


    /**
     * 处理互金会计科目文件
     *
     * @param filePath
     * @param isRemove
     * @param queryResult
     */
    public void handleAcctOrgFile(String filePath, Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleAcctOrgFile(filePath, isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleAcctOrgFile(filePath, isRemove, queryResult);
        } finally {
            //读取完200行后，对JVM的内存进行回收
            System.gc();
        }
    }

    /**
     * 处理互金文件具体逻辑
     *
     * @param filePath
     * @param isRemove
     * @param queryResult
     */
    private void settleAcctOrgFile(String filePath, Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        List<AcctOrgTempModel> orgList = AcctOrgConvert.makeAcctBusiList(filePath, readStartNum, readEndNum);
        log.info("待更新的数据从【{}】行到【{}】行，总数：【{}】", readStartNum, readEndNum, orgList.size());
        /** 已读取完成的数据入库 */
        if(isRemove) {
            acctOrgTempService.remove(null);
            log.info("AcctOrgTempModel数据清空成功");
        }
        boolean updateResult = acctOrgTempService.saveBatch(orgList);
        if(updateResult){
            log.info("AcctOrgTempModel保存成功");
        }
        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }
}
