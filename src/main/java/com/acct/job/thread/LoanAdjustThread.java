package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.business.wdData.model.CardiiModel;
import com.tkcx.api.constant.AcctRecordScene;
import com.tkcx.api.constant.BusiCodeConstant;
import com.tkcx.api.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷款形态调整明细清单、贷款调整登记簿
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class LoanAdjustThread extends AcctBaseThread {

    public LoanAdjustThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){
        LoanAdjustModel loanAdjustModel;
        AssetModel asset;
        CardiiModel cardii;
        Date startDate = new Date();
        log.info("LoanAdjustThread start..." + startDate);
        // 查询贷款调整登记簿
        List<LoanAdjustModel> loanAdjustList = new ArrayList<>();

        QueryWrapper<AcctDataModel> queryWrapperAcctData = getQueryWrapper(AcctDataModel.class, "createAt");
        queryWrapperAcctData.in("RET_STATUS", "0", "1");
        queryWrapperAcctData.in("SCENE", AcctRecordScene.OVERDUE_TO_IDLE, AcctRecordScene.NORMAL_TO_OVERDUE, AcctRecordScene.IDLE_TO_NORMAL_OR_OVERDUE);
        queryWrapperAcctData.select("ID", "SCENE", "ASSET_ITEM_NO", "CREATE_AT", "BIZ_TRACK_NO", "MESSAGE", "PRODUCT_CODE", "ACG_DT", "TRANS_SEQ_NO","REPAY_PLAN_NO","NORMAL_PRINCIPAL","OVERDUE_PRINCIPAL","IDLE_PRINCIPAL","BAD_PRINCIPAL");
        List<AcctDataModel> accDataList = acctDataService.list(queryWrapperAcctData);
        log.info("查询到AcctDataModel数据:" + accDataList.size() + "条");
        if (accDataList != null && accDataList.size() > 0) {
            for (AcctDataModel model : accDataList) {
                loanAdjustModel = new LoanAdjustModel();
                String assetItemNo = model.getAssetItemNo();
                asset = assetService.getAssetByItemNo(assetItemNo);
                if (asset == null || !("repay".equals(asset.getAssetStatus()) || "payoff".equals(asset.getAssetStatus()))) {
                    log.info("资产编号====" + assetItemNo + "数据异常");
                    continue;
                }
                cardii = cardiiService.getCardiiByCardiiIdnum(asset.getAssetBorrowerIdnum());
                if (cardii != null) {
                    loanAdjustModel.setLoanName(cardii.getCardiiName());//户名
                }else{
                    log.info("资产编号====" + assetItemNo + "数据异常");
                    continue;
                }

                loanAdjustModel.setDebtNo(asset.getAssetDebtNo());//（借据号）
                loanAdjustModel.setLoanAccount(asset.getAssetLoanAccount());//贷款账号
                loanAdjustModel.setContractNo(busiCommonService.getContractNoByAssetItemNo(assetItemNo));//合同号

                loanAdjustModel.setGrantDate(asset.getAssetActualGrantAt());//发放日期
                loanAdjustModel.setDueDate(asset.getAssetDueAt());//到期日期

                AcctBusiCodeModel[] busiCodeModels = getBusiCodeModel(model, loanAdjustModel);
                loanAdjustModel.setOriginalItemCode(busiCodeModels[0].getItemCtrl());
                loanAdjustModel.setOriginalItemName(busiCodeModels[0].getItemName());

                loanAdjustModel.setNewItemCode(busiCodeModels[1].getItemCtrl());
                loanAdjustModel.setNewItemName(busiCodeModels[1].getItemName());

/*                String subVal = asset.getAssetGrantedPrincipalAmount().subtract(busiCommonService.getSumRepaidPrincipalbyAssetItemNo(assetItemNo,model.getRepayPlanNo())).toPlainString();//放款金额-已还金额=剩余还款本金
                loanAdjustModel.setBalanceAmount(subVal);*/

                loanAdjustModel.setOrgCode(asset.getOrgid());
                // 查询会计凭证ACCT_ORG_TEMP表
                loanAdjustModel.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));
                loanAdjustModel.setAdjustDate(model.getCreateAt());
                if (model.getAcgDt() != null)
                    loanAdjustModel.setAcctDate(DateUtil.parse(model.getAcgDt(), "yyyy-MM-dd"));

                loanAdjustList.add(loanAdjustModel);
            }
        }
        if (loanAdjustList.size() > 0) {
            log.info("生成贷款形态调整数据：" + loanAdjustList.size() + "条");
            loanAdjustService.saveBatch(loanAdjustList);
            log.info("保存成功：" + loanAdjustList.size() + "条");
        }
        Date endDate = new Date();
        log.info("AcctVoucherThread end..." + endDate);
        log.info("AcctVoucherThread end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween( endDate,startDate));

    }

    /**
     *  解析BusiCode
     * @param model
     * @return
     */
    private AcctBusiCodeModel[] getBusiCodeModel(AcctDataModel model, LoanAdjustModel loanAdjustModel){
        String jsonMsg = model.getMessage(), scene = model.getScene(), productCode = model.getProductCode();
        //形态转移专用，用id关联DETAIL新表
        AcctBusiCodeModel[] models = new AcctBusiCodeModel[]{new AcctBusiCodeModel(), new AcctBusiCodeModel()};
        String message = acctDataService.selectMessage(model.getId());
        //网贷调整，ID对应多个ACCT_DATA_ID，多个busiCode值一样
        String newBusiCode = BusinessUtils.getValueByKey(message,"busiCode"), oldBusiCode = "", newCalanceIdentifier = "", oldCalanceIdentifier = "";
        if(AcctRecordScene.IDLE_TO_NORMAL_OR_OVERDUE.equals(scene)){

            oldBusiCode = productCode + BusiCodeConstant.PROC_CODE_IDLE;
            oldCalanceIdentifier = BusiCodeConstant.DZHIBJIN;
            if(BusiCodeConstant.PROC_CODE_NORMAL.equals(newBusiCode.replace(productCode,""))){
                newCalanceIdentifier = BusiCodeConstant.ZHCHBJIN;

                loanAdjustModel.setBalanceAmount(model.getNormalPrincipal().toPlainString());//转正常金额
            }else if(BusiCodeConstant.PROC_CODE_OVERDUE.equals(newBusiCode.replace(productCode,""))){
                newCalanceIdentifier = BusiCodeConstant.YUQIBJIN;

                loanAdjustModel.setBalanceAmount(model.getOverduePrincipal().toPlainString());//转逾期金额
            }
        }else if(AcctRecordScene.NORMAL_TO_OVERDUE.equals(scene)){
            oldBusiCode = productCode + BusiCodeConstant.PROC_CODE_NORMAL;
            newCalanceIdentifier = BusiCodeConstant.YUQIBJIN;
            oldCalanceIdentifier = BusiCodeConstant.ZHCHBJIN;

            loanAdjustModel.setBalanceAmount(model.getOverduePrincipal().toPlainString());//转逾期金额
        }else if(AcctRecordScene.OVERDUE_TO_IDLE.equals(scene)){
            oldBusiCode = productCode + BusiCodeConstant.PROC_CODE_OVERDUE;
            newCalanceIdentifier = BusiCodeConstant.DZHIBJIN;
            oldCalanceIdentifier = BusiCodeConstant.YUQIBJIN;

            loanAdjustModel.setBalanceAmount(model.getIdlePrincipal().toPlainString());//转呆滞金额
        }
        models[0] = acctBusiCodeService.getModelByBusiCode(oldBusiCode, oldCalanceIdentifier);
        models[1] = acctBusiCodeService.getModelByBusiCode(newBusiCode,newCalanceIdentifier);
        return models;
    }
}
