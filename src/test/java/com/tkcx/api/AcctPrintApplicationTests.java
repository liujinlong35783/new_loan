package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dcfs.esb.ftp.utils.ThreadSleepUtil;
import com.tkcx.api.business.acctPrint.dao.*;
import com.tkcx.api.business.acctPrint.html2pdf.BusiHtmlToPdf;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.business.hjtemp.Handle.HandleService;
import com.tkcx.api.business.hjtemp.utils.GenerateTxtFileUtil;
import com.tkcx.api.common.BusiCommonService;
import com.tkcx.api.common.RealTimeQueryService;
import com.tkcx.api.service.BankApiService;
import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.base.AppHeadVo;
import com.tkcx.api.vo.base.SysHeadVo;
import com.tkcx.api.vo.query.BusiOrgQuery;
import com.tkcx.api.vo.query.LoanReceiptQuery;
import com.tkcx.api.vo.query.TimeSegment;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AcctPrintApplicationTests {

    @Autowired
    private HandleService handleService;
    @Autowired
    private BusiCommonService busiCommonService;

    @Autowired
    private RealTimeQueryService realTimeQueryService;

    @Resource
    private RefundReceiptDao refundReceiptDao;

    @Resource
    private LoanReceiptDao loanReceiptDao;

    @Resource
    private BusiOrgBillDao busiOrgBillDao;

    @Resource
    private BusiOrgSeqDao busiOrgSeqDao;

    @Resource
    private LoanAccBillDao loanAccBillDao;

    @Resource
    private AccountVoucherDao accountVoucherDao;

    @Resource
    private LoanAdjustDao loanAdjustDao;

    @Resource
    private InterestBillDao interestBillDao;

    @Resource
    private LoanDetailBillDao loanDetailBillDao;


    @Resource
    private BankApiService bankApiService;

    private Date selectDate = DateUtil.parseDate("2033-04-04");

    @Test
    public void contextLoads() {
        //System.out.println(acctDataJobHandler);
        //handleService.startHandle(null, null, null, "D:\\\\spring-tool-work\\\\acct_print\\\\hjFile\\\\t_act_pub_org_20500101.txt");
        //handleService.startHandle("D:\\spring-tool-work\\acct_print\\hjFile\\t_act_brch_day_tot_20500101.txt","D:\\spring-tool-work\\acct_print\\hjFile\\t_act_busi_code_map_20500101.txt", "D:\\spring-tool-work\\acct_print\\hjFile\\t_act_one_detail_20500101.txt");

        TimeSegment segment = new TimeSegment();

        /*RefundReceiptQuery query = new RefundReceiptQuery();
        query.setLoanAccount("27018888011304130000087714");
        query.setRefundName("王绍华");
        query.setPayAccount("6225066000000140572");
        query.setTimeSegment(segment);
        List<RefundReceiptModel> list = refundReceiptDao.selectListByQuery(query);*/

        LoanReceiptQuery query = new LoanReceiptQuery();
        query.setBorrowerIdnum("610526199109140011");
        query.setContractNo("201910301548594002346");
        /*query.setBorrowerIdnum("");
        query.setTimeSegment(segment);*/
        // System.out.println(loanReceiptDao.selectListByQuery(query));

        /*BusiOrgQuery query = new BusiOrgQuery();
        query.setOrgCode("27010122");
        query.setAcctDate("2034-02-01");
        System.out.println(busiOrgBillDao.selectListByQuery(query).size());
        System.out.println(busiOrgSeqDao.selectListByQuery(query).size());*/

        /*LoanBillAndAdjustQuery query = new LoanBillAndAdjustQuery();
        query.setLoanAccount("4324324");
        query.setLoanName("3455432");
        query.setItem("11304");
        query.setOrgCode("27018888");
        query.setTimeSegment(segment);

        System.out.println(loanAccBillDao.selectListByQuery(query));
        System.out.println(loanAdjustDao.selectListByQuery(query));*/

        //System.out.println(busiCommonService.existHjData(DateUtil.parse("2034-11-14")));


        /*AcctVoucherQuery query = new AcctVoucherQuery("");
        query.setOrgCode("28018888");
        query.setBusiType(1);
        query.setSerialNo("789");
        query.setTimeSegment(segment);
        System.out.println(accountVoucherDao.selectListByQuery(query));*/

        /*InterestBillQuery query = new InterestBillQuery("");
        query.setLoanName("ghj");
        query.setLoanAccount("4324");
        query.setOrgCode("28018888");
        query.setPayoffFlag(2);
        query.setTimeSegment(segment);

        System.out.println(interestBillDao.selectListByQuery(query));*/

        Predicate<Integer> predicate = i -> i > 5;
        predicate.test(7);

        QueryWrapper<LoanReceiptModel> queryWrapper = Wrappers.query();
        queryWrapper.select(LoanReceiptModel.class, info -> info.getProperty().startsWith(""));
    }

    // @Test
    public void testHtmlToPdf() {
        //AccountVoucherModel acctVoucher = accountVoucherDao.selectById(12614);
        /*InterestBillQuery query = new InterestBillQuery();
        query.setLoanAccount("27018888011304130000098819");
        List<InterestBillModel> billModelList = interestBillDao.selectListByQuery(query);*/
        List<IAcctPrintCommon> listData = new ArrayList<>();
        // listData.addAll(billModelList);

        /*LoanBillAndAdjustQuery query = new LoanBillAndAdjustQuery();
        query.setLoanName("王绍华");
        listData.addAll(loanAdjustDao.selectListByQuery(query));*/
        BusiOrgQuery query = new BusiOrgQuery();
        query.setOrgCode("27018888");
        query.setAcctDate("2035-02-16");
        // listData.addAll(busiOrgSeqDao.selectListByQuery(query));
        listData.addAll(busiOrgBillDao.selectListByQuery(query));

        ZhqdQueryReqVo queryReq = new ZhqdQueryReqVo();
        queryReq.setInterfaceIden(3);
        queryReq.setQueryContent("");
        queryReq.setOrgName("陕西秦农农村商业银行股份有限公司高陵支行营业室 ");

        AppHeadVo appHeadVo = new AppHeadVo();
        appHeadVo.setOrgId("27018888");
        appHeadVo.setTxnTlrId("1111111");
        queryReq.setAppHeadVo(appHeadVo);

        SysHeadVo sysHeadVo = new SysHeadVo();
        sysHeadVo.setAcgDt("2035-02-16");
        queryReq.setSysHeadVo(sysHeadVo);

        BusiHtmlToPdf.toPdf(listData, queryReq, "D:\\temp\\pdf\\" + System.currentTimeMillis() + ".pdf");
    }


    @Test
    public void testMakeTxtFile() {
        List<IAcctPrintCommon> listData = new ArrayList<>();

        BusiOrgQuery query = new BusiOrgQuery();
        query.setOrgCode("27010113");
        query.setAcctDate("2035-02-16");
        // listData.addAll(busiOrgSeqDao.selectListByQuery(query));
        // listData.addAll(busiOrgBillDao.selectListByQuery(query));

        /*LoanBillAndAdjustQuery query = new LoanBillAndAdjustQuery();
        query.setLoanName("王绍华");
        // listData.addAll(loanAccBillDao.selectListByQuery(query));
        listData.addAll(loanDetailBillDao.selectListByQuery(query));*/

        /*InterestBillQuery query = new InterestBillQuery();
        query.setLoanName("王绍华");
        query.setLoanAccount("27018888011304130000098819");
        listData.addAll(interestBillDao.selectListByQuery(query));*/

        /*LoanBillAndAdjustQuery query = new LoanBillAndAdjustQuery();
        query.setLoanName("王绍华");*/
        // listData.addAll(loanAdjustDao.selectListByQuery(query));

        ZhqdQueryReqVo queryReq = new ZhqdQueryReqVo();
        queryReq.setInterfaceIden(3);
        queryReq.setQueryContent("");
        queryReq.setOrgName("陕西秦农农村商业银行股份有限公司高陵支行营业室");

        AppHeadVo appHeadVo = new AppHeadVo();
        appHeadVo.setOrgId("27011101");
        appHeadVo.setTxnTlrId("Q193");
        queryReq.setAppHeadVo(appHeadVo);

        SysHeadVo sysHeadVo = new SysHeadVo();
        sysHeadVo.setAcgDt("2056-09-22");
        queryReq.setSysHeadVo(sysHeadVo);

        GenerateTxtFileUtil.writeDataTxt(listData, queryReq, "D:\\temp\\txt\\BusiOrgBill" + System.currentTimeMillis() + ".txt");

        System.out.println("生成txt完成");

    }

    // @Test
    public void testRealTimeQueryService() {
        TimeSegment segment = new TimeSegment();

        /*RefundReceiptQuery query = new RefundReceiptQuery();
        query.setLoanAccount("27018888011304130000087714");
        query.setRefundName("王绍华");
        query.setPayAccount("6225066000000140572");
        query.setTimeSegment(segment);
        System.out.println(realTimeQueryService.queryRefundReceiptByRealTime(query));*/

        LoanReceiptQuery query = new LoanReceiptQuery();
        query.setBorrowerIdnum("610526199109140011");
        query.setContractNo("201910301548594002346");
        // query.setTimeSegment(segment);


        System.out.println(realTimeQueryService.queryLoanReceiptByRealTime(query));

        /*LoanBillAndAdjustQuery query = new LoanBillAndAdjustQuery();
        query.setTimeSegment(segment);
        query.setOrgCode("432432");
        query.setLoanName("432432");
        query.setLoanAccount("43243");
        System.out.println(realTimeQueryService.queryLoanDetailBillByRealTime(query));*/

        /*InterestBillQuery query = new InterestBillQuery();
        query.setTimeSegment(segment);
        query.setOrgCode("28018888");
        query.setLoanAccount("45678323");
        query.setLoanName("fdsfds");
        query.setBorrowerIdnum("32497384");
        query.setPayoffFlag(0);
        System.out.println(realTimeQueryService.queryInterestBillByRealTime(query));*/

    }

    // @Test
    public void testLoanReceiptThread() {
        selectDate = busiCommonService.getCoreSysDate();
        selectDate = DateUtil.parse("2055-06-23 ");
        Date startDate = DateUtil.parse("2034-01-01");
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);

        // 1. 借款借据回单
        LoanReceiptThread loanThead = new LoanReceiptThread();
        loanThead.setCurDate(selectDate);
        loanThead.setStartDate(startDate);
        exe.execute(new Thread(loanThead));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }

    // @Test
    public void testRefundReceiptThread(){
        selectDate = busiCommonService.getCoreSysDate();
        selectDate = DateUtil.parse("2055-06-23 ");
        Date startDate = DateUtil.parse("2034-01-01");
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);

        // 2. 还款回单
        RefundReceiptThread refundThead = new RefundReceiptThread();
        refundThead.setCurDate(selectDate);
        refundThead.setStartDate(startDate);
        exe.execute(new Thread(refundThead));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }

    //@Test
    public void testBusiOrgBillThread(){
        selectDate = busiCommonService.getCoreSysDate();
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);

        // 3. 网贷业务机构轧账单
        BusiOrgBillThread busiOrgBillThread = new BusiOrgBillThread();
        busiOrgBillThread.setCurDate(selectDate);
        exe.execute(new Thread(busiOrgBillThread));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }


    //@Test
    public void testBusiOrgSeqThread(){
        selectDate = busiCommonService.getCoreSysDate();
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);

        // 4. 网贷业务机构业务流水
        BusiOrgSeqThread busiOrgSeqThread = new BusiOrgSeqThread();
        busiOrgSeqThread.setCurDate(selectDate);
        exe.execute(new Thread(busiOrgSeqThread));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }
    // @Test
    public void testLoanAccBillThread(){
        // selectDate = busiCommonService.getCoreSysDate();
        Date selectDate = DateUtil.parseDate("2034-11-16");
        //Date startDate = DateUtil.parseDate("2034-10-14");
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);
        // 5. 贷款分户账
        LoanAccBillThread loanAccBillThread = new LoanAccBillThread();
        loanAccBillThread.setCurDate(selectDate);
        //loanAccBillThread.setStartDate(startDate);
        exe.execute(new Thread(loanAccBillThread));
        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }
    //@Test
    public void testLoanAdjustThread(){
        selectDate = busiCommonService.getCoreSysDate();
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);
        // 5. 贷款形态调整明细清单、贷款调整登记簿
        LoanAdjustThread loanAdjustThread = new LoanAdjustThread();
        loanAdjustThread.setCurDate(selectDate);
        exe.execute(new Thread(loanAdjustThread));
        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }

    //@Test
    public void testLoanDetailBillThread(){
        selectDate = busiCommonService.getCoreSysDate();
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);
        // 6. 贷款明细账
        LoanDetailBillThread loanDetailBillThread = new LoanDetailBillThread();

        loanDetailBillThread.setCurDate(selectDate);
        exe.execute(new Thread(loanDetailBillThread));
        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }

    // @Test
    public void testInterestBillThread() {
        selectDate = DateUtil.parseDate("2035-02-18");
        Date startDate = DateUtil.parseDate("2034-10-14");

        long days = DateUtil.betweenDay(startDate, selectDate, true);

        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(Integer.valueOf(String.valueOf(days)));

        for(int i = 0; i < days; i++) {
            // 7. 贷款利息登记簿
            InterestBillThread interestBillThread = new InterestBillThread();
            interestBillThread.setCurDate(selectDate);
            exe.execute(new Thread(interestBillThread));

            selectDate = DateUtil.offsetDay(selectDate, -1);

        }

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }


    //@Test
    public void testAcctVoucherThread() {
        selectDate = busiCommonService.getCoreSysDate();
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(1);

        // 8. 会计凭证(记账凭证/交易凭证)
        AcctVoucherThread acctVoucherThread = new AcctVoucherThread();
        acctVoucherThread.setCurDate(selectDate);
        exe.execute(new Thread(acctVoucherThread));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }
    //@Test
    public void testAcctDataJobHandler(){
        selectDate = busiCommonService.getCoreSysDate();
        Date startDate = DateUtil.parse("2034-10-15");
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(10);

        // 1. 借款借据回单
        LoanReceiptThread loanThead = new LoanReceiptThread();
        loanThead.setCurDate(selectDate);
        exe.execute(new Thread(loanThead));

        // 2. 还款回单
        RefundReceiptThread refundThead = new RefundReceiptThread();
        refundThead.setCurDate(selectDate);
        exe.execute(new Thread(refundThead));

        // 3. 网贷业务机构轧账单
        BusiOrgBillThread busiOrgBillThread = new BusiOrgBillThread();
        busiOrgBillThread.setCurDate(selectDate);
        exe.execute(new Thread(busiOrgBillThread));

        // 4. 网贷业务机构业务流水
        BusiOrgSeqThread busiOrgSeqThread = new BusiOrgSeqThread();
        busiOrgSeqThread.setCurDate(selectDate);
        exe.execute(new Thread(busiOrgSeqThread));

        // 5. 贷款分户账
        LoanAccBillThread loanAccBillThread = new LoanAccBillThread();
        loanAccBillThread.setCurDate(selectDate);
        exe.execute(new Thread(loanAccBillThread));

        // 6. 贷款明细账
        LoanDetailBillThread loanDetailBillThread = new LoanDetailBillThread();
        loanDetailBillThread.setCurDate(selectDate);
        exe.execute(new Thread(loanDetailBillThread));

        // 7. 贷款利息登记簿
        InterestBillThread interestBillThread = new  InterestBillThread();
        interestBillThread.setCurDate(selectDate);
        exe.execute(new Thread(interestBillThread));

        // 8. 会计凭证(记账凭证/交易凭证)
        AcctVoucherThread acctVoucherThread = new AcctVoucherThread();
        acctVoucherThread.setCurDate(selectDate);
        exe.execute(new Thread(acctVoucherThread));

        // 9. 贷款形态调整明细清单、贷款调整登记簿
        LoanAdjustThread loanAdjustThread = new LoanAdjustThread();
        loanAdjustThread.setCurDate(selectDate);
        exe.execute(new Thread(loanAdjustThread));

        // 关闭线程池
        exe.shutdown();

        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                Date endDate = new Date();
                System.out.println("定时任务耗时：" + DateUtil.formatBetween(startDate,endDate));
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
    }


    @Test
    public void testHjHotice() throws ApplicationException {

        String msg = "000016630030VQZlYUpkxjnij9o72ywnt/W5106100<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><SysHead><SvcCd>3002040002</SvcCd><SvcScn>03</SvcScn><SvcSplrTxnCd></SvcSplrTxnCd><CnsmrSysId>108400</CnsmrSysId><TxnDt>2020-12-17</TxnDt><TxnTm>224604</TxnTm><AcgDt>2020-12-17</AcgDt><CnsmrSeqNo>1084002020121610460407263058</CnsmrSeqNo><TxnChnlTp>01000000</TxnChnlTp><ChnlDtl>01000000</ChnlDtl><TxnTmlId></TxnTmlId><CnsmrSvrId>8.1.8.242</CnsmrSvrId><OrigCnsmrSeqNo>1084002020121610460407263058</OrigCnsmrSeqNo><OrigCnsmrId>108400</OrigCnsmrId><OrigTmlId></OrigTmlId><OrigCnsmrSvrId></OrigCnsmrSvrId><UsrLng>CHINESE</UsrLng><FileFlg></FileFlg></SysHead><AppHead><TxnTlrId>ACCT</TxnTlrId><OrgId>27013000</OrgId><TlrPwsd></TlrPwsd><TlrLvl></TlrLvl><TlrTp></TlrTp><AprvFlg></AprvFlg><AhrTlrInf type=\"array\"></AhrTlrInf><AprvTlrInf type=\"array\"></AprvTlrInf><AhrFlg></AhrFlg></AppHead><Body><TranOcrDt>22:46:04</TranOcrDt><Rmrk>数据抽取</Rmrk><DtlInfoAry type=\"array\"><Struct><MakeFileFlg>t_act_one_detail</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_one_detail_20201216.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_brch_day_tot</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_brch_day_tot_20201216.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_busi_code_map</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_busi_code_map_20201216.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_pub_org</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_pub_org_20201216.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct></DtlInfoAry></Body></Service>";
        bankApiService.hjHotice(msg);
    }
}
