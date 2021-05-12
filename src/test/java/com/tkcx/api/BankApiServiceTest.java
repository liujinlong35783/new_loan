package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagEnum;
import com.tkcx.api.service.BankApiService;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/3/30 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BankApiServiceTest {

    @Autowired
    private BankApiService bankApiService;

    @Autowired
    private HjFileInfoService hjFileInfoService;

    @Test
    public void testHjNotice() throws ApplicationException {

        String message = "000013190030DB+gscPjlTeu0IT7CtY/NIIr106100<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><SysHead><SvcCd>3002040002</SvcCd><SvcScn>03</SvcScn><SvcSplrTxnCd></SvcSplrTxnCd><CnsmrSysId>108400</CnsmrSysId><TxnDt>2018-03-22</TxnDt><TxnTm>155706</TxnTm><AcgDt>2018-03-22</AcgDt><CnsmrSeqNo>1084002021032903570605449097</CnsmrSeqNo><TxnChnlTp>01000000</TxnChnlTp><ChnlDtl>01000000</ChnlDtl><TxnTmlId></TxnTmlId><CnsmrSvrId>9.1.8.114</CnsmrSvrId><OrigCnsmrSeqNo>1084002021032903570605449097</OrigCnsmrSeqNo><OrigCnsmrId>108400</OrigCnsmrId><OrigTmlId></OrigTmlId><OrigCnsmrSvrId></OrigCnsmrSvrId><UsrLng>CHINESE</UsrLng><FileFlg></FileFlg></SysHead><AppHead><TxnTlrId>ACCT</TxnTlrId><OrgId>27013000</OrgId><TlrPwsd></TlrPwsd><TlrLvl></TlrLvl><TlrTp></TlrTp><AprvFlg></AprvFlg><AhrTlrInf type=\"array\"></AhrTlrInf><AprvTlrInf type=\"array\"></AprvTlrInf><AhrFlg></AhrFlg></AppHead><Body><TranOcrDt>15:57:06</TranOcrDt><Rmrk>数据抽取</Rmrk><DtlInfoAry type=\"array\"><Struct><MakeFileFlg>t_act_busi_code_map</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_busi_code_map_20180321.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_pub_org</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_pub_org_20180321.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct></DtlInfoAry></Body></Service>";
        bankApiService.hjNotice(message);
    }

    @Test
    public void testAddHJFile(){

        List<HjFileInfoModel> hjFileList = Lists.newArrayList();

        HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
        hjFileInfoModel.setFileName("test");
        hjFileInfoModel.setDeleteFlag(HjFileFlagEnum.NOT_DELETED);
        hjFileInfoModel.setFileTransCode("test");
        hjFileInfoModel.setFileType("test");
        // 文件日期
        hjFileInfoModel.setFileDate(DateUtil.parse("2021-04-09","yyyy-MM-dd"));
        hjFileInfoModel.setCreateDate(DateUtil.date());
        // 文件下载路径
        hjFileInfoModel.setFilePath("test");
        hjFileList.add(hjFileInfoModel);

        hjFileInfoService.saveBatch(hjFileList);
    }


    @Test
    public void testQueryHjFile(){
        HjFileInfoModel queryInfo = new HjFileInfoModel();
        queryInfo.setFileDate(DateUtil.parseDate("2077-10-25"));
        queryInfo.setDeleteFlag("0");
        List<HjFileInfoModel> hjData = hjFileInfoService.selectModelList(queryInfo);
    }

}
