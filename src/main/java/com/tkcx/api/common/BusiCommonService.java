package com.tkcx.api.common;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.AcctLogModel;
import com.tkcx.api.business.acctPrint.service.AcctLogService;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.wdData.dao.CommonMapper;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.business.wdData.model.CardiiModel;
import com.tkcx.api.business.wdData.model.RepayAssemblyRecordModel;
import com.tkcx.api.business.wdData.service.AcctDataService;
import com.tkcx.api.business.wdData.service.AssetService;
import com.tkcx.api.business.wdData.service.CardiiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公共业务查询类
 *
 * @author cuijh
 * @since 2019-08-09
 */
@Component
public class BusiCommonService {

    private static final int TOTAL_SIZE = 10000;


    @Autowired
    private AcctDataService acctDataService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AcctDetailTempService acctDetailTempService;

    @Autowired
    private CardiiService cardiiService;

    @Autowired
    private AcctLogService acctLogService;

    @Resource
    private CommonMapper commonMapper;

    /**
     * 科目控制字
     *
     * @param transSeqNo 核算交易流水号
     * @return
     */
    public String selectItemCtrl(String transSeqNo, String debtFlag) {
        AcctDetailTempModel acctDetailTemp = new AcctDetailTempModel();
        try {
            acctDetailTemp.setChannelSeq(transSeqNo);
            if(StringUtils.isNotEmpty(transSeqNo) && StringUtils.isNotEmpty(debtFlag)){
                acctDetailTemp = acctDetailTempService.getDetailBySeq(transSeqNo,debtFlag);
            }
        }catch (Exception e){

        }finally {
            if (null != acctDetailTemp) {
                return acctDetailTemp.getItemCtrl();
            }else {
                return null;
            }
        }

    }

    /**
     * 借贷标识
     *
     * @param bizTrackNo 核算交易流水号
     * @return
     */
    public String selectDebtFlag(String bizTrackNo) {
        AcctDetailTempModel acctDetailTemp = new AcctDetailTempModel();
        acctDetailTemp.setChannelSeq(bizTrackNo);

        acctDetailTemp = acctDetailTempService.selectOne(acctDetailTemp);
        if (null != acctDetailTemp) {
            return acctDetailTemp.getDebtFlag();
        }
        return null;
    }


    /**
     * 借据号
     *
     * @param assetItemNo  资产编号
     * @return
     */
    public String selectDebtNo(String assetItemNo) {
        AssetModel asset = new AssetModel();
        asset.setAssetItemNo(assetItemNo);

        asset = assetService.selectOne(asset);
        if (null != asset) {
            return asset.getAssetDebtNo();
        }
        return null;
    }
    /**
     * 资产信息
     * @param assetItemNo  资产编号
     * @return
     */
    public AssetModel getAssetModel(String assetItemNo) {
        AssetModel asset = new AssetModel();
        asset.setAssetItemNo(assetItemNo);
        asset = assetService.selectOne(asset);
        if (null != asset) {
            return asset;
        }
        return null;
    }
    /**
     * 资产信息
     * @param assetItemNo  资产编号
     * @return
     */
    public AssetModel getAssetModel(String assetItemNo, String... columns) {
        QueryWrapper<AssetModel> queryWrapper = new QueryWrapper<>();
        if(columns != null)
            queryWrapper.select(columns);
        queryWrapper.eq("ASSET_ITEM_NO",assetItemNo);
        AssetModel asset = assetService.getOne(queryWrapper);
        if (null != asset) {
            return asset;
        }
        return null;
    }

    /**
     * 户名
     *
     * @param idNum  身份证号码
     * @return
     */
    public String selectAccName(String idNum) {
        CardiiModel cardii = new CardiiModel();
        cardii.setCardiiIdnum(idNum);
        cardii = cardiiService.selectOne(cardii);
        if (null != cardii) {
            return cardii.getCardiiName();
        }
        return null;
    }


