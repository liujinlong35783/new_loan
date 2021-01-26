package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.AccountVoucherDao;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 20:51
 */
@Service
public class AccountVoucherService extends CommonService<AccountVoucherDao,AccountVoucherModel> {

    @Resource
    public AccountVoucherDao accountVoucherDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AccountVoucherModel entity) {
        return accountVoucherDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return accountVoucherDao.deleteById(id);
    }

    public AccountVoucherModel selectById(Integer id) {
        return accountVoucherDao.selectById(id);
    }

    public AccountVoucherModel selectOne(AccountVoucherModel entity) {
        QueryWrapper<AccountVoucherModel> queryWrapper = new QueryWrapper<>(entity);
        return accountVoucherDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AccountVoucherModel model) {
        return accountVoucherDao.selectModelCount(model);
    }

    public List<AccountVoucherModel> selectList(AccountVoucherModel model) {
        return accountVoucherDao.selectModelList(model);
    }

}
