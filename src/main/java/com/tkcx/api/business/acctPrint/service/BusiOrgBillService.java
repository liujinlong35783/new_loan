package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import javafx.scene.control.Pagination;
import com.tkcx.api.business.acctPrint.dao.BusiOrgBillDao;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;

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
public class BusiOrgBillService extends CommonService<BusiOrgBillDao,BusiOrgBillModel> {

    @Resource
    private BusiOrgBillDao busiOrgBillDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(BusiOrgBillModel entity) {
        return busiOrgBillDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return busiOrgBillDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(BusiOrgBillModel entity) {
        return this.updateById(entity);
    }

    
    public BusiOrgBillModel selectById(Integer id) {
        return busiOrgBillDao.selectById(id);
    }

    
    public BusiOrgBillModel selectOne(BusiOrgBillModel entity) {
        QueryWrapper<BusiOrgBillModel> queryWrapper = new QueryWrapper<>(entity);
        return busiOrgBillDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(BusiOrgBillModel model) {
        return busiOrgBillDao.selectModelCount(model);
    }

    
    public List<BusiOrgBillModel> selectList(BusiOrgBillModel model) {
        return busiOrgBillDao.selectModelList(model);
    }

}
