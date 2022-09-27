package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.AccountVoucherLoanDao;
import com.tkcx.api.business.acctPrint.model.Loan.AccountVoucherLoanModel;
import com.tkcx.api.common.CommonService;
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
public class AccountVoucherLoanService extends CommonService<AccountVoucherLoanDao, AccountVoucherLoanModel> {

    @Resource
    public AccountVoucherLoanDao accountVoucherLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AccountVoucherLoanModel entity) {
        return accountVoucherLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return accountVoucherLoanDao.deleteById(id);
    }

    public AccountVoucherLoanModel selectById(Integer id) {
        return accountVoucherLoanDao.selectById(id);
    }

    public AccountVoucherLoanModel selectOne(AccountVoucherLoanModel entity) {
        QueryWrapper<AccountVoucherLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return accountVoucherLoanDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AccountVoucherLoanModel model) {
        return accountVoucherLoanDao.selectModelCount(model);
    }

    public List<AccountVoucherLoanModel> selectList(AccountVoucherLoanModel model) {
        return accountVoucherLoanDao.selectModelList(model);
    }

}
