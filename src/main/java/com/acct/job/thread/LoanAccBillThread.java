package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.wdData.model.*;
import com.tkcx.api.constant.AcctRecordScene;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款分户账
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class LoanAccBillThread extends AcctBaseThread {

    private Map<String, LoanAccBillModel> modelMap = new HashMap<>();
    private LoanAccBillModel loanAccBillModel;
    private LoanAccBillModel normalModel, overdueModel, idleModel;
    private AssetModel asset;
    private CardiiModel cardii;

    public LoanAccBillThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run() {
        log.info("LoanAccBillThread start...");
        // 查询网贷放款记录信息
        assetGrantRecordData();
        // 查询网贷资产信息
        filterAcctDataMakeAccBill();
        log.info("共生成数据" + modelMap.size() + "条");
        // 保存贷款分户账
        loanAccBillService.saveOrUpdateBatch(modelMap.values());
        log.info("保存数据成功....");
        log.info("LoanAccBill record is {}.", modelMap.size());
    }

    /**
     * 放款信息
     */
    private void assetGrantRecordData() {
        QueryWrapper<AssetGrantRecordModel> queryWrapperGrant = getQueryWrapper(AssetGrantRecordModel.class, "assetGrantRecordCreateAt");
        queryWrapperGrant.select("ASSET_GRANT_RECORD_ASSET_ITEM_NO", "ASSET_GRANT_RECORD_REQ_TX", "CORE_SYS_DATE", "ASSET_GRANT_RECORD_LOAD_ACCOUNT");
        try {
            //查询网贷放款记录
            List<AssetGrantRecordModel> assetGrantRecordList = assetGrantRecordService.list(queryWrapperGrant);
            if (assetGrantRecordList != null && assetGrantRecordList.size() > 0) {
                log.info("AssetGrantRecordModel 查询数据" + assetGrantRecordList.size() + "条");
                for (AssetGrantRecordModel model : assetGrantRecordList) {
                    if (model.getAssetGrantRecordLoadAccount() == null) {
                        continue;
                    }
                    loanAccBillModel = new LoanAccBillModel();
                    String assetItemNo = model.getAssetGrantRecordAssetItemNo();
                    // 查询网贷资产明细
                    asset = assetService.getAssetByItemNo(assetItemNo);

                    if (asset == null || !("repay".equals(asset.getAssetStatus()) || "payoff".equals(asset.getAssetStatus()))) {
                        log.info("资产编号====" + assetItemNo + "放款数据异常");
                        continue;
                    }
                    cardii = cardiiService.getCardiiByCardiiIdnum(asset.getAssetBorrowerIdnum());
                    if (cardii != null) {
                        loanAccBillModel.setLoanName(cardii.getCardiiName());
                        loanAccBillModel.setGrantAccount(cardii.getCardiiAccount());//二类卡账号
                    } else {
                        log.info("资产编号====" + assetItemNo + "放款数据异常");
                        continue;
                    }
                    String seq = busiCommonService.selectChannelSeq(model.getAssetGrantRecordReqTx());
                    loanAccBillModel.setItem(busiCommonService.selectItemCtrl(seq, "D"));//互金数据

                    loanAccBillModel.setOrgCode(asset.getOrgid());
                    // 查询会计凭证ACCT_ORG_TEMP表
                    loanAccBillModel.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));

                    loanAccBillModel.setDebtNo(asset.getAssetDebtNo());//借据号
                    loanAccBillModel.setLoanAccount(asset.getAssetLoanAccount());//贷款账号

                    loanAccBillModel.setGrantDate(asset.getAssetActualGrantAt());
                    loanAccBillModel.setAccountStatus(EnumConstant.ACCOUNT_STATUS);

                    loanAccBillModel.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
                    //loanAccBillModel.setPayoffAccount(null);//还款为null
                    loanAccBillModel.setDueDate(asset.getAssetDueAt());
                    loanAccBillModel.setActualRate(asset.getAssetInterestRate());
                    loanAccBillModel.setGtantAmount(asset.getAssetPrincipalAmount().toPlainString());
                    loanAccBillModel.setBalanceAmount(asset.getAssetGrantedPrincipalAmount().toPlainString());//放款
                    loanAccBillModel.setPrincipalStatus(0);
                    //loanAccBillModel.setPayoffDate(asset.getAssetPayoffAt());
                    loanAccBillModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);//借款
                    loanAccBillModel.setAcctDate(model.getCoreSysDate());

                    modelMap.put(asset.getAssetLoanAccount() + "_0", loanAccBillModel);
                }
            }
        } catch (Exception e) {
            new RuntimeException();
            log.error("查询数据异常{}", e);
        } finally {
            log.info("AssetGrantRecordModel 生成数据" + modelMap.size() + "条");
        }
    }

    /**
     * 还款和形态调整信息
     */
    private void filterAcctDataMakeAccBill() {
        QueryWrapper<AcctDataModel> queryWrapperAcctData = getQueryWrapper(AcctDataModel.class, "createAt");
        queryWrapperAcctData.in("RET_STATUS", "0", "1");
        queryWrapperAcctData.in("SCENE", "normal_to_overdue", "overdue_to_idle", "idle_to_normal_or_overdue",
                "repay_normal", "repay_overdue", "repay_idle");
        queryWrapperAcctData.select("SCENE", "ASSET_ITEM_NO", "CREATE_AT", "BIZ_TRACK_NO",
                "MESSAGE", "PRODUCT_CODE", "ACG_DT", "TRANS_SEQ_NO", "REPAY_PLAN_NO",
                "NORMAL_PRINCIPAL", "OVERDUE_PRINCIPAL", "IDLE_PRINCIPAL", "BAD_PRINCIPAL");
        queryWrapperAcctData.orderByAsc("CREATE_AT");
        queryWrapperAcctData.last(" , REPAY_PLAN_NO NULLS LAST ");
        // 查询网贷资产信息
        List<AcctDataModel> acctDataList = acctDataService.list(queryWrapperAcctData);
        for (AcctDataModel model : acctDataList) {
            asset = assetService.getAssetByItemNo(model.getAssetItemNo());
            if (asset == null
                    || asset.getAssetLoanAccount() == null
                    || !("repay".equals(asset.getAssetStatus()) || "payoff".equals(asset.getAssetStatus()))) {
                log.info("资产编号====" + model.getAssetItemNo() + "数据异常");
                continue;
            }
            String scene = model.getScene();
            if ("normal_to_overdue".equals(scene)) {
                normalToOverdue(model);
            } else if ("overdue_to_idle".equals(scene)) {
                overdueToIdle(model);
            } else if ("idle_to_normal_or_overdue".equals(scene)) {
                idleToNormalOrOverdue(model);
            } else if ("repay_normal".equals(scene)) {
                repayNormal(model);
            } else if ("repay_overdue".equals(scene)) {
                repayOverdue(model);
            } else if ("repay_idle".equals(scene)) {
                repayIdle(model);
            }
        }
    }

    /**
     * 正常转逾期数据
     *
     * @param model
     */
    private void normalToOverdue(AcctDataModel model) {
        normalModel = getAccBillModel(asset.getAssetLoanAccount(), 0);
        if (null == normalModel) {
            return;
        }
        overdueModel = getAccBillModel(asset.getAssetLoanAccount(), 1);
        BigDecimal overduePrincipal = model.getOverduePrincipal().abs();
        if (null == overdueModel) {
            //执行新增
            overdueModel = new LoanAccBillModel();
            setCardiiData(asset);
            overdueModel.setLoanName(cardii.getCardiiName());
            //二类卡账号
            overdueModel.setGrantAccount(cardii.getCardiiAccount());
            // 查询会计凭证ACCT_BUSI_CODE表
            AcctBusiCodeModel busiCodeModel = acctBusiCodeService.getModelByBusiCode(BusinessUtils.getValueByKey(model.getMessage()
                    , "busiCode"), "YUQIBJIN");
            if (busiCodeModel != null) {
                //互金数据
                overdueModel.setItem(busiCodeModel.getItemCtrl());
            }
            overdueModel.setOrgCode(asset.getOrgid());
            overdueModel.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));
            //借据号
            overdueModel.setDebtNo(asset.getAssetDebtNo());
            //贷款账号
            overdueModel.setLoanAccount(asset.getAssetLoanAccount());
            overdueModel.setGrantDate(asset.getAssetActualGrantAt());
            overdueModel.setAccountStatus(EnumConstant.ACCOUNT_STATUS);
            overdueModel.setDueDate(asset.getAssetDueAt());
            overdueModel.setActualRate(asset.getAssetInterestRate());
            overdueModel.setGtantAmount(asset.getAssetPrincipalAmount().toPlainString());
            // 逾期金额
            overdueModel.setBalanceAmount(overduePrincipal.toPlainString());
            overdueModel.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
            overdueModel.setPrincipalStatus(1);
            // 借款
            overdueModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
            overdueModel.setAcctDate(DateUtil.parse(model.getAcgDt()));
            // 新增放入
            modelMap.put(getMapKey(asset, 1), overdueModel);
        } else {//执行更新
            overdueModel.setBalanceAmount(new BigDecimal(overdueModel.getBalanceAmount()).add(overduePrincipal).toPlainString());
        }
        //正常金额计算  = 历史本金余额-本次逾期金额
        normalModel.setBalanceAmount(new BigDecimal(normalModel.getBalanceAmount()).subtract(overduePrincipal).toPlainString());
    }

    /**
     * 逾期转呆滞数据
     *
     * @param model
     */
    private void overdueToIdle(AcctDataModel model) {
        normalModel = getAccBillModel(asset.getAssetLoanAccount(), 0);
        if (null == normalModel) return;
        overdueModel = getAccBillModel(asset.getAssetLoanAccount(), 1);
        idleModel = getAccBillModel(asset.getAssetLoanAccount(), 2);
        BigDecimal idlePrincipal = model.getIdlePrincipal().abs();
        if (null == idleModel) {//执行新增
            idleModel = new LoanAccBillModel();
            setCardiiData(asset);
            idleModel.setLoanName(cardii.getCardiiName());
            idleModel.setGrantAccount(cardii.getCardiiAccount());//二类卡账号
            AcctBusiCodeModel busiCodeModel = acctBusiCodeService.getModelByBusiCode(BusinessUtils.getValueByKey(model.getMessage()
                    , "busiCode"), "YUQIBJIN");
            if (busiCodeModel != null) {
                //互金数据
                idleModel.setItem(busiCodeModel.getItemCtrl());
            }
            idleModel.setOrgCode(asset.getOrgid());
            idleModel.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));
            idleModel.setDebtNo(asset.getAssetDebtNo());//借据号
            idleModel.setLoanAccount(asset.getAssetLoanAccount());//贷款账号
            idleModel.setGrantDate(asset.getAssetActualGrantAt());
            idleModel.setAccountStatus(EnumConstant.ACCOUNT_STATUS);
            idleModel.setDueDate(asset.getAssetDueAt());
            idleModel.setActualRate(asset.getAssetInterestRate());
            idleModel.setGtantAmount(asset.getAssetPrincipalAmount().toPlainString());
            idleModel.setBalanceAmount(idlePrincipal.toPlainString());//呆滞金额
            idleModel.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
            idleModel.setPrincipalStatus(2);
            idleModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);//借款
            idleModel.setAcctDate(DateUtil.parse(model.getAcgDt()));
            modelMap.put(getMapKey(asset, 2), idleModel);//新增放入
        }
        //正常金额计算  = 历史本金余额-本次逾期金额
        normalModel.setBalanceAmount("0");
        overdueModel.setBalanceAmount("0");
    }

    /**
     * 正常还款本金
     *
     * @param model
     */
    private void repayNormal(AcctDataModel model) {
        normalModel = getAccBillModel(asset.getAssetLoanAccount(), 0);
        if (null == normalModel) {
            return;
        }
        //本次还正常本金金额
        BigDecimal normalPrincipal = model.getNormalPrincipal().abs();
        normalModel.setBalanceAmount(new BigDecimal(normalModel.getBalanceAmount()).subtract(normalPrincipal).toPlainString());

        RepayAssemblyRecordModel rar = busiCommonService.getRepayAssemblyRecordByAcctRecordNo(model.getTransSeqNo());
        //本次还款本金总金额
        BigDecimal balanceAmount = busiCommonService.getRepayRepaidAmount(rar.getAssetNo(), rar.getRepayAssemblyId());

        normalModel.setPayoffAmount(balanceAmount.toPlainString());
        normalModel.setPayoffDate(rar.getRepayFinishTime());
        normalModel.setPayoffAccount(rar.getRepayCard());
        normalModel.setAcctDate(rar.getCoreSysDate());
    }

    /**
     * 逾期还款本金
     *
     * @param model
     */
    private void repayOverdue(AcctDataModel model) {
        overdueModel = getAccBillModel(asset.getAssetLoanAccount(), 1);
        if (null == overdueModel) {
            return;
        }
        //本次还逾期本金金额
        BigDecimal overduePrincipal = model.getOverduePrincipal().abs();
        overdueModel.setBalanceAmount(new BigDecimal(overdueModel.getBalanceAmount()).subtract(overduePrincipal).toPlainString());

        RepayAssemblyRecordModel rar = busiCommonService.getRepayAssemblyRecordByAcctRecordNo(model.getTransSeqNo());
        //本次还款本金总金额
        BigDecimal balanceAmount = busiCommonService.getRepayRepaidAmount(rar.getAssetNo(), rar.getRepayAssemblyId());

        overdueModel.setPayoffAmount(balanceAmount.toPlainString());
        overdueModel.setPayoffDate(rar.getRepayFinishTime());
        overdueModel.setPayoffAccount(rar.getRepayCard());
        overdueModel.setAcctDate(rar.getCoreSysDate());
    }

    /**
     * 呆滞还款本金
     * 更新三状态的还款信息
     *
     * @param model
     */
    private void repayIdle(AcctDataModel model) {
        idleModel = getAccBillModel(asset.getAssetLoanAccount(), 2);
        if (idleModel == null) {
            return;
        }
        RepayAssemblyRecordModel rar = busiCommonService.getRepayAssemblyRecordByAcctRecordNo(model.getTransSeqNo());
        BigDecimal balanceAmount = busiCommonService.getRepayRepaidAmount(rar.getAssetNo(), rar.getRepayAssemblyId());
        //更新呆滞还款信息
        idleModel.setPayoffAmount(balanceAmount.toPlainString());
        idleModel.setPayoffDate(rar.getRepayFinishTime());
        idleModel.setPayoffAccount(rar.getRepayCard());
        idleModel.setAcctDate(rar.getCoreSysDate());
    }

    /**
     * 呆滞转正常或逾期
     * 更新三状态的本金余额数值
     *
     * @param model
     */
    private void idleToNormalOrOverdue(AcctDataModel model) {
        normalModel = getAccBillModel(asset.getAssetLoanAccount(), 0);
        if (null == normalModel) {
            return;
        }
        overdueModel = getAccBillModel(asset.getAssetLoanAccount(), 1);
        idleModel = getAccBillModel(asset.getAssetLoanAccount(), 2);

        normalModel.setBalanceAmount(model.getNormalPrincipal().abs().toPlainString());
        overdueModel.setBalanceAmount(model.getOverduePrincipal().abs().toPlainString());
        idleModel.setBalanceAmount("0");
    }

    /**
     * 借款人信息
     *
     * @param asset
     */
    private void setCardiiData(AssetModel asset) {
        cardii = cardiiService.getCardiiByCardiiIdnum(asset.getAssetBorrowerIdnum());
        if (cardii == null) {
            cardii = new CardiiModel();
        }
    }

    /**
     * 获取map key
     *
     * @param asset
     * @return
     */
    private String getMapKey(AssetModel asset, Integer status) {
        return asset.getAssetLoanAccount() + "_" + status;
    }

    private BigDecimal getSceneAmount(AcctDataModel acct) {
        BigDecimal amount = new BigDecimal(0);
        if (AcctRecordScene.NORMAL_TO_OVERDUE.equals(acct.getScene())) {
            amount = acct.getOverduePrincipal();
        } else if (AcctRecordScene.OVERDUE_TO_IDLE.equals(acct.getScene())) {
            amount = acct.getIdlePrincipal();
        }
        return amount;
    }

    private LoanAccBillModel getAccBillModel(String account, Integer status) {
        LoanAccBillModel loanAccBillModel = modelMap.get(account + "_" + status);
        if (null == loanAccBillModel) {
            loanAccBillModel = loanAccBillService.getModelByLoanAccount(account, status);
            if (null != loanAccBillModel) {
                modelMap.put(account + "_" + status, loanAccBillModel);
            }
        }
        return loanAccBillModel;
    }
}