    /**
     * 二类卡账号
     *
     * @param idNum  身份证号码
     * @return
     */
    public String selectAccCode(String idNum) {
        CardiiModel cardii = new CardiiModel();
        cardii.setCardiiIdnum(idNum);
        cardii = cardiiService.selectOne(cardii);
        if (null != cardii) {
            return cardii.getCardiiAccount();
        }
        return null;
    }

    /**
     * 根据业务流水获取渠道流水
     * @param bizTrackNo
     * @return
     */
    public String selectChannelSeq(String bizTrackNo) {
        AcctDataModel acctData = new AcctDataModel();
        acctData.setBizTrackNo(bizTrackNo);
        acctData = acctDataService.selectOne(acctData);
        if (null != acctData) {
            return acctData.getTransSeqNo();
        }
        return null;
    }
    public String selectPrincipalAmount(String assetItemNo) {
        AssetModel asset = new AssetModel();
        asset.setAssetItemNo(assetItemNo);

        asset = assetService.selectOne(asset);
        if (null != asset) {
            asset.getAssetPrincipalAmount();
            return asset.getAssetDebtNo();
        }
        return null;
    }

    /**
     * 获取会计日期时间
     * @param
     * @return
     */
    public Date getCoreSysDate(){
        String sql = "select d.CORE_SYS_DATE from QN_DB_BIZ.CORE_SYS_DATE d where d.ASSET_TYPE = 'qn_qinyidai' and d.SYSTEM_STATUS = 'running' ";
        return (Date) commonMapper.getBySql(sql);
    }

    /**
     * 根据机构编号获取机构名称
     * @param orgCode
     * @return
     */
    public String getOrgNameByCode(String orgCode) {
        if (StringUtils.isNotEmpty(orgCode)) {
            return (String) commonMapper.getBySql("select org.ORG_NAME from QN_DB_ACCT.ACCT_ORG_TEMP org where org.ORG_CODE = '"+orgCode+"' and org.STATUS = '1'");
        }
        return "";
    }


    public String getVoucherNo(String orgCode, String date) {

        StringBuilder builder = new StringBuilder("WD");
        // 拿到数据库序列号
        int sn = getNextSn();
        // 超过4位数后从头开始
        int suffix = sn % TOTAL_SIZE;
        // 虚列号不足8位的前边用0补足
        String serialNumber = String.format("%04d", suffix);
        return builder.append(orgCode).append(date).append(serialNumber).toString();
    }


    private int getNextSn() {
        BigDecimal nextSn = (BigDecimal) commonMapper
                .getBySql("select QN_DB_ACCT.VOUCHER_NO_SEQ.NEXTVAL from dual");

        return nextSn.intValue();
    }
    /**
     * 根据资产编号获取合同号
     * @param assetItemNo
     * @return
     */
    public String getContractNoByAssetItemNo(String assetItemNo) {
        /*if (StringUtils.isNotEmpty(assetItemNo)) {
            return (String) commonMapper.getBySql("select a.ASSET_ATTACHMENT_CONTRACT_CODE from QN_DB_BIZ.ASSET_ATTACHMENT a where a.ASSET_ATTACHMENT_ASSET_ITEM_NO = '"+assetItemNo+"' and a.ASSET_ATTACHMENT_TYPE = 'bank_loan_contract'");
        }*/
        //我改的
        try {
            if (StringUtils.isNotEmpty(assetItemNo)) {
                String type = (String) commonMapper.getBySql("select a.asset_type from QN_DB_BIZ.ASSET a where a.ASSET_ITEM_NO = '" + assetItemNo + "'");
                Object object = commonMapper.getBySql("select a.ASSET_ATTACHMENT_CONTRACT_CODE from QN_DB_BIZ.ASSET_ATTACHMENT a where a.ASSET_ATTACHMENT_ASSET_ITEM_NO = '"+assetItemNo+"' and a.ASSET_ATTACHMENT_TYPE = 'bank_loan_contract'");
                if (object==null) {
                    return (String) commonMapper.getBySql("select a.ASSET_ATTACHMENT_CONTRACT_CODE from QN_DB_BIZ.ASSET_ATTACHMENT a where a.ASSET_ATTACHMENT_ASSET_ITEM_NO = '" + assetItemNo + "' and a.ASSET_ATTACHMENT_TYPE like '" + type + "%contract'");
                }else {
                    return (String) object;
                }}


        } catch (Exception e) {
            System.out.println(assetItemNo);
            throw new RuntimeException(e);
        }
        return "";
    }

