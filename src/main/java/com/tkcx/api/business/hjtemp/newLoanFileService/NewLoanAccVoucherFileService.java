package com.tkcx.api.business.hjtemp.newLoanFileService;

import com.acct.job.newLoadTemp.convert.NewLoanAcctVoucherConvert;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.business.acctPrint.model.Loan.AccountVoucherLoanModel;
import com.tkcx.api.business.acctPrint.service.AccountVoucherService;
import com.tkcx.api.business.acctPrint.service.Loan.AccountVoucherLoanService;
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
public class NewLoanAccVoucherFileService {

    @Autowired
    private AccountVoucherLoanService accountVoucherLoanService;

    @Autowired
    private HjCommonService hjCommonService;


    /**
     * 处理新网贷会计凭证文件
     *
     * @param isRemove
     * @param queryResult
     */
    public void handleAccVoucherFile(Boolean isRemove, HjFileInfoModel queryResult) {

        try{
            settleAccVoucherFile(isRemove, queryResult);
        } catch (Exception e) {
            log.error("读取【{"+queryResult.getNextReadStartNum()+"}】行到【{"+queryResult.getNextReadEndNum()+"}】行异常:"+ e);
            // 如果文件解析失败或者入库失败，则对该readStartNum到readEndNum行数据重新进行读取
            settleAccVoucherFile(isRemove, queryResult);
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
    private void settleAccVoucherFile(Boolean isRemove, HjFileInfoModel queryResult) {

        int readStartNum = queryResult.getNextReadStartNum();
        int readEndNum = queryResult.getNextReadEndNum();
        String filePath = queryResult.getFileDownloadPath();
        List<AccountVoucherLoanModel> accountVoucherModelList = NewLoanAcctVoucherConvert.makeAcctVoucherList(filePath, readStartNum, readEndNum);
        /** 已读取完成的数据入库 */
        if(isRemove) {
            accountVoucherLoanService.remove(null);
            log.info("AccountVoucherModel数据清空成功");
        }
        accountVoucherLoanService.saveBatch(accountVoucherModelList);
        /** 更新已读取的文件信息 */
        hjCommonService.updateReadFileInfo(queryResult);
    }



}
