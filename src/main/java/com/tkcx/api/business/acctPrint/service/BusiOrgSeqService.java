package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.BusiOrgSeqDao;
import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;

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
public class BusiOrgSeqService extends CommonService<BusiOrgSeqDao,BusiOrgSeqModel> {

    @Resource
    private BusiOrgSeqDao busiOrgSeqDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(BusiOrgSeqModel entity) {
        return busiOrgSeqDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return busiOrgSeqDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(BusiOrgSeqModel entity) {
        return this.updateById(entity);
    }

    
    public BusiOrgSeqModel selectById(Integer id) {
        return busiOrgSeqDao.selectById(id);
    }

    
    public BusiOrgSeqModel selectOne(BusiOrgSeqModel entity) {
        QueryWrapper<BusiOrgSeqModel> queryWrapper = new QueryWrapper<>(entity);
        return busiOrgSeqDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(BusiOrgSeqModel model) {
        return busiOrgSeqDao.selectModelCount(model);
    }

    
    public List<BusiOrgSeqModel> selectList(BusiOrgSeqModel model) {
        return busiOrgSeqDao.selectModelList(model);
    }

}
