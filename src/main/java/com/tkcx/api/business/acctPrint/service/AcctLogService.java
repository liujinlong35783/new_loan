package com.tkcx.api.business.acctPrint.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.AcctLogDao;
import com.tkcx.api.business.acctPrint.model.AcctLogModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * Service
 *
 * @author cuijh
 * @Date 2019-10-12 15:20
 */
@Service
public class AcctLogService extends CommonService<AcctLogDao,AcctLogModel> {

    @Resource
    private AcctLogDao acctLogDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctLogModel entity) {
        return acctLogDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctLogDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctLogModel entity) {
        return this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public AcctLogModel selectById(Integer id) {
        return acctLogDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public AcctLogModel selectOne(AcctLogModel entity) {
        QueryWrapper<AcctLogModel> queryWrapper = new QueryWrapper<>(entity);
        return acctLogDao.selectOne(queryWrapper );
    }

    public Integer selectCount(AcctLogModel model) {
        return acctLogDao.selectModelCount(model);
    }

    public List<AcctLogModel> selectList(AcctLogModel model) {
        return acctLogDao.selectModelList(model);
    }

    public Integer saveAcctLog(Integer logType, String serialNo, String content, String acctDate) {
        AcctLogModel acct = new AcctLogModel();
        acct.setLogType(logType);
        acct.setSerialNo(serialNo);
        acct.setContent(content);
        acct.setAcctDate(DateUtil.parse(acctDate));
        return acctLogDao.insert(acct);
    }
}
