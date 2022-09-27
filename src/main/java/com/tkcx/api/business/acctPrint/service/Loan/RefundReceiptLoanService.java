package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.RefundReceiptLoanDao;
import com.tkcx.api.business.acctPrint.dao.RefundReceiptDao;
import com.tkcx.api.business.acctPrint.model.Loan.RefundReceiptLoanModel;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;
import com.tkcx.api.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Service
public class RefundReceiptLoanService extends CommonService<RefundReceiptLoanDao, RefundReceiptLoanModel> {

    @Resource
    private RefundReceiptLoanDao refundReceiptLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RefundReceiptLoanModel entity) {
        return refundReceiptLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return refundReceiptLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RefundReceiptLoanModel entity) {
        return this.updateById(entity);
    }

    
    public RefundReceiptLoanModel selectById(Integer id) {
        return refundReceiptLoanDao.selectById(id);
    }

    
    public RefundReceiptLoanModel selectOne(RefundReceiptLoanModel entity) {
        QueryWrapper<RefundReceiptLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return refundReceiptLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(RefundReceiptLoanModel model) {
        return refundReceiptLoanDao.selectModelCount(model);
    }

    
    public List<RefundReceiptLoanModel> selectList(RefundReceiptLoanModel model) {
        return refundReceiptLoanDao.selectModelList(model);
    }
    public List<RefundReceiptLoanModel> selectListByDate(RefundReceiptLoanModel model) {
        return refundReceiptLoanDao.selectModelList(model);
    }

}