    /**
     * 获取历史已还本金金额（包含本次）
     *
     * @param assetItemNo   资产编号
     * @param repayAssemblyId   还款总成ID
     * @return
     */
    public BigDecimal getRepaidPrincipalHistory(String assetItemNo, int repayAssemblyId) {
        BigDecimal repaidPrincipal = new BigDecimal(0);
        if (StringUtils.isEmpty(assetItemNo)) {
            return repaidPrincipal;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("select sum(t.PRINCIPAL_REPAY_AMOUNT) as principalRepayAmount ");
        buffer.append("from QN_DB_BIZ.REPAY_PERIOD_RECORD t where t.ASSET_NO = ");
        buffer.append("'").append(assetItemNo).append("'");
        buffer.append("and t.ASSEMBLY_ID <= ").append(repayAssemblyId);

        BigDecimal obj = (BigDecimal)commonMapper.getBySql(buffer.toString());
        if (null == obj) {
            return repaidPrincipal;
        }
        return obj;

    }

    /**
     * 获取已还利息金额（包含本次）
     *
     * @param assetItemNo   资产编号
     * @param repayAssemblyId   还款总成ID
     * @return
     */
    public BigDecimal getRepaidInterestHistory(String assetItemNo, int repayAssemblyId) {
        BigDecimal repaidInterest = new BigDecimal(0);
        if (StringUtils.isEmpty(assetItemNo)) {
            return repaidInterest;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("select sum(t.CURR_REPAY_AMOUNT - t.PRINCIPAL_REPAY_AMOUNT) as repaidInterestHis ");
        buffer.append("from QN_DB_BIZ.REPAY_PERIOD_RECORD t where t.ASSET_NO = ");
        buffer.append("'").append(assetItemNo).append("'");
        buffer.append("and t.ASSEMBLY_ID <= ").append(repayAssemblyId);

        BigDecimal obj = (BigDecimal)commonMapper.getBySql(buffer.toString());
        if (null == obj) {
            return repaidInterest;
        }
        return obj;

    }
    /**
     * 获取已还利息金额（包含本次）
     *
     * @param assetItemNo   资产编号
     * @param repayAssemblyId   还款总成ID
     * @return
     */
    public BigDecimal getRepayRepaidAmount(String assetItemNo, int repayAssemblyId) {
        BigDecimal repaidInterest = new BigDecimal(0);
        if (StringUtils.isEmpty(assetItemNo)) {
            return repaidInterest;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("select sum(t.PRINCIPAL_REPAY_AMOUNT) as PRINCIPAL_REPAY_AMOUNT ");
        buffer.append("from QN_DB_BIZ.REPAY_PERIOD_RECORD t where t.ASSET_NO = ");
        buffer.append("'").append(assetItemNo).append("'");
        buffer.append("and t.ASSEMBLY_ID = ").append(repayAssemblyId);

        BigDecimal obj = (BigDecimal)commonMapper.getBySql(buffer.toString());
        if (null == obj) {
            return repaidInterest;
        }
        return obj;

    }

    /**
     * 获取逾期利息
     *
     * @param assetItemNo   资产编号
     * @param coreSysDate   核心日期,yyyy-MM-dd
     * @return
     */
    public BigDecimal getOverdueInterest(String assetItemNo, String coreSysDate) {
        BigDecimal overdueInterest = new BigDecimal(0);
        if (StringUtils.isEmpty(assetItemNo)) {
            return overdueInterest;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("select SUM(t.CURR_SETTLE_AMOUNT) as CURRACCRUALAMOUNT ");
        buffer.append("from QN_DB_BIZ.INTEREST_SETTLE t where t.ASSET_ITEM_NO = ");
        buffer.append("'").append(assetItemNo).append("' ");
        buffer.append("and t.CREATE_CORE_SYSTE_DATE <= TO_DATE('").append(coreSysDate).append("', 'yyyy-MM-dd')");
        buffer.append(" and t.SETTLE_TYPE != 'interest_settle'");

        BigDecimal obj = (BigDecimal)commonMapper.getBySql(buffer.toString());
        if (null == obj) {
            return overdueInterest;
        }
        return obj;

    }
    /**
     * 获取该笔资产已还本金
     *
     * @param assetItemNo   资产编号
     * @return
     */
    public BigDecimal getSumRepaidPrincipalbyAssetItemNo(String assetItemNo, String repayPlanNo) {
        BigDecimal result = new BigDecimal(0);
        if (StringUtils.isEmpty(assetItemNo)) {
            return result;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("select SUM(t.REPAID_PRINCIPAL) ");
        buffer.append("from QN_DB_BIZ.REPAY_PLAN_PROGRESS t where t.ASSET_ITEM_NO = ");
        buffer.append("'").append(assetItemNo).append("' ");
        buffer.append("and t.REPAY_PLAN_NO = '"+repayPlanNo+"' ");
        buffer.append("and t.PROGRESS_REASON = 'repay' ");

        result = (BigDecimal) commonMapper.getBySql(buffer.toString());
        if (null == result) {
            return new BigDecimal(0);
        }
        return result;

    }

    /**
     * 获取当天利息计提信息
     *
     * @param selectDate
     * @return
     */
    public List<Map<String, Object>> selectInterestAccrual(Date selectDate) {
        String ddate = DateUtil.formatDate(selectDate);

        StringBuffer buffer = new StringBuffer();
        /*buffer.append("SELECT  acc.ASSET_ITEM_NO , acc.REPAY_PLAN_NO, acc.ACCRUAL_SUM_AF, acc.ACCRUAL_TYPE, acc.CREATE_CORE_SYSTE_DATE ");
        buffer.append("FROM QN_DB_BIZ.INTEREST_ACCRUAL  acc WHERE  acc.INTEREST_ACCRUAL_ID in(");
        buffer.append("SELECT  MAX(t.INTEREST_ACCRUAL_ID)");
        buffer.append("FROM QN_DB_BIZ.INTEREST_ACCRUAL t, QN_DB_BIZ.REPAY_PLAN rp ");
        buffer.append("WHERE rp.REPAY_PLAN_NO = t.REPAY_PLAN_NO ");
        buffer.append("AND t.ACCRUAL_TYPE <> 'interest_accr' ");
        buffer.append("AND (t.CREATE_CORE_SYSTE_DATE = TO_DATE('");
        buffer.append(ddate).append("', 'yyyy-MM-dd') or (");
        buffer.append("rp.repay_plan_status = 'finish' ");
        buffer.append("AND t.CREATE_CORE_SYSTE_DATE = TO_DATE('");
        buffer.append(ddate).append("', 'yyyy-MM-dd')))   GROUP BY   t.ASSET_ITEM_NO )  ");

        buffer.append("UNION ALL SELECT t.ASSET_ITEM_NO, t.REPAY_PLAN_NO, t.ACCRUAL_SUM_AF, t.ACCRUAL_TYPE, t.CREATE_CORE_SYSTE_DATE ");
        buffer.append("FROM QN_DB_BIZ.INTEREST_ACCRUAL t, QN_DB_BIZ.REPAY_PLAN rp ");
        buffer.append("WHERE rp.REPAY_PLAN_NO = t.REPAY_PLAN_NO ");
        buffer.append("AND t.ACCRUAL_TYPE = 'interest_accr' ");
        buffer.append("AND t.CREATE_CORE_SYSTE_DATE = TO_DATE('");
        buffer.append(ddate).append("', 'yyyy-MM-dd') ");
        buffer.append("AND t.CREATE_CORE_SYSTE_DATE + 1 = rp.REPAY_PLAN_DUE_AT ");
        buffer.append("AND rp.REPAY_PLAN_ACTUAL_INTEREST > 0 ");*/

        buffer.append("SELECT t.ASSET_ITEM_NO, MAX(t.REPAY_PLAN_NO) as REPAY_PLAN_NO, ANY_VALUE(t.INTEREST_SETTLE_ID) as INTEREST_SETTLE_ID, ");
        buffer.append("ANY_VALUE(t.SETTLE_TYPE) as SETTLE_TYPE, ANY_VALUE(t.CREATE_CORE_SYSTE_DATE) as CREATE_CORE_SYSTE_DATE ");
        buffer.append("FROM QN_DB_BIZ.INTEREST_SETTLE t, QN_DB_BIZ.REPAY_PLAN r ");
        buffer.append("WHERE t.REPAY_PLAN_NO = r.REPAY_PLAN_NO ");
        buffer.append("AND r.REPAY_PLAN_DUE_AT <= TO_DATE('").append(ddate).append("', 'yyyy-MM-dd') ");
        buffer.append("AND t.CREATE_CORE_SYSTE_DATE = TO_DATE('").append(ddate).append("', 'yyyy-MM-dd') ");
        buffer.append("GROUP BY t.ASSET_ITEM_NO");

        List<Map<String, Object>> list = commonMapper.selectListBySql(buffer.toString());

        return list;
    }

    /**
     * 获取当前还款最大期次
     * @param repayAssemblyId
     * @return
     */
    public Integer getRepayPeriod(Integer repayAssemblyId){
        StringBuffer buffer = new StringBuffer();
        buffer.append("select max(t.REPAY_PERIOD)  ");
        buffer.append("from QN_DB_BIZ.REPAY_PERIOD_RECORD t where t.ASSEMBLY_ID = "+repayAssemblyId+" ");
        BigDecimal maxNo = (BigDecimal)commonMapper.getBySql(buffer.toString());
        return Integer.valueOf(maxNo.toPlainString());
    }

    /**
     * 判断是否一次结清
     *
     * @param repayAssemblyId
     * @return
     */
    public boolean getFinanceStatusByRepay(Integer repayAssemblyId) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT h.ASSET_STATUS_AF FROM QN_DB_BIZ.ASSET_HISTORY h, QN_DB_BIZ.REPAY_ASSEMBLY_RECORD r ");
        buffer.append("WHERE h.ASSET_HISTORY_ID = r.ASSET_HISTORY_ID ");
        buffer.append("AND r.REPAY_ASSEMBLY_ID = ").append(repayAssemblyId);
        String status = (String) commonMapper.getBySql(buffer.toString());

        if ("payoff".equals(status)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取截止当前历史未还本金, 获取截止当前历史未还本金利息,获取截止当前历史未还逾期利息
     * @param assetItemNo
     * @param repayAssemblyId
     * @return
     */
    public BigDecimal[] getPrincipalHistory(String assetItemNo, Integer repayAssemblyId, Date sysDate) {
        BigDecimal principal0,interest0,accrual0,principal1,interest1,accrual1;
        if (StringUtils.isEmpty(assetItemNo)) {
            return null;
        }

        boolean payoffFlag = this.getFinanceStatusByRepay(repayAssemblyId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT  ");
        buffer.append("NVL(SUM( t.REPAY_PLAN_NO_REPAY_PRINCIPAL ), 0) AS PRINCIPAL,  ");
        buffer.append("NVL(SUM( t.REPAY_PLAN_NO_REPAY_INTEREST ), 0) AS INTEREST  ");
        buffer.append("FROM  ");
        buffer.append("QN_DB_BIZ.REPAY_PLAN t ");
        buffer.append("WHERE  ");
        buffer.append("t.REPAY_PLAN_ASSET_ITEM_NO = '"+assetItemNo+"'  ");
        buffer.append("AND t.REPAY_PLAN_DUE_AT <= TO_DATE('").append(DateUtil.formatDate(sysDate)).append("', 'yyyy-MM-dd')");

        buffer.append("UNION ALL  ");
        buffer.append("SELECT  ");
        buffer.append("NVL(sum( rp.PRINCIPAL_REPAY_AMOUNT ), 0) AS PRINCIPAL,  ");
        buffer.append("NVL(sum( rp.INTEREST_REPAY_AMOUNT + rp.OFFBL_INTEREST_REPAY_AMOUNT ), 0) AS INTEREST  ");
        buffer.append("FROM  ");
        buffer.append("QN_DB_BIZ.REPAY_PERIOD_RECORD rp, QN_DB_BIZ.REPAY_PLAN t ");
        buffer.append("WHERE rp.REPAY_PLAN_NO = t.REPAY_PLAN_NO ");
        buffer.append("AND rp.ASSET_NO = '"+assetItemNo+"'  ");
        buffer.append("AND rp.ASSEMBLY_ID >= "+repayAssemblyId+"  ");
        if (!payoffFlag) {
            buffer.append("AND t.REPAY_PLAN_DUE_AT <= TO_DATE('").append(DateUtil.formatDate(sysDate)).append("', 'yyyy-MM-dd') ");
        }
        // buffer.append("AND RP.CORE_SYS_DATE <= TO_DATE('").append(DateUtil.formatDate(sysDate)).append("', 'yyyy-MM-dd')");

        List<Map<String, Object>> shouldobj = commonMapper.selectListBySql(buffer.toString());

        buffer = new StringBuffer();
        buffer.append("SELECT  ");
        buffer.append("NVL(SUM( acc.CURR_SETTLE_AMOUNT ), 0) AS ACCRUAL  ");
        buffer.append("FROM  ");
        buffer.append("QN_DB_BIZ.INTEREST_SETTLE acc  ");
        buffer.append("WHERE  ");
        buffer.append("acc.ASSET_ITEM_NO = '"+assetItemNo+"'  ");
        buffer.append("AND acc.SETTLE_TYPE <> 'interest_settle'  ");
        buffer.append("AND acc.CREATE_CORE_SYSTE_DATE <= TO_DATE('").append(DateUtil.formatDate(sysDate)).append("', 'yyyy-MM-dd')");

        accrual0 = (BigDecimal) commonMapper.getBySql(buffer.toString());

        buffer = new StringBuffer();
        buffer.append("SELECT  ");
        buffer.append("NVL(sum( rp.CURR_REPAY_AMOUNT - rp.PRINCIPAL_REPAY_AMOUNT - rp.INTEREST_REPAY_AMOUNT - rp.OFFBL_INTEREST_REPAY_AMOUNT ), 0) AS ACCRUAL  ");
        buffer.append("FROM  ");
        buffer.append("QN_DB_BIZ.REPAY_PERIOD_RECORD rp  ");
        buffer.append("WHERE  ");
        buffer.append("rp.ASSET_NO = '"+assetItemNo+"'  ");
        buffer.append("AND rp.ASSEMBLY_ID < "+repayAssemblyId+"  ");

        accrual1 = (BigDecimal) commonMapper.getBySql(buffer.toString());

        BigDecimal[] result = new BigDecimal[3];
        if(shouldobj!=null && shouldobj.size()==2){
            principal0 = (BigDecimal)shouldobj.get(0).get("PRINCIPAL") ;
            interest0 = (BigDecimal)shouldobj.get(0).get("INTEREST");
            principal1 = (BigDecimal)shouldobj.get(1).get("PRINCIPAL");
            interest1 = (BigDecimal)shouldobj.get(1).get("INTEREST");
            result[0] = principal0.add(principal1);
            result[1] = interest0.add(interest1);
            result[2] = accrual0.subtract(accrual1);
        }
        return result;
    }
    /**
     * 本次已还本金, 本次已还本金利息,本次已还逾期利息
     * @param repayAssemblyId
     * @return
     */
    public BigDecimal[] getPayoffPrincipalHistory(Integer repayAssemblyId) {
        if (repayAssemblyId==null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT  ");
        buffer.append("NVL(sum( rp.PRINCIPAL_REPAY_AMOUNT ), 0) AS PRINCIPAL,  ");
        buffer.append("NVL(sum( rp.INTEREST_REPAY_AMOUNT + rp.OFFBL_INTEREST_REPAY_AMOUNT ), 0)  AS INTEREST,  ");
        buffer.append("NVL(sum( rp.CURR_REPAY_AMOUNT - rp.PRINCIPAL_REPAY_AMOUNT - rp.INTEREST_REPAY_AMOUNT - rp.OFFBL_INTEREST_REPAY_AMOUNT ), 0)  AS ACCRUAL  ");
        buffer.append("FROM  ");
        buffer.append("QN_DB_BIZ.REPAY_PERIOD_RECORD rp  ");
        buffer.append("WHERE  ");
        buffer.append("rp.ASSEMBLY_ID = "+repayAssemblyId+"  ");
        Map<String, Object> shouldobj = commonMapper.getMapBySql(buffer.toString());
        BigDecimal[] result = new BigDecimal[3];
        if(shouldobj!=null){
            result[0] = (BigDecimal)shouldobj.get("PRINCIPAL");
            result[1] = (BigDecimal)shouldobj.get("INTEREST");
            result[2] = (BigDecimal)shouldobj.get("ACCRUAL");
        }
        return result;
    }


    /**
     * 获取利息计提对应的历史本金和利息
     *
     * @param assetItemNo
     * @param repayPlanNo
     * @return
     */
    public Map<String, Object> selectPrincipalInterestHis(String assetItemNo, String repayPlanNo) {


        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT NVL(SUM(REPAY_PLAN_PRINCIPAL), 0) PRINCIPAL, NVL(SUM(REPAY_PLAN_ACTUAL_INTEREST), 0) INTEREST ");
        buffer.append("FROM QN_DB_BIZ.REPAY_PLAN t WHERE t.REPAY_PLAN_ASSET_ITEM_NO = '").append(assetItemNo).append("' ");
        buffer.append("AND t.REPAY_PLAN_NO <= '").append(repayPlanNo).append("'");

        Map<String, Object> map = commonMapper.getMapBySql(buffer.toString());

        return map;
    }

    /**
     * 获取利息计提对应的已还本金和利息
     *
     * @param assetItemNo
     * @param repayPlanNo
     * @return
     */
    public Map<String, Object> selectPrincipalInterestRepaid(String assetItemNo, String repayPlanNo, Date endDate) {


        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT NVL(SUM(CURR_REPAY_AMOUNT), 0) CURR_REPAY_AMOUNT, NVL(SUM(PRINCIPAL_REPAY_AMOUNT), 0) PRINCIPAL_REPAY_AMOUNT, ");
        buffer.append("NVL(SUM(INTEREST_REPAY_AMOUNT + OFFBL_INTEREST_REPAY_AMOUNT), 0) INTEREST_REPAY_AMOUNT ");
        buffer.append("FROM QN_DB_BIZ.REPAY_PERIOD_RECORD p WHERE p.ASSET_NO = '").append(assetItemNo).append("' ");
        buffer.append("AND p.REPAY_PLAN_NO <= '").append(repayPlanNo).append("'");
        buffer.append("AND p.CORE_SYS_DATE < TO_DATE('"+DateUtil.formatDate(DateUtil.offsetDay(endDate,-1))+"', 'yyyy-MM-dd')  ");

        Map<String, Object> map= commonMapper.getMapBySql(buffer.toString());

        return map;
    }

    public BigDecimal selectOverInterestHis(String assetItemNo, String repayPlanNo, Date sysDate) {


        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT NVL(SUM(CURR_SETTLE_AMOUNT), 0) OVERDUE_AMOUNT ");
        buffer.append("FROM QN_DB_BIZ.INTEREST_SETTLE i WHERE i.ASSET_ITEM_NO = '").append(assetItemNo).append("' ");
        buffer.append("AND i.SETTLE_TYPE <> 'interest_settle' ");
        buffer.append("AND i.REPAY_PLAN_NO <= '").append(repayPlanNo).append("'");
        buffer.append("AND i.CREATE_CORE_SYSTE_DATE <= TO_DATE('")
                .append(DateUtil.formatDate(DateUtil.offsetDay(sysDate,-1))).append("', 'yyyy-MM-dd')");

        return (BigDecimal)commonMapper.getBySql(buffer.toString());
    }

    /**
     * 获取实际利息
     *
     * @param assetItemNo
     * @param repayAssemblyId
     * @return
     */
    public BigDecimal getInterestAmountByAsset(String assetItemNo, Integer repayAssemblyId) {
        Integer repayPeriod = getRepayPeriod(repayAssemblyId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("(SELECT NVL(SUM(t.REPAY_PLAN_ACTUAL_INTEREST), 0) FROM QN_DB_BIZ.REPAY_PLAN t ");
        buffer.append("WHERE t.REPAY_PLAN_ASSET_ITEM_NO = '").append(assetItemNo).append("' ");
        buffer.append("AND t.REPAY_PLAN_PERIOD <= ").append(repayPeriod).append(")");
        buffer.append("+(SELECT NVL(SUM(t.REPAY_PLAN_INTEREST), 0) FROM QN_DB_BIZ.REPAY_PLAN t ");
        buffer.append("WHERE t.REPAY_PLAN_ASSET_ITEM_NO = '").append(assetItemNo).append("' ");
        buffer.append("AND t.REPAY_PLAN_PERIOD > ").append(repayPeriod).append(")");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT (").append(buffer).append(") FROM dual");

        return (BigDecimal)commonMapper.getBySql(sql.toString());
    }

    /**
     * 判断当天是否存在互金数据
     * @param acctData
     * @return
     */
    public boolean existHjData(Date acctData) {
        AcctLogModel model = new AcctLogModel();
        model.setAcctDate(acctData);
        model = acctLogService.selectOne(model);

        if (model == null) {
            return false;
        }
        return true;
    }
    /**
     * 根据记账流水查询对应还款信息
     * @param acctRecordNo
     * @return
     */
    public RepayAssemblyRecordModel getRepayAssemblyRecordByAcctRecordNo(String acctRecordNo){
        RepayAssemblyRecordModel rar = new RepayAssemblyRecordModel();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  ");
        sb.append("rar.REPAY_ASSEMBLY_ID as ASSEMBLY_ID, rar.REPAY_FINISH_TIME as FINISH_TIME, " +
                "rar.REPAY_CARD as CARD, rar.CORE_SYS_DATE  as ACCT_DATE, rar.ASSET_NO as ASSET_NO ");
        sb.append("FROM ");
        sb.append("QN_DB_BIZ.REPAY_PERIOD_RECORD rpr, QN_DB_BIZ.REPAY_ASSEMBLY_RECORD rar  ");
        sb.append("WHERE  ");
        sb.append("rpr.ASSEMBLY_ID = rar.REPAY_ASSEMBLY_ID  ");
        sb.append("AND  ");
        sb.append("rpr.ACCT_RECORD_NO =  '");
        sb.append(acctRecordNo);
        sb.append("'  ");
        Map<String, Object> resultMap = commonMapper.getMapBySql(sb.toString());
        BigDecimal bigInt = (BigDecimal) resultMap.get("ASSEMBLY_ID");
        rar.setRepayAssemblyId(bigInt.intValue());
        rar.setAssetNo((String) resultMap.get("ASSET_NO"));
        rar.setRepayFinishTime((Date) resultMap.get("FINISH_TIME"));
        rar.setRepayCard((String) resultMap.get("CARD"));
        rar.setCoreSysDate((Date) resultMap.get("ACCT_DATE"));
        return rar;
    }
}
