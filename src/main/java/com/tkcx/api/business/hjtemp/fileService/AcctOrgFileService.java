package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.convert.AcctOrgConvert;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     * @param queryResult
     */
    public void handleAcctOrgFile(HjFileInfoModel queryResult) {

        try{
            settleAcctOrgFile(queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleAcctOrgFile(queryResult);
        } finally {
            //读取完200行后，对JVM的内存进行回收
            System.gc();
        }
    }

    public boolean delAcctOrgData(Date fileDate) {

        boolean remove = acctOrgTempService.remove(null);
        if(remove){
            log.info("{}日之前的AcctOrgTempModel数据清空成功", fileDate);
        }
        return remove;
    }

    /**
     * 处理互金文件具体逻辑
     *
     * @param queryResult
     */
    private void settleAcctOrgFile(HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<AcctOrgTempModel> orgList = AcctOrgConvert.makeAcctBusiList(filePath, readStartNum, readEndNum);
        acctOrgTempService.saveBatch(orgList);

        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }
}
