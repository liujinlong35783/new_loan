package com.tkcx.api.business.acctPrint.service;

import com.tkcx.api.business.acctPrint.dao.AcctSettleDao;
import com.tkcx.api.business.acctPrint.model.AcctSettleModel;
import com.tkcx.api.common.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Service
public class AcctSettleService extends CommonService<AcctSettleDao,AcctSettleModel> {

    @Resource
    private AcctSettleDao acctSettleDao;

    public boolean updateDateInfo(Date sysDate, String status) {
        return acctSettleDao.updateDateInfo(sysDate, status);
    }


    public Date queryDate(){
        return acctSettleDao.selectModelInfo();
    }



}
