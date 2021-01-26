package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.LoanDetailBillDao;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;

import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoanDetailBillService extends CommonService<LoanDetailBillDao,LoanDetailBillModel> {

    @Resource
    private LoanDetailBillDao loanDetailBillDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanDetailBillModel entity) {
        return loanDetailBillDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanDetailBillDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanDetailBillModel entity) {
        return this.updateById(entity);
    }

    
    public LoanDetailBillModel selectById(Integer id) {
        return loanDetailBillDao.selectById(id);
    }

    
    public LoanDetailBillModel selectOne(LoanDetailBillModel entity) {
        QueryWrapper<LoanDetailBillModel> queryWrapper = new QueryWrapper<>(entity);
        return loanDetailBillDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanDetailBillModel model) {
        return loanDetailBillDao.selectModelCount(model);
    }

    
    public List<LoanDetailBillModel> selectList(LoanDetailBillModel model) {
        return loanDetailBillDao.selectModelList(model);
    }
}
