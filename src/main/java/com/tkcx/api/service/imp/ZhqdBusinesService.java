package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.acctPrint.dao.*;
import com.tkcx.api.business.acctPrint.html2pdf.BusiHtmlToPdf;
import com.tkcx.api.business.acctPrint.model.*;
import com.tkcx.api.business.acctPrint.service.AcctLogService;
import com.tkcx.api.business.acctPrint.service.VoucherPrintService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.GenerateTxtFileUtil;
import com.tkcx.api.common.RealTimeQueryService;
import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.ZhqdQueryRspVo;
import com.tkcx.api.vo.ftpFile.FileUploadReqVo;
import com.tkcx.api.vo.ftpFile.FileUploadRspVo;
import com.tkcx.api.vo.query.*;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 综合前端业务处理服务类
 */
@Slf4j
@Service
public class ZhqdBusinesService {

    @Resource
    public AccountVoucherDao accountVoucherDao;
    @Resource
    private BusiOrgBillDao busiOrgBillDao;
    @Resource
    private BusiOrgSeqDao busiOrgSeqDao;
    @Resource
    private InterestBillDao interestBillDao;
    @Resource
    private LoanAccBillDao loanAccBillDao;
    @Resource
    private LoanAdjustDao loanAdjustDao;
    @Resource
    private LoanDetailBillDao loanDetailBillDao;
    @Resource
    private LoanReceiptDao loanReceiptDao;
    @Resource
    private RefundReceiptDao refundReceiptDao;
    @Resource
    private AcctLogService acctLogService;
    @Resource
    private VoucherPrintService voucherPrintService;
    /**
     * 临时上传目录
     */
    @Value("${storage.tempUpload.path}")
    private String tempUploadPath;
    @Autowired
    private QnFtpClientServiceImpl qnFtpClientServiceImpl;
    @Autowired
    private RealTimeQueryService realTimeQueryService;

    /**
     * InterfaceIden：1-客户借款信息2-客户还款信息3-机构扎账单4-机构流水5-贷款科目分户账6-贷款明细账7-贷款利息登记簿8-电子凭证9-贷款形态调整登记簿
     * @param queryReq
     * @return
     */
    public ZhqdQueryRspVo queryEntry(ZhqdQueryReqVo queryReq) {
        List queryResult = null; ZhqdQueryRspVo rsp = null;
        String queryContent = queryReq.getQueryContent();
        if (StringUtils.isNotEmpty(queryContent) && queryReq.getPrintFlag() == 0) {
            switch (queryReq.getInterfaceIden()) {
                case 1:
                    LoanReceiptQuery loanReceiptQuery = new LoanReceiptQuery(queryContent);
                    log.info("查询----客户借款信息-----登记簿");
                    queryResult = realTimeQueryService.realTime(loanReceiptQuery);
                    if(queryResult == null){
                        queryResult = loanReceiptDao.selectListByQuery(loanReceiptQuery);
                    }
                    break;
                case 2:
                    RefundReceiptQuery refundReceiptQuery = new RefundReceiptQuery(queryContent);
                    log.info("查询----客户还款回单信息-----登记簿");
                    queryResult = realTimeQueryService.realTime(refundReceiptQuery);
                    if(queryResult == null){
                        queryResult = refundReceiptDao.selectListByQuery(refundReceiptQuery);
                    }
                    break;
                case 3:
                    log.info("查询----机构扎账单-----登记簿");
                    queryResult = busiOrgBillDao.selectListByQuery(new BusiOrgQuery(queryContent));
                    break;
                case 4:
                    log.info("查询----机构流水-----登记簿");
                    queryResult = busiOrgSeqDao.selectListByQuery(new BusiOrgQuery(queryContent));
                    break;
                case 5:
                    LoanBillAndAdjustQuery adjustQuery = new LoanBillAndAdjustQuery(queryContent, queryReq.getInterfaceIden());
                    log.info("查询----贷款科目分户账-----登记簿");
                    queryResult = loanAccBillDao.selectListByQuery(adjustQuery);
                    break;
                case 6:
                    LoanBillAndAdjustQuery loanBillAndAdjustQuery = new LoanBillAndAdjustQuery(queryContent, queryReq.getInterfaceIden());
                    log.info("查询----贷款明细账-----登记簿");
                    queryResult = realTimeQueryService.realTime(loanBillAndAdjustQuery);
                    if(queryResult == null) {
                        queryResult = loanDetailBillDao.selectListByQuery(loanBillAndAdjustQuery);
                    }
                    break;
                case 7:
                    InterestBillQuery interestBillQuery = new InterestBillQuery(queryContent);
                    log.info("查询----贷款利息-----登记簿");
                    queryResult = realTimeQueryService.realTime(interestBillQuery);
                    if(queryResult == null) {
                        queryResult = interestBillDao.selectListByQuery(interestBillQuery);
                    }
                    break;
                case 8:
                    log.info("查询----电子凭证-----登记簿");
                    queryResult = accountVoucherDao.selectListByQuery(new AcctVoucherQuery(queryContent));
                    break;
                case 9:
                    log.info("查询----贷款形态调整-----登记簿");
                    queryResult = loanAdjustDao.selectListByQuery(new LoanBillAndAdjustQuery(queryContent, queryReq.getInterfaceIden()));
                    break;
                default:
                    log.error("该登记簿不存在");
                    break;
            }
            rsp = makeLocalFile(queryResult, queryReq);
        }else if(queryReq.getPrintFlag() == 1){
            rsp = printReqReturnRsp(queryReq);
        }
        try {
            acctLogService.saveAcctLog(queryReq.getInterfaceIden(), queryReq.getQueryNo(), queryReq.getQueryContent(), queryReq.getSysHeadVo().getAcgDt());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rsp;
        }
    }
    /**
     * 生成txt文件与pdf文件
     *
     * @param queryResults
     * @param queryReq
     * @return
     */
    private ZhqdQueryRspVo makeLocalFile(List queryResults, ZhqdQueryReqVo queryReq) {
        ZhqdQueryRspVo rsp = new ZhqdQueryRspVo();
        rsp.setTxnSt("S");
        rsp.setRetCd("000000");
        rsp.setRetMsg("通知成功");

        Integer interfaceIden = queryReq.getInterfaceIden();
        String uuidName = cn.hutool.core.lang.UUID.randomUUID().toString();
        String pathName = tempUploadPath + DateUtil.formatDate(new Date()) + "/" + interfaceIden + "/" + uuidName;
        //远程存储路径，不包含文件名
        String remoteFullPath = "/send/"+DateUtil.format(new Date(),"yyyyMMdd")+"/";
        FileUploadReqVo uploadVo = new FileUploadReqVo();
        uploadVo.setFilePath(pathName);
        uploadVo.setFileName(uuidName);
        uploadVo.setRemoteFullPath(remoteFullPath);
        Integer queryCount = 0;//查询结果数量
        //生成txt文本
        try {
            if ((queryResults != null && queryResults.size() > 0) && ((interfaceIden == 1 || interfaceIden == 2 || interfaceIden == 8)
                    ? FileUtil.makeFile(queryResults, pathName + ".txt")
                    : GenerateTxtFileUtil.writeDataTxt(queryResults, queryReq, pathName + ".txt"))) {//生成本地txt文件
                uploadVo.setFileType("txt");
                FileUploadRspVo rspVo = qnFtpClientServiceImpl.ftpFileUpload(uploadVo,queryReq);//上传本地txt文件
                queryCount = queryResults.size();
                rsp.setFileTrnsmCd(rspVo.getTranCode());
                rsp.setFilePath(rspVo.getUrl());
                rsp.setFileName(uuidName);
            }
        } catch (ApplicationException e) {
            log.error("生成txt异常{}", e);
        }finally {
            if (queryResults != null && queryResults.size() > 0){
                //生成pdf文件
                makePdfFile(queryResults, queryReq, pathName, uploadVo);
            }
            if(interfaceIden == 1 || interfaceIden == 2 || interfaceIden == 8){
                saveOrUpdateCustPrintInfo(queryResults, queryReq);
            }
            rsp.setQueryCount(queryCount);
            return rsp;
        }
    }

