package com.tkcx.api.business.hjtemp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.wdData.service.AcctDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/9 18:01
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AcctDetailTempServiceTest {

    @Autowired
    private AcctDataService acctDataService;


    @Autowired
    private AcctDetailTempService acctDetailTempService;

    @Test
    public void insertList() {


        ArrayList<AcctDetailTempModel> details = new ArrayList(10000);
        String transSeqNo = "1135002021102118063339391815";
        for(int i=0;i<10000;i++) {
            AcctDetailTempModel acctDetailTempModel = new AcctDetailTempModel();
            acctDetailTempModel.setChannelSeq(transSeqNo);
            acctDetailTempModel.setChannelDate(DateUtil.parseDate("2084-01-26"));
            acctDetailTempModel.setChannelWay("01010500");
            acctDetailTempModel.setAcctDate(DateUtil.parseDate("2084-01-26"));
            acctDetailTempModel.setAcctSeq("1084002033040123051001009600");
            acctDetailTempModel.setServiceCode("WDJT2001");
            acctDetailTempModel.setServiceName("网贷计提");
            acctDetailTempModel.setSerialNo(1L);
            acctDetailTempModel.setAcctOrg("27011101");
            acctDetailTempModel.setItemCtrl("1132A2");
            acctDetailTempModel.setItemCode("11320112");
            acctDetailTempModel.setAccountCode("270111011132A20000100001");
            acctDetailTempModel.setAccountName("非农贷款应计利息");
            acctDetailTempModel.setCurrency("CNY");
            acctDetailTempModel.setTransferFlag("2");
            acctDetailTempModel.setBankNote("1");
            acctDetailTempModel.setDebtFlag("D");
            acctDetailTempModel.setAcctType("1");
            acctDetailTempModel.setTransAmount(new BigDecimal(2.22));
            acctDetailTempModel.setOffBalanceFlag("0");
            acctDetailTempModel.setCriticizeFlag("0");
            acctDetailTempModel.setStatus("02");
            acctDetailTempModel.setCreateDate(DateUtil.date());
            acctDetailTempModel.setIdentifier("002");
            details.add(acctDetailTempModel);
        }
        log.info("新增记录数：【{}】", details.size());
        acctDetailTempService.saveBatch(details);
    }

    @Test
    public void updateList() {


        List<AcctDetailTempModel> acctDetailTempModels = acctDetailTempService.selectList(new AcctDetailTempModel());


        for (int j=0;j<20;j++) {
            AcctDetailTempModel acctDetailTempModel = acctDetailTempModels.get(j);
            Integer count = acctDetailTempService.selectCount(acctDetailTempModel);
            for(int i=0; i<count; i++) {
                acctDetailTempModel.setChannelSeq("1135002021102118063339391788");
                acctDetailTempService.updateById(acctDetailTempModel);
            }
        }
    }

}

