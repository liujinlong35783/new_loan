package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.service.AcctDataService;
import com.tkcx.api.common.BusiCommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/9 17:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AcctDataServiceTest {

    @Autowired
    private AcctDataService acctDataService;

    @Autowired
    private BusiCommonService busiCommonService;


    @Test
    public void insert() {

        ArrayList<AcctDataModel> acctDatas = new ArrayList(10000);
        for (int i = 0; i < 10000; i++) {
            String message = "{\"bizTrackNo\":\"1135002021102118063339391815\"," +
                    "\"hostIp\":\"172.18.255.221\",\"reqTime\":1634811034865,\"servcId\":\"AS00100001\",\"servcScn\":\"01\"," +
                    "\"sysIndicator\":\"113500\",\"transBranch\":\"27018888\",\"transMedium\":\"01010500\",\"transSeqNo\":\"" +
                    "1135002021102118063339391815\"}}";
            AcctDataModel acctDataModel = new AcctDataModel();
            acctDataModel.setTransSeqNo("1135002021102118063339391815");

            acctDataModel.setCreateAt(DateUtil.parseDate("2084-01-26"));
            acctDataModel.setUpdateAt(DateUtil.parseDate("2084-01-27"));
            acctDataModel.setRetStatus("3");
            acctDataModel.setBizTrackNo("1135002021102118063339391815");
            acctDataModel.setAcgDt("2084-01-26");
            acctDataModel.setAssetItemNo("8138976824316444672");
            acctDataModel.setRepayPlanNo("RPM8138976979480526848-0000");
            acctDataModel.setScene("accrual_normal");
            acctDataModel.setNormalPrincipal(new BigDecimal(0));
            acctDataModel.setMessage(message);
            acctDataModel.setOrgid("27018888");
//            acctDataService.insert(acctDataModel);
            acctDatas.add(acctDataModel);
        }
        log.info("新增记录数：【{}】", acctDatas.size());
        acctDataService.saveBatch(acctDatas);
    }


    @Test
    public void testQuery() {

        Date curDate = busiCommonService.getCoreSysDate();
        if(curDate!=null){
            curDate = DateUtil.parse(DateUtil.formatDate(curDate),"yyyy-MM-dd");
        }


        Date preDate = DateUtil.parse(DateUtil.formatDate(DateUtil.offsetDay(curDate, -1)));
        log.info("查询条件，preDate：【{}】，curDate：【{}】", preDate, curDate);
        Integer count = acctDataService.selectCount(preDate, curDate);
        log.info("总记录数：{}",count);
    }


}