    /**
     * 异步生成pdf文件
     * @param queryResults
     * @param queryReq
     * @param pathName
     * @param uploadVo
     */
    @Async
    public void makePdfFile(List queryResults, ZhqdQueryReqVo queryReq, String pathName, FileUploadReqVo uploadVo) {
        if (BusiHtmlToPdf.toPdf(queryResults, queryReq, pathName + ".pdf")) {
            uploadVo.setFileType("pdf");
            try {
                qnFtpClientServiceImpl.ftpFileUpload(uploadVo,queryReq);//上传本地pdf文件
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 1：用 合同号 做唯一标识
     * 2：用 合同号+还款时间 做唯一标识
     * 8：用 流水号 做唯一标识
     * @param queryReq
     * @param queryResults
     */
    private void saveOrUpdateCustPrintInfo(List queryResults, ZhqdQueryReqVo queryReq) {
        VoucherPrintModel vpm;
        //List<VoucherPrintModel> vpmList = new ArrayList<>();
        Integer interfaceIden = queryReq.getInterfaceIden();
        for (Object model : queryResults) {
            vpm = new VoucherPrintModel();
            vpm.setQueryNo(queryReq.getQueryNo());
            vpm.setBusiType(interfaceIden);
            vpm.setPrintCount(0);
            if (interfaceIden == 1) {
                LoanReceiptModel lRModel = (LoanReceiptModel) model;
                vpm.setSerialNo(lRModel.getContractNo()+"");
            } else if (interfaceIden == 2) {
                RefundReceiptModel rRmodel = (RefundReceiptModel) model;
                if (rRmodel.getPayoffDate() != null)
                    vpm.setSerialNo(rRmodel.getContractNo() + DateUtil.format(rRmodel.getPayoffDate(), "yyyyMMddHHmmss"));
            } else if (interfaceIden == 8) {
                AccountVoucherModel aVmodel = (AccountVoucherModel) model;
                vpm.setSerialNo(aVmodel.getSerialNo()+"");
            }
            voucherPrintService.saveOrUpdateAcctLogBySerialNo(vpm);
        }
    }

    /**
     * 打印计数通知
     * @param queryReq
     * @return
     */
    private ZhqdQueryRspVo printReqReturnRsp(ZhqdQueryReqVo queryReq){
        ZhqdQueryRspVo rsp = new ZhqdQueryRspVo();
        try {
            rsp.setTxnSt("S");
            rsp.setRetCd("000000");
            rsp.setRetMsg("通知打印计数成功");
            voucherPrintService.updatePrintCountByQueryNo(queryReq.getQueryNo());
            rsp.setFileTrnsmCd("0");
            rsp.setFilePath("0");
            rsp.setFileName("0");
            rsp.setQueryCount(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rsp;
        }
    }

}
