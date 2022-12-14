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
import java.text.SimpleDateFormat;
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

    public Integer selectCount(Date preDate, Date curDate) {
        return acctDataDao.selectModelCount(preDate, curDate);
    }

    public List<AcctDataModel> selectList(Date startDate, Date endDate) {
        return acctDataDao.selectModelList(startDate, endDate);
    }

    public String selectMessage(Integer id){
        return acctDataDao.selectAcctDataDetailMessage(id);
    }

    public List<AcctDataModel> selectModelPage(Page<AcctDataModel> page, Date startDate, Date endDate) {
        // ????????? count sql ??????????????? MP ?????????????????? SQL ??????????????????????????????????????? count ??????
        // page.setOptimizeCountSql(false);
        // ??? total ????????? 0 ???????????? setSearchCount(false) ???????????????????????? count ??????
        // ??????!! ???????????????????????????????????????????????????
        Page<AcctDataModel> result = new Page<>(page.getCurrent(), page.getSize());
        IPage<AcctDataModel> acctDataModelIPage = acctDataDao.selectListByPage(page, startDate, endDate);


//        Page<Student> page = new Page<>(pageNum.longValue(), pageSize.longValue());
//        IPage<Student> iPage = settlementBankMapper.selectPage(page, queryWrapper);
//        return iPage.getRecords();

        return acctDataModelIPage.getRecords();
    }

    public AcctDataModel selectDateByDate(String transSeqNo,Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String acctDate =sdf.format(date);
        return acctDataDao.selectDateByDate(transSeqNo,acctDate);
    }
}
