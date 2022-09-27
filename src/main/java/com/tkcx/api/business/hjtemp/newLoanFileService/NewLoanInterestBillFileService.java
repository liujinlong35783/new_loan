package com.tkcx.api.business.hjtemp.newLoanFileService;

import com.acct.job.newLoadTemp.convert.NewLoanInterestBillConvert;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.InterestBillLoanModel;
import com.tkcx.api.business.acctPrint.service.InterestBillService;
import com.tkcx.api.business.acctPrint.service.Loan.InterestBillLoanService;
import com.tkcx.api.business.hjtemp.fileService.HjCommonService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project：
 * @author： liujinlong
 * @description：
 * @create： 2022/5/16 14:08
 */
@Slf4j
@Service
public class NewLoanInterestBillFileService {

    @Autowired
    private InterestBillLoanService interestBillLoanService;

    @Autowired
    private HjCommonService hjCommonService;


    /**
     * 处理新网贷：贷款利息登记簿文件
     *
     * @param isRemove
     * @param queryResult
     */
    public void handleInterestBillFile(Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleInterestBillFile(isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{}】行到【{}】行异常：{}",
                    queryResult.getNextReadStartNum(), queryResult.getNextReadEndNum(), e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleInterestBillFile(isRemove, queryResult);
        } finally {
            //读取完200行后，对JVM的内存进行回收
            System.gc();
        }
    }

    /**
     * 处理新网贷文件具体逻辑
     *
     * @param isRemove
     * @param queryResult
     */
    private void settleInterestBillFile(Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<InterestBillLoanModel> interestBillModelList = NewLoanInterestBillConvert.makeInterestBillList(filePath, readStartNum, readEndNum);
        /** 已读取完成的数据入库 */
        if(isRemove) {
            interestBillLoanService.remove(null);
            log.info("InterestBillModel数据清空成功");
        }
        interestBillLoanService.saveBatch(interestBillModelList);
        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }



}
