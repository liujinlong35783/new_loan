package com.tkcx.api.business.hjtemp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.hjtemp.dao.AcctDetailTempDao;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.model.vo.BusiOrgBillVo;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Slf4j
@Service
public class AcctDetailTempService extends CommonService<AcctDetailTempDao,AcctDetailTempModel> {

    @Resource
    private AcctDetailTempDao acctDetailTempDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctDetailTempModel entity) {
        return acctDetailTempDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctDetailTempDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctDetailTempModel entity) {
        return this.updateById(entity);
    }

    
    public AcctDetailTempModel selectById(Integer id) {
        return acctDetailTempDao.selectById(id);
    }

    
    public AcctDetailTempModel selectOne(AcctDetailTempModel entity) {
        QueryWrapper<AcctDetailTempModel> queryWrapper = new QueryWrapper<>(entity);
        return acctDetailTempDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(AcctDetailTempModel model) {
        return acctDetailTempDao.selectModelCount(model);
    }

    public Integer selectCountNotInEleAccount(AcctDetailTempModel model) {
        return acctDetailTempDao.selectCountNotInEleAccount(model);
    }
    
    public List<AcctDetailTempModel> selectList(AcctDetailTempModel model) {
        return acctDetailTempDao.selectModelList(model);
    }

    public AcctDetailTempModel getDetailBySeq(String channelSeq, String debtFlag){
        return acctDetailTempDao.getDetailBySeq(channelSeq, debtFlag);
    }

    public List<AcctDetailTempModel> getDetailByAcctDate(Date acctDate){
        return acctDetailTempDao.getDetailByAcctDate(acctDate);
    }

    /**
     * 统计轧帐单
     * @param acctDate
     * @return
     */
    public  List<BusiOrgBillModel> statAllBusiOrgBillVo(Date acctDate){
        List<BusiOrgBillVo> voList = acctDetailTempDao.findStatAllBusiOrgBillVo(acctDate);
        List<BusiOrgBillModel> modelList = new ArrayList<>();
        String uid = "",debtFlag = "",offBalanceFlag = "";
        BigDecimal transAmount = null;
        BusiOrgBillModel busiOrgBill = null;
        for (BusiOrgBillVo vo: voList) {
            if(uid.equals(vo.getAcctOrg()+vo.getItemCtrl()) && busiOrgBill != null){//如果已存在
                if(debtFlag.equals(vo.getDebtFlag()) && offBalanceFlag.equals(vo.getOffBalanceFlag())){//表内外关系相同
                    transAmount = transAmount.add(vo.getTransAmount());
                    if("D".equals(vo.getDebtFlag())){
                        busiOrgBill.setDebtAmount(ToolUtil.yuanToFen(transAmount.toPlainString()));
                        busiOrgBill.setDebtNum(vo.getTransNum()+busiOrgBill.getDebtNum());
                    } else {
                        busiOrgBill.setCreditAmount(ToolUtil.yuanToFen(transAmount.toPlainString()));
                        busiOrgBill.setCreditNum(vo.getTransNum()+busiOrgBill.getCreditNum());
                    }
                    continue;
                }
                if(offBalanceFlag.equals(vo.getOffBalanceFlag())){//表内外关系相同
                    if("D".equals(vo.getDebtFlag())){
                        busiOrgBill.setDebtAmount(ToolUtil.yuanToFen(vo.getTransAmount().toPlainString()));
                        busiOrgBill.setDebtNum(vo.getTransNum());
                    } else {
                        busiOrgBill.setCreditAmount(ToolUtil.yuanToFen(vo.getTransAmount().toPlainString()));
                        busiOrgBill.setCreditNum(vo.getTransNum());
                    }
                    continue;
                }
            }
            busiOrgBill = new BusiOrgBillModel();
            modelList.add(busiOrgBill);
            busiOrgBill.setOrgCode(vo.getAcctOrg());
            busiOrgBill.setOrgName(vo.getOrgName());
            busiOrgBill.setItemCode(vo.getItemCtrl());
            busiOrgBill.setFlag(vo.getOffBalanceFlag());
            busiOrgBill.setAcctDate(acctDate);
            busiOrgBill.setBusiDate(acctDate);
            busiOrgBill.setCreateDate(new Date());
            if("D".equals(vo.getDebtFlag())){
                busiOrgBill.setDebtAmount(ToolUtil.yuanToFen(vo.getTransAmount().toPlainString()));
                busiOrgBill.setDebtNum(vo.getTransNum());
                busiOrgBill.setCreditAmount("0");
                busiOrgBill.setCreditNum(0);
            } else {
                busiOrgBill.setCreditAmount(ToolUtil.yuanToFen(vo.getTransAmount().toPlainString()));
                busiOrgBill.setCreditNum(vo.getTransNum());
                busiOrgBill.setDebtAmount("0");
                busiOrgBill.setDebtNum(0);
            }
            debtFlag = vo.getDebtFlag();
            offBalanceFlag = vo.getOffBalanceFlag();
            transAmount = vo.getTransAmount();
            uid = vo.getAcctOrg()+vo.getItemCtrl();
        }
        return modelList;
    }
}
