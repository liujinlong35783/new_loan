package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.CardiiDao;
import com.tkcx.api.business.wdData.model.CardiiModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:36
 */
@Service
public class CardiiService extends CommonService<CardiiDao,CardiiModel> {

    @Resource
    private CardiiDao cardiiDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(CardiiModel entity) {
        return cardiiDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return cardiiDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(CardiiModel entity) {
        return this.updateById(entity);
    }

    public CardiiModel selectById(Integer id) {
        return cardiiDao.selectById(id);
    }

    public CardiiModel selectOne(CardiiModel entity) {
        QueryWrapper<CardiiModel> queryWrapper = new QueryWrapper<>(entity);
        return cardiiDao.selectOne(queryWrapper);
    }

    public Integer selectCount(CardiiModel model) {
        return cardiiDao.selectModelCount(model);
    }

    public List<CardiiModel> selectList(CardiiModel model) {
        return cardiiDao.selectModelList(model);
    }

    public CardiiModel getCardiiByCardiiIdnum(String cardiiIdnum){
        return cardiiDao.getCardiiByCardiiIdnum(cardiiIdnum);
    }
}
