package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
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

    @Override
    public void run(){

        log.info("BusiOrgSeqThread start..." + new Date());

        // 查询网贷前一天业务流水
        QueryWrapper queryWrapper = getQueryWrapper(AcctDataModel.class, "createAt");
        // 查询网贷业务流水
        List<AcctDataModel> acctDataList = acctDataService.list(queryWrapper);
        log.info("BusiOrgSeq of AcctData record is {}.", acctDataList.size());
        List<BusiOrgSeqModel> busiOrgSeqList = new ArrayList<>();
        for (AcctDataModel acctData : acctDataList) {

            // 查询业务流水对应的分录账
            queryWrapper = new QueryWrapper<>();
            queryWrapper.select("ITEM_CTRL","TRANS_AMOUNT","DEBT_FLAG","ACCT_TYPE");
            queryWrapper.eq("CHANNEL_SEQ",acctData.getTransSeqNo());
            queryWrapper.notIn("ITEM_CTRL","200501");
            // 查询会计凭证  ACCT_DETAIL_TEMP 表
            List<AcctDetailTempModel> detailList = acctDetailTempService.list(queryWrapper);

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
        }

        // 保存业务机构业务流水
        if (!busiOrgSeqList.isEmpty()) {
            log.info("BusiOrgSeq record is {}.", busiOrgSeqList.size());
            busiOrgSeqService.saveBatch(busiOrgSeqList);
        }
        log.info("BusiOrgSeqThread end..." + new Date());
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
}
