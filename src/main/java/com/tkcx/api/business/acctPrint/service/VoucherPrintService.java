package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.VoucherPrintDao;
import com.tkcx.api.business.acctPrint.model.VoucherPrintModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-10-31 11:50
 */
@Service
public class VoucherPrintService extends CommonService<VoucherPrintDao,VoucherPrintModel> {

    @Resource
    private VoucherPrintDao voucherPrintDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(VoucherPrintModel entity) {
        return voucherPrintDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return voucherPrintDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public VoucherPrintModel selectById(Integer id) {
        return voucherPrintDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public VoucherPrintModel selectOne(VoucherPrintModel entity) {
        QueryWrapper<VoucherPrintModel> queryWrapper = new QueryWrapper<>(entity);
        return voucherPrintDao.selectOne(queryWrapper );
    }

    public Integer selectCount(VoucherPrintModel model) {
        return voucherPrintDao.selectModelCount(model);
    }

    public List<VoucherPrintModel> selectList(VoucherPrintModel model) {
        return voucherPrintDao.selectModelList(model);
    }
    /**
     * 根据流水号进行批量更新或插入
     * @param model
     * @return
     */
    public Integer saveOrUpdateAcctLogBySerialNo(VoucherPrintModel model){
        VoucherPrintModel oldModel = voucherPrintDao.getVoucherPrintByBusiTypeAndSerialNo(model.getBusiType(), model.getSerialNo());
        if (model != null && oldModel != null) {//存在
            oldModel.setUpdateDate(new Date());//更新
            oldModel.setQueryNo(model.getQueryNo());
            voucherPrintDao.updateById(oldModel);
        }else if(model != null ){//不存在
            voucherPrintDao.insert(model);
        }
        return 1;
    }

    /**
     * 根据查询批次号更新打印次数
     * @param queryNo
     * @return
     */
    public Integer updatePrintCountByQueryNo(String queryNo){
            return voucherPrintDao.updatePrintCountByQueryNo(queryNo);
    }
}
