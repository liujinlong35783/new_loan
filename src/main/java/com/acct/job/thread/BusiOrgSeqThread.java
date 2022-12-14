package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.acct.job.util.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgSeqLoanModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.constant.AcctRecordScene;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网贷业务机构业务流水
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class BusiOrgSeqThread extends AcctBaseThread {

    public BusiOrgSeqThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){

        super.run();
        log.info("BusiOrgSeqThread start..." + new Date());

        try {
            BusiOrgSeqLoanModel busiOrgSeqLoanModel = new BusiOrgSeqLoanModel();
            busiOrgSeqLoanModel.setAcctDate(super.getCurDate());
            List<BusiOrgSeqLoanModel> busiOrgSeqLoanList = busiOrgSeqLoanService.selectList(busiOrgSeqLoanModel);
            List<BusiOrgSeqLoanModel> orgSeqLoanList = new ArrayList<>();
            for (BusiOrgSeqLoanModel orgSeqLoanModel : busiOrgSeqLoanList) {
                AcctDetailTempModel acctDetail = queryDetailByTransSeqNo(orgSeqLoanModel.getTransSeqNo());
                orgSeqLoanModel.setItemCtrl(acctDetail.getItemCtrl());
                orgSeqLoanModel.setBusiType(Integer.parseInt(acctDetail.getAcctType()));
                orgSeqLoanModel.setLoanAccount(acctDetail.getAccountCode());
                // 红字显示负值
                if ("2".equals(acctDetail.getAcctType())) {
                    orgSeqLoanModel.setAmount("-" + ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                } else {
                    orgSeqLoanModel.setAmount(ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                }
                if ("D".equals(acctDetail.getDebtFlag())) {
                    orgSeqLoanModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
                } else if ("C".equals(acctDetail.getDebtFlag())) {
                    orgSeqLoanModel.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
                }
                orgSeqLoanList.add(orgSeqLoanModel);
            }
            if (!orgSeqLoanList.isEmpty()) {
                log.info("BusiOrgBill record is {}.", orgSeqLoanList.size());
                busiOrgSeqLoanService.updateBatchById(orgSeqLoanList);
            }
        }catch (Exception e) {
            log.error("新希望业务机构业务流水线程执行：{}", e);
        }
    }
    /**
     * 查询会计科目数据

     * @param transSeqNo
     * @return
     */
    private AcctDetailTempModel queryDetailByTransSeqNo(String transSeqNo){
        AcctDetailTempModel detailBySeqNo = acctDetailTempService.getDetailBySeqNo(transSeqNo);
        if (detailBySeqNo==null){
            log.info("queryDetailByTransSeqNo  detailBySeqNo==null");
        }
        return detailBySeqNo;
    }

    public static String getAbstracts(String scene) {
        String abstracts = "";
        switch (scene) {
            case AcctRecordScene.GRANT_SUCCESS :
                abstracts = "放款成功";
                break;
            case AcctRecordScene.REPAY_NORMAL :
                abstracts = "正常状态还款";
                break;
            case AcctRecordScene.REPAY_NORMAL_REDUE :
                abstracts = "正常状态还款利息减免";
                break;
            case AcctRecordScene.REPAY_OVERDUE :
                abstracts = "逾期状态还款";
                break;
            case AcctRecordScene.REPAY_IDLE :
                abstracts = "呆滞状态还款";
                break;
            case AcctRecordScene.REPAY_OVERDUE_REDUE :
                abstracts = "逾期状态还款利息减免";
                break;
            case AcctRecordScene.REPAY_OFFBALANCE :
                abstracts = "非应计状态下还款";
                break;
            case AcctRecordScene.REPAY_OFFBALANCE_REDUE :
                abstracts = "非应计状态下还款利息减免";
                break;
            case AcctRecordScene.REPAY_WRITEOFF :
                abstracts = "核销状态下还款";
                break;
            case AcctRecordScene.REPAY_WRITEOFF_REDUE :
                abstracts = "核销状态下还款利息减免";
                break;
            case AcctRecordScene.ACCRUAL_NORMAL :
                abstracts = "正常利息计提";
                break;
            case AcctRecordScene.ACCRUAL_OVERDUE :
                abstracts = "逾期利息计提";
                break;
            case AcctRecordScene.ACCRUAL_OFFBALANCE :
                abstracts = "非应计利息计提";
                break;
            case AcctRecordScene.SETTLE_NORMAL :
                abstracts = "正常状态利息结息";
                break;
            case AcctRecordScene.SETTLE_IDLE :
                abstracts = "呆滞状态利息结息";
                break;
            case AcctRecordScene.SETTLE_BAD :
                abstracts = "呆账状态利息结息";
                break;
            case AcctRecordScene.SETTLE_OVERDUE :
                abstracts = "逾期（应计）状态利息结息";
                break;
            case AcctRecordScene.NORMAL_TO_OVERDUE :
                abstracts = "正常转逾期";
                break;
            case AcctRecordScene.OVERDUE_TO_IDLE :
                abstracts = "逾期转呆滞";
                break;
            case AcctRecordScene.IDLE_TO_NORMAL_OR_OVERDUE :
                abstracts = "呆滞转正常或逾期";
                break;
            case AcctRecordScene.BALANCE_TO_OFFBALANCE :
                abstracts = "应计转非应计";
                break;
            case AcctRecordScene.OFFBALANCE_TO_BALANCE :
                abstracts = "非应计转应计";
                break;
            case AcctRecordScene.WRITE_OFF :
                abstracts = "核销";
                break;
        }

        return abstracts;
    }



    private void handleBusiOrgSeq(int currentPage, List<AcctDataModel> acctDataList) {

        try {
            dealBusOrgSeq(acctDataList);
        }catch (Exception e) {
            log.error("读取【{}】页异常：{}", currentPage, e);
            // 如果该页处理失败，则对该页数据重新进行处理
            dealBusOrgSeq(acctDataList);
        } finally {
            log.info("BusiOrgSeqThread end..." + new Date());
            //读取完200行后，对JVM的内存进行回收
            System.gc();
        }
    }


    /**
     * 网贷业务机构业务流水
     *
     * @param acctDataList
     */
    private void dealBusOrgSeq(List<AcctDataModel> acctDataList) {


        for (AcctDataModel acctData : acctDataList) {
            String transSeqNo = acctData.getTransSeqNo();
            // 查询总页数
            int acctDetailTotalPage = queryDetailTotalPage(transSeqNo);

            for(int acctDetailCurPage = PageUtils.startPageNum ; acctDetailCurPage <= acctDetailTotalPage;
                acctDetailCurPage++){
                List<AcctDetailTempModel> detailList = queryDetailByPage(acctDetailCurPage, PageUtils.pageSize,
                        transSeqNo);
                log.info("AcctDetailTempModel第【{}】页，分页查询结果：{}", acctDetailCurPage, detailList.size());
                if(detailList == null){
                    log.info("互金会计分录无CHANNEL_SEQ：【{}】对应的数据", acctData.getTransSeqNo());
                    continue;
                }
                saveBusOrgList(acctDetailCurPage, detailList, acctData);
            }
        }

    }


    private void saveBusOrgList(int acctDetailCurPage, List<AcctDetailTempModel> detailList, AcctDataModel acctData) {

        List<BusiOrgSeqModel> busiOrgSeqList = new ArrayList<>();
        for (AcctDetailTempModel acctDetail : detailList) {
            BusiOrgSeqModel busiOrgSeq = new BusiOrgSeqModel();
            busiOrgSeq.setOrgCode(acctData.getOrgid());
            busiOrgSeq.setOrgName(busiCommonService.getOrgNameByCode(busiOrgSeq.getOrgCode()));
            busiOrgSeq.setBusiDate(acctData.getCreateAt());
            busiOrgSeq.setTransSeqNo(acctData.getTransSeqNo());
            busiOrgSeq.setBizTrackNo(acctData.getBizTrackNo());
            busiOrgSeq.setOperator("QNWD");
            // 查询网贷资产信息
            AssetModel asset = busiCommonService.getAssetModel(acctData.getAssetItemNo(),"ASSET_DEBT_NO","ASSET_LOAN_ACCOUNT");
            if(asset!=null){
                busiOrgSeq.setDebtNo(asset.getAssetDebtNo());
                busiOrgSeq.setLoanAccount(asset.getAssetLoanAccount());
            }
            busiOrgSeq.setItemCtrl(acctDetail.getItemCtrl());
            busiOrgSeq.setAbstracts(getAbstracts(acctData.getScene()));
            // 红字显示负值
            if ("2".equals(acctDetail.getAcctType())) {
                busiOrgSeq.setAmount("-" + ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
            } else {
                busiOrgSeq.setAmount(ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
            }
            busiOrgSeq.setBusiType(EnumConstant.BUSI_TYPE_NOMAL);
            busiOrgSeq.setAcctDate(DateUtil.parse(acctData.getAcgDt(),"yyyy-MM-dd"));
            if ("D".equals(acctDetail.getDebtFlag())) {
                busiOrgSeq.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
            } else if ("C".equals(acctDetail.getDebtFlag())) {
                busiOrgSeq.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
            }
            busiOrgSeqList.add(busiOrgSeq);
        }
        boolean saveResult = busiOrgSeqService.saveBatch(busiOrgSeqList);
        if(saveResult) {
            log.info("第{}页，入库成功记录数：{}", acctDetailCurPage, busiOrgSeqList.size());
        }
    }


    private int queryAcctDataTotalPage(Date curDate) {

        Date preDate = DateUtil.parse(DateUtil.formatDate(DateUtil.offsetDay(curDate, -1)));
        return acctDataService.selectCount(preDate, curDate);
    }

    /**
     * 查询ACCT_DETAIL_TEMP总页数
     * @return
     */
    private int queryDetailTotalPage(String transSeqNo) {

        AcctDetailTempModel acctDetailModel = new AcctDetailTempModel();
        acctDetailModel.setChannelSeq(transSeqNo);
        // 这里查询条件是不等于
        acctDetailModel.setItemCtrl("200501");
        int totalRecord = acctDetailTempService.selectCountNotInEleAccount(acctDetailModel);
        int totalPage = PageUtils.calTotalPage(totalRecord);
        log.info("ACCT_DETAIL_TEMP中的channelSeq是【{}】,总记录数【{}】总页数【{}】", transSeqNo, totalRecord, totalPage);
        return totalPage;
    }



    /**
     * 分页查询ACCT_DATA资产数据
     *
     * @return
     */
    private List<AcctDataModel> queryAcctDataByPage(int currentPage, int pageSize, Date curDate){


        Page<AcctDataModel> pageReq = new Page<>(currentPage, pageSize);
        Date startDate = DateUtil.parse(DateUtil.formatDate(DateUtil.offsetDay(curDate, -1)));
        return acctDataService.selectModelPage(pageReq, startDate, curDate);
    }

    /**
     * 分页查询会计科目数据
     *
     * @param currentPage
     * @param pageSize
     * @param transSeqNo
     * @return
     */
    private List<AcctDetailTempModel> queryDetailByPage(int currentPage, int pageSize, String transSeqNo){

        Page<AcctDetailTempModel> acctDetailPage = new Page<>(currentPage, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ITEM_CTRL","TRANS_AMOUNT","DEBT_FLAG","ACCT_TYPE");
        queryWrapper.eq("CHANNEL_SEQ",transSeqNo);
        queryWrapper.notIn("ITEM_CTRL","200501");
        log.info("AcctDetailTempModel分页查询条件:{}", acctDetailPage);
        IPage page = acctDetailTempService.page(acctDetailPage, queryWrapper);
        return page.getRecords();
    }

}
