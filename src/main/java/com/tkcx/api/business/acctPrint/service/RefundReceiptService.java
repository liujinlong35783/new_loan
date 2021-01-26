package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.RefundReceiptDao;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;

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
public class RefundReceiptService extends CommonService<RefundReceiptDao,RefundReceiptModel> {

    @Resource
    private RefundReceiptDao refundReceiptDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RefundReceiptModel entity) {
        return refundReceiptDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return refundReceiptDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RefundReceiptModel entity) {
        return this.updateById(entity);
    }

    
    public RefundReceiptModel selectById(Integer id) {
        return refundReceiptDao.selectById(id);
    }

    
    public RefundReceiptModel selectOne(RefundReceiptModel entity) {
        QueryWrapper<RefundReceiptModel> queryWrapper = new QueryWrapper<>(entity);
        return refundReceiptDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(RefundReceiptModel model) {
        return refundReceiptDao.selectModelCount(model);
    }

    
    public List<RefundReceiptModel> selectList(RefundReceiptModel model) {
        return refundReceiptDao.selectModelList(model);
    }

}
