package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkcx.api.business.wdData.dao.AcctDataDao;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:33
 */
@Service
public class AcctDataService extends CommonService<AcctDataDao,AcctDataModel> {

    @Resource
    private AcctDataDao acctDataDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctDataModel entity) {
        return acctDataDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctDataDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctDataModel entity) {
        return this.updateById(entity);
    }

    public AcctDataModel selectById(Integer id) {
        return acctDataDao.selectById(id);
    }

    public AcctDataModel selectOne(AcctDataModel entity) {
        QueryWrapper<AcctDataModel> queryWrapper = new QueryWrapper<>(entity);
        return acctDataDao.selectOne(queryWrapper);
    }

    public Integer selectCount(Date preDate, Date curDate, String transSeqNo) {
        return acctDataDao.selectModelCount(preDate, curDate, transSeqNo);
    }

    public List<AcctDataModel> selectList(Date startDate, Date endDate) {
        return acctDataDao.selectModelList(startDate, endDate);
    }

    public List<AcctDataModel> selectModelPage(Page<AcctDataModel> page, Date startDate, Date endDate) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        Page<AcctDataModel> result = new Page<>(page.getCurrent(), page.getSize());
        IPage<AcctDataModel> acctDataModelIPage = acctDataDao.selectListByPage(page, startDate, endDate);


//        Page<Student> page = new Page<>(pageNum.longValue(), pageSize.longValue());
//        IPage<Student> iPage = settlementBankMapper.selectPage(page, queryWrapper);
//        return iPage.getRecords();

        return acctDataModelIPage.getRecords();
    }
}
