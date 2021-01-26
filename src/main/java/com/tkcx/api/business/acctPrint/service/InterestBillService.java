package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.InterestBillDao;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;

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
public class InterestBillService extends CommonService<InterestBillDao,InterestBillModel> {

    @Resource
    private InterestBillDao interestBillDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(InterestBillModel entity) {
        return interestBillDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return interestBillDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(InterestBillModel entity) {
        return this.updateById(entity);
    }

    
    public InterestBillModel selectById(Integer id) {
        return interestBillDao.selectById(id);
    }

    
    public InterestBillModel selectOne(InterestBillModel entity) {
        QueryWrapper<InterestBillModel> queryWrapper = new QueryWrapper<>(entity);
        return interestBillDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(InterestBillModel model) {
        return interestBillDao.selectModelCount(model);
    }

    
    public List<InterestBillModel> selectList(InterestBillModel model) {
        return interestBillDao.selectModelList(model);
    }

}
