package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.convert.AcctBusiConvert;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.AcctBusiCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description： 业务编码文件处理类
 * @create： 2021/11/3 18:36
 */
@Slf4j
@Service
public class BusiCodeFileService {


    /**
     * 业务编码服务类
     */
    @Autowired
    private AcctBusiCodeService acctBusiCodeService;

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
    public void handleBusiCodeFile(Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleBusiCodeFile(isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleBusiCodeFile(isRemove, queryResult);
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
    private void settleBusiCodeFile(Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<AcctBusiCodeModel> busiList = AcctBusiConvert.makeAcctBusiList(filePath, readStartNum, readEndNum);
        /** 已读取完成的数据入库 */
        if(isRemove) {
            acctBusiCodeService.remove(null);
            log.info("AcctBrchTempModel数据清空成功");
        }
        acctBusiCodeService.saveBatch(busiList);
        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }
}
