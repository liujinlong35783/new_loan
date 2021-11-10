package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.service.AcctDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void rrt() {

        Date curDate = DateUtil.parseDate("2084-01-27");
        Date startDate = DateUtil.parse(DateUtil.formatDate(DateUtil.offsetDay(curDate, -1)));

        List<AcctDataModel> acctDataModels = acctDataService.selectList(startDate, curDate);

        for (AcctDataModel acctDataModel : acctDataModels) {
            String transSeqNo = acctDataModel.getTransSeqNo();
            Integer count = acctDataService.selectCount(startDate, curDate, transSeqNo);
            log.info("transSeqNo:【{}】,记录数：【{}】", transSeqNo, count);

            if (count > 30000) {
                continue;
            }
            ArrayList<AcctDataModel> acctDatas = new ArrayList<>();
            for (int i = 0; i < 30; i++) {

                String bizTrackNo = acctDataModel.getBizTrackNo();
                String hostIp = "172.18.255.221";
                String reqTime = "1634811034865";
                String servcId = "AS00100001";
                String servcScn = "01";
                String sysIndicator = "113500";
                String transBranch = "27018888";
                String transMedium = "01010500";

                StringBuffer sb = new StringBuffer();
                sb.append("{\"bizTrackNo\": " + bizTrackNo + ",");
                sb.append("{\"hostIp\": " + hostIp + ",");
                sb.append("{\"reqTime\": " + reqTime + ",");
                sb.append("{\"servcId\": " + servcId + ",");
                sb.append("{\"servcScn\": " + servcScn + ",");
                sb.append("{\"sysIndicator\": " + sysIndicator + ",");
                sb.append("{\"transBranch\": " + transBranch + ",");
                sb.append("{\"transMedium\": " + transMedium + ",");
                sb.append("{\"transSeqNo\": " + transSeqNo + ",");
                sb.append("}");
                acctDataModel.setCreateAt(DateUtil.parseDate("2084-01-26"));
                acctDataModel.setUpdateAt(DateUtil.parseDate("2084-01-27"));
                acctDataModel.setMessage(sb.toString());
                acctDatas.add(acctDataModel);
            }
            log.info("新增记录数：【{}】", acctDatas.size());
            acctDataService.saveBatch(acctDatas);
        }
    }

    @Test
    public void insert() {

        ArrayList<AcctDataModel> acctDatas = new ArrayList(10000);
        for (int i = 0; i < 10000; i++) {
            String message = "{\"bizTrackNo\":\"1135002021102118063339391805\"," +
                    "\"hostIp\":\"172.18.255.221\",\"reqTime\":1634811034865,\"servcId\":\"AS00100001\",\"servcScn\":\"01\"," +
                    "\"sysIndicator\":\"113500\",\"transBranch\":\"27018888\",\"transMedium\":\"01010500\",\"transSeqNo\":\"" +
                    "1135002021102118063339391805\"}}";
            AcctDataModel acctDataModel = new AcctDataModel();
            acctDataModel.setTransSeqNo("1135002021102118063339391805");

            acctDataModel.setCreateAt(DateUtil.parseDate("2084-01-26"));
            acctDataModel.setUpdateAt(DateUtil.parseDate("2084-01-27"));
            acctDataModel.setRetStatus("3");
            acctDataModel.setBizTrackNo("1135002021102118063339391805");
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




}
