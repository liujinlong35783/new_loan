package com.tkcx.api;

import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
import com.tkcx.api.business.acctPrint.service.BusiOrgSeqService;
import com.tkcx.api.constant.EnumConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/11 9:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BusiOrgSeqTest {

    @Autowired
    public BusiOrgSeqService busiOrgSeqService;

    @Test
    public void testSaveBusSeq() {

        for(int j =0 ;j<11;j++){
            List<BusiOrgSeqModel> busiOrgSeqList = new ArrayList<>();
            for (int i=0 ; i<10 ; i++) {
                BusiOrgSeqModel busiOrgSeq = new BusiOrgSeqModel();
                busiOrgSeq.setOrgCode("test");
                busiOrgSeq.setOrgName("test");
                busiOrgSeq.setBusiDate(new Date());
                busiOrgSeq.setTransSeqNo("test");
                busiOrgSeq.setBizTrackNo("test");
                busiOrgSeq.setOperator("QNWD");
                // 查询网贷资产信息
                busiOrgSeq.setDebtNo("test");
                busiOrgSeq.setLoanAccount("test");

                busiOrgSeq.setItemCtrl("test");
                busiOrgSeq.setAbstracts("test");

                busiOrgSeq.setAmount("test");

                busiOrgSeq.setBusiType(EnumConstant.BUSI_TYPE_NOMAL);
                busiOrgSeq.setAcctDate(new Date());
                busiOrgSeq.setDebtFlag(1);

                busiOrgSeqList.add(busiOrgSeq);
            }
            // 保存业务机构业务流水
            if (!busiOrgSeqList.isEmpty()) {
                log.info("待入库的业务流水记录数：【{}】", busiOrgSeqList.size());
                busiOrgSeqService.saveBatch(busiOrgSeqList);
            }
            log.info("第{}次，入库成功记录数：{}", j, busiOrgSeqList.size());
        }


    }

}
